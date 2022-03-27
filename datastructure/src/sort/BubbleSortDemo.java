package sort;

import java.util.Arrays;

/**
 * 冒泡排序,时间复杂度O(n^2)
 *
 * @author lilibo
 * @create 2021-08-10 7:42 PM
 */
public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] array = {3, 9, -1, 1, 20, 5, 4, 2};
        System.out.println("排序前数组为: " + Arrays.toString(array));
        bubbleSort(array);
        System.out.println("排序后数组为: " + Arrays.toString(array));
    }

    private static void bubbleSort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) { // 第i趟大排序
            boolean changeFlag = false;
            for(int j = 0; j < array.length - 1 - i; j++) {
                if(array[j] > array[j + 1]){
                    // 交换
                    int o = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = o;
                    changeFlag = true;
                }
            }
            // 优化,如果1趟比较下来, 没有发生交换, 则数组已经有序
            if(!changeFlag) {
                break;
            }
        }
    }

}
