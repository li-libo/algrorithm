package binarysorttree;

import java.util.Arrays;

/**
 * 二叉排序树：BST: (Binary Sort(Search) Tree), 对于二叉排序树的任何一个非叶子节点，要求左子节点的值比当前节点的值小，
 * 右子节点的值比当前节点的值大。 特别说明：如果有相同的值，可以将该节点放在左子节点或右子节点.
 * <p>
 * 如果使用数组或链表:
 * 数组
 * 数组未排序， 优点：直接在数组尾添加，速度快。 缺点：查找速度慢. 数组排序，
 * 优点：可以使用二分查找，查找速度快，缺点：为了保证数组有序，在添加新数据时，找到插入位置后，后面的数据需整体移动，速度慢。
 * 链表
 * 使用链式存储-链表, 不管链表是否有序，查找速度都慢，添加数据速度比数组快，不需要数据整体移动。
 *
 * @author lilibo
 * @create 2021-08-15 6:19 PM
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int[] array = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        Arrays.stream(array).forEach(x -> {
            binarySortTree.addNode(x);
        });
        binarySortTree.midOrder(); // 中序遍历排序二叉树(值从小到大排序)
        binarySortTree.delete(12);
        System.out.println("删除weight = 12结点后");
        binarySortTree.midOrder();
        binarySortTree.delete(5);
        binarySortTree.delete(10);
        binarySortTree.delete(2);
        binarySortTree.delete(3);
        binarySortTree.delete(9);
        binarySortTree.delete(1);
        binarySortTree.delete(7);
        System.out.println("删除结点后");
        binarySortTree.midOrder();
    }

}

class BinarySortTree {

    private BinarySortTreeNode root;

    public void addNode(int weight) {
        BinarySortTreeNode newNode = new BinarySortTreeNode(weight);
        if (root == null) {
            root = newNode;
            return;
        }
        addNode(root, newNode);
    }

    public void midOrder() {
        if (root == null) {
            System.out.println("二叉树为空...");
            return;
        }
        System.out.println("开始中序遍历...");
        midOrder(root);
    }

    /**
     * 删除节点, 删除节点比较复杂, 分为3种情况
     * (1) 要删除的节点为叶子节点
     * (2) 要删除的节点有1个子节点
     * (3) 要删除的节点有2个子节点
     *
     * @param weight
     */
    public void delete(int weight) {
        if (root == null) {
            System.out.println("该树为空树!");
            return;
        }
        // 先找到要删除的节点
        BinarySortTreeNode target = searchTarget(root, weight);
        if (target == null) {
            System.out.println("找不到要删除的weight = " + weight + "的节点!");
            return;
        }
        // 找到要删除节点的父节点
        BinarySortTreeNode parent = searchParent(root, weight);
        System.out.printf("weight = %d 的节点为%s, 其父节点为%s\n", weight, target, parent);
        // 被删除的节点为叶子节点
        if (target.getLeft() == null && target.getRight() == null) {
            if (parent == null) {
                root = null;
            } else if (parent.getLeft() == target) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
        // 被删除的节点只有1个子树
        else if ((target.getLeft() == null && target.getRight() != null) || (target.getRight() == null && target.getLeft() != null)) {
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
        }
        // 要删除的节点有两个子树
        else {
            // 获得并删除target右子树的最小值或左子树的最大值
//            int min = getMinAndDeleteInRight(target.getRight());
//            target.setValue(min);
            int max = getMaxAndDeleteInLeft(target.getLeft());
            target.setValue(max);
        }
    }

    private int getMaxAndDeleteInLeft(BinarySortTreeNode left) {
        BinarySortTreeNode max = left;
        while (max.getRight() != null) {
            max = max.getRight();
        }
        int maxValue = max.getValue();
        delete(maxValue);
        return maxValue;
    }

    private int getMinAndDeleteInRight(BinarySortTreeNode right) {
        BinarySortTreeNode min = right;
        while (min.getLeft() != null) {
            min = min.getLeft();
        }
        int minValue = min.getValue();
        delete(minValue);
        return minValue;
    }

    private BinarySortTreeNode searchParent(BinarySortTreeNode node, int weight) {
        if (node == null || root.getValue() == weight) {
            return null;
        }
        if (weight < node.getValue()) {
            if (node.getLeft() != null && node.getLeft().getValue() == weight) {
                return node;
            } else {
                return searchParent(node.getLeft(), weight);
            }
        } else {
            if (node.getRight() != null && node.getRight().getValue() == weight) {
                return node;
            } else {
                return searchParent(node.getRight(), weight);
            }
        }
    }

    private BinarySortTreeNode searchTarget(BinarySortTreeNode node, int weight) {
        if (node == null) {
            return null;
        }
        if (weight == node.getValue()) {
            return node;
        } else if (weight < node.getValue()) {
            return searchTarget(node.getLeft(), weight);
        } else {
            return searchTarget(node.getRight(), weight);
        }
    }

    private void midOrder(BinarySortTreeNode node) {
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

    private void addNode(BinarySortTreeNode node, BinarySortTreeNode newNode) {
        if (node == null) {
            return;
        }
        if (newNode.getValue() < node.getValue()) {
            if (node.getLeft() == null) {
                node.setLeft(newNode);
                return;
            } else {
                addNode(node.getLeft(), newNode);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(newNode);
                return;
            } else {
                addNode(node.getRight(), newNode);
            }
        }
    }
}

class BinarySortTreeNode {

    private int value;

    public BinarySortTreeNode(int value) {
        this.value = value;
    }

    private BinarySortTreeNode left;

    private BinarySortTreeNode right;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinarySortTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinarySortTreeNode left) {
        this.left = left;
    }

    public BinarySortTreeNode getRight() {
        return right;
    }

    public void setRight(BinarySortTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinarySortTreeNode{" +
                "value=" + value +
                '}';
    }

}