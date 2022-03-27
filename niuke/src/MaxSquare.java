/**
 * 在一个由0 和 0 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 * @author lilibo
 * @create 2022-02-24 5:21 PM
 */
public class MaxSquare {

    public static void main(String[] args) {
        int[][] matrix = {{1, 0, 1, 0, 0}, {1, 0, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 1, 0}};
        /*
         * 解题思路
         * 使用动态规划的思想，即原问题的最优解可以由子问题的最优解推导得到。在本问题中，如下图，以坐标（i,j）为右下角的最大全1正方形可以由其左边（i-1,j），
         * 上边(i,j-1)，左上角（i-1,j-1）的最大全1正方形中的最小值决定。
         */
        // 设bp[i][j]表示以[i,j]为右下角全部为1的正方形最大边长
        // 则bp[i][j]是由左、上、左上角最大全是1的正方形边长决定
        int m = matrix.length, n = matrix[0].length, max = 0;
        int[][] bp = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == 1) {
                    if(i == 0 || j == 0) {
                        bp[i][j] = 1;
                    }else {
                        bp[i][j] = Math.min(Math.min(bp[i-1][j], bp[i][j -1]), bp[i-1][j-1]) + 1;
                    }
                    max = Math.max(max, bp[i][j]);
                }else {
                    bp[i][j] = 0;
                }
            }
        }
        System.out.println(max * max);
    }
}
