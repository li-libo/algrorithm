package example;

/**
 * @author lilibo
 * @create 2022-02-04 5:15 PM
 */
public class TowerOfHanoi {

    public static void main(String[] args) {
        move(5, "A", "B", "C");
    }

    public static final String format1 = "第%d个盘子由%s移动到%s";

    private static void move(int numOfPlate, String start, String mid, String end) {
        // 假如只有1个盘子
        if(numOfPlate == 1) {
            System.out.println(String.format(format1, numOfPlate, start, end));
        }else {
            move(numOfPlate - 1, start, end, mid);
            System.out.println(String.format(format1, numOfPlate, start, end));
            move(numOfPlate - 1, mid, start, end);
        }
    }
}
