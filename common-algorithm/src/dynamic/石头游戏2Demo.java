package dynamic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 你和你的朋友面前有一排石头堆，用一个数组 piles 表示，piles[i] 表示第 i 堆石子有多少个。你们轮流拿石头，一次拿一堆，但是只能拿走最左边或者
 * 最右边的石头堆。所有石头被拿完后，谁拥有的石头多，谁获胜。
 * 石头的堆数可以是任意正整数，石头的总数也可以是任意正整数，这样就能打破先手必胜的局面了。比如有三堆石头 piles = [1, 100, 3]，先手不管拿 1
 * 还是 3，能够决定胜负的 100 都会被后手拿走，后手会获胜。
 * 假设两人都很聪明，请你设计一个算法，返回先手和后手的最后得分（石头总数）之差。比如上面那个例子，先手能获得 4 分，后手会获得 100 分，你的算法
 * 应该返回 -96。
 * 这样推广之后，这个问题算是一道 Hard 的动态规划问题了。博弈问题的难点在于，两个人要轮流进行选择，而且都贼精明，应该如何编程表示这个过程呢？
 * @author lilibo
 * @create 2022-03-11 9:53 PM
 */
public class 石头游戏2Demo {

    @Test
    public void test1() throws CloneNotSupportedException {
        int[] piles = {2, 8, 3, 5};
        int diffScore = stoneGame(piles);
        System.out.println(Arrays.toString(piles) + "数组先手和后手分差为: " + diffScore);
    }

    public int stoneGame(int[] piles) throws CloneNotSupportedException {
        int n = piles.length;
        /*
         状态3个: 开始的索引i、结束的索引j、先手/后手
         选择2个: 选择最左边的石头, 选择最右边的石头
         假设dp[i][j].fir/sec为piles[i]到piles[j]的先手后手分数
         转态方程:
         left = piles[i] + dp[i + 1][j].sec;
         right = piles[j] + dp[i][j - 1].sec;
         if(left > right) {
            dp[i][j].fir = left;
            dp[i][j].sec = dp[i + 1][j].fir;
         }else {
            dp[i][j].fir = right;
            dp[i][j].sec = dp[i][j - 1].fir;
         }
         */
        MyPair<Integer>[][] dp = new MyPair[n][n];
        // base case
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                dp[i][j] = new MyPair<>(0, 0 );
            }
        }
        for(int i = 0; i < n; i++) {
            dp[i][i].fir = piles[i];
            dp[i][i].sec = 0;
        }
        // 从对角线开始从下往上推、从左往右推dp
        for(int i = n - 2; i >=0 ; i--) {
            for(int j = i + 1; j < n; j++) {
                int left = piles[i] + dp[i + 1][j].sec;
                int right = piles[j] + dp[i][j - 1].sec;
                if(left > right) {
                    dp[i][j].fir = left;
                    dp[i][j].sec = dp[i + 1][j].fir;
                }else {
                    dp[i][j].fir = right;
                    dp[i][j].sec = dp[i][j - 1].fir;
                }
            }
        }
        return dp[0][n - 1].fir - dp[0][n - 1].sec;
    }

}

class MyPair<T> implements Cloneable{
    T fir;
    T sec;

    public MyPair(T fir, T sec) {
        this.fir = fir;
        this.sec = sec;
    }

    public T getFir() {
        return fir;
    }

    public void setFir(T fir) {
        this.fir = fir;
    }

    public T getSec() {
        return sec;
    }

    public void setSec(T sec) {
        this.sec = sec;
    }

    @Override
    public String toString() {
        return "MyPair{" +
                "fir=" + fir +
                ", sec=" + sec +
                '}';
    }
}