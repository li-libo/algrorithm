package treedemo;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-02-01 9:40 PM
 */
public class AvlTreeExample {

    public static void main(String[] args) {
        int[] array = {10, 11, 7, 6, 8, 9};
        AvlTree avlTree = new AvlTree();
        Arrays.stream(array).forEach(weight -> {
            avlTree.add(weight);
        });
        avlTree.midOrder();
        System.out.println("当前树的高度为: " + avlTree.getRoot().getHeight());
    }
}

class AvlTree {

    private AvlTreeNode root;

    public AvlTreeNode getRoot() {
        return root;
    }

    public void add(int weight) {
        if (root == null) {
            root = new AvlTreeNode(weight);
            return;
        }

        // 定义1个辅助指针
        AvlTreeNode temp = root;
        while (temp != null) {
            if (weight < temp.getWeight()) {
                if (temp.getLeft() == null) {
                    temp.setLeft(new AvlTreeNode(weight));
                    break;
                } else {
                    temp = temp.getLeft();
                }
            } else {
                if (temp.getRight() == null) {
                    temp.setRight(new AvlTreeNode(weight));
                    break;
                } else {
                    temp = temp.getRight();
                }
            }
        }
        // 双螺旋
        towRotate();
    }

    private void towRotate() {
        if (root.getLeftHeight() > root.getRightHeight() + 1) {
            if (root.getLeft() != null && root.getLeft().getRightHeight() > root.getLeft().getLeftHeight()) {
                leftRotate(root.getLeft());
            }
            rightRotate(root);
        } else if (root.getRightHeight() > root.getLeftHeight() + 1) {
            if (root.getRight() != null && root.getRight().getLeftHeight() > root.getRight().getRightHeight()) {
                rightRotate(root.getRight());
            }
            leftRotate(root);
        }
    }

    private void rightRotate(AvlTreeNode node) {
        AvlTreeNode newNode = new AvlTreeNode(node.getWeight());
        newNode.setRight(node.getRight());
        newNode.setLeft(node.getLeft().getRight());
        node.setWeight(node.getLeft().getWeight());
        node.setRight(newNode);
        node.setLeft(node.getLeft().getLeft());
    }

    private void leftRotate(AvlTreeNode node) {
        AvlTreeNode newNode = new AvlTreeNode(node.getWeight());
        newNode.setLeft(node.getLeft());
        newNode.setRight(node.getRight().getLeft());
        node.setWeight(node.getRight().getWeight());
        node.setLeft(newNode);
        node.setRight(node.getRight().getRight());
    }

    public void midOrder() {
        System.out.println("开始中序遍历...");
        if (root == null) {
            System.out.println("Avl树为空!");
            return;
        }
        midOrder(root);
        System.out.println("中序遍历结束!");
    }

    private void midOrder(AvlTreeNode node) {
        if (node.getLeft() != null) {
            midOrder(node.getLeft());
        }
        System.out.println(node);
        if (node.getRight() != null) {
            midOrder(node.getRight());
        }
    }
}

class AvlTreeNode {

    private int weight;

    private AvlTreeNode left;

    private AvlTreeNode right;

    public int getHeight() {
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int getLeftHeight() {
        return left == null ? 0 : left.getHeight();
    }

    public int getRightHeight() {
        return right == null ? 0 : right.getHeight();
    }

    public AvlTreeNode(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public AvlTreeNode getLeft() {
        return left;
    }

    public void setLeft(AvlTreeNode left) {
        this.left = left;
    }

    public AvlTreeNode getRight() {
        return right;
    }

    public void setRight(AvlTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "AvlTreeNode{" +
                "weight=" + weight +
                '}';
    }
}