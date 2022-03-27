package dynamic;

import org.junit.Test;

/**
 * 难度困难
 * <p>
 * 有 n 个气球，编号为0 到 n-1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
 * <p>
 * 现在要求你戳破所有的气球。如果你戳破气球 i ，就可以获得 nums[left] * nums[i] * nums[right] 个硬币。 这里的 left 和 right 代表和 i 相邻的两个气球的序号。注意当你戳破了气球 i 后，气球 left 和气球 right 就变成了相邻的气球。
 * <p>
 * 求所能获得硬币的最大数量。
 * <p>
 * 说明:
 * <p>
 * 你可以假设 nums[-1] = nums[n] = 1，但注意它们不是真实存在的所以并不能被戳破。
 * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 1008
 * <p>
 * 输入: [3,1,5,8]
 * 输出: 167
 * 解释: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 * Poke the balloon
 *
 * @author lilibo
 * @create 2022-03-03 17:50
 */
public class 戳气球Demo {

    @Test
    public void test1() {
        int[] nums = {3, 1, 5, 8};
        pokeBalloon(nums);
    }

    public void pokeBalloon(int[] num) {
        int length = num.length;
        // 在2端加入虚拟气球
        int[] points = new int[length + 2];
        points[0] = 1;
        points[points.length - 1] = 1;
        for (int i = 1; i < points.length - 1; i++) {
            points[i] = num[i - 1];
        }
        // 状态方程, dp[i][j]定义为在i、j区间内戳破气球的最大分数(不包含i、j)
        // 遍历k取dp最大值, i<k<j
        // dp[i][j] = dp[i][k] + dp[k][j] + points[i] * points[k]* points[j]
        // dp[i][j] 所依赖的状态是 dp[i][k] 和 dp[k][j]，那么我们必须保证：在计算 dp[i][j] 时，dp[i][k] 和 dp[k][j] 已经被计算出来了（其中 i < k < j）。
        int[][] dp = new int[length + 2][length + 2];
        for (int i = length; i >= 0; i--) {
            for (int j = i + 2 ; j < length + 2; j++) {
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + points[i] * points[k] * points[j]);
                }
            }
        }
        System.out.println(dp[0][length + 1]);
    }

}
