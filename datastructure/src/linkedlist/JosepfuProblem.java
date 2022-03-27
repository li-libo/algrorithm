package linkedlist;

/**
 * 约瑟夫小孩出圈问题
 * @author lilibo
 * @create 2022-01-27 9:43 AM
 */
public class JosepfuProblem {

    public static void main(String[] args) {
        // 测试构建环形链表和遍历
        CircleLinkedList circleLinkedList = new CircleLinkedList();
        // 创建5个小孩节点的环形链表
        circleLinkedList.constructLinkedList(5);
        circleLinkedList.print();
        // 测试1把小孩出圈是否正确
        circleLinkedList.countBoy(1, 2, 5);
    }
}

class CircleLinkedList {

    private BoyNode head;

    public void constructLinkedList(int numOfNodes) {
        if(numOfNodes <= 0) {
            System.out.println("参数numOfNodes<=0!");
            return;
        }
        head = new BoyNode(1);
        if(numOfNodes == 1) {
            head.setNext(head);
        }else {
            BoyNode temp = head;
            for(int i = 2; i<= numOfNodes; i++) {
                BoyNode cur = new BoyNode(i);
                if(i == numOfNodes) {
                    cur.setNext(head);
                }
                temp.setNext(cur);
                temp = cur;
            }
        }
    }

    public void print() {
        if(head == null) {
            return;
        }
        BoyNode temp = head;
        while (temp.getNext()!=head) {
            System.out.printf(temp + " -> ");
            temp = temp.getNext();
        }
        System.out.println(temp);
    }

    /**
     * 小孩出圈
     * @param startNo 从第几个小孩开始数数
     * @param numOfCount 数几下出圈
     * @param numOfNodes 小孩个数
     */
    public void countBoy(int startNo, int numOfCount, int numOfNodes) {
        if(startNo < 1 || startNo > numOfNodes) {
            System.out.println("参数错误");
            return;
        }
        if(head == null) {
            System.out.println("head为空!");
            return;
        }
        // 定义1个辅助指针pre, 指向当前节点cur的前1个节点, 先将pre移动到尾结点
        BoyNode pre = head;
        while (pre.getNext() != head) {
            pre = pre.getNext();
        }
        BoyNode cur = head;
        // 先将pre和cur移动到startNo
        for(int i = 1; i < startNo; i++) {
            pre = pre.getNext();
            cur = cur.getNext();
        }
        // 当pre == cur时链表只剩1个节点
        while (cur != pre) {
            for(int i = 1; i <= numOfCount; i++) {
                if(i == numOfCount) {
                    // 出圈
                    System.out.printf(cur + " -> ");
                    pre.setNext(cur.getNext());
                    cur = cur.getNext();
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

    private int no;

    private BoyNode next;

    public BoyNode() {
    }

    public BoyNode(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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
                "no=" + no +
                '}';
    }
}