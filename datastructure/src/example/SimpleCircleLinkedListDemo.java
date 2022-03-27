package example;

/**
 * @author lilibo
 * @create 2022-02-15 6:47 PM
 */
public class SimpleCircleLinkedListDemo {

    public static void main(String[] args) {
        SimpleCircleLinkedList simpleCircleLinkedList = new SimpleCircleLinkedList();
        // 创建5个小孩的环形链表
        simpleCircleLinkedList.construct(5);
        // 遍历环形链表
        simpleCircleLinkedList.print();
        // 测试1把小孩出圈是否正确
        simpleCircleLinkedList.countBoy(1, 2);
    }
}

class SimpleCircleLinkedList {

    private BoyNode head;

    public void print() {
        System.out.println("开始遍历环形链表...");
        if (head == null) {
            return;
        }
        BoyNode temp = head;
        while (temp.getNext() != head) {
            System.out.println(temp);
            temp = temp.getNext();
        }
        System.out.println(temp);
        System.out.println("遍历环形链表结束...");
    }

    public void construct(int numOfNodes) {
        if(numOfNodes < 1) {
            throw new IllegalArgumentException("numOfNodes < 1");
        }
        if(numOfNodes == 1) {
            BoyNode newBoyNode = new BoyNode(1);
            head = newBoyNode;
            head.setNext(head);
        }else {
            BoyNode newBoyNode = new BoyNode(1);
            head = newBoyNode;
            head.setNext(head);
            for(int count = 2; count <= numOfNodes; count++) {
                BoyNode newNode = new BoyNode(count);
                BoyNode temp = head;
                while (temp.getNext() != head) {
                    temp = temp.getNext();
                }
                newNode.setNext(head);
                temp.setNext(newNode);
            }
        }
    }

    public void countBoy(int startNo, int numOfCount) {
        // 定义2个辅助指针cur/pre
        BoyNode cur = head;
        // 先将pre移动到cur的前1个元素, 即尾元素
        BoyNode pre = head;
        while (pre.getNext() != head) {
            pre = pre.getNext();
        }
        // 将cur和pre移动到初始位置
        for(int count = 1; count < startNo; count++) {
            cur = cur.getNext();
            pre = pre.getNext();
        }
        // 当cur == pre时链表只剩1个元素
        while (cur != pre) {
            for(int count = 1; ;count++) {
                if(count == numOfCount) {
                    // 准备出圈
                    pre.setNext(cur.getNext());
                    System.out.printf("出圈 " + cur + " -> ");
                    cur = cur.getNext();
                    break;
                }else {
                    pre = pre.getNext();
                    cur = cur.getNext();
                }
            }
        }
        System.out.println(cur);
    }
}

class BoyNode {

    private int id;
    private BoyNode next;

    public BoyNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BoyNode getNext() {
        return next;
    }

    public void setNext(BoyNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "BoyNode{" +
                "id=" + id +
                '}';
    }
}