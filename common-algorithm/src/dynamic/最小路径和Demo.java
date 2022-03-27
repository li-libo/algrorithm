package dynamic;

/**
 * 现在给你输入一个二维数组 grid，其中的元素都是非负整数，现在你站在左上角，只能向右或者向下移动，需要到达右下角。现在请你计算，经过的路径和最小是多少？
 * 函数签名如下：
 * int minPathSum(int[][] grid);
 *
 * @author lilibo
 * @create 2022-03-02 11:22 PM
 */
public class 最小路径和Demo {

    public static void main(String[] args) {
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        int sum = minPathSum(grid);
        System.out.println(sum);
        System.out.println(minPathSum1(grid));
    }

    public static int minPathSum(int[][] grid) {
        // 状态方程dp[i][j] = Math.min(dp[i-1][j] + grid[i][j], dp[i][j-1] + grid[i][j];
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int j1 = 1; j1 < grid[0].length; j1++) {
            dp[0][j1] = dp[0][j1 - 1] + grid[0][j1];
        }
        for (int i1 = 1; i1 < grid.length; i1++) {
            dp[i1][0] = dp[i1 - 1][0] + grid[i1][0];
        }
        for (int i1 = 1; i1 < grid.length; i1++) {
            for (int j1 = 1; j1 < grid[i1].length; j1++) {
                dp[i1][j1] = Math.min(dp[i1 - 1][j1] + grid[i1][j1], dp[i1][j1 - 1] + grid[i1][j1]);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    public static int minPathSum1(int[][] grid) {
        return dp(grid, grid.length - 1, grid[0].length - 1);
    }

    /**
     * 递归求解
     *
     * @param grid
     * @param i
     * @param j
     * @return
     */
    public static int dp(int[][] grid, int i, int j) {
        // base case
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        // 如果索引出界，返回一个很大的值，
        // 保证在取 min 的时候不会被取到
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }

        // 左边和上面的最小路径和加上 grid[i][j]
        // 就是到达 (i, j) 的最小路径和
        return Math.min(
                dp(grid, i - 1, j),
                dp(grid, i, j - 1)
        ) + grid[i][j];
    }

}
