package treedemo;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-01-30 8:53 PM
 */
public class HeapSortExample {

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 4, 3, 5, 7, 9, 8, 11, 22, -3, -4};
        System.out.println("排序前数组为: " + Arrays.toString(array));
        heapSortAsc(array);
        System.out.println("升序排序后数组为: " + Arrays.toString(array));
        heapSortDesc(array);
        System.out.println("降序排序后数组为: " + Arrays.toString(array));
    }

    private static void heapSortDesc(int[] array) {
        // 先调整为小顶堆
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            adjustMinHeap(array, i, array.length);
        }
        // 将最小值沉到末尾
        for(int lastIndex = array.length - 1; lastIndex > 0; lastIndex--) {
            int min = array[0];
            array[0] = array[lastIndex];
            array[lastIndex] = min;
            adjustMinHeap(array, 0, lastIndex);
        }
    }

    private static void adjustMinHeap(int[] array, int i, int length) {
        int temp = array[i];
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 先确定左右子树较小值
            if(k + 1 < length && array[k + 1] < array[k]) {
                k++;
            }
            if(array[k] < temp) {
                array[i] = array[k];
                i = k;
            }else {
                break;
            }
        }
        array[i] = temp;
    }

    private static void heapSortAsc(int[] array) {
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            adjustMaxHeap(array, i, array.length);
        }
        for (int lastIndex = array.length - 1; lastIndex > 0; lastIndex--) {
            int max = array[0];
            array[0] = array[lastIndex];
            array[lastIndex] = max;
            adjustMaxHeap(array, 0, lastIndex);
        }
    }

    private static void adjustMaxHeap(int[] array, int i, int length) {
        int temp = array[i];
        for (int k = 2 * i + 1; k < length; k = 2 * i + 1) {
            // 确定左右子树较大值
            if (k + 1 < length && array[k + 1] > array[k]) {
                k++;
            }
            if (array[k] > temp) {
                array[i] = array[k];
                i = k;
            } else {
                // 因为是从底部向上调整大顶堆array[k]若不大于temp,那么array[k]的子节点更不会大于temp
                break;
            }
        }
        array[i] = temp;
    }
}
