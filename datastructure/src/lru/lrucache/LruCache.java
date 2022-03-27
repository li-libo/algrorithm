package lru.lrucache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LruCache缓存自实现
 * @author lilibo
 * @create 2021-09-03 4:01 PM
 */
public class LruCache {

    private final Map<Object, Node> nodeMap = new HashMap<>();

    private final DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

    private final ReentrantLock reentrantLock = new ReentrantLock();

    private final int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
    }

    public Object get(Object key) {
        try {
            reentrantLock.lock();
            Node node = nodeMap.get(key);
            if (node == null) {
                return null;
            }
            doubleLinkedList.remove(node);
            doubleLinkedList.putToHead(node);
            return node.getValue();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void put(Object key, Object value) {
        try {
            reentrantLock.lock();
            Node newNode = new Node(key, value);
            if (nodeMap.get(key) != null) {
                doubleLinkedList.remove(nodeMap.get(key));
                doubleLinkedList.putToHead(newNode);
                nodeMap.put(key, newNode);
            } else {
                if (doubleLinkedList.size() == capacity) {
                    Object tailKey = doubleLinkedList.removeTail();
                    nodeMap.remove(tailKey);
                }
                doubleLinkedList.putToHead(newNode);
                nodeMap.put(key, newNode);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public void print() {
        doubleLinkedList.print();
    }

}
