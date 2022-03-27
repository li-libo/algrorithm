package consistenthash;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lilibo
 * @create 2021-09-03 5:38 PM
 */
public class ConsistentHashDemo {

    public static void main(String[] args) throws InterruptedException {
        Set<String> addressSet = new HashSet<>();
        for(int port = 0; port < 9; port++) {
            String ip = "192.9.200." + port;
            addressSet.add(ip);
        }
        ConsistentHash consistentHash = new ConsistentHash(addressSet, 10);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int sumDataSize = 1024 * 1024;
        CountDownLatch countDownLatch = new CountDownLatch(sumDataSize);
        for(int i = 0; i <sumDataSize; i++) {
            executorService.execute(()->{
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

    private final SortedMap<Integer, String> soltMap = new ConcurrentSkipListMap<>();

    private final Map<String, Map<String, Object>> dataMap = new ConcurrentHashMap<>();

    private final Set<String> addressSet = new HashSet<>();

    private final int numOfVirtualNodes;

    public ConsistentHash(Collection<String> addresses, int numOfVirtualNodes) {
        this.numOfVirtualNodes = numOfVirtualNodes;
        addressSet.addAll(addresses);
        for (String address : addressSet) {
            for (int i = 0; i < numOfVirtualNodes; i++) {
                String nodeId = address + "-" + i;
                int soltHash = getHash(nodeId);
                soltMap.put(soltHash, nodeId);
            }
            dataMap.put(address, new ConcurrentHashMap<>());
        }
    }

    public void put(String key, Object value){
        int hash = getHash(key);
        SortedMap<Integer, String> tailMap = soltMap.tailMap(hash);
        if(tailMap.isEmpty()){ // 如果tailMap为空,从soltMap的头solt开始
            tailMap = soltMap;
        }
        String targetNodeId = tailMap.get(tailMap.firstKey());
        String targetIp = targetNodeId.substring(0, targetNodeId.indexOf("-"));
        dataMap.get(targetIp).put(key, value);
    }

    public void printServerNodeDataSize() {
        Set<Map.Entry<String, Map<String, Object>>> entries = dataMap.entrySet();
        for(Map.Entry<String, Map<String, Object>> entry : entries) {
            String address = entry.getKey();
            int size = entry.getValue().size();
            System.out.println("address = " + address + "节点的数据量大小为: " + size);
        }
    }

    public void addNode(String address) {
        boolean cotainFlag = addressSet.add(address);
        if(!cotainFlag) {
            return;
        }
        for (int i = 0; i < numOfVirtualNodes; i++) {
            String nodeId = address + "-" + i;
            int soltHash = getHash(nodeId);
            soltMap.put(soltHash, nodeId);
        }
        dataMap.put(address, new ConcurrentHashMap<>());
    }

    public void delNode(String address) {
        for (int i = 0; i < numOfVirtualNodes; i++) {
            String nodeId = address + "-" + i;
            int soltHash = getHash(nodeId);
            soltMap.remove(soltHash);
        }
        dataMap.remove(address);
    }

    //使用FNV1_32_HASH算法计算服务器的Hash值
    public static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

}