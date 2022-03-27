package example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * @author lilibo
 * @create 2022-02-06 12:44 AM
 */
public class LruCache2 {

    private final Map<Object, Node> nodeMap = new ConcurrentHashMap<>();

    private final LinkedList<Node> linkedList = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();

    private final int capacity;

    public static void main(String[] args) {
        int testNum = 20;
        LruCache2 lruCache = new LruCache2(10);
        Stream.iterate(1, count -> count + 1).limit(testNum).forEach(count -> {
            Object key = "key-" + count;
            Object value = "value-" + count;
            lruCache.put(key, value);
        });
        System.out.println("---------");
        System.out.println("linkedList = " + lruCache.getLinkedList());
        System.out.println("---------");
        Stream.iterate(11, count -> count + 1).limit(testNum).forEach(count -> {
            Object key = "key-" + count;
            System.out.println("key = " + key + "对应的值: " + lruCache.get(key));
        });
        System.out.println("---------");
        System.out.println("linkedList = " + lruCache.getLinkedList());
    }

    public LinkedList<Node> getLinkedList() {
        return linkedList;
    }

    public LruCache2(int capacity) {
        this.capacity = capacity;
    }

    public void put(Object key, Object value) {
        try {
            lock.lock();
            Node newNode = new Node(key, value);
            if(nodeMap.containsKey(key)) {
                linkedList.remove(nodeMap.get(key));
                linkedList.addFirst(newNode);
                nodeMap.put(key, newNode);
            }else {
                if(linkedList.size() >= capacity) {
                    Node tail = linkedList.removeLast();
                    Object tailKey = tail.getKey();
                    nodeMap.remove(tailKey);
                    linkedList.remove(tail);
                    linkedList.addFirst(newNode);
                    nodeMap.put(key, newNode);
                }else {
                    nodeMap.put(key, newNode);
                    linkedList.addFirst(newNode);
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public Object get(Object key) {
        try{
            lock.lock();
            Node target = nodeMap.get(key);
            if(target == null) {
                return null;
            }
            linkedList.remove(target);
            linkedList.addFirst(target);
            return target;
        }finally {
            lock.unlock();
        }
    }

    public void remove(Object key) {
        try {
            lock.lock();
            if(nodeMap.get(key) == null) {
                return;
            }
            Node target = nodeMap.get(key);
            linkedList.remove(target);
            nodeMap.remove(key);
        }finally {
            lock.unlock();
        }
    }

}

class Node {
    private Object key;
    private Object value;
    private Node pre;
    private Node next;

    public Node(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}