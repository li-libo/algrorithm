package linkedlist;

/**
 * 双向链表demo
 * <p>
 * 单向链表缺点分析
 * 1) 单向链表，查找的方向只能是一个方向，而双向链表可以向前或者向后查找。
 * 2) 单向链表不能自我删除，需要靠辅助节点 ，而双向链表，则可以自我删除，所以前面我们单链表删除节点，总是找到temp,temp是待删除节点的前一个节点(认真体会).
 *
 * @author lilibo
 * @create 2021-08-08 4:03 PM
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        // 测试
        System.out.println("双向链表的测试");
        // 先创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        // 创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero4);

        doubleLinkedList.print();

        // 修改
        doubleLinkedList.update(4, "公孙胜", "入云龙");
        System.out.println("修改后的链表情况");
        doubleLinkedList.print();

        // 删除
        doubleLinkedList.delete(3);
        System.out.println("删除后的链表情况~~");
        doubleLinkedList.print();
    }

}

class DoubleLinkedList {

    private HeroNode2 head;

    public DoubleLinkedList() {
        head = new HeroNode2(-1, null, null);
    }

    public void add(HeroNode2 node) {
        // 定义1个辅助指针
        HeroNode2 temp = head;
        while (temp.getNext() != null) {
            temp.getNext();
        }
        node.setPre(temp);
        temp.setNext(node);
    }

    public void delete(int no) {
        // 定义1个辅助指针
        HeroNode2 temp = head.getNext();
        boolean findFlag = false;
        while (temp != null) {
            if (no == temp.getNo()) {
                findFlag = true;
                break;
            }
            temp = temp.getNext();
        }
        if (!findFlag) {
            System.out.println("无对应no = " + no + "节点删除!!!");
            return;
        }
        HeroNode2 pre = temp.getPre();
        HeroNode2 next = temp.getNext();
        pre.setNext(next);
    }

    public void print() {
        HeroNode2 temp = head.getNext();
        while (temp != null) {
            System.out.println(temp);
            temp = temp.getNext();
        }
    }

    public void update(int no, String name, String nickName) {
        HeroNode2 temp = head.getNext();
        while (temp != null) {
            if (temp.getNo() == no) {
                break;
            }
            temp = temp.getNext();
        }
        if (temp != null) {
            temp.setName(name);
            temp.setNickName(nickName);
        }
    }

    /**
     * 按照顺序添加,如果有相同编号则添加失败
     *
     * @param node
     */
    public void addByOrder(HeroNode2 node) {
        HeroNode2 temp = head;
        while (temp.getNext() != null) {
            if (temp.getNext().getNo() == node.getNo()) {
                System.out.println("已有相同no = " + node.getNo());
                return;
            }
            if (temp.getNext().getNo() > node.getNo()) {
                break;
            }
            temp = temp.getNext();
        }
        HeroNode2 lastNode = temp.getNext();
        node.setPre(temp);
        node.setNext(lastNode);
        temp.setNext(node);
        if (lastNode != null) { // 小心
            lastNode.setPre(node);
        }
    }
}

class HeroNode2 {
    private int no;
    private String name;
    private String nickName;
    private HeroNode2 pre;
    private HeroNode2 next;

    public HeroNode2() {
    }

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    public HeroNode2 getPre() {
        return pre;
    }

    public void setPre(HeroNode2 pre) {
        this.pre = pre;
    }

    public HeroNode2 getNext() {
        return next;
    }

    public void setNext(HeroNode2 next) {
        this.next = next;
    }

    public int getNo() {
        return no;
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

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}