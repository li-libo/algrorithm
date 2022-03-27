import org.w3c.dom.NodeList;

import java.util.Scanner;

/**
 * 描述
 * 将一个节点数为 size 链表 m 位置到 n 位置之间的区间反转，要求时间复杂度 O(n)O(n)O(n)，空间复杂度 O(1)O(1)O(1)。
 * 例如：
 * 给出的链表为 1→2→3→4→5→NULL1\to 2 \to 3 \to 4 \to 5 \to NULL1→2→3→4→5→NULL, m=2,n=4m=2,n=4m=2,n=4,
 * 返回 1→4→3→2→5→NULL1\to 4\to 3\to 2\to 5\to NULL1→4→3→2→5→NULL.
 *
 * 数据范围： 链表长度 0<size≤10000 < size \le 1000 0<size≤1000，0<m≤n≤size0 < m \le n \le size0<m≤n≤size，链表中每个节点的值满足 ∣val∣≤1000|val| \le 1000 ∣val∣≤1000
 * 要求：时间复杂度 O(n)O(n)O(n) ，空间复杂度 O(n)O(n)O(n)
 * 进阶：时间复杂度 O(n)O(n)O(n)，空间复杂度 O(1)O(1)O(1)
 * @author lilibo
 * @create 2022-02-23 21:21
 */
public class BM2 {

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            while(scanner.hasNext()) {
                Node2List nodeList = new Node2List();
                scanner.nextLine();
                int[] array = {1, 2, 3, 4, 5};
                for(int value : array) {
                    nodeList.addNode(new Node2(value));
                }
                // nodeList.print();
                nodeList.reverseBetween(2, 4);
                nodeList.print();
            }
        }
    }
}

class Node2List {

    private Node2 head;

    public void addNode(Node2 newNode) {
        if(head == null) {
            head = newNode;
            return;
        }
        Node2 temp = head;
        while(temp.getNext()!=null) {
            temp = temp.getNext();
        }
        temp.setNext2(newNode);
    }

    public void reverseBetween(int m, int n) {
        Node2 dummy = new Node2(-1);
        dummy.setNext2(head);
        Node2 start = head;
        Node2 preStart = dummy;
        // 先移动到m位置
        for(int i = 1; i < m; i++) {
            preStart = start;
            start = start.getNext();
        }
        // 反转
        for(int i = 0; i < n - m; i++) {
            Node2 temp = start.getNext();
            start.setNext2(temp.getNext());
            temp.setNext2(preStart.getNext());
            preStart.setNext2(temp);
        }
        head = dummy.getNext();
    }

    public void print() {
        if(head == null) {
            return;
        }
        System.out.print("{");
        Node2 temp = head;
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

class Node2 {
    private int value;

    private Node2 next;

    public Node2(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setNext2(Node2 next) {
        this.next = next;
    }

    public Node2 getNext() {
        return next;
    }

    public String toString() {
        return "" + value;
    }

}