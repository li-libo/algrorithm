package divideandconquer;

/**
 * 归并算法示例-汉诺塔
 * 汉诺塔游戏的演示和思路分析:
 * 1) 如果是有一个盘， A->C
 * 2）如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的盘 2. 上面的盘
 * 先把最上面的盘 A->B
 * 把最下边的盘 A->C
 * 把B塔的所有盘 从 B->C
 *
 * @author lilibo
 * @create 2021-08-22 9:09 PM
 */
public class HanoiTower {

    public static void main(String[] args) {
        solveHanoiTower(3, "A", "B", "C");
    }

    /**
     * 当n>=2时,我们总可以看成是2个盘: 1.最下边的盘 2.上面的所有盘
     * @param numOfPlate
     * @param start
     * @param mid
     * @param end
     */
    private static void solveHanoiTower(int numOfPlate, String start, String mid, String end) {
        if (numOfPlate == 1) {
            System.out.printf("将第1个盘子从%s -> %s\n", start, end);
        }else {
            // 先将最下边盘上所有的盘从start借助end移动到mid
            solveHanoiTower(numOfPlate - 1, start, end, mid);
            // 将最下边的盘移动到end
            System.out.printf("将第%d个盘子从%s -> %s\n", numOfPlate, start, end);
            // 再将mid上的盘子借助start移动到end
            solveHanoiTower(numOfPlate - 1, mid, start, end);
        }
    }

}
