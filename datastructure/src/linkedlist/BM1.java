package linkedlist;

/**
 *  给定一个单链表的头结点pHead(该头节点是有值的，比如在下图，它的val是1)，长度为n，反转该链表后，返回新链表的表头。
 *
 * 数据范围： 0≤n≤1000
 * 要求：空间复杂度 O(1)O(1)O(1) ，时间复杂度 O(n)O(n)O(n) 。
 *
 * 如当输入链表{1,2,3}时，
 * 经反转后，原链表变为{3,2,1}，所以对应的输出为{3,2,1}。
 * @author lilibo
 * @create 2022-02-23 19:56
 */
public class BM1 {
    public static void main(String[] args) {
        int[] valueArray = {6, 5, 4, 3, 2, 1};
        SimpleNodeList simpleNodeList = new SimpleNodeList();
        for(int value: valueArray) {
            simpleNodeList.addNode(new Node(value));
        }
        // simpleNodeList.reverseInRecursion();
        // simpleNodeList.reverseInRecursion(3);
        simpleNodeList.reverseRangeInRecursion(2, 4);
        simpleNodeList.print();
    }

}

class SimpleNodeList {
    private Node head;

    public void addNode(Node newNode) {
        if(head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while(temp.getNext()!=null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

    public void reverse() {
        if(head == null || head.getNext() == null) {
            return;
        }
        Node reverseHead = new Node(-1);
        Node temp = head;
        while(temp != null) {
            Node oldNext = temp.getNext();
            temp.setNext(reverseHead.getNext());
            reverseHead.setNext(temp);
            temp = oldNext;
        }
        head = reverseHead.getNext();
    }

    /**
     * 递归反转链表
     */
    public void reverseInRecursion() {
        if(head == null || head.getNext() == null) {
            return;
        }
        head = reverseInRecursion(head);
    }

    public void reverseInRecursion(int n) {
        if(head == null || head.getNext() == null) {
            return;
        }
        head = reverseInRecursionForN(head, n);
    }

    private Node successor = null;

    public void reverseRangeInRecursion(int n, int m) {
        if(head == null || head.getNext() == null) {
            return;
        }
        head = reverseRangeInRecursion(head, n, m);
    }

    private Node reverseRangeInRecursion(Node head, int n, int m) {
        if(n == 1) {
            return reverseInRecursionForN(head, m);
        }
        head.setNext(reverseRangeInRecursion(head.getNext(), n - 1, m - 1));
        return head;
    }

    private Node reverseInRecursionForN(Node head, int n) {
        if(n == 1) {
            successor = head.getNext();
            return head;
        }
        Node last = reverseInRecursionForN(head.getNext(), n - 1);
        head.getNext().setNext(head);
        head.setNext(successor);
        return last;
    }

    private Node reverseInRecursion(Node head) {
        if(head == null) {
            return null;
        }
        if(head.getNext() == null) {
            return head;
        }
        Node last = reverseInRecursion(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return last;
    }

    public void print() {
        if(head == null) {
            return;
        }
        System.out.print("{");
        Node temp = head;
        while(temp!=null) {
            if(temp.getNext() == null) {
                System.out.print(temp);
            }else {
                System.out.print(temp + ",");
            }
            temp = temp.getNext();
        }
        System.out.print("}\n");
    }

}

class Node {

    private int value;

    private Node next;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public String toString() {
        return "" + value;
    }
}
