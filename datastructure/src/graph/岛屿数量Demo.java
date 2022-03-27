package graph;

import org.junit.Test;

/**
 * @author lilibo
 * @create 2022-03-12 12:57 AM
 */
public class 岛屿数量Demo {

    @Test
    public void test1() {
        char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
                };
        char[][] clonedGrid = new char[grid.length][grid[0].length];
        for(int i = 0; i < clonedGrid.length; i++) {
            for(int j = 0; j < clonedGrid[i].length; j++) {
                clonedGrid[i][j] = grid[i][j];
            }
        }
        int num = numIslands(clonedGrid);
        System.out.println("岛的数量为num = " + num);
    }

    void dfs(char[][] grid, int r, int c) {
        int m = grid.length;
        int n = grid[0].length;
        if (r < 0 || c < 0 || r >= m || c >= n || grid[r][c] == '0') {
            return;
        }
        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int numOfIslands = 0;
        for (int r = 0; r < m; ++r) {
            for (int c = 0; c < n; ++c) {
                if (grid[r][c] == '1') {
                    ++numOfIslands;
                    dfs(grid, r, c);
                }
            }
        }
        return numOfIslands;
    }

}
