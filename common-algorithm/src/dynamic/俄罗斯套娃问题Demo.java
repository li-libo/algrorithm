package dynamic;

import java.util.Arrays;

/**
 * 俄罗斯套娃问题
 * 给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * <p>
 * 说明:
 * 不允许旋转信封。
 * <p>
 * 示例:
 * 输入: envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 输出: 3
 * 解释: 最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
 * <p>
 * A：
 * 这道题⽬其实是最⻓递增⼦序列（Longes Increasing Subsequence，简写为LIS）的⼀个变种，因为很显然，每次合法的嵌套是⼤的套⼩的，相当于找⼀个最⻓递增的⼦序列，其⻓度就是最多能嵌套的信封个数。但是难点在于，标准的 LIS 算法只能在数组中寻找最⻓⼦序列，⽽我们的信封是由 (w, h) 这样的⼆维数对形式表⽰的，如何把 LIS 算法运⽤过来呢？
 * 这道题的解法是⽐较巧妙的：
 * <p>
 * 先对宽度 w 进⾏升序排序，如果遇到 w 相同的情况，则按照⾼度 h 降序排序。之后把所有的 h 作为⼀个数组，在这个数组上计算 LIS 的⻓度就是答案。
 * 这个解法的关键在于，对于宽度 w 相同的数对，要对其高度 h 进行降序排序。因为两个宽度相同的信封不能相互包含的，逆序排序保证在 w 相同的数对中最多只选取一个。
 *
 * @author lilibo
 * @create 2022-03-02 8:39 PM
 */
public class 俄罗斯套娃问题Demo {

    public static void main(String[] args) {
        int[][] envelopes = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        int maxmaxEnvelopes = maxEnvelopes(envelopes);
        System.out.println("最大的信封个数为: " + maxmaxEnvelopes);
    }

    public static int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                return o1[0] - o2[0];
            }
        });
        System.out.println("按w升序、w相同按降序排序后的信封数组为...");
        for (int[] w : envelopes) {
            System.out.println(Arrays.toString(w));
        }
        int[] array = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            array[i] = envelopes[i][1];
        }
        // 获取array的最长子序列
        return getLengthOfLis(array);
    }

    private static int getLengthOfLis(int[] array) {
        /*
            最长自增子序列状态方程为:
             dp[i] = Math.max(dp[i], dp[j] + 1);
         */
        int[] dp = new int[array.length];
        Arrays.fill(dp, 1);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i] > array[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = 0;
        for(int i : dp) {
            max = Math.max(max, i);
        }
        return max;
    }

}
