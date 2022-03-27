package sort;

import java.util.Arrays;

/**
 * 插入排序（Insertion Sorting）的基本思想是：把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，
 * 无序表中包含有 n-1 个元素，排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入
 * 到有序表中的适当位置，使之成为新的有序表。
 * <p>
 * 简单插入排序存在的问题: 当需要插入的数是较小的数时,后移次数明显增多,对效率有影响
 *
 * @author lilibo
 * @create 2021-08-10 8:26 PM
 */
public class InsertSortDemo {

    public static void main(String[] args) {
        int[] array = {2, 1, 3, 4, 2, 3, 5, 6, 7, 1};
        System.out.println("排序前数组为: " + Arrays.toString(array));
        insertSort(array);
        System.out.println("排序后数组为: " + Arrays.toString(array));
    }

    private static void insertSort(int[] array) {
        for(int i = 1; i < array.length; i++) {
            // 先假定要插入的值与要插入的位置
            int insertValue = array[i];
            int insertIndex = i - 1;
            while (insertIndex >=0 && array[insertIndex] > insertValue) {
                array[insertIndex + 1] = array[insertIndex]; // 后移
                insertIndex--; // 继续向前寻找
            }
            if(insertIndex + 1 != i) {
                array[insertIndex + 1] = insertValue; // 插入到有序表中指定位置
            }
        }
    }

}

