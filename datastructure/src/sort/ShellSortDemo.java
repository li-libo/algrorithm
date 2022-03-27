package sort;

import java.util.Arrays;

/**
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含
 * 的关键词越来越多，当增量减至 1 时，整个文件恰被分成一组，算法便终止
 *
 * @author lilibo
 * @create 2021-08-10 9:33 PM
 */
public class ShellSortDemo {

    public static void main(String[] args) {
        int[] array = {3, 2, 1, 5, 6, 10, 1, 2, 9, 0};
        System.out.println("希尔排序前数组: " + Arrays.toString(array));
        shellSort2(array);
        System.out.println("希尔排序后数组: " + Arrays.toString(array));
    }

    /**
     * 移位方式希尔排序
     *
     * @param array
     */
    private static void shellSort2(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int insertIndex = i - gap;
                int insertValue = array[i];
                while (insertIndex >= 0 && array[insertIndex] > insertValue) {
                    array[insertIndex + gap] = array[insertIndex];
                    insertIndex -= gap;
                }
                if (insertIndex + gap != i) {
                    array[insertIndex + gap] = insertValue;
                }
            }
        }
    }

    /**
     * 交换方式希尔排序
     *
     * @param array
     */
    private static void shellSort1(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (array[j] > array[j + gap]) {
                        int o = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = o;
                    }
                }
            }
        }
    }


}
