package treedemo;

import java.lang.annotation.Target;

/**
 * @author lilibo
 * @create 2022-01-30 10:19 AM
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        // 创建需要的节点
        TreeNode node1 = new TreeNode(1, "宋江");
        TreeNode node2 = new TreeNode(2, "卢俊义");
        TreeNode node3 = new TreeNode(3, "吴用");
        TreeNode node4 = new TreeNode(4, "公孙胜");
        TreeNode node5 = new TreeNode(5, "关胜");
        // 手动创建2叉🌲
        node1.setLeft(node2);
        node1.setRight(node3);
        node3.setLeft(node5);
        node3.setRight(node4);
        System.out.println("前序遍历...");
        binaryTree.preOrder(node1);
        System.out.println("中序遍历...");
        binaryTree.midOrder(node1);
        System.out.println("后序遍历...");
        binaryTree.postOrder(node1);
        System.out.println("前序搜索...");
        TreeNode target = binaryTree.preSearch(node1, 5);
        System.out.println("no == 5的target为: " + target);
        System.out.println("中序搜索...");
        target = binaryTree.midSearch(node1, 5);
        System.out.println("no == 5的target为: " + target);
        System.out.println("后序搜索...");
        target = binaryTree.postSearch(node1, 5);
        System.out.println("no == 5的target为: " + target);
        System.out.println("删除5号和3号...");
        binaryTree.deleteNode(node1, 5);
        binaryTree.deleteNode(node1, 3);
        binaryTree.preOrder(node1);
    }
}

class BinaryTree {
    private TreeNode root;

    public void addNode(TreeNode newNode) {
        if(root == null) {
            root = newNode;
            return;
        }
        addNode(root, newNode);
    }

    public void postOrder() {
        if(root == null) {
            return;
        }
        postOrder(root);
    }

    public void postOrder(TreeNode temp) {
        if(temp == null) {
            return;
        }
        if(temp.getLeft() != null) {
            postOrder(temp.getLeft());
        }
        if(temp.getRight() != null) {
            postOrder(temp.getRight());
        }
        System.out.println(temp);
    }

    public TreeNode preSearch(TreeNode temp, int no) {
        if(temp == null) {
            return null;
        }
        if(temp.getId() == no) {
            return temp;
        }
        if(temp.getLeft() != null) {
            TreeNode target = preSearch(temp.getLeft(), no);
            if(target != null) {
                return target;
            }
        }
        return preSearch(temp.getRight(), no);
    }

    public TreeNode midSearch(TreeNode temp, int no) {
        if(temp == null) {
            return null;
        }
        if(temp.getLeft() != null) {
            TreeNode target = midSearch(temp.getLeft(), no);
            if(target!=null) {
                return target;
            }
        }
        if(temp.getId() == no) {
            return temp;
        }
        return midSearch(temp.getRight(), no);
    }

    public TreeNode postSearch(TreeNode temp, int no) {
        if(temp == null) {
            return null;
        }
        if(temp.getLeft() != null) {
            TreeNode target = postSearch(temp.getLeft(), no);
            if(target!=null) {
                return target;
            }
        }
        if(temp.getRight() != null) {
            TreeNode target = postSearch(temp.getRight(), no);
            if(target!=null) {
                return target;
            }
        }
        if(temp.getId() == no) {
            return temp;
        }
        return null;
    }

    private void addNode(TreeNode temp, TreeNode newNode) {
        if(temp == null || newNode == null) {
            return;
        }
        int id = newNode.getId();
        if(id < temp.getId()) {
            if(temp.getLeft()!=null){
                addNode(temp.getLeft(), newNode);
            }else {
                temp.setLeft(newNode);
            }
        }else {
            if(temp.getRight() != null){
                addNode(temp.getRight(), newNode);
            }else {
                temp.setRight(newNode);
            }
        }
    }

    public void preOrder() {
        if(root == null) {
            return;
        }
        System.out.println("开始前序遍历...");
        preOrder(root);
        System.out.println("前序遍历结束!");
    }

    public void preOrder(TreeNode temp) {
        if(temp == null) {
            return;
        }
        System.out.println(temp);
        preOrder(temp.getLeft());
        preOrder(temp.getRight());
    }

    public void midOrder() {
        if(root == null) {
            return;
        }
        midOrder(root);
    }

    public void midOrder(TreeNode temp) {
        if(temp == null) {
            return;
        }
        if(temp.getLeft() != null) {
            midOrder(temp.getLeft());
        }
        System.out.println(temp);
        if(temp.getRight() != null) {
            midOrder(temp.getRight());
        }
    }

    /**
     * 因为该树是单向的, 有左右指针没有前驱指针, 因此需要找到被删除节点的父节点将左/右子树置null
     * @param node
     * @param no
     */
    public void deleteNode(TreeNode node, int no) {
        if(node == null) {
            return;
        }
        if(node.getLeft()!=null && node.getLeft().getId() == no) {
            node.setLeft(null);
            return;
        }
        if(node.getRight()!=null && node.getRight().getId() == no) {
            node.setRight(null);
            return;
        }
        if(node.getLeft()!=null) {
            deleteNode(node.getLeft(), no);
        }
        if(node.getRight()!=null) {
            deleteNode(node.getRight(), no);
        }
    }

}

class TreeNode {
    private int id;
    private String name;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int id, String name) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}