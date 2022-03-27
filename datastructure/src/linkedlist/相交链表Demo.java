package linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lilibo
 * @create 2022-03-13 12:18 AM
 */
public class 相交链表Demo {

    /**
     * 解法1, 利用Set, 时间复杂度m + n, 空间复杂度m
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> setA = new HashSet<>();
        ListNode temp = headA;
        while (temp != null) {
            setA.add(temp);
            temp = temp.getNext();
        }
        temp = headB;
        while (temp != null) {
            if(setA.contains(temp)) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    /**
     * 双指针法:
     * 分析:假设2个链表listA和listB, 不相交部分为a和b相加部分为c
     * 如果a == b, 2个链表指针从a、b开始遍历,会同时到达c节点, 此时返回相交节点c
     * 如果a != b, 2个指针不会同时到达c节点, 会有1个链表先到达末尾, 然后指针pA移动到headB头结点, 指针pB移动到headA头结点, 然后2个指针继续
     * 移动, 在指针pA移动了a+c+b次,指针pB移动了b+c+a次, 指针pA和pB会到达2个链表相交的节点
     */
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.getNext();
            pB = pB == null ? headA : pB.getNext();
        }
        return pA;
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
}
