package dynamic;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-03-03 12:04 AM
 */
public class 最小生命值问题Demo {
    public static void main(String[] args) {

    }

    /* 主函数 */
    public static int calculateMinimumHP(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return dp(grid, 0, 0);
    }

    // 备忘录，消除重叠子问题
    private static int[][] memo;

    /* 定义：从 (i, j) 到达右下角，需要的初始血量至少是多少 */
    private static int dp(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        // base case
        if (i == m - 1 && j == n - 1) {
            return grid[i][j] >= 0 ? 1 : -grid[i][j] + 1;
        }
        if (i == m || j == n) {
            return Integer.MAX_VALUE;
        }
        // 状态转移逻辑
        int res = Math.min(
                dp(grid, i, j + 1),
                dp(grid, i + 1, j)
        ) - grid[i][j];
        return res <= 0 ? 1 : res;
    }

}
