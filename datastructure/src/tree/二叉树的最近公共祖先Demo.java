package tree;

import org.junit.Test;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大
 * （一个节点也可以是它自己的祖先）
 * @author lilibo
 * @create 2022-03-15 3:16 PM
 */
public class 二叉树的最近公共祖先Demo {

    @Test
    public void test1() {
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node1 = new TreeNode(1);
        node3.left = node5;
        node3.right = node1;
        System.out.println("5和1的最近公共祖先为: " + lowestCommonAncestor(node3, node5, node1).val);
    }

    /**
     * 判断root是否为p、q的祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return false;
        }
        // 在当前节点
        boolean inCurrentNode = root.val == p.val || root.val == q.val;
        // 在左节点
        boolean inLeft = dfs(root.left, p, q);
        // 在右节点
        boolean inRight = dfs(root.right, p, q);
        // 更新ans,因为是从底向上更新,因此ans为深度最大的最近公共祖先
        if((inLeft && inRight) || (inCurrentNode && (inLeft || inRight))) {
            ans = root;
        }
        return inLeft || inRight || inCurrentNode;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return ans;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    private TreeNode ans;

}

