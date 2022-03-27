package dynamic;

import org.junit.Test;

/**
 * 给定一个整数数组 cost，其中 cost[i]是从楼梯第i个台阶向上爬需要支付的费用，下标从0开始。
 * 一旦你支付此费用，即可选择向上爬一个或者两个台阶。
 * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
 * 请你计算并返回达到楼梯顶部的最低花费。
 *
 * 输入：
 * [2,5,20]
 * 返回值：
 * 2
 * 说明：
 * 你将从下标为0的台阶开始，支付2 ，向上爬两个台阶，到达楼梯顶部。总花费为2
 *
 * @author lilibo
 * @create 2022-03-08 10:57 PM
 */
public class 最小楼梯花费Demo {

    @Test
    public void test1() {
        int[] cost = {10, 15, 20};
        int minCost = minCostMoney(cost);
        System.out.println("最低消费为: " + minCost);
    }

    /**
     * 分析:设状态方程dp[i]为达到i层楼梯最低花费, 则dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
     * @param cost
     * @return
     */
    public int minCostMoney(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        // base case
        dp[0] = 0;
        dp[1] = 0;
        for(int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[n];
    }
}
