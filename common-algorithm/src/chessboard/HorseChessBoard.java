package chessboard;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author lilibo
 * @create 2021-09-01 8:56 PM
 */
public class HorseChessBoard {

    private static final int rowLength = 8;

    private static final int colLength = 8;

    private static final String format = "step = %d, rowIndex = %d, colIndex = %d";

    private static boolean finished;

    public static void main(String[] args) {
        int[][] chessBoard = new int[rowLength][colLength];
        boolean[] isVisited = new boolean[rowLength * colLength];
        travelChessBoard(chessBoard, isVisited, 1, 1, 1);
        printChessBoard(chessBoard);
    }

    private static void printChessBoard(int[][] chessBoard) {
        for (int[] row : chessBoard) {
            for (int step : row) {
                System.out.printf("%5d", step);
            }
            System.out.printf("\n");
        }
    }

    /**
     * 遍历棋盘,相当于图的深度优先搜索
     *
     * @param chessBoard
     * @param isVisited
     * @param rowIndex
     * @param colIndex
     * @param step
     */
    private static void travelChessBoard(int[][] chessBoard, boolean[] isVisited, int rowIndex, int colIndex, int step) {
        chessBoard[rowIndex][colIndex] = step;
        isVisited[rowIndex * colLength + colIndex] = true;
        LinkedList<Pair<Integer, Integer>> nextPointQueue = getNextPoint(rowIndex, colIndex);
        // 贪心算法优化,按照nextPointQueue中点下一个点集合数量少的排序
        nextPointQueue.sort((a, b) -> {
            return getNextPoint(a.getKey(), a.getValue()).size() - getNextPoint(b.getKey(), b.getValue()).size();
        });
        while (nextPointQueue.size() > 0) {
            Pair<Integer, Integer> nextPoint = nextPointQueue.poll();
            if (!isVisited[nextPoint.getKey() * colLength + nextPoint.getValue()]) {
                travelChessBoard(chessBoard, isVisited, nextPoint.getKey(), nextPoint.getValue(), step + 1);
            }
        }
        if (step == rowLength * colLength) { // step == 64,递归结束
            System.out.println(String.format(format, step, rowIndex, colIndex));
            finished = true;
        } else if (finished) {
            System.out.println(String.format(format, step, rowIndex, colIndex));
        } else { // 不能遍历完所有棋盘,恢复棋盘状态
            chessBoard[rowIndex][colIndex] = 0;
            isVisited[rowIndex * colLength + colIndex] = false;
        }
    }

    private static LinkedList<Pair<Integer, Integer>> getNextPoint(int rowIndex, int colIndex) {
        LinkedList<Pair<Integer, Integer>> nextPointQueue = new LinkedList<>();
        Pair<Integer, Integer> nextPoint = null;
        if (rowIndex - 2 >= 0 && colIndex - 1 >= 0) {
            nextPoint = new Pair<>(rowIndex - 2, colIndex - 1);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex - 1 >= 0 && colIndex - 2 >= 0) {
            nextPoint = new Pair<>(rowIndex - 1, colIndex - 2);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex + 1 < rowLength && colIndex - 2 >= 0) {
            nextPoint = new Pair<>(rowIndex + 1, colIndex - 2);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex + 2 < rowLength && colIndex - 1 >= 0) {
            nextPoint = new Pair<>(rowIndex + 2, colIndex - 1);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex - 2 >= 0 && colIndex + 1 < colLength) {
            nextPoint = new Pair<>(rowIndex - 2, colIndex + 1);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex - 1 >= 0 && colIndex + 2 < colLength) {
            nextPoint = new Pair<>(rowIndex - 1, colIndex + 2);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex + 1 < rowLength && colIndex + 2 < colLength) {
            nextPoint = new Pair<>(rowIndex + 1, colIndex + 2);
            nextPointQueue.offer(nextPoint);
        }
        if (rowIndex + 2 < rowLength && colIndex + 1 < colLength) {
            nextPoint = new Pair<>(rowIndex + 2, colIndex + 1);
            nextPointQueue.offer(nextPoint);
        }
        return nextPointQueue;
    }

}
