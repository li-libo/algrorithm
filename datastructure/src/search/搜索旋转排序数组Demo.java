package search;

import org.junit.Test;

import java.util.Arrays;

/**
 * 33. 搜索旋转排序数组
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为[4,5,6,7,0,1,2] 。
 * 给你旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
 * @author lilibo
 * @create 2022-03-12 12:09 AM
 */
public class 搜索旋转排序数组Demo {

    public static final String format = "%s中target = %d对应的下标为: %d";

    @Test
    public void test1() {
        int[] nums = {0, 1, 2, 4, 5, 6, 7};
        int target = 4;
        int targetIndex = binarySearch(nums, target);
        System.out.println(String.format(format, Arrays.toString(nums), 4, targetIndex));
    }

    /**
     * 但是这道题中，数组本身不是有序的，进行旋转后只保证了数组的局部是有序的，这还能进行二分查找吗？答案是可以的。
     * 可以发现的是，我们将数组从中间分开成左右两部分的时候，一定有一部分的数组是有序的。
     * @param nums
     * @param target
     * @return
     */
    public int binarySearch(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        if(nums.length == 1) {
            if(nums[0] == target) {
                return 0;
            }
        }
        int l = 0;
        int r = nums.length - 1;
        int targetIndex = -1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if(target == nums[mid]) {
                targetIndex = mid;
                break;
            }else if(target < mid) {
                r = mid - 1;
            }else {
                l = mid + 1;
            }
        }
        return targetIndex;
    }

    public int searchRotatedArray(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        if(nums.length == 1) {
            if(nums[0] == target) {
                return 0;
            }
            return -1;
        }
        int l = 0;
        int r = nums.length - 1;
        int targetIndex = -1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if(nums[mid] == target) {
                targetIndex = mid;
            }
            if(nums[mid] >= nums[0]) {
                if(nums[0] < target && target < nums[mid]) {
                    r = mid - 1;
                }else {
                    l = mid + 1;
                }
            }else {
                if(nums[mid] < target && target < nums[nums.length - 1]) {
                    l = mid + 1;
                }else {
                    r = mid - 1;
                }
            }
        }
        return targetIndex;
    }

}
