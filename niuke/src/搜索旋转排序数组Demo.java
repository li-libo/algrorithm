import org.junit.Test;

import java.util.Arrays;

/**
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1],
 * nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * @author lilibo
 * @create 2022-03-13 18:18
 */
public class 搜索旋转排序数组Demo {

    public static final String format = "%s中target = %d对应的下标为: %d";

    @Test
    public void test1() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int targetIndex = searchRotatedArray(nums, 0);
        System.out.println(String.format(format, Arrays.toString(nums), 0, targetIndex));
    }

    /**
     * 虽然旋转了,但array从某一轴劈开,有一半是有序的
     * @param array
     * @param target
     * @return
     */
    public int searchRotatedArray(int[] array, int target) {
        int l = 0;
        int r = array.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if(array[mid] == target) {
                return mid;
            }
            if(array[mid - 1] > array[l]) {
                if(target >= array[l] && target <= array[mid - 1]) {
                    r = mid - 1;
                }else {
                    l = mid + 1;
                }
            }else {
                if(target >= array[mid + 1] && target <= array[r]) {
                    l = mid + 1;
                }else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}
