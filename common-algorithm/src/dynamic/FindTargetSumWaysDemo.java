package dynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lilibo
 * @create 2022-03-01 12:59 AM
 */
public class FindTargetSumWaysDemo {

    public static void main(String[] args) {
        int[] nums = {1,3,1,4,2};
        int target = 5;
        int result = findTargetSumWays(nums, target);
        System.out.println(result + "种组合");
    }

    private static int result = 0;

    /* 主函数 */
    public static int findTargetSumWays(int[] nums, int target) {
        if (nums.length == 0) return 0;
        backtrack(nums, 0, target);
        return result;
    }

    /* 回溯算法模板 */
    private static void backtrack(int[] nums, int i, int remain) {
        // base case
        if (i == nums.length) {
            if (remain == 0) {
                // 说明恰好凑出 target
                result++;
            }
            return;
        }
        // 给 nums[i] 选择 - 号
        remain += nums[i];
        // 穷举 nums[i + 1]
        backtrack(nums, i + 1, remain);
        // 撤销选择
        remain -= nums[i];

        // 给 nums[i] 选择 + 号
        remain -= nums[i];
        // 穷举 nums[i + 1]
        backtrack(nums, i + 1, remain);
        // 撤销选择
        remain += nums[i];
    }

    public static int findTargetSumWays1(int[] nums, int target) {
        if (nums.length == 0) return 0;
        return dp(nums, 0, target);
    }

    // 备忘录
    private static Map<String, Integer> memo = new HashMap<>();
    private static int dp(int[] nums, int i, int remain) {
        // base case
        if (i == nums.length) {
            if (remain == 0) return 1;
            return 0;
        }
        // 把它俩转成字符串才能作为哈希表的键
        String key = i + "," + remain;
        // 避免重复计算
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        // 还是穷举
        int result = dp(nums, i + 1, remain - nums[i]) + dp(nums, i + 1, remain + nums[i]);
        // 记入备忘录
        memo.put(key, result);
        return result;
    }

}
