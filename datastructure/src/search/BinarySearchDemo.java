package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lilibo
 * @create 2021-08-12 4:03 PM
 */
public class BinarySearchDemo {

    public static void main(String[] args) {
        int[] array = {1, 3, -1, 10, 20, 5, 6, 7, 5, 33, 21, 11, 5, 11};
        insertSort(array);
        System.out.println("插入排序后数组: " + Arrays.toString(array));
        int target = 11;
        List<Integer> targetIndexList = binarySearchAll(array, 0, array.length - 1, target);
        System.out.printf("target = %d 的下标为%s", target, Arrays.toString(targetIndexList.toArray()));
    }

    private static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            // 假设要插入的值和位置下标
            int insertValue = array[i];
            int insertIndex = i - 1;
            while (insertIndex >= 0 && array[insertIndex] > insertValue) {
                array[insertIndex + 1] = array[insertIndex]; // 后移
                insertIndex--; //继续向前查找
            }
            if (insertIndex + 1 != i) {
                array[insertIndex + 1] = insertValue;
            }
        }
    }

    private static int binarySearch(int[] array, int left, int right, int target) {
        if (left <= right) {
            int mid = (left + right) / 2;
            int pivot = array[mid];
            if (target < pivot) {
                return binarySearch(array, left, mid - 1, target);
            } else if (target > pivot) {
                return binarySearch(array, mid + 1, right, target);
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static List<Integer> binarySearchAll(int[] array, int left, int right, int target) {
        ArrayList<Integer> newList = new ArrayList<>();
        if (left <= right) {
            int mid = (left + right) / 2;
            int pivot = array[mid];
            if (target < pivot) {
                newList.addAll(binarySearchAll(array, left, mid - 1, target));
                newList.sort((x, y) -> x - y);
                return newList;
            } else if (target > pivot) {
                newList.addAll(binarySearchAll(array, mid + 1, right, target));
                newList.sort((x, y) -> x - y);
                return newList;
            } else {
                newList.add(mid);
                // 继续mid下标左右查找相同元素添加到集合中(记住此时array是有序的)
                int l = mid - 1;
                // 左查找
                while (l >= left) {
                    if (array[l] == target) {
                        newList.add(l);
                        l--;
                    } else {
                        break;
                    }
                }
                // 右查找
                int r = mid + 1;
                while (r <= right) {
                    if (array[r] == target) {
                        newList.add(r);
                        r++;
                    } else {
                        break;
                    }
                }
                newList.sort((x, y) -> x - y);
                return newList;
            }
        }
        return newList;
    }

}
