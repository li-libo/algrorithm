package treedemo;

/**
 * @author lilibo
 * @create 2022-01-30 3:34 PM
 */
public class ThreadBinaryTreeExample {

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
        ThreadBinaryTree threadedBinaryTree = new ThreadBinaryTree(root);
        threadedBinaryTree.threadTree();
        //测试: 以10号节点测试
        EmpNode leftNode = node5.getLeft();
        EmpNode rightNode = node5.getRight();
        System.out.println("10号结点的前驱结点是 = " + leftNode); //3
        System.out.println("10号结点的后继结点是 = " + rightNode); //1
        // 传统中序遍历二叉树
        threadedBinaryTree.midOrder();
        // 线索化中序遍历二叉树
        threadedBinaryTree.threadMidOrder();
    }
}

class ThreadBinaryTree {

    private EmpNode root;

    public ThreadBinaryTree(EmpNode root) {
        this.root = root;
    }


    private EmpNode pre;

    

    /**
     * 中序线索化二叉树
     */
    public void threadTree() {
        if(root == null) {
            return;
        }
        threadTree(root);
    }

    /**
     * 传统中序遍历二叉树
     */
    public void midOrder() {
        if(root == null) {
            return;
        }
        System.out.println("传统方式中序遍历线索化二叉树...");
        midOrder(root);
    }

    public void threadMidOrder() {
        if(root == null) {
            return;
        }
        System.out.println("线索化中序遍历二叉树...");
        threadMidOrder(root);
    }

    private void threadMidOrder(EmpNode node) {
        if(node == null) {
            return;
        }
        while (node != null) {
            // 先找到最左边节点
            while (node.getLeft() != null && node.getLeftType() == 0) {
                node = node.getLeft();
            }
            System.out.println(node);
            // 持续输出后继节点
            while (node.getRight() != null && node.getRightType() == 1) {
                node = node.getRight();
                System.out.println(node);
            }
            node = node.getRight();
        }
    }

    private void midOrder(EmpNode node) {
        if(node == null) {
            return;
        }
        if(node.getLeft() != null && node.getLeftType() == 0) {
            midOrder(node.getLeft());
        }
        System.out.println(node);
        if(node.getRight() != null && node.getRightType() == 0) {
            midOrder(node.getRight());
        }
    }

    private void threadTree(EmpNode node) {
        // 左递归
        if(node.getLeft() != null) {
            threadTree(node.getLeft());
        }
        // 处理当前节点和pre的前驱后继关系
        if(node.getLeft() == null && pre != null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        if(pre!=null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        // 处理完当前node和pre,后移pre
        pre = node;
        // 右递归
        if(node.getRight()!=null) {
            threadTree(node.getRight());
        }
    }

}

class EmpNode {
    private int id;
    private String name;

    private EmpNode left;

    // leftType == 0 为普通节点, 1为前驱节点
    private int leftType;

    private EmpNode right;

    // rightType = 0 为普通节点, 1为后继节点
    private int rightType;

    public EmpNode(int id, String name) {
        this.id = id;
        this.name = name;
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
