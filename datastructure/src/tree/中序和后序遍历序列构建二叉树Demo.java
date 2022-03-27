package tree;

import org.junit.Test;

/**
 * @author lilibo
 * @create 2022-03-15 7:27 PM
 */
public class 中序和后序遍历序列构建二叉树Demo {

    @Test
    public void test1() {
        int[] inorder = {9, 3, 15, 20, 7};
        int[] postOrder = {9, 15, 7, 20, 3};
        printTreeByPreOrder(buildTree(inorder, postOrder));
    }

    private int postIndex;

    public TreeNode buildTree(int[] inorder, int[] postOrder) {
        postIndex = postOrder.length - 1;
        return buildTree(inorder, postOrder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] inorder, int[] postOrder, int midStart, int midEnd) {
        if(postIndex < 0) {
            return null;
        }
        int flag = 0;
        while (inorder[flag] != postOrder[postIndex]) {
            flag++;
        }
        TreeNode node = new TreeNode(postOrder[postIndex]);
        postIndex--;
        if(flag < midEnd) {
            node.right = buildTree(inorder, postOrder, flag + 1, midEnd);
        }
        if(flag > midStart) {
            node.left = buildTree(inorder, postOrder, midStart, flag - 1);
        }
        return node;
    }

    public void printTreeByPreOrder(TreeNode node) {
        if(node == null) {
            return;
        }
        System.out.println(node);
        if(node.left != null) {
            printTreeByPreOrder(node.left);
        }
        if(node.right != null) {
            printTreeByPreOrder(node.right);
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
