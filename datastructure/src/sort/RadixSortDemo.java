package sort;

import java.util.Arrays;

/**
 * 基数排序
 * <p>
 * 1) 基数排序（radix sort）属于“分配式排序”（distribution sort）， 又称“桶子法”（bucket sort）或 bin sort， 顾名思义，
 * 它是通过键值的各个位的值，将要排序的元素分配至某些“桶”中，达到排序的作用
 * 2) 基数排序法是属于稳定性的排序，基数排序法的是效率高的稳定性排序法
 * 3) 基数排序(Radix Sort)是桶排序的扩展
 * <p>
 * 基数排序基本思想: 将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。 这样从最低位排序
 * 一直到最高位排序完成以后, 数列就变成一个有序序列。
 *
 * @author lilibo
 * @create 2021-08-12 2:06 PM
 */
public class RadixSortDemo {

    public static void main(String[] args) {
        int[] array = {21, 1, 0, 3, 45, 341, 1024, 2132, 67, 59, 38, 70, 33, 22, 984};
        System.out.println("基数排序前数组: " + Arrays.toString(array));
        radixSort(array);
        System.out.println("基数排序后数组: " + Arrays.toString(array));
    }

    /**
     * 基数/桶排序
     *
     * @param array
     */
    private static void radixSort(int[] array) {
        // 获取最大值的位数
        int max = array[0];
        int index = 1;
        while (index < array.length) {
            if (array[index] > max) {
                max = array[index];
            }
            index++;
        }
        int maxLength = (max + "").length();
        // 定义桶和桶计数器
        int[][] buckets = new int[10][array.length];
        int[] bucketCounts = new int[10];
        for (int figures = 1, n = 1; figures <= maxLength; figures++, n *= 10) {
            // 放入桶中
            for (int i = 0; i < array.length; i++) {
                int remainder = array[i] / n % 10;
                int bucketCount = bucketCounts[remainder];
                buckets[remainder][bucketCount] = array[i];
                bucketCount++; // 计数器增1
                bucketCounts[remainder] = bucketCount;
            }
            int t = 0;
            // 将桶中的数拷贝回原数组
            for (int i = 0; i < bucketCounts.length; i++) {
                int bucketCount = bucketCounts[i];
                if (bucketCount != 0) {
                    for (int j = 0; j < bucketCount; j++) {
                        array[t] = buckets[i][j];
                        t++;
                    }
                }
            }
            // 清空桶和桶计数器以便下次循环
            Arrays.fill(bucketCounts, 0);
            for (int i = 0; i < buckets.length; i++) {
                Arrays.fill(buckets[i], 0);
            }
        }
    }

}
