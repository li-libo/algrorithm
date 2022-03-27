package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lilibo
 * @create 2022-02-17 3:09 PM
 */
public class SearchDemo {

    public static void main(String[] args) {
        int[] array = {3, 2, 1, 1, 4, 5, 6, 3, 7};
        System.out.println("排序前数组: " + Arrays.toString(array));
        // selectSort(array);
        // bubbleSort(array);
        // quickSort(array, 0, array.length - 1);
        // insertSort(array);
        // shellSort(array);
        mergeSort(array, 0, array.length - 1, new int[array.length]);
        System.out.println("排序后数组: " + Arrays.toString(array));
        int target = 3;
        // 递归方式查找所有target为3的元素下标
        List<Integer> allIndexList = binarySearch(array, 0, array.length - 1, target);
        System.out.println("target = " + target + "的所有下标集合为: " + allIndexList);
        // 非递归方式查找所有target为3的元素下标
        allIndexList = binarySearch(array, target);
        System.out.println("target = " + target + "的所有下标集合为: " + allIndexList);
    }

    /**
     * 归并排序,分而治之
     * @param array
     */
    private static void mergeSort(int[] array, int left, int right, int[] tempArray) {
        if(left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid, tempArray);
            mergeSort(array, mid + 1, right, tempArray);
            merge(array, left, mid, right, tempArray); // 合并
        }
    }

    private static void merge(int[] array, int left, int mid, int right, int[] tempArray) {
        int index1 = left;
        int index2 = mid + 1;
        int t = 0;
        while (index1 <= mid && index2 <= right) {
            if(array[index1] < array[index2]) {
                tempArray[t] = array[index1];
                t++;
                index1++;
            }else {
                tempArray[t] = array[index2];
                t++;
                index2++;
            }
        }
        while (index1 <= mid) {
            tempArray[t] = array[index1];
            t++;
            index1++;
        }
        while (index2 <= right) {
            tempArray[t] = array[index2];
            t++;
            index2++;
        }
        // 拷贝回原数组
        t = 0;
        index1 = left;
        while (index1 <= right) {
            array[index1] = tempArray[t];
            t++;
            index1++;
        }
    }

    /**
     * shell排序
     * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
     * @param array
     */
    private static void shellSort(int[] array) {
        for(int gap = array.length / 2; gap > 0; gap/=2) {
            for(int i = gap; i < array.length; i++) {
                int insertValue = array[i];
                int insertIndex = i - gap;
                while (insertIndex >= 0 && array[insertIndex] > insertValue) {
                    // 元素后移
                    array[insertIndex + gap] = array[insertIndex];
                    // insertIndex指针向前移动
                    insertIndex-=gap;
                }
                if(insertIndex + gap != i) {
                    array[insertIndex + gap] = insertValue;
                }
            }
        }
    }

    /**
     * 插入排序
     * @param array
     */
    private static void insertSort(int[] array) {
        for(int i = 1; i < array.length; i++) {
            // 要插入的值
            int insertValue = array[i];
            // 要插入的位置
            int insertIndex =  i - 1;
            while (insertIndex >= 0 && array[insertIndex] > insertValue) {
                // 后移, 相当于空出要插入的位置
                array[insertIndex + 1] = array[insertIndex];
                // 指针继续前移
                insertIndex--;
                if(insertIndex + 1 != i) {
                    array[insertIndex + 1] = insertValue;
                }
            }
        }
    }

    /**
     * 选择排序
     * @param array
     */
    private static void selectSort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            int min = array[i];
            int minIndex = i;
            for(int j = i + 1; j < array.length; j++) {
                if(array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            if(min != array[i]) { // 最小值不是头元素
                int temp = array[i];
                array[i] = min;
                array[minIndex] = temp;
            }
        }
    }

    /**
     * 冒泡排序
     * @param array
     */
    private static void bubbleSort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {
            boolean changed = false;
            for(int j = 0; j < array.length - 1 - i; j++) {
                // 比较交换
                if(array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    changed = true;
                }
            }
            if(!changed) { // 如果1趟比较下来没有发生交换则数组已经有序
                break;
            }
        }
    }

    private static List<Integer> binarySearch(int[] array, int target) {
        List<Integer> allIndexList = new ArrayList<>();
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int pivot = array[mid];
            if(target == pivot) {
                allIndexList.add(mid);
                int l = mid - 1;
                while (l >= left && array[l] == target) {
                    allIndexList.add(l);
                    l--;
                }
                int r = mid + 1;
                while (r <= right && array[r] == target) {
                    allIndexList.add(r);
                    r++;
                }
                break;
            }else if(target < pivot) {
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        allIndexList.sort((x, y) -> x - y);
        return allIndexList;
    }

    private static List<Integer> binarySearch(int[] array, int left, int right, int target) {
        List<Integer> newList = new ArrayList<>();
        if (left > right) {
            return newList;
        }
        int mid = (left + right) / 2;
        int pivot = array[mid];
        if (target == pivot) {
            newList.add(mid);
            int l = mid - 1;
            while (l >= left && array[l] == target) {
                newList.add(l);
                l--;
            }
            int r = mid + 1;
            while (r <= right && array[r] == target) {
                newList.add(r);
                r++;
            }
            newList.sort((x, y) -> x - y);
            return newList;
        } else if (target < pivot) {
            newList.addAll(binarySearch(array, left, mid - 1, target));
            newList.sort((x, y) -> x - y);
            return newList;
        } else {
            newList.addAll(binarySearch(array, mid + 1, right, target));
            newList.sort((x, y) -> x - y);
            return newList;
        }
    }

    private static void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int l = left;
        int r = right;
        int pivot = array[(l + r) / 2];
        while (l < r) {
            while (array[l] < pivot && l <= right) {
                l++;
            }
            while (array[r] > pivot && r >= left) {
                r--;
            }
            if (l <= r) { // 包含了l == r需要错开指针的情况
                int temp = array[l];
                array[l] = array[r];
                array[r] = temp;
                l++;
                r--;
            }
        }
        if (r > left) {
            quickSort(array, left, r);
        }
        if (l < right) {
            quickSort(array, l, right);
        }
    }


}
