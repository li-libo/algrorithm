/**
 * @author lilibo
 * @create 2022-02-21 5:14 PM
 */
public class AvlTreeDemo {

    public static final String format = "树的高度为%d, 左子树的高度为%d, 右子树的高度为%d, 是否满足AVL树?%s";

    public static void main(String[] args) {
        int[] weightArray = {5, 3, 1, 2, 7, 4, 9};
        AvlTree avlTree = new AvlTree();
        for(int weight : weightArray) {
            avlTree.addNode(new AvlNode(weight));
        }
        // 开始前序遍历
        avlTree.preOrderPrint();
        boolean isAvlTreeFlag = avlTree.getRightHeight() - avlTree.getLeftHeight() <= 1 && avlTree.getLeftHeight() - avlTree.getRightHeight() <= 1;
        // 输出树的高度
        System.out.println(String.format(format, avlTree.getHeight(), avlTree.getLeftHeight(), avlTree.getRightHeight(), isAvlTreeFlag));
    }

}

class AvlTree {

    private AvlNode root;

    public int getHeight() {
        if(root == null) {
            return 0;
        }
        return root.getHeight();
    }

    public int getLeftHeight() {
        if(root == null || root.getLeft() == null) {
            return 0;
        }
        return root.getLeftHeight();
    }

    public int getRightHeight() {
        if(root == null || root.getRight() == null) {
            return 0;
        }
        return root.getRightHeight();
    }

    public void addNode(AvlNode newNode) {
        if(root == null) {
            root = newNode;
            return;
        }
        addNode(root, newNode);
        twoRotate();
    }

    private void twoRotate() {
        if(root.getRightHeight() > root.getLeftHeight() + 1) {
            if(root.getRight().getLeftHeight() > root.getRight().getRightHeight()) {
                root.getRight().rightRotate();
            }
            root.leftRotate();
        }else if(root.getLeftHeight() > root.getRightHeight() + 1) {
            if(root.getLeft().getRightHeight() > root.getLeft().getLeftHeight()) {
                root.getLeft().leftRotate();
            }
            root.rightRotate();
        }
    }

    private void addNode(AvlNode temp, AvlNode newNode) {
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
        System.out.println("开始前序遍历...");
        preOrderPrint(root);
        System.out.println("前序遍历结束...");
    }

    private void preOrderPrint(AvlNode temp) {
        if(temp == null) {
            return;
        }
        System.out.println(temp);
        if(temp.getLeft() != null) {
            preOrderPrint(temp.getLeft());
        }
        if(temp.getRight() != null) {
            preOrderPrint(temp.getRight());
        }
    }
}

class AvlNode {

    private AvlNode left;
    private AvlNode right;
    private int weight;

    public AvlNode(int weight) {
        this.weight = weight;
    }

    public AvlNode getLeft() {
        return left;
    }

    public void setLeft(AvlNode left) {
        this.left = left;
    }

    public AvlNode getRight() {
        return right;
    }

    public void setRight(AvlNode right) {
        this.right = right;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

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
        if(left == null) {
            return 0;
        }
        return left.getHeight();
    }

    public int getRightHeight() {
        if(right == null) {
            return 0;
        }
        return right.getHeight();
    }

    public void leftRotate() {
        AvlNode newNode = new AvlNode(weight);
        newNode.left = left;
        newNode.right = right.left;
        weight = right.weight;
        left = newNode;
        right = right.right;
    }

    public void rightRotate() {
        AvlNode newNode = new AvlNode(weight);
        newNode.right = right;
        newNode.left = left.right;
        weight = left.weight;
        left = left.left;
        right = newNode;
    }

    @Override
    public String toString() {
        return "AvlNode{" +
                "weight=" + weight +
                '}';
    }
}