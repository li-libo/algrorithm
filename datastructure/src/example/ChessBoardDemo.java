package example;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 马踏棋盘算法
 * @author lilibo
 * @create 2022-02-05 8:36 PM
 */
public class ChessBoardDemo {

    public static final int rowLength = 8;

    public static final int colLength = 8;

    public static final String format1 = "step = %d, rowIndex = %d, colIndex = %d";

    private static boolean finished = false;

    public static void main(String[] args) {
        int[][] chessBoard = new int[rowLength][colLength];
        boolean[] isVisited = new boolean[rowLength * colLength];
        travelChessBoard(1, 1, chessBoard, isVisited, 1);
        System.out.println("是否遍历完毕? " + finished);
        for(int[] row : chessBoard) {
            for(int step : row) {
                System.out.printf("%5d", step);
            }
            System.out.println();
        }
    }

    private static void travelChessBoard(int rowIndex, int colIndex, int[][] chessBoard, boolean[] isVisited, int step) {
        System.out.println(String.format(format1, step, rowIndex, colIndex));
        chessBoard[rowIndex][colIndex] = step;
        isVisited[rowIndex * colLength + colIndex] = true;
        LinkedList<Pair<Integer, Integer>> nextQueue = getNextQueue(rowIndex, colIndex, chessBoard, isVisited);
        nextQueue.sort((a, b)-> getNextQueue(a.getKey(), a.getValue(), chessBoard, isVisited).size() - getNextQueue(b.getKey(), b.getValue(), chessBoard, isVisited).size());
        while (nextQueue.size() > 0){
            Pair<Integer, Integer> pair = nextQueue.poll();
            if(!isVisited[pair.getKey() * colLength + pair.getValue()]) {
                travelChessBoard(pair.getKey(), pair.getValue(), chessBoard, isVisited, step + 1);
            }
        }
        if(step == rowLength * colLength) {
            finished = true;
        }
     }

    private static LinkedList<Pair<Integer, Integer>> getNextQueue(int rowIndex, int colIndex, int[][] chessBoard, boolean[] isVisited) {
        LinkedList<Pair<Integer, Integer>> linkedList = new LinkedList<Pair<Integer, Integer>>();
        if((rowIndex - 1) >= 0 && (colIndex + 2) < colLength && !isVisited[(rowIndex - 1) * colLength + colIndex + 2]) {
           linkedList.add(new Pair<>(rowIndex - 1, colIndex + 2));
        }
        if((rowIndex - 2) >= 0 && (colIndex + 1) < colLength && !isVisited[(rowIndex - 2) * colLength + colIndex + 1]) {
            linkedList.add(new Pair<>(rowIndex - 2, colIndex + 1));
        }
        if((rowIndex - 2) >= 0 && (colIndex - 1) >= 0 && !isVisited[(rowIndex - 2) * colLength + colIndex - 1]) {
            linkedList.add(new Pair<>(rowIndex - 2, colIndex - 1));
        }
        if((rowIndex - 1) >= 0 && (colIndex - 2) >= 0 && !isVisited[(rowIndex - 1) * colLength + colIndex - 2]) {
            linkedList.add(new Pair<>(rowIndex - 1, colIndex - 2));
        }
        if((rowIndex + 1) < rowLength && (colIndex - 2) >= 0 && !isVisited[(rowIndex + 1) * colLength + colIndex - 2]) {
            linkedList.add(new Pair<>(rowIndex + 1, colIndex - 2));
        }
        if((rowIndex + 2) < rowLength && (colIndex - 1) >= 0 && !isVisited[(rowIndex + 2) * colLength + colIndex - 1]) {
            linkedList.add(new Pair<>(rowIndex + 2, colIndex - 1));
        }
        if((rowIndex + 2) < rowLength && (colIndex + 1) < colLength && !isVisited[(rowIndex + 2) * colLength + colIndex + 1]) {
            linkedList.add(new Pair<>(rowIndex + 2, colIndex + 1));
        }
        if((rowIndex + 1) < rowLength && (colIndex + 2) < colLength && !isVisited[(rowIndex + 1) * colLength + colIndex + 2]) {
            linkedList.add(new Pair<>(rowIndex + 1, colIndex + 2));
        }
        return linkedList;
    }
}
