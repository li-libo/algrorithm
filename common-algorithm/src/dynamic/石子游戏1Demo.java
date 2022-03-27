package dynamic;

import org.junit.Test;

/**
 * 877. 石子游戏
 * Alice 和 Bob 用几堆石子在做游戏。一共有偶数堆石子，排成一行；每堆都有 正 整数颗石子，数目为 piles[i]。
 * 游戏以谁手中的石子最多来决出胜负。石子的总数是奇数 ，所以没有平局。
 * Alice 和 Bob 轮流进行，Alice 先开始。每回合，玩家从行的开始或结束处取走整堆石头。这种情况一直持续到没有更多的石子堆为止，
 * 此时手中石子最多的玩家获胜 。
 * 假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回true，当 Bob 赢得比赛时返回false。

 示例1:
 输入：piles = [5,3,4,5]
 输出：true
 解释：
 Alice 先开始，只能拿前 5 颗或后 5 颗石子 。
 假设他取了前 5 颗，这一行就变成了 [3,4,5] 。
 如果 Bob 拿走前 3 颗，那么剩下的是 [4,5]，Alice 拿走后 5 颗赢得 10 分。
 如果 Bob 拿走后 5 颗，那么剩下的是 [3,4]，Alice 拿走后 4 颗赢得 9 分。
 这表明，取前 5 颗石子对 Alice 来说是一个胜利的举动，所以返回 true

 示例 2：
 输入：piles = [3,7,2,3]
 输出：true
 提示：
 2 <= piles.length <= 500
 piles.length 是 偶数
 1 <= piles[i] <= 500
 sum(piles[i])是 奇数

 * @author lilibo
 * @create 2022-03-11 11:00 PM
 */
public class 石子游戏1Demo {

    @Test
    public void test1() {
        int[] piles = {5,3,4,5};
        boolean b = stoneGame(piles);
        System.out.println(b);
    }

    public boolean stoneGame(int[] piles) {
        /*
         定义状态方程 dp[i][j]
         定义二维数组 dp，其行数和列数都等于石子的堆数，dp[i][j] 表示当剩下的石子堆为下标i到下标j时，即在下标范围 [i,j] 中，
         当前玩家与另一个玩家的石子数量之差的最大值，注意当前玩家不一定是先手Alice。
         */
        int length = piles.length;
        int[][] dp = new int[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = piles[i];
        }
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][length - 1] > 0;
    }

    /**
     * 486. 预测赢家
     * 给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
     * 玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。每一回合，玩家从数组的任意一端取一个数字
     * （即，nums[0] 或 nums[nums.length - 1]），取到的数字将会从数组中移除（数组长度减 1 ）。玩家选中的数字将会加到他的得分上。
     * 当数组中没有剩余数字可取时，游戏结束。
     * 如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，也返回 true 。你可以假设每个玩家的玩法
     * 都会使他的分数最大化。
     * @param nums
     * @return
     */
    public boolean PredictTheWinner(int[] nums) {
        int length = nums.length;
        int[][] dp = new int[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = nums[i];
        }
        for (int i = length - 2; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][length - 1] >= 0;
    }

}
