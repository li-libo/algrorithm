package linkedlist;

import java.util.Stack;

/**
 * 链表是有序的列表,但并不是连续的存储
 *
 * @author lilibo
 * @create 2021-08-07 6:23 PM
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        //进行测试
        //先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        //创建要给链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //加入
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        // 删除
        // singleLinkedList.delete(3);
        // 更新
        singleLinkedList.updateNode(2, "胖子", "贱胖子");
        singleLinkedList.print();
        // 链表长度为
        System.out.println("链表长度为: " + singleLinkedList.getLength());
        int lastIndex = 2;
        System.out.printf("倒数第%d个节点:%s\n", lastIndex, singleLinkedList.findLastIndexNode(lastIndex));
        System.out.println("第1次反转>>>");
        singleLinkedList.reverseByStack();
        singleLinkedList.print();
        System.out.println("第2次反转>>>");
        singleLinkedList.reverse();
        singleLinkedList.print();
        singleLinkedList.reversePrint();
    }

}

class SingleLinkedList {

    private HeroNode head;

    public void add(HeroNode node) {
        if (head == null) {
            head = node;
            return;
        }
        // 定义一个辅助指针
        HeroNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(node);
    }

    public void print() {
        if (head == null) {
            System.out.println("链表为空!");
            return;
        }
        // 定义一个辅助指针
        HeroNode temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    public void updateNode(int no, String name, String nickName) {
        if (head == null) {
            System.out.println("链表为空!");
            return;
        }
        if (head.getNo() == no) {
            head.setName(name);
            head.setNickName(nickName);
            return;
        }
        // 定义1个辅助指针
        HeroNode temp = head.getNext();
        while (temp != null) {
            // 找到要修改的节点更新
            if (temp.getNo() == no) {
                temp.setName(name);
                temp.setNickName(nickName);
                return;
            }
            temp = temp.getNext();
        }
    }

    /* 删除节点需要找到要删除节点的前一个节点 */
    public void delete(int no) {
        if (head == null) {
            System.out.println("链表为空!!!");
            return;
        }
        if (head.getNo() == no) {
            head = null;
            return;
        }
        HeroNode temp = head;
        boolean findFlag = false;
        while (temp.getNext() != null) {
            if (temp.getNext().getNo() == no) {
                findFlag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (findFlag && temp != null) {
            HeroNode successor = temp.getNext().getNext();
            temp.setNext(successor);
        }
    }

    /* 按顺序添加,根据no将节点插入到指定位置,如果有这个节点则添加失败并给予提示 */
    public void addByOrder(HeroNode node) {
        if (head == null) {
            head = node;
            return;
        }
        // 定义一个辅助指针
        HeroNode temp = head;
        while (temp.getNext() != null) {
            if (temp.getNext().getNo() == node.getNo()) {
                System.out.println("已有相同序号元素, 添加失败!");
                return;
            }
            if (temp.getNext().getNo() > node.getNo()) {
                break;
            }
            temp = temp.getNext();
        }
        HeroNode successor = temp.getNext();
        node.setNext(successor);
        temp.setNext(node);
    }

    /**
     * 利用栈将原链表反转
     */
    public void reverseByStack() {
        if (head == null || head.getNext() == null) {
            System.out.println("原链表为null或只有一个节点, 无需反转!");
            return;
        }
        // 定义一个辅助指针
        HeroNode temp = head;
        Stack<HeroNode> stack = new Stack<>();
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }
        head = stack.pop(); // 重置head
        temp = head; // 重置辅助指针
        while (stack.size() > 0) {
            HeroNode node = stack.pop();
            node.setNext(null); // 清理原先的后继关系
            temp.setNext(node);
            temp = temp.getNext(); // 指针继续后移
        }
    }

    /**
     * 将单链表反转
     * 思路: (1) 定义1个reverseHead (2) 遍历原链表, 每遍历一个节点, 就将该节点置为reverseHead (3) 遍历原链表完毕后, head = reverseHead
     */
    public void reverse() {
        if (head == null || head.getNext() == null) {
            System.out.println("原链表为空或只有一个节点, 无需反转!");
            return;
        }
        HeroNode reverseHead = null; // 反转链表头结点
        HeroNode temp = head; // 定义一个辅助指针
        while (temp != null) {
            HeroNode nextNode = temp.getNext(); // 先取出原链表下一个节点保存
            HeroNode oldReverseHead = reverseHead;
            temp.setNext(oldReverseHead);
            reverseHead = temp; // 设置新反转链表头结点
            temp = nextNode; // 继续遍历
        }
        head = reverseHead;
    }

    public int getLength() {
        if (head == null) {
            return 0;
        }
        int count = 0;
        HeroNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    /*
        查找单链表中倒数第lastIndex个节点
     */
    public HeroNode findLastIndexNode(int lastIndex) {
        int length = getLength();
        if (length < lastIndex) {
            System.out.println("length < lastIndex !");
            return null;
        }
        HeroNode temp = head;
        for (int i = 0; i < length - lastIndex; i++) {
            temp = temp.getNext();
        }
        return temp;
    }


    public void reversePrint() {
        System.out.println("反向输出>>>");
        if (head == null) {
            System.out.println("链表为空!");
            return;
        }
        Stack<HeroNode> stack = new Stack<>();
        HeroNode temp = head;
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
        System.out.println("反向输出完毕!");
    }

}

class HeroNode {

    private int no;

    private String name;

    private String nickName;

    private HeroNode next;

    public HeroNode() {
    }

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
