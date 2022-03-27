package example;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分布式哈希示例
 * @author lilibo
 * @create 2022-02-07 2:48 PM
 */
public class ConsistentHashDemo {

    public static void main(String[] args) throws InterruptedException {
        Set<String> addressSet = new HashSet<>();
        for(int port = 0; port < 9; port++) {
            String ip = "192.9.200." + port;
            addressSet.add(ip);
        }
        ConsistentHash consistentHash = new ConsistentHash(addressSet, 10);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);
        int sumDataSize = 1024 * 1024;
        CountDownLatch countDownLatch = new CountDownLatch(sumDataSize);
        for(int i = 0; i< sumDataSize; i++) {
            executorService.execute(()-> {
                String uuid = UUID.randomUUID().toString();
                consistentHash.put(uuid, uuid);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        consistentHash.printServerNodeDataSize();
        executorService.shutdown();
    }
}

class ConsistentHash {

    private final Set<String> addressSet;

    private int numOfVirtualNodes;

    private final SortedMap<Integer, String> slotMap;

    private final Map<String, Map<String, Object>> dataMap;

    public ConsistentHash(Set<String> addressSet, int numOfVirtualNodes) {
        this.addressSet = addressSet;
        this.numOfVirtualNodes = numOfVirtualNodes;
        slotMap = new ConcurrentSkipListMap<>();
        dataMap = new ConcurrentHashMap<>();
        for(String address : addressSet) {
            for(int i = 0; i < numOfVirtualNodes; i++) {
                String name = address + "#" + i;
                int slot = getSlot(name);
                slotMap.put(slot, name);
            }
            dataMap.put(address, new ConcurrentHashMap<>());
        }
    }

    public void put(String key, String value) {
        int slot = getSlot(key);
        SortedMap<Integer, String> tailMap = slotMap.tailMap(slot);
        if(tailMap == null) {
            tailMap = slotMap;
        }
        int startSlot = tailMap.firstKey();
        String startName = tailMap.get(startSlot);
        String address = startName.split("#")[0];
        dataMap.get(address).put(key, value);
    }

    public Object get(String key) {
        int slot = getSlot(key);
        String address = slotMap.get(slot).split("#")[0];
        return dataMap.get(address).get(key);
    }

    public void printServerNodeDataSize() {
        dataMap.forEach((address, map) -> {
            System.out.println("address = " + address + "节点对应size = " + map.size());
        });
    }

    private int getSlot(Object name) {
        int slot = Math.abs(name.hashCode()) % (addressSet.size() * numOfVirtualNodes);
        return slot;
    }
}