package dynamic;

import org.junit.Test;
import java.util.Arrays;

/**
 * House Robber
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * <p>
 * 来源：力扣（LeetCode）
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 * <p>
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 *
 * @author lilibo
 * @create 2022-03-03 19:50
 */
public class 打家劫舍Demo {

    @Test
    public void test1() {
        int[] array = {2, 5, 1, 2};
        System.out.println(Arrays.toString(array) + "打家劫舍最优解为: " + rob2(array));
    }

    public int rob1(int[] array) {
        return dp(array, 0);
    }

    /**
     * 状态函数dp[startIndex] = array[startIndex] + dp[array, startIndex + 2] // 选
     * = dp[array, startIndex + 1] //不选
     */
    private int dp(int[] array, int startIndex) {
        if (startIndex >= array.length) {
            return 0;
        }
        return Math.max(array[startIndex] + dp(array, startIndex + 2), dp(array, startIndex + 1));
    }

    /**
     * 打家劫舍问题2, 这些房子不是一排,而是围成1圈
     *
     * @param array
     */
    public int rob2(int[] array) {
        int n = array.length;
        if(n == 1) return array[0];
        return Math.max(robRange(array, 0, n - 2), robRange(array, 1, n - 1));
    }

    private int robRange(int[] nums, int start, int end) {
        int n = nums.length;
        int dp_i_1 = 0, dp_i_2 = 0; int dp_i = 0;
        for (int i = start; i <=end; i++) {
            dp_i = Math.max(dp_i_1, nums[i] + dp_i_2);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }
        return dp_i;
    }

    /**
     打家劫舍问题3 强盗发现现在⾯对的房⼦不是⼀排，不是 ⼀圈，⽽是⼀棵⼆叉树！房⼦在⼆叉树的节点上，相连的两个房⼦不能同时 被抢劫
     */
    public int rob3(TreeHorseNode node) {
        if (node == null) return 0;
        // 抢劫
        int do_it = node.getValue() + (node.getLeft() == null ? 0 : rob3(node.getLeft().getLeft()) + rob3(node.getLeft().getRight()))
                + (node.getRight() == null ? 0 : rob3(node.getRight().getLeft()) + rob3(node.getRight().getRight()));
        // 不抢,去下家
        int not_do = rob3(node.getLeft()) + rob3(node.getRight());
        int res = Math.max(do_it, not_do);
        return res;
    }

}

class TreeHorseNode {

    private TreeHorseNode left;

    private TreeHorseNode right;

    private int value;

    public TreeHorseNode getLeft() {
        return left;
    }

    public void setLeft(TreeHorseNode left) {
        this.left = left;
    }

    public TreeHorseNode getRight() {
        return right;
    }

    public void setRight(TreeHorseNode right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TreeHorseNode{" +
                "value=" + value +
                '}';
    }
}