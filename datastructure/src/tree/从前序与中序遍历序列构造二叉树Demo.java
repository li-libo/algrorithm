package tree;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 * @author lilibo
 * @create 2022-03-15 6:40 PM
 */
public class 从前序与中序遍历序列构造二叉树Demo {

    @Test
    public void test1() {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        printTreeByPreOrder(buildTree(preorder, inorder));
    }

    private int preIndex = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
       return buildTree(preorder, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int midStart, int midEnd) {
        if(preIndex >= preorder.length) {
            return null;
        }
        int flag = 0;
        while (inorder[flag] != preorder[preIndex]) {
            flag++;
        }
        TreeNode node = new TreeNode(inorder[flag]);
        preIndex++;
        if(flag > midStart) {
            node.left = buildTree(preorder, inorder, midStart, flag - 1);
        }
        if(midEnd > flag) {
            node.right = buildTree(preorder, inorder, flag + 1, midEnd);
        }
        return node;
    }


    public void printTreeByPreOrder(TreeNode node) {
        //System.out.println("开始前序遍历...");
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

        public TreeNode(int val) {
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
