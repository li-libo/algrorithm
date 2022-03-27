package linkedlist;

import org.junit.Test;

/**
 * @author lilibo
 * @create 2022-03-13 15:13
 */
public class 翻转链表Demo {

    /**
     * 测试反转整个链表
     */
    @Test
    public void test1() {
        int[] valArray = {1, 2, 3, 4, 5};
        ListNode head = new ListNode(1);
        ListNode temp = head;
        for(int i = 1; i < valArray.length; i++) {
            ListNode newNode = new ListNode(valArray[i]);
            temp.setNext(newNode);
            temp = newNode;
        }
        printList(head);
        System.out.println("开始反转链表...");
        head = reverseListByRecursion(head);
        printList(head);
    }

    /**
     * 测试反转m到n的链表
     */
    @Test
    public void test2() {
        int[] valArray = {1, 2, 3, 4, 5};
        ListNode head = new ListNode(1);
        ListNode temp = head;
        for(int i = 1; i < valArray.length; i++) {
            ListNode newNode = new ListNode(valArray[i]);
            temp.setNext(newNode);
            temp = newNode;
        }
        printList(head);
        System.out.println("开始反转链表(m = 2, n = 4)...");
        head = reverseBetweenByRecursion(head, 2, 4);
        printList(head);
    }

    /**
     * 反转前n个元素
     */
    @Test
    public void test3() {
        int[] valArray = {1, 2, 3, 4, 5};
        ListNode head = new ListNode(1);
        ListNode temp = head;
        for(int i = 1; i < valArray.length; i++) {
            ListNode newNode = new ListNode(valArray[i]);
            temp.setNext(newNode);
            temp = newNode;
        }
        printList(head);
        System.out.println("开始反转链表前3个元素...");
        head = reversePreNByRecursion(head, 3);
        printList(head);
    }

    /**
     * 以k为一组反转链表
     */
    @Test
    public void test4() {
        int[] valArray = {1, 2, 3, 4, 5};
        ListNode head = new ListNode(1);
        ListNode temp = head;
        for(int i = 1; i < valArray.length; i++) {
            ListNode newNode = new ListNode(valArray[i]);
            temp.setNext(newNode);
            temp = newNode;
        }
        printList(head);
        int k = 2;
        System.out.println("以k = 2为一组反转链表...");
        head = reverseKGroup(head, k);
        printList(head);
    }

    /**
     * 以k为一组翻转链表
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.setNext(head);
        ListNode cur = head;
        ListNode pre = dummy;
        // 统计链表长度
        int lengthOfList = 0;
        ListNode temp = head;
        while (temp != null) {
            lengthOfList++;
            temp = temp.getNext();
        }
        for(int i = 0; i < lengthOfList / k; i++) { // 需要翻转几组
            for(int j = 1; j < k; j++) { // 反转k个元素
                temp = cur.getNext();
                cur.setNext(temp.getNext());
                temp.setNext(pre.getNext());
                pre.setNext(temp);
            }
            // 指针后移
            pre = cur;
            cur = cur.getNext();
        }
        return dummy.getNext();
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    /**
     * 翻转整个链表
     * @param head
     */
    public ListNode reverseList(ListNode head) {
        ListNode dummy = new ListNode(-1);
        ListNode temp = head;
        while (temp != null) {
            ListNode oldNext = temp.getNext();
            ListNode dummyNext = dummy.getNext();
            temp.setNext(dummyNext);
            dummy.setNext(temp);
            temp = oldNext;
        }
        return dummy.getNext();
    }

    /**
     * 递归反转整个链表
     * @param head
     * @return
     */
    public ListNode reverseListByRecursion(ListNode head) {
        if(head == null || head.getNext() == null) {
            return head;
        }
        ListNode last = reverseListByRecursion(head.next);
        head.getNext().setNext(head);
        head.setNext(null);
        return last;
    }

    /**
     * 反转m到n的链表
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.setNext(head);
        ListNode start = head;
        ListNode preStart = dummy;
        // 先移动到m位置
        for(int i = 1; i < m; i++) {
            preStart = preStart.getNext();
            start = start.getNext();
        }
        ListNode temp = null;
        // 反转
        for(int i = 1; i <= n - m; i++) {
            temp = start.getNext();
            start.setNext(temp.getNext());
            temp.setNext(preStart.getNext());
            preStart.setNext(temp);
        }
        return dummy.getNext();
    }

    public ListNode reverseBetweenByRecursion(ListNode head, int m, int n) {
        if(m == 1) {
            return reversePreNByRecursion(head, n);
        }
        head.setNext(reverseBetweenByRecursion(head.getNext(), m - 1, n - 1));
        return head;
    }

    /**
     * 反转前m个元素
     * @param head
     * @param n
     * @return
     */
    public ListNode reversePreN(ListNode head, int n) {
        if(head == null || head.getNext() == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.setNext(head);
        ListNode start = head;
        ListNode preStart = dummy;
        ListNode temp = null;
        for(int i = 1; i < n; i++) {
            temp = start.getNext();
            start.setNext(temp.getNext());
            temp.setNext(preStart.getNext());
            preStart.setNext(temp);
        }
        return dummy.getNext();
    }

    private ListNode successor;

    public ListNode reversePreNByRecursion(ListNode head, int n) {
        if(n == 1) {
            successor = head.getNext();
            return head;
        }
        ListNode last = reversePreNByRecursion(head.next, n - 1);
        head.getNext().setNext(head);
        head.setNext(successor);
        return last;
    }



    /**
     * 打印链表
     * @param node
     */
    public void printList(ListNode node) {
        if(node == null) {
            return;
        }
        System.out.println("开始打印链表...");
        ListNode temp = node;
        while (temp!= null) {
            System.out.printf(temp + " -> ");
            temp = temp.getNext();
        }
        System.out.printf("\n打印链表结束!\n");
    }

}
