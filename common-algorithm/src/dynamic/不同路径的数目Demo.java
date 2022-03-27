package dynamic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 一个机器人在m×n大小的地图的左上角（起点）。
 * 机器人每次可以向下或向右移动。机器人要到达地图的右下角（终点）。
 * 可以有多少种不同的路径从起点走到终点？
 * @author lilibo
 * @create 2022-03-09 12:20 AM
 */
public class 不同路径的数目Demo {

    @Test
    public void test1() {
        uniquePaths(2, 2);
    }

    public void uniquePaths(int m, int n) {
        /*
        分析状态方程dp[i][j] 为到达(i,j)的路径数
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
         */
        int[][] dp = new int[m][n];
        // base case
        Arrays.fill(dp[0], 1);
        for(int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        System.out.println(m + "x" + n + "矩阵到达右下角路径数为: " + dp[m - 1][n - 1]);
    }
}
