package sort;

import java.util.Arrays;

/**
 * 归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的
 * 问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。
 *
 * @author lilibo
 * @create 2021-08-11 9:22 PM
 */
public class MergeSortDemo {

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 2, 0, 8, 9, 10, 33, 22, 11, 9, 10, 1};
        System.out.println("归并排序前数组: " + Arrays.toString(array));
        // 归并排序需要一个保存中间变量的额外数组
        mergeSort(array, 0, array.length - 1, new int[array.length]);
        System.out.println("归并排序后数组: " + Arrays.toString(array));
    }

    /**
     * 归并排序
     *
     * @param array 原数组
     * @param left  左边界
     * @param right 右边界
     * @param temp  保存中间变量额外数组
     */
    private static void mergeSort(int[] array, int left, int right, int[] temp) {
        if (left < right) {
            // 拆分
            int mid = (left + right) / 2;
            mergeSort(array, left, mid, temp);
            mergeSort(array, mid + 1, right, temp);
            // 合并
            merge(array, left, mid, right, temp);
        }
    }

    private static void merge(int[] array, int left, int mid, int right, int[] temp) {
        int index1 = left;
        int index2 = mid + 1;
        int t = 0;
        // 将左右两段放置到临时数组temp
        while (index1 <= mid && index2 <= right) {
            if (array[index1] < array[index2]) {
                temp[t] = array[index1];
                t++;
                index1++;
            } else {
                temp[t] = array[index2];
                t++;
                index2++;
            }
        }
        while (index1 <= mid) {
            temp[t] = array[index1];
            t++;
            index1++;
        }
        while (index2 <= right) {
            temp[t] = array[index2];
            t++;
            index2++;
        }
        // 拷贝回原数组
        t = 0;
        index1 = left;
        while (index1 <= right) {
            array[index1] = temp[t];
            index1++;
            t++;
        }
    }

}
