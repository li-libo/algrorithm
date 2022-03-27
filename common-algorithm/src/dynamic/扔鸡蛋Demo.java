package dynamic;

/**
 * 你面前有一栋从 1 到 N 共 N 层的楼，然后给你 K 个鸡蛋（K 至少为 1）。现在确定这栋楼存在楼层 0 <= F <= N，在这层楼将鸡蛋扔下去，
 * 鸡蛋恰好没摔碎（高于 F 的楼层都会碎，低于 F 的楼层都不会碎）。现在问你，最坏情况下，你至少要扔几次鸡蛋，才能确定这个楼层 F 呢？
 * @author lilibo
 * @create 2022-03-03 1:05 AM
 */
public class 扔鸡蛋Demo {

    /**
     * 当有k个鸡蛋时,n楼层的最优解
     * 状态方程:
     * dp(k,i) = 碎了: dp(k-1, i - 1)
     *         = 没碎: dp(k, n - i)
     * @param k
     * @param n
     */
    public int dp(int k, int n) {
        if(k == 1) {
            return n;
        }
        if(n == 0) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        for(int i = 1; i <= n; i++){
            //最坏情况下的最少扔鸡蛋次数
            res = Math.min(res,
                    Math.max( dp(k - 1, i - 1),  // 碎
                            dp(k, n - i) ) //没碎
                            + 1); //在i楼层又扔了一次
        }
        return res;
    }
}
