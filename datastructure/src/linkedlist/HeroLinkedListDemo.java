package linkedlist;

import org.junit.Test;

import java.util.Stack;

/**
 * @author lilibo
 * @create 2022-01-25 9:34 PM
 */
public class HeroLinkedListDemo {

    @Test
    public void test1() {
        //先创建节点
        MyHeroNode hero1 = new MyHeroNode(1, "宋江", "及时雨");
        MyHeroNode hero2 = new MyHeroNode(2, "卢俊义", "玉麒麟");
        MyHeroNode hero3 = new MyHeroNode(3, "吴用", "智多星");
        MyHeroNode hero4 = new MyHeroNode(4, "林冲", "豹子头");

        HeroLinkedList heroLinkedList = new HeroLinkedList();
        heroLinkedList.add(hero1);
        heroLinkedList.add(hero4);
        heroLinkedList.add(hero3);
        heroLinkedList.add(hero2);
        heroLinkedList.print();

        heroLinkedList.clear();
        heroLinkedList.add(hero1);
        heroLinkedList.add(hero4);
        heroLinkedList.add(hero3);
        heroLinkedList.add(hero2);
        heroLinkedList.print();

        heroLinkedList.clear();
        heroLinkedList.addByOrder(hero1);
        heroLinkedList.addByOrder(hero4);
        heroLinkedList.addByOrder(new MyHeroNode(3, "吴用", "小野胖夫"));
        heroLinkedList.addByOrder(hero3);
        heroLinkedList.addByOrder(hero2);
        heroLinkedList.print();
        System.out.println("更新no == 3的数据...");
        heroLinkedList.update(3, "🐷", "小猪娃");
        heroLinkedList.print();
        System.out.println("准备删除no == 3的数据...");
        heroLinkedList.deleteNode(3);
        heroLinkedList.deleteNode(3);
        heroLinkedList.print();
        System.out.println("统计单链表中节点个数, count = " + heroLinkedList.length());
        System.out.println("重新添加no == 3的节点...");
        heroLinkedList.addByOrder(new MyHeroNode(3, "吴用", "小野胖夫"));
        heroLinkedList.print();
        System.out.println("查找单链表中的倒数第2个节点" + heroLinkedList.getLastNode(2));
        System.out.println("反向打印链表...");
        heroLinkedList.reversePrint();
        System.out.println("单链表的反转...");
        heroLinkedList.reverseList();
        heroLinkedList.print();
    }

}

class HeroLinkedList {
    private final MyHeroNode head;

    public HeroLinkedList() {
        this.head = new MyHeroNode();
    }

    public void addByOrder(MyHeroNode newNode) {
        if (newNode == null) {
            return;
        }
        if (head.getNext() == null) {
            head.setNext(newNode);
            return;
        }
        MyHeroNode next = head;
        int no = newNode.getNo();
        boolean findFlag = false;
        while (next.getNext() != null) {
            if (next.getNext().getNo() >= no) {
                findFlag = true;
                break;
            }
            next = next.getNext();
        }
        if (findFlag) {
            newNode.setNext(next.getNext());
            next.setNext(newNode);
        } else {
            next.setNext(newNode);
        }
    }

    public void clear() {
        System.out.println("*****开始清理*****");
        MyHeroNode next = head;
        Stack<MyHeroNode> stack = new Stack<>();
        while (next != null) {
            stack.push(next);
            next = next.getNext();
        }
        while (stack.size() > 0) {
            stack.pop().setNext(null);
        }
        System.out.println("*****清理完毕!*****");
    }

    public void add(MyHeroNode newNode) {
        if (newNode == null) {
            return;
        }
        MyHeroNode next = head;
        while (next.getNext() != null) {
            next = next.getNext();
        }
        next.setNext(newNode);
    }

    public void print() {
        if (head.getNext() == null) {
            return;
        }
        MyHeroNode last = head.getNext();
        while (last != null) {
            System.out.println(last);
            last = last.getNext();
        }
    }

    public void update(int no, String name, String nickname) {
        if (head.getNext() == null) {
            System.out.println("无可修改数据, no = " + no);
            return;
        }
        MyHeroNode temp = head.getNext();
        while (temp != null) {
            if (temp.getNo() == no) { // 找到no
                temp.setName(name);
                temp.setNickName(nickname);
                break;
            }
            temp = temp.getNext();
        }
    }

    public void deleteNode(int no) {
        if (head.getNext() == null) {
            System.out.println("无可删除数据, no == " + no);
            return;
        }
        MyHeroNode temp = head.getNext();
        boolean findFlag = false;
        while (temp.getNext() != null) {
            if (temp.getNext().getNo() == no) { // 找到
                findFlag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (findFlag == true) {
            MyHeroNode oldNextNextNode = temp.getNext().getNext();
            temp.setNext(oldNextNextNode);
        } else {
            System.out.println("找不到要删除数据, no = " + no);
        }
    }

    public int length() {
        if (head.getNext() == null) {
            return 0;
        }
        int count = 0;
        MyHeroNode temp = head.getNext();
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    public MyHeroNode getLastNode(int k) {
        int length = length();
        // 倒数第k个节点为正数第length-k+1个节点
        if (head.getNext() == null) {
            return null;
        }
        int count = 0;
        int targetCount = length - k + 1;
        if (targetCount > length || targetCount < 1) {
            return null;
        }
        MyHeroNode temp = head.getNext();
        while (temp != null) {
            count++;
            if (count == targetCount) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    public void reversePrint() {
        if(head.getNext() == null) {
            return;
        }
        MyHeroNode temp = head.getNext();
        Stack<MyHeroNode> stack = new Stack<>();
        while (temp!=null) {
            stack.push(temp);
            temp = temp.getNext();
        }
        while (stack.size() > 0) {
            if(stack.size() == 1) {
                System.out.print(stack.pop());
            }else {
                System.out.print(stack.pop() + ", ");
            }
        }
        System.out.println("\n");
    }

    /**
     * 反转单链表
     */
    public void reverseList() {
        // 如果当前链表没有实际节点或者只有1个节点, 则无需反转
        if(head.getNext() == null || head.getNext().getNext() == null) {
            return;
        }
        // 辅助反向链表头结点
        MyHeroNode reverseHead = new MyHeroNode();
        // 定义1个辅助指针
        MyHeroNode cur = head.getNext();
        while (cur!=null) {
            //先保存cur后续节点
            MyHeroNode next = cur.getNext();
            // 获取原反向链表后续节点
            MyHeroNode oldNext = reverseHead.getNext();
            // 将原反向链表后续节点挂载在新指针节点后面
            cur.setNext(oldNext);
            // 设置反向链表最新节点
            reverseHead.setNext(cur);
            cur = next; // 指针后移
        }
        head.setNext(reverseHead.getNext());
    }
}

class MyHeroNode {
    private int no;
    private String name;
    private String nickName;
    private MyHeroNode next;

    public MyHeroNode() {
    }

    public MyHeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
        this.next = next;
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

    public MyHeroNode getNext() {
        return next;
    }

    public void setNext(MyHeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "MyHeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
