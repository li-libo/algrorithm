package tree;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2021-08-14 4:29 PM
 */
public class HeapSortDemo {

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 4, 5, 3, 7, 8, 6, 11, 22, -3, 4, -8, 10};
        System.out.println("大顶堆排序前数组: " + Arrays.toString(array));
        heapSortAsc(array);
        System.out.println("大顶堆排序后数组: " + Arrays.toString(array));
        heapSortDesc(array);
        System.out.println("小顶堆排序后数组: " + Arrays.toString(array));
    }

    private static void heapSortDesc(int[] array) {
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            minHeapAdjust(array, i, array.length);
        }
        for (int lastIndex = array.length - 1; lastIndex >= 0; lastIndex--) {
            // 交换, 将最小值沉到末尾
            int temp = array[lastIndex];
            array[lastIndex] = array[0];
            array[0] = temp;
            minHeapAdjust(array, 0, lastIndex);
        }
    }

    private static void minHeapAdjust(int[] array, int i, int length) {
        int temp = array[i];
        for (int k = 2 * i + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && array[k + 1] < array[k]) {
                k++;
            }
            if (array[k] < temp) {
                array[i] = array[k];
                i = k;
            } else {
                break;
            }
        }
        array[i] = temp;
    }

    private static void heapSortAsc(int[] array) {
        // 先将数组调整为1个大顶堆, 按照从左往右、从下到上调整
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            maxHeapAdjust(array, i, array.length);
        }
        for (int lastIndex = array.length - 1; lastIndex >= 0; lastIndex--) {
            // 交换, 将大顶堆顶部沉到末尾
            int temp = array[lastIndex];
            array[lastIndex] = array[0];
            array[0] = temp;
            // 继续调整大顶堆
            maxHeapAdjust(array, 0, lastIndex);
        }
    }

    /**
     * 将一个数组(二叉树), 调整成一个大顶堆, 从左往右, 自下向上调整
     * 功能：完成将以i对应的非叶子结点的树调整成大顶堆
     * 举例 int arr[] = {4, 6, 8, 5, 9}; => i = 1 => adjustHeap => 得到 {4, 9, 8, 5, 6}
     * 如果我们再次调用 adjustHeap 传入的是 i = 0 => 得到 {4, 9, 8, 5, 6} => {9, 6, 8, 5, 4}
     *
     * @param array  待调整的数组
     * @param i      表示非叶子结点在数组中索引
     * @param length 表示对多少个元素继续调整, length是在逐渐的减少
     */
    private static void maxHeapAdjust(int[] array, int i, int length) {
        int temp = array[i];
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 获得左右子树较大值下标
            if (k + 1 < length && array[k + 1] > array[k]) {
                k++;
            }
            if (array[k] > temp) {
                array[i] = array[k];
                i = k; // 让i指向k, 使之继续循环判断是否还有比temp大的子节点
            } else {
                // 因为自底向下调整,底下为大顶堆,不需要继续判断了
                break;
            }
        }
        array[i] = temp;
    }

}
