package recursion;

import org.junit.Test;

/**
 * 迷宫问题
 * @author lilibo
 * @create 2022-01-27 8:01 PM
 */
public class MazeProblem {

    @Test
    public void test1() {
        int[][] mazeArray = new int[8][7];
        for(int colIndex = 0; colIndex < mazeArray[0].length; colIndex++) {
            mazeArray[0][colIndex] = 1;
            mazeArray[7][colIndex] = 1;
        }
        for(int rowIndex = 0; rowIndex < mazeArray.length; rowIndex++) {
            mazeArray[rowIndex][0] = 1;
            mazeArray[rowIndex][6] = 1;
        }
        mazeArray[3][1] = 1;
        mazeArray[3][2] = 1;
        Maze maze = new Maze(mazeArray);
        maze.printArray();
        System.out.println("-----------");
        boolean findFlag = maze.findWay2(1, 1, 6, 5);
        System.out.println("是否找到目标节点? " + findFlag);
        maze.printArray();
    }

}

class Maze {
    // 迷宫数组,1代表墙,0代表未走过
    private int[][] mazeArray;

    public Maze(int[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    public void printArray() {
        for(int rowInex = 0; rowInex < mazeArray.length; rowInex++) {
            for(int colIndex = 0; colIndex < mazeArray[rowInex].length; colIndex++) {
                System.out.printf("%8d", mazeArray[rowInex][colIndex]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * 策略: 上下左右
     * 使用递归回溯来寻找target
     * @param startI 出发坐标i
     * @param startJ 出发坐标j
     * @param targetI 目的坐标i
     * @param targetJ 目的坐标j
     * @return 是否找到targetI、targetJ
     *
     * 约定: 当array[i][j] == 0表示该点没有走过, == 1表示墙, 2表示通路可走, 3表示该点走过但没走通
     */
    public boolean findWay1(int startI, int startJ, int targetI, int targetJ) {
        if(mazeArray[targetI][targetJ] == 2) {
            return true;
        }
        if(mazeArray[startI][startJ] == 0) {
            mazeArray[startI][startJ] = 2;
            if(findWay1(startI - 1, startJ, targetI, targetJ)) {
                return true;
            }else if(findWay1(startI + 1, startJ, targetI, targetJ)) {
                return true;
            }else if(findWay1(startI, startJ - 1, targetI, targetJ)){
                return true;
            }else if(findWay1(startI, startJ + 1, targetI, targetJ)){
                return true;
            }else {
                mazeArray[startI][startJ] = 3;
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean findWay2(int startI, int startJ, int targetI, int targetJ) {
        if(mazeArray[targetI][targetJ] == 2) {
            return true;
        }
        if(mazeArray[startI][startJ] == 0) {
            // 先假设该节点可以走通
            mazeArray[startI][startJ] = 2;
            if(findWay2(startI - 1, startJ, targetI, targetJ)) {
                return true;
            }else if(findWay2(startI, startJ - 1, targetI, targetJ)) {
                return true;
            }else if(findWay2(startI + 1, startJ, targetI, targetJ)) {
                return true;
            }else if(findWay2(startI, startJ + 1, targetI, targetJ)) {
                return true;
            }else {
                // 无法走通置为3
                mazeArray[startI][startJ] = 3;
                return false;
            }
        }else {
            return false;
        }
    }


}