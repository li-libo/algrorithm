package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一棵二叉搜索树，请你返回一棵平衡后的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。
 * 如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是平衡的 。
 * 如果有多种构造方法，请你返回任意一种。
 * @author lilibo
 * @create 2022-03-13 20:24
 */
public class 将二叉搜索树变平衡Demo {

    private List<Integer> inorderValList = new ArrayList<>();

    /**
     * 「平衡」要求它是一棵空树或它的左右两个子树的高度差的绝对值不超过 1，这很容易让我们产生这样的想法——左右子树的大小越平均，
     *  这棵树会不会越平衡？于是一种贪心策略的雏形就形成了：
     *  我们可以通过中序遍历将原来的二叉搜索树转化为一个有序序列，然后对这个有序序列递归建树，对于区间 [L,R]:
     *     取 mid = (L + R) / 2 ，即中心位置作为当前节点的值;
     *     如果 L <= mid - 1，那么递归地将区间[L, mid - 1]作为当前节点的左子树;
     *     如果 mid+1 <= R，那么递归地将区间[mid+1,R]作为当前节点的右子树。
     * @param root
     * @return
     */
    public TreeNode balanceBST(TreeNode root) {
        collectValByInOrder(root);
        return build(0, inorderValList.size() - 1);
    }

    private void collectValByInOrder(TreeNode node) {
        if(node.left != null) {
            collectValByInOrder(node.left);
        }
        inorderValList.add(node.val);
        if(node.right != null) {
            collectValByInOrder(node.right);
        }
    }

    public TreeNode build(int l, int r) {
        int mid = (l + r) / 2;
        TreeNode newNode = new TreeNode(inorderValList.get(mid));
        if(l < mid - 1) {
            newNode.left = build(l, mid - 1);
        }
        if(mid + 1 < r) {
            newNode.right = build(mid + 1, r);
        }
        return newNode;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {};
        TreeNode(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
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
                    "val=" + val +
                    '}';
        }
    }
}
