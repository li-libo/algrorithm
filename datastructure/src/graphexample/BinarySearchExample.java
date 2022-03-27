package graphexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lilibo
 * @create 2022-02-02 5:34 PM
 */
public class BinarySearchExample {

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 4, 7, 6, 8, 9, 3};
        System.out.println("排序前数组为: " + Arrays.toString(array));
        selectSort(array);
        System.out.println("排序后数组为: " + Arrays.toString(array));
        System.out.println("3的下标为: " + binarySearch(array, 3));
    }

    private static List<Integer> binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        List<Integer> indexList = new ArrayList<>();
        while (left < right) {
            int mid = (left + right) / 2;
            int pivot = array[mid];
            if(target == pivot) {
                indexList.add(mid);
                int l = mid - 1;
                while (l >= left && array[l] == target) {
                    indexList.add(l);
                    l--;
                }
                int r = mid + 1;
                while (r <= right && array[r] == target) {
                    indexList.add(r);
                    r++;
                }
                return indexList;
            }else if(target < pivot){
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return indexList;
    }

    private static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = array[i];
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if(array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            if(i != minIndex) {
                int temp = array[i];
                array[i] = min;
                array[minIndex] = temp;
            }
        }
    }
}
