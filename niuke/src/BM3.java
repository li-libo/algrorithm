import java.util.Scanner;

/**
 * BM3 链表中的节点每k个一组翻转
 * 描述
 * 将给出的链表中的节点每 k 个一组翻转，返回翻转后的链表
 * 如果链表中的节点数不是 k 的倍数，将最后剩下的节点保持原样
 * 你不能更改节点中的值，只能更改节点本身。
 *
 * 数据范围：  0≤n≤2000\ 0 \le n \le 2000 0≤n≤2000 ， 1≤k≤20001 \le k \le 20001≤k≤2000 ，链表中每个元素都满足 0≤val≤10000 \le val \le 10000≤val≤1000
 * 要求空间复杂度 O(1)O(1) O(1)，时间复杂度 O(n)O(n)O(n)
 * 例如：
 * 给定的链表是 1→2→3→4→51\to2\to3\to4\to51→2→3→4→5
 * 对于 k=2k = 2k=2 , 你应该返回 2→1→4→3→52\to 1\to 4\to 3\to 52→1→4→3→5
 * 对于 k=3k = 3k=3 , 你应该返回 3→2→1→4→53\to2 \to1 \to 4\to 53→2→1→4→5
 * @author lilibo
 * @create 2022-02-24 1:20 AM
 */
public class BM3 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                Node3List nodeList = new Node3List();
                scanner.nextLine();
                int[] array = {1, 2, 3, 4, 5};
                for (int value : array) {
                    nodeList.addNode(new Node3(value));
                }
                nodeList.reverseKGroup(3);
                nodeList.print();
            }
        }
    }
}


class Node3List {

    private Node3 head;

    public void addNode(Node3 newNode) {
        if (head == null) {
            head = newNode;
            return;
        }
        Node3 temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

    public void reverseKGroup(int k) {
        if (head == null || head.getNext() == null || k < 2) {
            return;
        }
        Node3 dummy = new Node3(-1);
        dummy.setNext(head);
        Node3 cur = head;
        Node3 pre = dummy;
        int len = 0;
        while (head != null) {
            len++;
            head = head.getNext();
        }
        for (int i = 0; i < len / k; i++) {
            for (int j = 1; j < k; j++) {
                Node3 temp = cur.getNext();
                cur.setNext(temp.getNext());
                temp.setNext(pre.getNext());
                pre.setNext(temp);
            }
            // 指针后移
            pre = cur;
            cur = cur.getNext();
        }
        head = dummy.getNext();
    }

    public void print() {
        if (head == null) {
            return;
        }
        System.out.print("{");
        Node3 temp = head;
        while (temp != null) {
            if (temp.getNext() == null) {
                System.out.print(temp);
            } else {
                System.out.print(temp + ",");
            }
            temp = temp.getNext();
        }
        System.out.print("}\n");
    }
}

class Node3 {

    private int value;

    private Node3 next;

    public Node3(int value) {
        this.value = value;
    }

    public Node3 getNext() {
        return next;
    }

    public void setNext(Node3 next) {
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
}
