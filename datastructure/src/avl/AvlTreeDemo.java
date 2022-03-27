package avl;

import java.time.chrono.MinguoChronology;

/**
 * @author lilibo
 * @create 2021-08-15 9:01 PM
 */
public class AvlTreeDemo {

    public static void main(String[] args) {
        int[] array = {10, 11, 7, 6, 8, 9};
        AvlTree avlTree = new AvlTree();
        for (int weight : array) {
            avlTree.addInRecursion(weight);
            //System.out.println("weight = " + weight + ", findTarget = " + avlTree.findTarget(weight));
        }
        avlTree.midOrder();
        String format = "weight = %d, target = %s";
        System.out.println(String.format(format, 8, avlTree.findTarget(8).getHeight()));
        System.out.println(String.format(format, 7, avlTree.findTarget(7).getHeight()));
        System.out.println(String.format(format, 10, avlTree.findTarget(10).getHeight()));

        avlTree.delNode(7);
        avlTree.midOrder();
        avlTree.delNode(8);
        avlTree.midOrder();
    }

}

class AvlTree {

    private AvlTreeNode root;

    public void add(int weight) {
        AvlTreeNode newNode = new AvlTreeNode(weight);
        if (root == null) {
            root = newNode;
            return;
        }
        // 定义1个辅助变量
        AvlTreeNode temp = root;
        while (temp != null) {
            if (weight < temp.getWeight()) {
                if (temp.getLeft() == null) {
                    temp.setLeft(newNode);
                    break;
                } else {
                    temp = temp.getLeft();
                }
            } else {
                if (temp.getRight() == null) {
                    temp.setRight(newNode);
                    break;
                } else {
                    temp = temp.getRight();
                }
            }
        }
        // 平衡调整,双螺旋
        twoRotate();
    }

    public void addInRecursion(int weight) {
        if (root == null) {
            root = new AvlTreeNode(weight);
            return;
        }
        addInRecursion(root, weight);
        // 平衡调整,双螺旋
        twoRotate();
    }

    /**
     * 删除节点比较复杂,分为3种情况
     * 1. 被删除节点没有叶子节点
     * 2. 被删除节点只有1个叶子节点
     * 3. 被删除节点有2个叶子节点
     *
     * @param weight
     */
    public void delNode(int weight) {
        AvlTreeNode target = findTarget(weight);
        AvlTreeNode parent = findParent(weight);
        if (target == null) {
            System.out.println("没有找到要删除的节点, weight = " + weight);
            return;
        }
        if (target.getLeft() == null && target.getRight() == null) {
            if (parent != null && parent.getLeft() == target) {
                parent.setLeft(null);
            } else if (parent != null && parent.getRight() == target) {
                parent.setRight(null);
            }
        } else if ((target.getLeft() != null && target.getRight() == null) || (target.getRight() != null && target.getLeft() == null)) {
            if (parent != null && parent.getLeft() == target) {
                if (target.getLeft() != null) {
                    parent.setLeft(target.getLeft());
                } else {
                    parent.setLeft(target.getRight());
                }
            } else if (parent != null && parent.getRight() == target) {
                if (target.getLeft() != null) {
                    parent.setRight(target.getLeft());
                } else {
                    parent.setRight(target.getRight());
                }
            }
        } else {
            // 查询并删除target左子树最大值或右子树最小值
            int min = deleteMinInRight(target.getRight());
            target.setWeight(min);
        }
    }

    private int deleteMinInRight(AvlTreeNode right) {
        AvlTreeNode min = right;
        while (min.getLeft() != null) {
            min = min.getLeft();
        }
        int minValue = min.getWeight();
        delNode(minValue);
        return minValue;
    }

    private AvlTreeNode findParent(int weight) {
        if (root == null) {
            return null;
        }
        if (root.getWeight() == weight) {
            return null;
        }
        // 定义1个辅助指针
        AvlTreeNode temp = root;
        while (temp != null) {
            if (weight < temp.getWeight()) {
                if (temp.getLeft() != null && temp.getLeft().getWeight() == weight) {
                    return temp;
                } else if (temp.getLeft() != null) {
                    temp = temp.getLeft();
                }
            } else {
                if (temp.getRight() != null && temp.getRight().getWeight() == weight) {
                    return temp;
                } else if (temp.getRight() != null) {
                    temp = temp.getRight();
                }
            }
        }
        return null;
    }

    private void addInRecursion(AvlTreeNode node, int weight) {
        if (node == null) {
            return;
        }
        if (weight < node.getWeight()) {
            if (node.getLeft() == null) {
                node.setLeft(new AvlTreeNode(weight));
                return;
            } else {
                addInRecursion(node.getLeft(), weight);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new AvlTreeNode(weight));
                return;
            } else {
                addInRecursion(node.getRight(), weight);
            }
        }
    }

    private void twoRotate() {
        if (root.getRightHeight() > root.getLeftHeight() + 1) {
            if (root.getRight().getLeftHeight() > root.getRight().getRightHeight()) {
                root.getRight().rightRotate();
            }
            root.leftRotate();
        }
        if (root.getLeftHeight() > root.getRightHeight() + 1) {
            if (root.getLeft().getRightHeight() > root.getLeft().getLeftHeight()) {
                root.getLeft().leftRotate();
            }
            root.rightRotate();
        }
    }

    public void midOrder() {
        if (root == null) {
            System.out.println("该树为空树!");
            return;
        }
        System.out.println("开始中序遍历...");
        midOrder(root);
    }

    public AvlTreeNode findTarget(int weight) {
        if (root == null) {
            return null;
        }
        // 定义1个辅助变量
        AvlTreeNode temp = root;
        while (temp != null) {
            if (weight == temp.getWeight()) {
                return temp;
            } else if (weight < temp.getWeight()) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
        }
        return null;
    }

    private void midOrder(AvlTreeNode node) {
        if (node == null) {
            return;
        }
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

    public int getLeftHeight() {
        return this.left == null ? 0 : this.left.getHeight();
    }

    public int getRightHeight() {
        return this.right == null ? 0 : this.right.getHeight();
    }

    public int getHeight() {
        int leftHeight = 0;
        int rightHeight = 0;
        if (this.left != null) {
            leftHeight = this.left.getHeight();
        }
        if (this.right != null) {
            rightHeight = this.right.getHeight();
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public void leftRotate() {
        AvlTreeNode newNode = new AvlTreeNode(weight);
        newNode.setLeft(left);
        newNode.setRight(right.left);
        weight = right.weight;
        left = newNode;
        right = right.right;
    }

    public void rightRotate() {
        AvlTreeNode newNode = new AvlTreeNode(weight);
        newNode.setRight(right);
        newNode.setLeft(left.right);
        weight = left.weight;
        left = left.left;
        right = newNode;
    }

    @Override
    public String toString() {
        return "AvlTreeNode{" +
                "weight=" + weight +
                '}';
    }
}