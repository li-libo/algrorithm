package sort;

import java.util.Arrays;

/**
 * 快速排序（Quicksort）是对冒泡排序的一种改进。基本思想是：
 * 通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部分数据分别进行快速排序，
 * 整个排序过程可以递归进行，以此达到整个数据变成有序序列.
 *
 * @author lilibo
 * @create 2021-08-11 7:36 PM
 */
public class QuickSortDemo {

    public static void main(String[] args) {
        int[] array = {1, 2, 0, 3, 2, 9, 5, 11, 10, 4, 1, 7};
        System.out.println("快速排序前数组: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println("快速排序后数组: " + Arrays.toString(array));
    }

    private static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int l = left;
            int r = right;
            int pivot = array[(l + r) / 2];
            while (l < r) {
                while (array[l] < pivot) {
                    l++;
                }
                while (array[r] > pivot) {
                    r--;
                }
                if (l == r) { // 为pivot
                    break;
                }
                // 交换
                int temp = array[l];
                array[l] = array[r];
                array[r] = temp;
                if (array[l] == pivot) {
                    r--;
                }
                if (array[r] == pivot) {
                    l++;
                }
            }
            // 防止栈溢出
            if (l == r) {
                l++;
                r--;
            }
            // 左递归
            if (r > left) {
                quickSort(array, left, r);
            }
            // 右递归
            if (right > l) {
                quickSort(array, l, right);
            }
        }
    }

}
