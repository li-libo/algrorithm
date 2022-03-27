package tree;

/**
 * @author lilibo
 * @create 2022-03-13 1:35 AM
 */
public class 另一棵树的子树Demo {

    /**
     * 方法一：深度优先搜索暴力匹配
     * 思路和算法
     * 这是一种最朴素的方法——深度优先搜索枚举 s中的每一个节点，判断这个点的子树是否和 t 相等。如何判断一个节点的子树是否和 t 相等呢，
     * 我们又需要做一次深度优先搜索来检查，即让两个指针一开始先指向该节点和 t 的根，然后「同步移动」两根指针来「同步遍历」这两棵树，判断对应位置是否相等。
     * @param s
     * @param t
     * @return
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return dfs(s, t);
    }


    private boolean dfs(TreeNode s, TreeNode t) {
        if(s == null) {
            return false;
        }
        return check(s, t) || dfs(s.left, t) || dfs(s.right, t);
    }

    private boolean check(TreeNode s, TreeNode t) {
        if(s == null && t == null) {
            return true;
        }
        if(s == null || t == null || s.val != t.val) {
            return false;
        }
        return check(s.left, t.left) && check(s.right, t.right);
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
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
