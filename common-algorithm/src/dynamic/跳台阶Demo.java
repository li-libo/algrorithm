package dynamic;

import org.junit.Test;

/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 * 数据范围：1≤n≤40
 * 要求：时间复杂度：O(n)，空间复杂度：O(1)
 * 2
 * 2
 * 7
 * 21
 * @author lilibo
 * @create 2022-03-08 10:17 PM
 */
public class 跳台阶Demo {

    @Test
    public void test1() {
        System.out.println(jumpFloor(7));
    }

    /**
     * 分析,如果跳到第n层,那么可由n - 1层跳到第n层, 也可以由n - 2层跳到第n层
     * 假设dp[n]为跳到第n层的方法,则dp[n] = dp[n - 1] + dp[n - 2]
     * @param target
     * @return
     */
    public int jumpFloor(int target) {
        if(target <= 2) return target;
        return jumpFloor(target - 1) + jumpFloor(target - 2);
    }
}
