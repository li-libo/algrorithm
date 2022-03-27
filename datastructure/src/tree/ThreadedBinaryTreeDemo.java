package tree;

/**
 * @author lilibo
 * @create 2021-08-14 2:20 PM
 */
public class ThreadedBinaryTreeDemo {

    public static void main(String[] args) {
        //测试一把中序线索二叉树的功能
        EmpNode root = new EmpNode(1, "tom");
        EmpNode node2 = new EmpNode(3, "jack");
        EmpNode node3 = new EmpNode(6, "smith");
        EmpNode node4 = new EmpNode(8, "mary");
        EmpNode node5 = new EmpNode(10, "king");
        EmpNode node6 = new EmpNode(14, "dim");

        //二叉树，后面我们要递归创建, 现在简单处理使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        //测试中序线索化
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(root);
        threadedBinaryTree.threadNodes();
        //测试: 以10号节点测试
        EmpNode leftNode = node5.getLeft();
        EmpNode rightNode = node5.getRight();
        System.out.println("10号结点的前驱结点是 = " + leftNode); //3
        System.out.println("10号结点的后继结点是 = " + rightNode); //1

        threadedBinaryTree.midOrder();
        threadedBinaryTree.threadMidOrder();
    }
}

class ThreadedBinaryTree {

    private EmpNode pre; // 线索化二叉树辅助变量当前节点node的前驱节点pre

    private EmpNode root;

    public ThreadedBinaryTree(EmpNode root) {
        this.root = root;
    }

    /**
     * 中序线索化二叉树
     */
    public void threadNodes() {
        if (root == null) {
            System.out.println("二叉树为空!");
            return;
        }
        threadNodes(root);
    }

    public void midOrder() {
        if (root == null) {
            System.out.println("二叉树为空树!");
            return;
        }
        System.out.println("开始递归中序遍历...");
        midOrder(root);
    }

    private void midOrder(EmpNode node) {
        if (node.getLeft() != null && node.getLeftType() == 0) {
            midOrder(node.getLeft());
        }
        System.out.println(node);
        if (node.getRight() != null && node.getRightType() == 0) {
            midOrder(node.getRight());
        }
    }

    /**
     * 线索化中序遍历二叉树
     */
    public void threadMidOrder() {
        if (root == null) {
            System.out.println("当前二叉树为空!");
            return;
        }
        System.out.println("线索化中序遍历二叉树...");
        // 定义1个辅助指针
        EmpNode temp = root;
        while (temp != null) {
            // 找到最左边的节点
            while (temp.getLeft() != null && temp.getLeftType() == 0) {
                temp = temp.getLeft();
            }
            System.out.println(temp);
            while (temp.getRight() != null && temp.getRightType() == 1) {
                temp = temp.getRight();
                System.out.println(temp); // 持续输出后继节点
            }
            temp = temp.getRight();
        }
    }

    private void threadNodes(EmpNode node) {
        if (node == null) {
            return;
        }
        // 先线索化左节点
        if (node.getLeft() != null) {
            threadNodes(node.getLeft());
        }
        // 线索化当前节点的前驱后继关系
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        // 处理完当前节点的前驱后继关系, 将pre指向当前node
        pre = node;
        // 线索化右节点
        if (node.getRight() != null) {
            threadNodes(node.getRight());
        }
    }

}

class EmpNode {

    private int no;

    private String name;

    private EmpNode left;

    // 左节点类型,0表示左节点,1表示前驱节点
    private int leftType;

    private EmpNode right;

    // 右节点类型,0表示右节点,1表示后继节点
    private int rightType;

    public EmpNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public EmpNode getLeft() {
        return left;
    }

    public void setLeft(EmpNode left) {
        this.left = left;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public EmpNode getRight() {
        return right;
    }

    public void setRight(EmpNode right) {
        this.right = right;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    @Override
    public String toString() {
        return "EmpNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}