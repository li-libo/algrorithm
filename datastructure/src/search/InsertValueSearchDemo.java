package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 插值查找算法
 * 插值查找算法类似于二分查找,不同的是每次从自适应的mid处查找
 * 二分查找mid = (left + right) / 2 = left + (1/2) * (right - left);
 * 插值查找mid = left + (target - array[left]) / (array[right] - array[left]) * (right - left);
 *
 * @author lilibo
 * @create 2021-08-12 4:46 PM
 */
public class InsertValueSearchDemo {

    public static void main(String[] args) {
        int[] array = {1, 3, -1, 10, 20, 7, 5, 6, 7, 5, 33, 21, 7, 11, 5, 11};
        selectSort(array);
        System.out.println("选择排序后数组: " + Arrays.toString(array));
        int target = 5;
        List<Integer> targetIndexList = insertValueSearchAllWithNoRecursion(array, 0, array.length - 1, target);
        System.out.printf("target = %d 的下标集合为: %s", target, Arrays.toString(targetIndexList.toArray()));
    }

    private static List<Integer> insertValueSearchAllWithNoRecursion(int[] array, int left, int right, int target) {
        ArrayList<Integer> list = new ArrayList<>();
        if (left <= right) {
            int mid = left + (target - array[left]) / (array[right] - array[left]) * (right - left);
            if (target < array[mid]) {
                list.addAll(insertValueSearchAllWithNoRecursion(array, left, mid - 1, target));
                list.sort((x, y) -> x - y);
                return list;
            } else if (target > array[mid]) {
                list.addAll(insertValueSearchAllWithNoRecursion(array, mid + 1, right, target));
                list.sort((x, y) -> x - y);
                return list;
            } else {
                list.add(mid);
                // 向mid左右两侧查找满足条件的所有下标(注意此时array为有序数组)
                int l = mid - 1;
                while (l >= 0 && array[l] == target) {
                    list.add(l);
                    l--;
                }
                int r = mid + 1;
                while (r <= right && array[r] == target) {
                    list.add(r);
                    r++;
                }
                list.sort((x, y) -> x - y);
                return list;
            }
        }
        return list;
    }

    private static List<Integer> insertValueSearchAllWithNoRecursion(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        ArrayList<Integer> list = new ArrayList<>();
        while (left <= right) {
            int mid = left + (target - array[left]) / (array[right] -array [left]) * (right - left);
            if(target < array[mid]) {
                right = mid - 1;
            }else if(target > array[mid]) {
                left = mid + 1;
            }else {
                list.add(mid);
                // 继续向左右两侧查找
                int l = mid - 1;
                while(l>=0 && array[l] == target){
                    list.add(l);
                    l--;
                }
                int r = mid + 1;
                while (r <= right && array[r] == target) {
                    list.add(r);
                    r++;
                }
            }
        }
        return list;
    }

    private static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = array[i];
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[i];
                array[i] = min;
                array[minIndex] = temp;
            }
        }
    }

}
