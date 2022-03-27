package example;

/**
 * @author lilibo
 * @create 2022-02-07 11:16 AM
 */
public class MyDoubleLinkedList {

    private final int capacity;

    private LruNode head;

    private LruNode tail;

    private int count;

    public MyDoubleLinkedList(int capacity) {
        this.capacity = capacity;
    }

    public void put(LruNode newNode) {
        if(head == null && tail == null) {
            head = newNode;
            tail = newNode;
            count++;
            return;
        }
        newNode.setPre(tail);
        tail.setNext(newNode);
        count++;
    }

    public void putToHead(LruNode newNode) {
        if(head == null && tail == null) {
            head = newNode;
            tail = newNode;
            count++;
            return;
        }
        head.setPre(newNode);
        newNode.setNext(head);
        head = newNode;
        count++;
    }

    public void remove(LruNode node) {
        if(head == node && tail == node) {
            head = tail = null;
            count--;
            return;
        }
        if(head == node) {
            LruNode next = head.getNext();
            next.setPre(null);
            head = next;
            count--;
            return;
        }
        if(tail == node) {
            LruNode pre = tail.getPre();
            pre.setNext(null);
            tail = pre;
            count--;
            return;
        }
        LruNode pre = node.getPre();
        LruNode next = node.getNext();
        pre.setNext(next);
        next.setPre(pre);
        count--;
    }

    public LruNode removeTail() {
        if(tail == null) {
            return null;
        }
        LruNode oldTail = tail;
        tail = oldTail.getPre();
        tail.setNext(null);
        count--;
        return oldTail;
    }
}

class LruNode {

    private Object key;

    private Object value;

    private LruNode pre;

    private LruNode next;

    public LruNode(Object key, Object value) {
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

    public LruNode getPre() {
        return pre;
    }

    public void setPre(LruNode pre) {
        this.pre = pre;
    }

    public LruNode getNext() {
        return next;
    }

    public void setNext(LruNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "LruNode{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
