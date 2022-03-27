package dynamic;

/**
 * V1.0——递归方法
 *
 * 首先我们用最容易理解的递归方法来尝试解决这个问题
 F(i,C)=max(F(i−1,C), v(i)+F(i−1,C−w(i)))
 * @author lilibo
 * @create 2022-02-24 10:32 PM
 */
public class 递归求解背包问题Demo {
    /**
     * 解决背包问题的递归函数
     *
     * @param w        物品的重量数组
     * @param v        物品的价值数组
     * @param index    当前待选择的物品索引
     * @param capacity 当前背包有效容量
     * @return 最大价值
     */
    private static int solveKS(int[] w, int[] v, int index, int capacity) {

        //基准条件：如果索引无效或者容量不足，直接返回当前价值0
        if (index < 0 || capacity <= 0)
            return 0;

        //不放第index个物品所得价值
        int res = solveKS(w, v, index - 1, capacity);

        //放第index个物品所得价值（前提是：第index个物品可以放得下）
        if (w[index] <= capacity) {
            res = Math.max(res, v[index] + solveKS(w, v, index - 1, capacity - w[index]));
        }
        return res;
    }

    public static int knapSack(int[] w, int[] v, int C) {
        int size = w.length;
        return solveKS(w, v, size - 1, C);
    }

    /**
     *         productList.add(new Product("吉他", 1, 1500));
     *         productList.add(new Product("音响", 4, 3000));
     *         productList.add(new Product("电脑", 3, 2000));
     * @param args
     */
    public static void main(String[] args){
        int[] w = {1, 4, 3};
        int[] v = {1500, 3000, 2000};
        System.out.println(knapSack(w,v,4));
    }
}

