package sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lilibo
 * @create 2022-03-14 10:14 PM
 */
public class 三数之和Demo {

    @Test
    public void test1() {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(nums));
    }

    /**
     * 使用排序+双指针算法
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        // 先排序,简化重复
        Arrays.sort(nums);
        List<List<Integer>> resList = new ArrayList<>();
        // 枚举first
        for (int first = 0; first < n; first++) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // third对应的指针指向数组的最右端
            int third = n - 1;
            int target = -nums[first];
            // 枚举second
            for (int second = first + 1; second < n; second++) {
                // 需要和上一次枚举的数不同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证second的指针在third的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }
                // 如果指针重合,随着second后续的增加
                // 就不会有满足 first + second + third = 0并且second < third的third了,可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    resList.add(list);
                }
            }
        }
        return resList;
    }

}
