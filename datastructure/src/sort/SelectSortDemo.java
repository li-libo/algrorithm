package sort;

import java.util.Arrays;

/**
 * 选择排序（select sorting）也是一种简单的排序方法。它的基本思想是：第一次从 arr[0]~arr[n-1]中选取最小值, 与arr[0]交换,
 * 第二次从 arr[1]~arr[n-1]中选取最小值，与 arr[1]交换，第三次从 arr[2]~arr[n-1]中选取最小值，与 arr[2] 交换，…，第i次
 * 从arr[i-1]~arr[n-1]中选取最小值，与 arr[i-1]交换，…, 第 n-1 次从 arr[n-2]~arr[n-1]中选取最小值, 与arr[n-2]交换,
 * 总共通过n-1次, 得到一个按排序码从小到大排列的有序序列。
 *
 * @author lilibo
 * @create 2021-08-10 7:58 PM
 */
public class SelectSortDemo {

    public static void main(String[] args) {
        int[] array = {8, 3, 2, 1, 7, 4, 6, 5};
        System.out.println("排序前数组为: " + Arrays.toString(array));
        selectSort(array);
        System.out.println("排序后数组为: " + Arrays.toString(array));
    }

    /**
     * 选择排序
     * @param array 待排序的数组
     */
    private static void selectSort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) { // 第i趟大排序
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for(int j = i; j < array.length; j++) {
                if(array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            // 将i -> array.length - 1内最小值与i的元素交换
            if(minIndex != -1 && min != array[i]) {
                int o = array[i];
                array[i] = min;
                array[minIndex] = o;
            }
        }
    }

}
