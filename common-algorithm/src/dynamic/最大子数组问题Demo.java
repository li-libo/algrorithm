package dynamic;

import java.util.Arrays;

/**
 * 最大子数组
 * 力扣第 53 题「 最大子序和」问题和前文讲过的 经典动态规划：最长递增子序列 的套路非常相似，代表着一类比较特殊的动态规划问题的思路。
 * <p>
 * 题目如下：
 * <p>
 * 给你输入一个整数数组 nums，请你找在其中找一个和最大的子数组，返回这个子数组的和。
 * <p>
 * 函数签名如下：
 * <p>
 * int maxSubArray(int[] nums);
 * 比如说输入 nums = [-3,1,3,-1,2,-4,2]，算法返回 5，因为最大子数组 [1,3,-1,2] 的和为 5。
 *
 * @author lilibo
 * @create 2022-03-02 9:25 PM
 */
public class 最大子数组问题Demo {
    public static void main(String[] args) {
        int[] nums = {-3, 1, 3, -1, 2, -4, 2};
        System.out.println(Arrays.toString(nums) + "最大子数组和为: " + maxSubArray1(nums));
    }

    /**
     * 这道「最大子数组和」就和「最长递增子序列」非常类似，dp 数组的定义是以 nums[i] 为结尾的最大子数组和/最长递增子序列为 dp[i]。
     * 因为只有这样定义才能将 dp[i+1] 和 dp[i] 建立起联系，利用数学归纳法写出状态转移方程。
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        // 定义：dp[i] 记录以 nums[i] 为结尾的「最大子数组和」
        int[] dp = new int[n];
        // base case
        // 第一个元素前面没有子数组
        dp[0] = nums[0];
        // 状态转移方程
        for (int i = 1; i < n; i++) {
            // 要么自成一派，要么和前面的子数组合并
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }
        // 得到 nums 的最大子数组
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            maxSum = Math.max(maxSum, dp[i]);
        }
        return maxSum;
    }

    /**
     * 以上解法时间复杂度是 O(N)，空间复杂度也是 O(N)，较暴力解法已经很优秀了，不过注意到 dp[i] 仅仅和 dp[i-1] 的状态有关，
     * 那么我们可以施展前文动态规划的降维打击 讲的技巧进行进一步优化，将空间复杂度降低：
     * @param nums
     * @return
     */
    public static int maxSubArray1(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        // base case
        int dp_0 = nums[0];
        int dp_1 = 0, res = dp_0;

        for (int i = 1; i < n; i++) {
            // dp[i] = max(nums[i], nums[i] + dp[i-1])
            dp_1 = Math.max(nums[i], nums[i] + dp_0);
            dp_0 = dp_1;
            // 顺便计算最大的结果
            res = Math.max(res, dp_1);
        }
        return res;
    }

}
