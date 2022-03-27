package binarysearch;

import java.util.Arrays;

/**
 * 二分查找法,递归/非递归
 *
 * 1) 前面我们讲过了二分查找算法，是使用递归的方式，下面我们讲解二分查找算法的非递归方式
 * 2) 二分查找法只适用于从有序的数列中进行查找(比如数字和字母等)，将数列排序后再进行查找
 * 3) 二分查找法的运行时间为对数时间 O(㏒₂n) ，即查找到需要的目标位置最多只需要㏒₂n 步，假设从[0,99]的
 * 队列(100 个数，即 n=100)中寻到目标数 30， 则需要查找步数为㏒₂100 , 即最多需要查找 7 次( 2^6 < 100 < 2^7)
 *
 * @author lilibo
 * @create 2021-08-22 8:27 PM
 */
public class BinarySearchDemo {

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 4, 3, 6, 7, 8, 11, 5};
        System.out.println("排序前数组:" + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println("快速排序后数组:" + Arrays.toString(array));
        int target = 4;
        int targetIndex1 = binarySerachInRecursion(array, 0, array.length - 1, target);
        System.out.printf("二分查找法(递归)查找target = %d的下标为: %d\n", target, targetIndex1);
        int targetIndex2 = binarySerach(array, target);
        System.out.printf("二分查找法(非递归)查找target = %d的下标为: %d\n", target, targetIndex2);
    }

    private static int binarySerach(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target == array[mid]) {
                return mid;
            } else if (target < array[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    private static int binarySerachInRecursion(int[] array, int left, int right, int target) {
        if (left <= right) {
            int mid = (left + right) / 2;
            int pivot = array[mid];
            if (target == pivot) {
                return mid;
            } else if (target < pivot) {
                return binarySerachInRecursion(array, left, mid - 1, target);
            } else {
                return binarySerachInRecursion(array, mid + 1, right, target);
            }
        }
        return -1;
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
                if (l == r) {
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
            if (r > left) {
                quickSort(array, left, r);
            }
            if (right > l) {
                quickSort(array, l, right);
            }
        }
    }

}
