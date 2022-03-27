import org.junit.Test;

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 *
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 * @author lilibo
 * @create 2022-03-13 18:32
 */
public class 买卖股票的最佳时机Demo {

    @Test
    public void test1() {
        int[] prices = {7, 1, 5, 3, 6, 4};
        int maxProfit = maxProfit(prices);
        System.out.println("最大利润为 " + maxProfit);
    }

    /**
     * 分析,状态: 天数, 交易次数(本题为1), 是否持有股票
     *     选择: 出售、买入
     * 状态方程 dp[i][0]表示第i天不持有股票最大利润
     *        dp[i][1]表示第天持有股票时最大利润
     *  原始dp方程:
     *  dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
     *  dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i]);
     *
     *  dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
     *  dp[i][1] = Math.max(dp[i - 1][1], - prices[i]);
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];

        // 动态规划构建
        for(int i = 0; i < n; i++) {
            if(i == 0) {
                // base case
                dp[0][0] = 0;
                dp[0][1] = -prices[0];
                continue;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], - prices[i]);
        }
        // 最大利润为第n天已然卖了股票
        return dp[n - 1][0];
    }
}
