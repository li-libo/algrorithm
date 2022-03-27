package dynamic;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-02-27 1:37 AM
 */
public class 斐波那契数列Demo {

    // 求斐波那契数列值
    public static void main(String[] args) {
        int n = 5;
        //System.out.println("n = " + 5 + "的斐波那契数列:" + Arrays.toString(fib1(5)));
        System.out.println("n = 5的斐波那契数列: " + Arrays.toString(fib1(5)));
    }

    public static int[] fib(int n) {
        // 备忘录初始化全为0
        int[] memo = new int[n + 1];
        // 进行带备忘录的递归
        helper(memo, n);
        return memo;
    }

    private static int helper(int[] memo, int n) {
        // base case
        if(n == 0 || n == 1) {
            return n;
        }
        // 已经计算过了不再计算
        if(memo[n] != 0) {
            return memo[n];
        }
        memo[n] = helper(memo, n -1) + helper(memo, n - 2);
        return memo[n];
    }

    public static int[] fib1(int n) {
        int[] dp = new int[n + 1];
        // base case
        dp[0] = 0;
        dp[1] = 1;
        // 状态转移
        for(int i = 2; i <= n; i++){
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp;
    }

    /**
     * 状态压缩优化
     * @param n
     * @return
     */
    public static int fib2(int n) {
        // base case
        if(n == 0 || n== 1) {
            return n;
        }
        int dp_i_1 = 0, dp_i_2 =1;
        for(int i = 2; i <= n; i++){
            // 状态方程dp[i] = dp[i-1] + dp[i-2]
            int dp_i = dp_i_1 + dp_i_2;
            // 滚动更新
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }
        return dp_i_1;
    }
}
