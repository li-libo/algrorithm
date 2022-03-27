package tree;

import org.junit.Test;

/**
 * 124. 二叉树中的最大路径和
 * 路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中至多出现一次。
 * 该路径至少包含一个节点，且不一定经过根节点。
 * 路径和是路径中各节点值的总和。
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 *
 * 输入：root = [1,2,3]
 * 输出：6
 * 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
 *
 * 输入：root = [-10,9,20,null,null,15,7]
 * 输出：42
 * 解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42

 * @author lilibo
 * @create 2022-03-13 17:12
 */
public class 二叉树中的最大路径和Demo {

    @Test
    public void test1() {
        TreeNode node1 = new TreeNode(-10);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);
        node1.setLeft(node2);
        node1.setRight(node3);
        node3.setLeft(node4);
        node3.setRight(node5);
        System.out.println("最大路径和为: " + maxPathSum(node1));
    }

    public int maxPathSum(TreeNode node) {
        maxGain(node);
        return maxSum;
    }

    private int maxSum = Integer.MIN_VALUE;

    public int maxGain(TreeNode node) {
        if(node == null) {
            return 0;
        }
        // 递归计算左右子节点的最大贡献值
        // 只有最大贡献值 > 0时, 才会选取对应子节点
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);
        // 节点的最大路径和取决于该节点的值与该节点左右子树的最大贡献值
        int nodeGain = node.value + leftGain + rightGain;
        // 更新最大路径和
        maxSum = Math.max(nodeGain, maxSum);
        // 返回节点的最大贡献值
        return nodeGain;
    }

    class TreeNode {

        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
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
                    "value=" + value +
                    '}';
        }
    }
}
