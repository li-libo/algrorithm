package dynamic;

import java.util.Arrays;

/**
 * 最长子序列问题
 * @author lilibo
 * @create 2022-02-25 12:14 AM
 */
public class 最长子序列问题Demo {

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int lengthOfLIS = getLengthOfLIS(nums);
        System.out.println("最长子序列长度为: " + lengthOfLIS);
    }

    public static int getLengthOfLIS(int[] nums) {
        // 我们的定义是这样的：dp[i] 表⽰以 nums[i] 这个数结尾的最⻓递增⼦序列的⻓度
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        // dp数组全部初始化为1, 因为最小子序列长度也是1
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j < i; j ++) {
                if(nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;

        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        } return res;
    }

    public static int getLengthOfLIS1(int[] nums) {
        int[] top = new int[nums.length];
        // 牌堆数初始化为 0
        int piles = 0;
        for (int i = 0; i < nums.length; i++) {
            // 要处理的扑克牌
            int poker = nums[i];

            /***** 搜索左侧边界的二分查找 *****/
            int left = 0, right = piles;
            while (left < right) {
                int mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            /*********************************/

            // 没找到合适的牌堆，新建一堆
            if (left == piles) piles++;
            // 把这张牌放到牌堆顶
            top[left] = poker;
        }
        // 牌堆数就是 LIS 长度
        return piles;
    }


}
