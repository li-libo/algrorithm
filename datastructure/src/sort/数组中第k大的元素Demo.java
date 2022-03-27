package sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * 示例 1:
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 *
 * 示例 2:
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * @author lilibo
 * @create 2022-03-12 16:53
 */
public class 数组中第k大的元素Demo {

    @Test
    public void test1() {
        int[] array = {3, 2, 1, 5, 6, 4};
        System.out.println("快速排序前数组为: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println("快速排序后数组为: " + Arrays.toString(array));
        // 寻找第k大的元素
        int[] array1 = {3, 2, 1, 5, 6, 4};
        int k = 2;
        int targetK = findK(array1, array1.length - k, 0, array1.length - 1);
        System.out.println("第" + k + "大的元素为: " + targetK);
    }

    @Test
    public void test2() {
        int[] array = {3, 2, 1, 5, 6, 4};
        System.out.println("堆排序前数组为: " + Arrays.toString(array));
        heapSortAsc(array);
        System.out.println("堆排序后数组为: " + Arrays.toString(array));
        int k = 2;
        int targetK = findKByHeapSort(array, k);
        System.out.println("第" + k + "大的元素为: " + targetK);
    }

    public int findK(int[] array, int k, int left, int right) {
        int pivotIndex = findPivotIndex(array, left, right);
        if(pivotIndex == k - 1) {
            return array[pivotIndex];
        }else if(pivotIndex < k - 1) {
            return findK(array, k, pivotIndex + 1, right);
        }else {
            return findK(array, k, left, pivotIndex - 1);
        }
    }

    private int findPivotIndex(int[] array, int left, int right) {
        int l = left;
        int r = right;
        int pivotIndex = (l + r) / 2;
        int pivot = array[pivotIndex];
        while (l < r) {
            while (l <= right && array[l] < pivot) {
                l++;
            }
            while (r >= left && array[r] > pivot) {
                r--;
            }
            if(l <= r) {
                int temp = array[l];
                array[l] = array[r];
                array[r] = temp;
                l++;
                r--;
            }
        }
        return pivotIndex;
    }


    public void quickSort(int[] array, int left, int right) {
        if(left >= right) {
            return;
        }
        int l = left;
        int r = right;
        int pivotIndex = (l + r) / 2;
        int pivot = array[pivotIndex];
        while (l < r) {
            while (l <= right && array[l] < pivot) {
                l++;
            }
            while (r >= left && array[r] > pivot) {
                r--;
            }
            if(l <= r) {
                int temp = array[l];
                array[l] = array[r];
                array[r] = temp;
                l++;
                r--;
            }
        }
        if(l < right) {
            quickSort(array, l, right);
        }
        if(r > left) {
            quickSort(array, left, r);
        }
    }

    public static void heapSortAsc(int[] array) {
        // 先将数组调整为1个大顶堆, 按照从左往右, 从下往上调整
        for(int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            maxHeapAdjust(array, i, array.length);
        }
        for(int j = array.length - 1; j > 0; j--) {
            int temp = array[j];
            // 将最大元素沉到末尾
            array[j] = array[0];
            array[0] = temp;
            // 从根节点重新调整大顶堆
            maxHeapAdjust(array, 0 , j);
        }
    }

    /**
     * 寻找第k大的数
     * @param array
     */
    public static int findKByHeapSort(int[] array, int k) {
        // 先将数组调整为1个大顶堆, 按照从左往右, 从下往上调整
        for(int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            maxHeapAdjust(array, i, array.length);
        }
        for(int j = array.length - 1; j > array.length - k; j--) {
            int temp = array[j];
            // 将最大元素沉到末尾
            array[j] = array[0];
            array[0] = temp;
            // 从根节点重新调整大顶堆
            maxHeapAdjust(array, 0 , j);
        }
        return array[0];
    }

    /**
     * 将1个二叉数组,调整为1个大顶堆,从左往右,从下往上调整
     * 功能: 完成以i对应的非叶子节点调整为大顶堆
     * @param array 待调整的数组
     * @param i 表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整,length在逐渐减少
     */
    private static void maxHeapAdjust(int[] array, int i, int length) {
        int temp = array[i];
        for(int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            // 获得左右子树较大值
            if(k + 1 < length && array[k + 1] > array[k]) {
                k++;
            }
            if(array[k] > temp) {
                array[i] = array[k];
                i = k;
            }else {
                break;
            }
        }
        array[i] = temp;
    }

}
