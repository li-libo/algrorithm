package linkedlist;

import org.junit.Test;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * @author lilibo
 * @create 2022-03-12 16:19
 */
public class 合并有序链表Demo {

    @Test
    public void test1() {
       int[] array1 = {1,2,4};
       ListNode list1 = createNodeList(array1);
       int[] array2 = {1, 3, 4};
       ListNode list2 = createNodeList(array2);
       ListNode newList = mergeTwoListsInRecursion(list1, list2);
       printList(newList);
    }

    /**
     * 非递归方式合并2个有序链表
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;
        ListNode l1 = list1;
        ListNode l2 = list2;
        while (l1 != null && l2 != null) {
            if(l1.value < l2.value) {
                temp.next = l1;
                temp = temp.getNext();
                l1 = l1.getNext();
            }else {
                temp.next = l2;
                temp = temp.getNext();
                l2 = l2.getNext();
            }
        }
        temp.next = l1 == null? l2 : l1;
        return dummy.next;
    }

    public ListNode mergeTwoListsInRecursion(ListNode l1, ListNode l2) {
        if(l1 == null) {
            return l2;
        }
        if(l2 == null) {
            return l1;
        }
        if(l1.value < l2.value) {
            l1.next = mergeTwoListsInRecursion(l1.next, l2);
            return l1;
        }else {
            l2.next = mergeTwoListsInRecursion(l1, l2.next);
            return l2;
        }
    }

    public void printList(ListNode node) {
        if(node == null) {
            return;
        }
        ListNode temp = node;
        while (temp!=null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    public ListNode createNodeList(int[] array1) {
        ListNode dummy1 = new ListNode(-1);
        ListNode temp = dummy1;
        for(int value : array1) {
            ListNode newNode = new ListNode(value);
            temp.setNext(newNode);
            temp = temp.getNext();
        }
        return dummy1.getNext();
    }



}


class ListNode {
    int value;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
                "value=" + value +
                '}';
    }
}