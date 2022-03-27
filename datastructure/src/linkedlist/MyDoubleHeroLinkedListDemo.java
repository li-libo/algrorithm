package linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lilibo
 * @create 2022-01-26 9:47 PM
 */
public class MyDoubleHeroLinkedListDemo {

    public static void main(String[] args) {
        MyDoubleHeroNode hero1 = new MyDoubleHeroNode(1, "宋江", "及时雨");
        MyDoubleHeroNode hero2 = new MyDoubleHeroNode(2, "卢俊义", "玉麒麟");
        MyDoubleHeroNode hero3 = new MyDoubleHeroNode(3, "吴用", "智多星");
        MyDoubleHeroNode hero4 = new MyDoubleHeroNode(4, "林冲", "豹子头");
        // 创建链表
        MyDoubleLinkedList doubleLinkedList = new MyDoubleLinkedList();
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);
        // 打印链表
        doubleLinkedList.print();
        // 清理链表
        doubleLinkedList.clear();
        doubleLinkedList.print();
        // 重新乱序添加元素到链表
        System.out.println("重新乱序添加元素到链表...");
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.print();
        // 修改no == 3的信息
        System.out.println("修改no == 3的信息...");
        doubleLinkedList.update(3, "小猪", "🐷");
        doubleLinkedList.print();
        System.out.println("删除no == 2,3的数据...");
        // 删除no == 3的数据
        doubleLinkedList.delete(2);
        doubleLinkedList.delete(3);
        doubleLinkedList.print();
    }

}

class MyDoubleLinkedList {

    private MyDoubleHeroNode head;

    public void add(MyDoubleHeroNode newNode) {
        if (head == null) {
            head = newNode;
            return;
        }
        MyDoubleHeroNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        newNode.setPre(temp);
        temp.setNext(newNode);
    }

    public void print() {
        if (head == null) {
            return;
        }
        MyDoubleHeroNode temp = head;
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    public void clear() {
        if(head == null) {
            return;
        }
        System.out.println("开始清理链表...");
        List<MyDoubleHeroNode> list = new ArrayList<>();
        MyDoubleHeroNode temp = head;
        while (temp!=null){
            list.add(temp);
            temp = temp.getNext();
        }
        for(MyDoubleHeroNode node : list) {
            node.setPre(null);
            node.setNext(null);
        }
        head = null;
        System.out.println("清理链表完毕!");
    }

    public void addByOrder(MyDoubleHeroNode newNode) {
        if(head == null) {
            head = newNode;
            return;
        }
        int no = newNode.getNo();
        MyDoubleHeroNode temp = head;
        boolean findFlag = false;
        while (temp.getNext()!=null) {
            if(temp.getNext().getNo() >= no){
                findFlag = true;
                break;
            }
            temp = temp.getNext();
        }
        if(findFlag) {
            MyDoubleHeroNode last = temp.getNext();
            newNode.setPre(temp);
            newNode.setNext(last);
            temp.setNext(newNode);
            last.setPre(newNode);
        }else {
            newNode.setPre(temp);
            temp.setNext(newNode);
        }
    }

    public void update(int no, String name, String nickname) {
        if(head == null) {
            return;
        }
        MyDoubleHeroNode cur = head;
        boolean findFlag = false;
        while (cur!=null) {
            if(cur.getNo() == no) {
                findFlag = true;
                break;
            }
            cur = cur.getNext();
        }
        if(findFlag) {
            cur.setName(name);
            cur.setNickName(nickname);
        }
    }

    public void delete(int no) {
        if(head == null) {
            return;
        }
        MyDoubleHeroNode cur = head;
        boolean findFlag = false;
        while (cur!=null) {
            if(cur.getNo() == no) {
                findFlag = true;
                break;
            }
            cur = cur.getNext();
        }
        if(findFlag) {
            MyDoubleHeroNode pre = cur.getPre();
            MyDoubleHeroNode next = cur.getNext();
            // 删除
            pre.setNext(next);
            next.setPre(pre);
        }
    }
}

class MyDoubleHeroNode {
    private int no;
    private String name;
    private String nickName;
    private MyDoubleHeroNode pre;
    private MyDoubleHeroNode next;

    public MyDoubleHeroNode(int no, String name, String nickName) {
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

    public MyDoubleHeroNode getPre() {
        return pre;
    }

    public void setPre(MyDoubleHeroNode pre) {
        this.pre = pre;
    }

    public MyDoubleHeroNode getNext() {
        return next;
    }

    public void setNext(MyDoubleHeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "MyDoubleHeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}