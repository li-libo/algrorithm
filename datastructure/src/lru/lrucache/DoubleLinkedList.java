package lru.lrucache;

/**
 * 自定义双向链表(删除时不需找到前驱节点)
 *
 * @author lilibo
 * @create 2021-09-03 2:46 PM
 */
public class DoubleLinkedList {

    private Node head;

    private Node tail;

    private int size;

    public void putToHead(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            size++;
            return;
        }
        node.setNext(head);
        head.setPre(node);
        head = node;
        size++;
    }

    public Object remove(Node node) {
        if(head == tail && head == node){
            head = null;
            tail = null;
        }
        else if (head == node) {
            head.getNext().setPre(null);
            head = head.getNext();
        } else if (tail == node) {
            tail.getPre().setNext(null);
            tail = tail.getPre();
        } else {
            node.getPre().setNext(node.getNext());
            node.getNext().setPre(node.getPre());
        }
        size--;
        return node.getKey();
    }

    public Object removeTail() {
        if (head == null) {
            return null;
        }
        Node oldTail = tail;
        tail.getPre().setNext(null);
        tail = tail.getPre();
        size--;
        return oldTail.getKey();
    }

    public int size() {
        return size;
    }

    public void print() {
        if (head == null) {
            return;
        }
        //定义1个辅助变量
        Node temp = head;
        while (temp != null) {
            System.out.printf(temp + " -> ");
            temp = temp.getNext();
        }
        System.out.println();
    }

}
