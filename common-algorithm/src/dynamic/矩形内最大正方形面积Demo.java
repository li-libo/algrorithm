package dynamic;

/**
 * 求矩阵内最大正方形面积
 * 假设有一个由0和1组成的2维矩阵
 * 求：该矩阵中最大的由1组成的正方形的面积
 * @author lilibo
 * @create 2022-02-24 7:55 PM
 */
public class 矩形内最大正方形面积Demo {

    public static void main(String[] args) {
        int[][] matrix = {{1, 0, 1, 0, 0}, {1, 0, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 1, 0}};
        // 假设dp[i][j]是以i,j为右下标最大的正方形边长
        int[][] dp = new int[matrix.length][matrix[0].length];
        int maxLengthOfEdge = 0;
        // 采用动态规划算法
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 1) {
                    if(i == 0 || j == 0) {
                        dp[i][j] = 1;
                    }else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j-1]), dp[i - 1][j - 1]) + 1;
                    }
                    maxLengthOfEdge = Math.max(maxLengthOfEdge, dp[i][j]);
                }
            }
        }
        System.out.println("值为1的最大矩形面积为: " + maxLengthOfEdge * maxLengthOfEdge);
    }
}
