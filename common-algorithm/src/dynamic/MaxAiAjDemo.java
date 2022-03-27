package dynamic;

/**
 *
 * Input: int a = [100,3,-9,29,-13,28]；
 * Output: max(aj-ai)；j>i
 * 时间复杂度 On
 * @author lilibo
 * @create 2022-03-07 11:27 AM
 */
public class MaxAiAjDemo {

    public static void main(String[] args) {
        int[] array = {100, 3, -9, 29, -13, 28};
        int[] minNumberArray = new int[array.length];
        minNumberArray[0] = array[0];
        for(int i = 1; i < array.length; i++) {
            minNumberArray[i] = array[i] < minNumberArray[i - 1] ? array[i] : minNumberArray[i - 1];
        }
        int result = Integer.MIN_VALUE;
        for(int i = 1; i < array.length; i++) {
            result = array[i] - minNumberArray[i] > result ? array[i] - minNumberArray[i] : result;
        }
        System.out.println("Max(ai, aj) = " + result);
    }

}
