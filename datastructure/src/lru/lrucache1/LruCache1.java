package lru.lrucache1;

import lru.lrucache.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用jdk原有集合实现Lru缓存
 *
 * @author lilibo
 * @create 2021-09-04 5:05 PM
 */
public class LruCache1 {

    /**
     * 缓存链表
     */
    private final LinkedList<Node> linkedList = new LinkedList<>();

    /**
     * key-node映射Map
     */
    private final Map<Object, Node> nodeMap = new HashMap<>();

    /**
     * LruCache容量
     */
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();

    public LruCache1(int capacity) {
        this.capacity = capacity;
    }

    public Object get(Object key) {
        try {
            lock.lock();
            Node target = nodeMap.get(key);
            if (nodeMap.get(key) == null) {
                return null;
            }
            linkedList.remove(target);
            linkedList.addFirst(target);
            return target.getValue();
        } finally {
            lock.unlock();
        }
    }

    public void put(Object key, Object value) {
        try {
            lock.lock();
            Node newNode = new Node(key, value);
            if (nodeMap.get(key) != null) {
                // 删除旧值
                linkedList.remove(nodeMap.get(key));
                linkedList.addFirst(newNode);
                nodeMap.put(key, newNode);
            } else {
                if (linkedList.size() == capacity) {
                    Node oldTail = linkedList.removeLast(); // 移除末尾元素
                    nodeMap.remove(oldTail.getKey()); // 去除末尾元素映射关系
                }
                linkedList.addFirst(newNode);
                nodeMap.put(key, newNode);
            }
        } finally {
            lock.unlock();
        }
    }

    public void printElements() {
        try {
            lock.lock();
            System.out.println("size = " + linkedList.size() + ", " + Arrays.toString(linkedList.toArray()));
        } finally {
            lock.unlock();
        }
    }

}
