package linkedlist;

import org.junit.Test;

import java.util.Arrays;

/**
 * 88. 合并两个有序数组
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * 请你合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m
 * 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。

 * @author lilibo
 * @create 2022-03-13 11:26 PM
 */
public class 合并两个有序数组Demo {

    @Test
    public void test1() {
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = {2,5,6};
        merge2(nums1, 3, nums2, 3);
    }

    /**
     * 逆向双指针
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int tail = m + n - 1;
        int cur;
        while (p1 >= 0 || p2 >= 0) {
            if(p1 == -1) {
                cur = nums2[p2--];
            }else if(p2 == -1) {
                cur = nums1[p1--];
            }else if(nums1[p1] > nums2[p2]) {
                cur = nums1[p1--];
            }else {
                cur = nums2[p2--];
            }
            nums1[tail--] = cur;
        }
        System.out.println(Arrays.toString(nums1));
    }

    /**
     * 双指针
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge1(int[] nums1, int m, int[] nums2, int n) {
        int[] nums3 = new int[m + n];
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        while (p1 < m && p2 < n) {
            if(nums1[p1] < nums2[p2]) {
                nums3[p3] = nums1[p1];
                p3++;
                p1++;
            }else {
                nums3[p3] = nums2[p2];
                p3++;
                p2++;
            }
        }
        while (p1 < m) {
            nums3[p3] = nums1[p1];
            p3++;
            p1++;
        }
        while (p2 < n) {
            nums3[p3] = nums2[p2];
            p3++;
            p2++;
        }
        System.out.println(Arrays.toString(nums3));
    }



}
