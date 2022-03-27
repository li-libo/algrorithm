package treedemo;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-02-01 6:00 PM
 */
public class SortBinaryTreeExample {

    public static void main(String[] args) {
        int[] array = {7, 3, 10, 12, 5, 1, 9, 2};
        SortBinaryTree sortBinaryTree = new SortBinaryTree();
        Arrays.stream(array).forEach(weight -> {
            sortBinaryTree.addNode(weight);
        });
        sortBinaryTree.midOrder();
        Arrays.stream(array).forEach(weight -> {
            System.out.println("weight = " + weight + "的节点为: " + sortBinaryTree.getNode(weight) + ", 其父节点为: " + sortBinaryTree.getParent(weight));
        });
        sortBinaryTree.delete(5);
        sortBinaryTree.delete(10);
        sortBinaryTree.delete(2);
        sortBinaryTree.delete(3);
        sortBinaryTree.delete(9);
        sortBinaryTree.delete(1);
        sortBinaryTree.delete(7);
        System.out.println("删除所有节点后...");
        sortBinaryTree.midOrder();
    }
}

class SortBinaryTree {

    private SortBinaryTreeNode root;

    public void addNode(int weight) {
        if (root == null) {
            root = new SortBinaryTreeNode(weight);
            return;
        }
        addNode(root, weight);
    }

    public void midOrder() {
        System.out.println("开始中序遍历二叉排序树...");
        if (root == null) {
            return;
        }
        midOrder(root);
        System.out.println("中序遍历二叉树结束...");
    }

    public SortBinaryTreeNode getNode(int weight) {
        if (root == null) {
            return null;
        }
        if (root.getWeight() == weight) {
            return root;
        }
        return getNode(root, weight);
    }

    public SortBinaryTreeNode getParent(int weight) {
        if (root == null) {
            return null;
        }
        if (root.getWeight() == weight) {
            return null;
        }
        return getParent(root, weight);
    }

    public void delete(int weight) {
        if (root == null) {
            return;
        }
        if (root.getWeight() == weight) {
            root = null;
            return;
        }
        SortBinaryTreeNode target = getNode(weight);
        if (target == null) {
            System.out.println("找不到weight = " + weight + "对应的节点!");
            return;
        }
        SortBinaryTreeNode parent = getParent(weight);
        if (parent == null) {
            root = null;
            return;
        }
        // 1. target为叶子节点
        if (target.getLeft() == null && target.getRight() == null) {
            if (parent.getLeft() == target) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
        // 2. target只有1个节点
        else if ((target.getLeft() != null && target.getRight() == null) && (target.getRight() != null && target.getLeft() == null)) {
            if(target.getLeft()!=null && target.getRight() == null) {
                if(parent.getLeft() == target) {
                    parent.setLeft(target.getLeft());
                }else {
                    parent.setRight(target.getLeft());
                }
            }else if(target.getRight()!=null && target.getLeft() == null) {
                if(parent.getLeft() == target) {
                    parent.setLeft(target.getRight());
                }else {
                    parent.setRight(target.getRight());
                }
            }
        }
        // 3. target有2个节点
        else if(target.getLeft() != null && target.getRight() !=null) {
            // 寻找并删除target左子树最大值节点, 并将最大值返回
            int max = getAndDeleteMaxInLeft(target.getLeft());
            target.setWeight(max);
        }
    }

    public int getAndDeleteMaxInLeft(SortBinaryTreeNode left) {
        SortBinaryTreeNode node = left;
        while (node.getRight()!=null) {
            node = node.getRight();
        }
        int max = node.getWeight();
        delete(max);
        return max;
    }

    private SortBinaryTreeNode getParent(SortBinaryTreeNode node, int weight) {
        if (weight < node.getWeight()) {
            if (node.getLeft() != null && node.getLeft().getWeight() == weight) {
                return node;
            }
            if (node.getLeft() != null) {
                return getParent(node.getLeft(), weight);
            }
        } else {
            if (node.getRight() != null && node.getRight().getWeight() == weight) {
                return node;
            }
            if (node.getRight() != null) {
                return getParent(node.getRight(), weight);
            }
        }
        return null;
    }

    private SortBinaryTreeNode getNode(SortBinaryTreeNode node, int weight) {
        if (node == null) {
            return null;
        }
        if (weight < node.getWeight()) {
            if (node.getLeft() != null && node.getLeft().getWeight() == weight) {
                return node.getLeft();
            }
            if (node.getLeft() != null) {
                return getNode(node.getLeft(), weight);
            }
        } else {
            if (node.getRight() != null && node.getRight().getWeight() == weight) {
                return node.getRight();
            }
            if (node.getRight() != null) {
                return getNode(node.getRight(), weight);
            }
        }
        return null;
    }

    private void midOrder(SortBinaryTreeNode node) {
        if (node.getLeft() != null) {
            midOrder(node.getLeft());
        }
        System.out.println(node);
        if (node.getRight() != null) {
            midOrder(node.getRight());
        }
    }

    private void addNode(SortBinaryTreeNode node, int weight) {
        if (node == null) {
            return;
        }
        if (weight < node.getWeight()) {
            if (node.getLeft() == null) {
                node.setLeft(new SortBinaryTreeNode(weight));
                return;
            } else {
                addNode(node.getLeft(), weight);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(new SortBinaryTreeNode(weight));
                return;
            } else {
                addNode(node.getRight(), weight);
            }
        }
    }
}

class SortBinaryTreeNode {

    private int weight;

    public SortBinaryTreeNode(int weight) {
        this.weight = weight;
    }

    private SortBinaryTreeNode left;

    private SortBinaryTreeNode right;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public SortBinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(SortBinaryTreeNode left) {
        this.left = left;
    }

    public SortBinaryTreeNode getRight() {
        return right;
    }

    public void setRight(SortBinaryTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "SortBinaryTreeNode{" +
                "weight=" + weight +
                '}';
    }
}