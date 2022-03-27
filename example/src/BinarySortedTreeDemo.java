/**
 * @author lilibo
 * @create 2022-02-21 3:02 PM
 */
public class BinarySortedTreeDemo {

    private static final String format = "weight = %d 对应的target = %s, parentNode = %s, height = %d";

    public static void main(String[] args) {
        int[] arr = {6, 3, 1, 5, 7, 4, 9, 8};
        BinarySortedTree binarySortedTree = new BinarySortedTree();
        for(int weight : arr) {
            binarySortedTree.addNode(new Node(weight));
        }
        binarySortedTree.preOrderPrint();
        for(int weight : arr) {
            Node target = binarySortedTree.searchNode(weight);
            Node parentNode = binarySortedTree.searchParentNode(weight);
            System.out.println(String.format(format, weight, target, parentNode, target.getHeight()));
        }
        // 删除二叉排序树节点
        System.out.println("删除weight = 4的节点...");
        binarySortedTree.delNode(4);
        System.out.println("删除weight = 7的节点...");
        binarySortedTree.delNode(7);
        binarySortedTree.preOrderPrint();
        System.out.println("删除根节点...");
        binarySortedTree.delNode(6);
        binarySortedTree.preOrderPrint();
    }
}

class BinarySortedTree {

    private Node root;

    public void addNode(Node newNode) {
        if(root == null) {
            root = newNode;
            return;
        }
        addNode(root, newNode);
    }

    public Node searchNode(int weight) {
        if(root == null) {
            System.out.println("二叉树为空!");
            return null;
        }
        if(root.getWeight() == weight) {
            return root;
        }
        return searchNode(root, weight);
    }

    public Node searchParentNode(int weight) {
        if(root == null) {
            System.out.println("二叉树为空树!");
            return null;
        }
        if(root.getWeight() == weight) {
            return null;
        }
        return searchParentNode(root, weight);
    }

    /**
     * 删除二叉树内节点
     * @param weight
     */
    public void delNode(int weight) {
        if(root == null) {
            System.out.println("二叉树为空!");
            return;
        }
        if(root.getWeight() == weight) {
            root = null;
            return;
        }
        Node targetNode = searchNode(weight);
        if(targetNode == null) {
            System.out.println("找不到weight = " + weight + "对应的二叉树节点!");
            return;
        }
        Node parentNode = searchParentNode(weight);
        // 被删除的节点分为3种情况
        boolean isLeftNodeFlag = targetNode == parentNode.getLeft() ? true : false;
        // 1. 被删除的节点为叶子节点
        if(targetNode.getLeft() == null && targetNode.getRight() == null) {
            if(isLeftNodeFlag) {
                parentNode.setLeft(null);
            }else {
                parentNode.setRight(null);
            }
        }
        // 2.被删除的节点为单个子树节点
        else if((targetNode.getLeft()!= null && targetNode.getRight() == null) || (targetNode.getRight()!=null && targetNode.getLeft() == null)) {
            if(isLeftNodeFlag) {
                if(targetNode.getLeft() != null) {
                    parentNode.setLeft(targetNode.getLeft());
                }else {
                    parentNode.setLeft(targetNode.getRight());
                }
            }else {
                if(targetNode.getLeft() != null) {
                    parentNode.setRight(targetNode.getLeft());
                }else {
                    parentNode.setRight(targetNode.getRight());
                }
            }
        }
        // 3. 被删除的节点有2个子树
        else {
            // 找到target左子树最大值或右子树最小值
            int returnValue = getMaxAndDelete(targetNode.getLeft());
            if(returnValue != -1) {
                targetNode.setWeight(returnValue);
            }
        }
    }

    private int getMaxAndDelete(Node temp) {
        if(temp == null) {
            return -1;
        }
        while (temp.getRight() != null) {
            temp = temp.getRight();
        }
        int returnValue = temp.getWeight();
        delNode(returnValue);
        return returnValue;
    }

    private Node searchParentNode(Node temp, int weight) {
        if(temp == null) {
            return null;
        }
        if(temp.getLeft()!=null && temp.getLeft().getWeight() == weight) {
            return temp;
        }
        if(temp.getRight()!= null && temp.getRight().getWeight() == weight) {
            return temp;
        }
        if(weight < temp.getWeight()) {
            return searchParentNode(temp.getLeft(), weight);
        }else {
            return searchParentNode(temp.getRight(), weight);
        }
    }

    private Node searchNode(Node temp, int weight) {
        if(temp == null) {
            return null;
        }
        if(temp.getWeight() == weight) {
            return temp;
        }
        if(weight < temp.getWeight() && temp.getLeft() != null) {
            return searchNode(temp.getLeft(), weight);
        }
        if(weight > temp.getWeight() && temp.getRight() != null) {
            return searchNode(temp.getRight(), weight);
        }
        return null;
    }

    private void addNode(Node temp, Node newNode) {
        if(temp == null) {
            return;
        }
        if(newNode.getWeight() < temp.getWeight()) {
            if(temp.getLeft() == null) {
                temp.setLeft(newNode);
            }else {
                addNode(temp.getLeft(), newNode);
            }
        }else {
            if(temp.getRight() == null) {
                temp.setRight(newNode);
            }else {
                addNode(temp.getRight(), newNode);
            }
        }
    }

    public void preOrderPrint() {
        if(root == null) {
            System.out.println("二叉树为空树!");
            return;
        }
        System.out.println("开始前序遍历二叉树...");
        preOrderPrint(root);
        System.out.println("前序遍历二叉树完毕...");
    }

    private void preOrderPrint(Node temp) {
        if(temp == null) {
            return;
        }
        if(temp.getLeft() != null) {
            preOrderPrint(temp.getLeft());
        }
        System.out.println(temp);
        if(temp.getRight() != null) {
            preOrderPrint(temp.getRight());
        }
    }
}

class Node {
    private Node left;
    private Node right;
    private int weight;

    public int getHeight() {
        int leftHeight = 0;
        int rightHeight = 0;
        if(left != null) {
            leftHeight = left.getHeight();
        }
        if(right != null) {
            rightHeight = right.getHeight();
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int getLeftHeight() {
        if(left != null) {
            return left.getHeight();
        }
        return 0;
    }

    public int getRightHeight() {
        if(right != null) {
            return right.getHeight();
        }
        return 0;
    }

    public Node(int weight) {
        this.weight = weight;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "weight=" + weight +
                '}';
    }
}