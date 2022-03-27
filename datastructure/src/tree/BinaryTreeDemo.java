package tree;
/**
 * @author lilibo
 * @create 2021-08-12 8:27 PM
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        //创建需要的结点
        TreeNode root = new TreeNode(1, "宋江");
        TreeNode node2 = new TreeNode(2, "吴用");
        TreeNode node3 = new TreeNode(3, "卢俊义");
        TreeNode node4 = new TreeNode(4, "林冲");
        TreeNode node5 = new TreeNode(5, "关胜");

        //说明，先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);

        // 前/中/后序遍历
        BinaryTree binaryTree = new BinaryTree(root);
        binaryTree.preOrder();
        binaryTree.midOrder();
        binaryTree.postOrder();

        // 前/中/后序查找
        System.out.println("binaryTree.preSearch(3) = " + binaryTree.preSearch(3));
        System.out.println("binaryTree.midSearch(5) = " + binaryTree.midSearch(5));
        System.out.println("binaryTree.postSearch(1) = " + binaryTree.postSearch(1));

        // 测试删除节点
        System.out.println("删除no = 2、5的节点");
        binaryTree.delNode(5);
        binaryTree.delNode(2);
        binaryTree.preOrder();
        // 删除根节点
        binaryTree.delNode(1);
        binaryTree.postOrder();
    }

}

class BinaryTree {

    private TreeNode root;

    public BinaryTree(TreeNode root) {
        this.root = root;
    }

    public void postOrder() {
        if (root == null) {
            System.out.println("本树根节点为null!");
            return;
        }
        System.out.println("开始后序遍历...");
        postOrder(root);
    }

    public void preOrder() {
        if (root == null) {
            System.out.println("本树root节点为空!!!");
            return;
        }
        System.out.println("开始前序遍历...");
        preOrder(root);
    }

    public TreeNode preSearch(int no) {
        if (root == null) {
            return null;
        }
        System.out.println("开始前序查找...");
        return preSearch(root, no);
    }

    public TreeNode postSearch(int no) {
        if (root == null) {
            return null;
        }
        System.out.println("开始后序查找...");
        return postSearch(root, no);
    }

    public void midOrder() {
        if (root == null) {
            return;
        }
        midOrder(root);
    }

    public TreeNode midSearch(int no) {
        if (root == null) {
            return null;
        }
        System.out.println("开始中序查找...");
        return midSearch(root, no);
    }

    /**
     * 删除指定no序号的TreeNode
     * 规定:
     * (1) 如果删除no节点为叶子节点, 则直接删除该节点
     * (2) 如果删除no节点为非叶子节点, 则删除该子树
     * @param no
     */
    public void delNode(int no) {
        if(root == null) {
            System.out.println("该树为空树!");
            return;
        }
        if(root.getNo() == no) {
            root = null;
            return;
        }
        delNode(root, no);
    }

    /**
     * 删除节点,因为二叉树是单向的,因此不能找到no对应的TreeNode删除, 需要找到要删除节点的父节点然后将对应的左/右节点置空
     * @param node
     * @param no
     */
    private void delNode(TreeNode node, int no) {
        if(node == null) {
            return;
        }
        if(node.getLeft() != null) {
            if(node.getLeft().getNo() == no) {
                node.setLeft(null);
                return;
            }else {
                delNode(node.getLeft(), no);
            }
        }
        if(node.getRight() != null) {
            if(node.getRight().getNo() == no) {
                node.setRight(null);
                return;
            }else {
                delNode(node.getRight(), no);
            }
        }
    }


    private TreeNode midSearch(TreeNode node, int no) {
        if (node == null) {
            return null;
        }
        TreeNode target = null;
        if (node.getLeft() != null) {
            target = midSearch(node.getLeft(), no);
        }
        if (target != null) {
            return target;
        }
        if (node.getNo() == no) {
            return node;
        }
        if (node.getRight() != null) {
            return midSearch(node.getRight(), no);
        }
        return null;
    }

    private void midOrder(TreeNode node) {
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

    private TreeNode postSearch(TreeNode node, int no) {
        if (node == null) {
            return null;
        }
        TreeNode target = null;
        if (node.getLeft() != null) {
            target = postSearch(node.getLeft(), no);
        }
        if (target != null) {
            return target;
        }
        if (node.getNo() == no) {
            return node;
        }
        if (node.getRight() != null) {
            return preSearch(node.getRight(), no);
        }
        return null;
    }

    private TreeNode preSearch(TreeNode node, int no) {
        if (node == null) {
            return null;
        }
        if (node.getNo() == no) {
            return node;
        }
        TreeNode target = null;
        if (node.getLeft() != null) {
            target = preSearch(node.getLeft(), no);
        }
        if (target != null) {
            return target;
        }
        if (node.getRight() != null) {
            return preSearch(node.getRight(), no);
        }
        return null;
    }

    private void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.getLeft() != null) {
            postOrder(node.getLeft());
        }
        if (node.getRight() != null) {
            postOrder(node.getRight());
        }
        System.out.println(node);
    }

    private void preOrder(TreeNode node) {
        if (node == null) return;
        System.out.println(node);
        if (node.getLeft() != null) {
            preOrder(node.getLeft());
        }
        if (node.getRight() != null) {
            preOrder(node.getRight());
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}

class TreeNode {

    private int no;

    private String name;

    private TreeNode left;

    private TreeNode right;

    public TreeNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

}