package dynamic;

import java.util.Arrays;

/**
 * 给定1个只包含正整数的非空数组,可否将这个数组分割成2个子集,使2个子集元素和相等
 * @author lilibo
 * @create 2022-02-27 12:41 AM
 */
public class 子集分割问题Demo {

    public static void main(String[] args) {
        int[] array = {3, 2, 1, 5, 1};
        boolean b = canPartition1(array);
        System.out.println(Arrays.toString(array) + "是否能分成2个等子集? " + b);
    }

    /**
     * 判断是否能将这个数组分割成2个子集
     * @param array
     */
    private static boolean canPartition(int[] array) {
        int sum = Arrays.stream(array).sum();
        // 如果和为奇数,不可能分割成2个等子集
        if(sum % 2 == 1) {
            return false;
        }
        int rowLength = array.length;
        sum = sum / 2;
        // 定义状态方程
        boolean[][] dp = new boolean[rowLength + 1][sum + 1];
        // base case
        for(int i = 0; i <= rowLength; i++) {
            dp[i][0] = true;
        }
        for(int i = 1; i <= rowLength; i++) {
            for(int j = 1; j <= sum; j++) {
                if(j < array[i - 1]) {
                    // 背包容量不够,不能装入
                    dp[i][j] = dp[i - 1][j];
                }else {
                    // 装入或者不装入背包
                    dp[i][j] = dp[i-1][j] || dp[i - 1][j - array[i -1]];
                }
            }
        }
        return dp[rowLength][sum];
    }

    /**
     * 进一步优化(状态压缩)
     *  再进一步，是否可以优化这个代码呢？注意到 dp[i][j] 都是通过上一行 dp[i-1][..] 转移过来的，之前的数据都不会再使用了。
     *  所以，我们可以 对动态规划进行降维打击，将二维 dp 数组压缩为一维，节约空间复杂度
     * @param array
     * @return
     */
    public static boolean canPartition1(int[] array) {
        int sum = Arrays.stream(array).sum();
        // 如果和为奇数,不可能分割成2个等子集
        if(sum % 2 == 1) {
            return false;
        }
        int rowLength = array.length;
        sum = sum / 2;
        // 定义状态方程
        boolean[] dp = new boolean[sum + 1];
        // base case
        dp[0] = true;
        for(int i = 0; i < rowLength; i++) {
            for(int j = sum; j >=0 ; j--){
                if(j >= array[i]) {
                    dp[j] = dp[j] || dp[j - array[i]];
                }
            }
        }
        return dp[sum];
    }

}
