package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lilibo
 * @create 2021-08-12 6:31 PM
 */
public class FibonacciSearchDemo {

    public static void main(String[] args) {
        int[] array = {1, 3, -1, 10, 20, 5, 6, 7, 5, 33, 21, 11, 5, 11};
        System.out.println("排序前数组: " + Arrays.toString(array));
        mergeSort(array, 0, array.length - 1, new int[array.length]); //归并排序需要额外保存中间变量的数组
        System.out.println("排序后数组: " + Arrays.toString(array));
        // getFibonacciArray(20);
        int target = 11;
        List<Integer> targetIndexList = fibSearch(array, target);
        System.out.printf("target = %d 的下标集合为: %s", target, Arrays.toString(targetIndexList.toArray()));
    }

    private static List<Integer> fibSearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        // 确定新数组长度, 使之满足斐波那契数列长度
        int[] fibonacciArray = getFibonacciArray(20);
        int k = 0;
        // f[k] - 1 = (f[k-1] - 1) + 1 + (f[k-2] -1)
        while (fibonacciArray[k] - 1 < array.length) {
            k++;
        }
        // 构建新数组
        int newLength = fibonacciArray[k] - 1;
        int[] newArray = Arrays.copyOf(array, newLength);
        for (int i = array.length; i < newLength; i++) {
            newArray[i] = array[array.length - 1];
        }
        List<Integer> list = new ArrayList<>();
        while (left <= right) {
            int mid = left + fibonacciArray[k - 1] - 1;
            if (target < newArray[mid]) {
                right = mid - 1;
                k--;
            } else if (target > newArray[mid]) {
                left = mid + 1;
                k -= 2;
            } else {
                if (mid >= right) {
                    list.add(right);
                    int l = right - 1;
                    while (l >= left && newArray[l] == target) {
                        list.add(l);
                        l--;
                    }
                } else {
                    list.add(mid);
                    int l = mid - 1;
                    while (l >= left && newArray[l] == target) {
                        list.add(l);
                        l--;
                    }
                    int r = mid + 1;
                    while (r <= right && newArray[r] == target) {
                        list.add(r);
                        r++;
                    }
                }
                list.sort((x, y) -> x - y);
                return list;
            }
        }
        return list;
    }

    private static int[] getFibonacciArray(int size) {
        if (size < 2) {
            System.out.println("size < 2无法构成斐波那契数列!");
        }
        int[] arr = new int[size];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < size; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        System.out.println("获取的斐波那契数列为: " + Arrays.toString(arr));
        return arr;
    }

    private static void mergeSort(int[] array, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 拆分
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
        t = 0;
        index1 = left;
        while (index1 <= right) {
            array[index1] = temp[t];
            index1++;
            t++;
        }
    }

}
