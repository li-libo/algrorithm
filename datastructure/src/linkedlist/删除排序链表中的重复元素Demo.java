package linkedlist;

import org.junit.Test;

/**
 * 83. 删除排序链表中的重复元素
 * 给定一个已排序的链表的头 head ,删除所有重复的元素，使每个元素只出现一次。返回已排序的链表 。
 * 输入：head = [1,1,2]
 * 输出：[1,2]
 *
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 * @author lilibo
 * @create 2022-03-15 4:58 PM
 */
public class 删除排序链表中的重复元素Demo {

    @Test
    public void test1() {
        int[] vals = {1, 1, 2};
        ListNode head = new ListNode(vals[0]);
        ListNode temp = head;
        for(int i = 1; i < vals.length; i++) {
            ListNode newNode = new ListNode(vals[i]);
            temp.next = newNode;
            temp = temp.next;
        }
        head = deleteDuplicates(head);
        printLinkedList(head);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) {
            return null;
        }
        ListNode temp = head;
        while (temp.next!=null) {
            if(temp.val == temp.next.val) {
                temp.next = temp.next.next;
            }
            temp = temp.next;
        }
        return head;
    }

    public void printLinkedList(ListNode head) {
        if(head == null) {
            return;
        }
        System.out.println("开始打印链表...");
        ListNode temp = head;
        while (temp!=null) {
            System.out.println(temp);
            temp = temp.next;
        }
        System.out.println("打印链表结束...");
    }

    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
