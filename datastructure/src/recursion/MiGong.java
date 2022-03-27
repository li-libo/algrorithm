package recursion;

/**
 * @author lilibo
 * @create 2020-09-26 5:20 PM
 */
public class MiGong {

    private static final int targetI = 6;

    private static final int targetJ = 5;

    public static void main(String[] args) {
        //初始化迷宫,1代表墙, 0代表没走过的点
        int[][] map = new int[8][7];
        for (int i = 0; i < map.length; i++) {
            map[i][0] = 1;
            map[i][map[i].length - 1] = 1;
        }
        for (int j = 0; j < map[0].length; j++) {
            map[0][j] = 1;
            map[map.length - 1][j] = 1;
        }
        map[3][1] = 1;
        map[3][2] = 1;
        printMap(map);
        System.out.println("是否找到[" + targetI + "]["+ targetJ + "] :" + getWay(map, 1, 1));
        printMap(map);
    }

    private static void printMap(int[][] map) {
        // 输出迷宫
        for (int[] row : map) {
            for (int ele : row) {
                System.out.printf(ele + "  ");
            }
            System.out.println();
        }
    }

    // 假设0是没走过的点,1是墙,2是可以走的点,3是不可以走的点
    public static boolean getWay(int[][] map, int startI, int startJ) {
        // 递归退出条件
        if (map[targetI][targetJ] == 2) {
            return true;
        }
        if (map[startI][startJ] == 0) { // 如果该点没有走过
            // 先假设该点可以走通
            map[startI][startJ] = 2;
            // 规则:上下左右
            if (getWay(map, startI - 1, startJ)) {
                return true;
            } else if (getWay(map, startI + 1, startJ)) {
                return true;
            } else if (getWay(map, startI, startJ - 1)) {
                return true;
            } else if (getWay(map, startI, startJ + 1)) {
                return true;
            } else {
                map[startI][startJ] = 3; // 不能走重置为3
                return false;
            }
        } else { // 1, 2, 3
            return false;
        }
    }

    //换个策略
    public static boolean getWay2(int[][] map, int startI, int startJ) {
        if (map[targetI][targetJ] == 2) {
            return true;
        }
        if (map[startI][startJ] == 0) {
            // 先假定可以走通
            map[startI][startJ] = 2;
            // 规则: 左右上下
            if (getWay2(map, startI, startJ - 1)) {
                return true;
            } else if (getWay2(map, startI, startJ + 1)) {
                return true;
            } else if (getWay2(map, startI - 1, startJ)) {
                return true;
            } else if (getWay2(map, startI + 1, startJ)) {
                return true;
            } else {
                map[startI][startJ] = 3; // 不能走重置为3
                return false;
            }
        } else {
            return false;
        }
    }

}
