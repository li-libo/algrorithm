package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 插值查找案例
 * @author lilibo
 * @create 2022-01-29 11:02 PM
 */
public class InsertSearchExample {

    public static void main(String[] args) {
        int[] arr = {1, 10, 8, 89, 100, 32, 100, 78, 234};
        System.out.println("排序前数组为: " + Arrays.toString(arr));
        shellSort(arr);
        System.out.println("排序后数组为: " + Arrays.toString(arr));
        int target = 100;
        List<Integer> list = insertSearch(arr, 0, arr.length - 1, target);
        System.out.println("target = " + target + "的索引集合为: " + list);
    }

    private static List<Integer> insertSearch(int[] arr, int left, int right, int target) {
        List<Integer> list = new ArrayList<>();
        if(left < right) {
            int mid = left + (target - arr[left]) / (arr[right] - arr[left]) * (right - left);
            if(arr[mid] == target) {
                list.add(mid);
                int l = mid - 1;
                while (l >= left && arr[l] == target) {
                    list.add(l);
                    l--;
                }
                int r = mid + 1;
                while (r <= right && arr[r] == target) {
                    list.add(r);
                    r++;
                }
                return list;
            }else if(target < arr[mid]) {
                list.addAll(insertSearch(arr, left, mid - 1, target));
                return list;
            }else {
                list.addAll(insertSearch(arr, mid + 1, right, target));
                return list;
            }
        }
        return list;
    }

    private static void shellSort(int[] arr) {
        for(int gap = arr.length / 2; gap > 0; gap /= 2){
            for(int i = gap; i < arr.length; i++) {
                int insertIndex = i - gap;
                int insertValue = arr[i];
                while (insertIndex >= 0 && arr[insertIndex] > insertValue) {
                    arr[insertIndex + gap] = arr[insertIndex];
                    insertIndex-=gap;
                }
                if(insertIndex + gap != i) {
                    arr[insertIndex + gap] = insertValue;
                }
            }
        }
    }
}
