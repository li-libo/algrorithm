package dynamic;

import java.util.Arrays;

/**
 * 给你 k 种面值的硬币，面值分别为 c1, c2 ... ck，每种硬币的数量无限，再给一个总金额 amount，问你最少需要几枚硬币凑出这个金额，如果不可能凑出，算法返回 -1 。算法的函数签名如下：
 * // coins 中是可选硬币面值，amount 是目标金额
 * int coinChange(int[] coins, int amount);
 * 比如说 k = 3，面值分别为 1，2，5，总金额 amount = 11。那么最少需要 3 枚硬币凑出，即 11 = 5 + 5 + 1。
 * @author lilibo
 * @create 2022-02-28 1:04 AM
 */
public class 零钱兑换问题Demo {

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int minCount = coinChanges3(coins, 11);
        System.out.println("最小次数为: " + minCount);
    }

    /**
     * 解法1
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange(int[] coins, int amount) {
        // 状态方程: n < 0 , min = -1; n = 0, min = 0; n > 0: dp[n] = dp[n -coin] + 1;
        return dp(coins, amount);
    }

    private static int dp(int[] coins, int amount) {
        if(amount < 0) {
            return -1;
        }
        if(amount == 0) {
            return 0;
        }
        // base case
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        int res = Integer.MAX_VALUE;
        for(int coin : coins) {
            // 计算子问题结果
            int subProblem = dp(coins, amount - coin);
            // 子问题无解则跳过
            if(subProblem == -1) {
                continue;
            }
            res = Math.min(res, subProblem + 1);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * 解法2. 借助备忘录优化空间
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange1(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -666);
        return dp1(coins, amount, memo);
    }

    private static int dp1(int[] coins, int amount, int[] memo) {
        if(amount < 0){
            return -1;
        }
        if(amount == 0) {
            return 0;
        }
        if(memo[amount] != -666) {
            return memo[amount];
        }
        int res = Integer.MAX_VALUE;
        for(int coin : coins) {
            int subProblem = dp1(coins, amount - coin, memo);
            if(subProblem == -1) {
                continue;
            }
            res = Math.min(res, subProblem + 1);
        }
        memo[amount] = (res == Integer.MAX_VALUE ? -1 : res);
        return memo[amount];
    }

    /**
     * 解法3 dp数组迭代解法
     * dp数组的定义: 当目标金额为i时, 至少需要dp[i]枚硬币凑出
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChanges3(int[] coins, int amount) {
        // 初始化dp数组
        int[] dp = new int[amount + 1];
                Arrays.fill(dp, Integer.MAX_VALUE);

        // base case
        dp[0] = 0;
        // 外层for循环在遍历所有的状态值
        for(int i = 0; i < dp.length; i++) {
            // 内层for循环在求所有选择最小值
            for(int coin : coins) {
                if(i - coin < 0) {
                    continue; // 无解
                }
                dp[i] = Math.min(dp[i], 1 + dp[i -coin]);
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
