package example;

import java.util.Stack;

/**
 * @author lilibo
 * @create 2022-02-15 4:14 PM
 */
public class SimpleLinkedListDemo {

    public static void main(String[] args) {
        StaffNode node1 = new StaffNode(1, "Jon", "fatBuddy");
        StaffNode node2 = new StaffNode(2, "lena", "lena anderson");
        StaffNode node3 = new StaffNode(3, "blaire", "blaire ivory");
        StaffNode node4 = new StaffNode(4, "tom", "tom drew");
        SimpleLinkedList linkedList = new SimpleLinkedList();
        linkedList.addNodeById(node2);
        linkedList.addNodeById(node1);
        linkedList.addNodeById(node4);
        linkedList.addNodeById(node3);
        System.out.println("*****添加节点(按id排序)*****");
        linkedList.print();
        System.out.println("*****删除node3*****");
        linkedList.deleteNode(3);
        linkedList.print();
        System.out.println("*****添加node3(按id排序)*****");
        linkedList.addNodeById(node3);
        linkedList.print();
        linkedList.update(3, "小野胖夫", "大胖小子");
        linkedList.print();
        System.out.println("链表的长度为: " + linkedList.size());
        System.out.println("链表倒数第1个节点为: " + linkedList.getLastNode(1));
        System.out.println("链表倒数第2个节点为: " + linkedList.getLastNode(2));
        System.out.println("链表倒数第4个节点为: " + linkedList.getLastNode(4));
        linkedList.reversePrint();
        System.out.println("反转原先单链表...");
        linkedList.reverse();
        linkedList.print();
    }
}

class SimpleLinkedList {

    private StaffNode head;

    public void addNode(StaffNode newNode) {
        if(head == null) {
            head = newNode;
            return;
        }
        StaffNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

    public void addNodeById(StaffNode newNode) {
        if(head == null) {
            head = newNode;
            return;
        }
        if(newNode.getId() < head.getId()) {
            newNode.setNext(head);
            head = newNode;
            return;
        }
        StaffNode temp = head;
        boolean findGeFlag = false;
        while (temp.getNext() != null) {
            if(temp.getNext().getId() >= newNode.getId()) {
                findGeFlag = true;
                break;
            }
            temp = temp.getNext();
        }
        if(findGeFlag) {
            StaffNode oldNext = temp.getNext();
            newNode.setNext(oldNext);
            temp.setNext(newNode);
        }else {
            temp.setNext(newNode);
        }
    }

    public void print() {
        System.out.println("开始遍历链表...");
        if(head == null) {
            return;
        }
        StaffNode temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
        System.out.println("遍历链表结束...");
    }

    public void deleteNode(int id) {
        if(head == null) {
            return;
        }
        if(head.getId() == id) {
            head = head.getNext();
            return;
        }
        StaffNode temp = head;
        boolean findTargetFlag = false;
        while (temp.getNext() != null) {
            if(temp.getNext().getId() == id) {
                findTargetFlag = true;
                break;
            }
            temp = temp.getNext();
        }
        if(findTargetFlag) {
           StaffNode oldNext = temp.getNext();
           temp.setNext(oldNext.getNext());
        }
    }

    public void update(int id, String name, String nickname) {
        if(head == null) {
            return;
        }
        if(head.getId() == id) {
            head.setName(name);
            head.setNickName(nickname);
            return;
        }
        StaffNode temp = head;
        while (temp != null) {
            if(temp.getId() == id) {
                break;
            }
            temp = temp.getNext();
        }
        if(temp != null) {
            temp.setName(name);
            temp.setNickName(nickname);
        }
    }

    public int size() {
        if(head == null) {
            return 0;
        }
        int count = 0;
        StaffNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    public StaffNode getLastNode(int lastNum) {
        if(head == null) {
            throw new IllegalArgumentException("链表为空!");
        }
        if(lastNum <= 0) {
            throw new IllegalArgumentException("参数lastNum <= 0!");
        }
        int size = size();
        if(lastNum > size) {
            throw new IllegalArgumentException("参数lastNum >= size!");
        }
        // 倒数第i个为正数size - i + 1个
        int targetCount = size - lastNum + 1;
        int count = 0;
        StaffNode temp = head;
        while (temp != null) {
            count++;
            if(count == targetCount) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    /**
     * 反向输出链表
     */
    public void reversePrint() {
        System.out.println("反向输出链表...");
        if(head == null) {
            return;
        }
        if(head.getNext() == null) { // 如果只有1个节点无需反向遍历输出
            System.out.println(head);
            return;
        }
        Stack<StaffNode> stack = new Stack<>();
        StaffNode temp = head;
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }
        // 利用栈的性质反向输出链表
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
        System.out.println("反向输出链表结束...");
    }

    /**
     * 单链表的反转
     */
    public void reverse() {
        if(head == null || head.getNext() == null) {
            return;
        }
        // 定义1个临时头结点用于构建反向链表
        StaffNode temporalHead = new StaffNode(-1, null, null);
        StaffNode temp = head;
        while (temp != null) {
            StaffNode oldTemporalNext = temporalHead.getNext();
            // 先取出原链表的下1节点防止temp的next指针替换找不到原先下1节点
            StaffNode next = temp.getNext();
            temp.setNext(oldTemporalNext);
            temporalHead.setNext(temp);
            temp = next;
        }
        head = temporalHead.getNext();
    }
}

class StaffNode {

    private int id;

    private String name;

    private String nickName;

    private StaffNode next;

    public StaffNode(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public StaffNode getNext() {
        return next;
    }

    public void setNext(StaffNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "StaffNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}