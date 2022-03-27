package dynamic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 现在有 n 个城市，分别用 0, 1…, n - 1 这些序号表示，城市之间的航线用三元组 [from, to, price] 来表示，
 * 比如说三元组 [0,1,100] 就表示，从城市 0 到城市 1 之间的机票价格是 100 元。
 * 题目会给你输入若干参数：正整数 n 代表城市个数，数组 flights 装着若干三元组代表城市间的航线及价格，城市编号
 * src 代表你所在的城市，城市编号 dst 代表你要去的目标城市，整数 K 代表你最多经过的中转站个数。
 *
 * 函数签名如下：
 *
 * int findCheapestPrice(int n, int[][] flights, int src, int dst, int K);
 * 请你的算法计算，在 K 次中转之内，从 src 到 dst 所需的最小花费是多少钱，如果无法到达，则返回 -1。
 *
 * 比方说题目给的例子：
 *
 * n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, K = 1
 *
 * 提示: s1, s2 是指向 dst 的相邻节点，我只要知道 K - 1 步之内从 src 到达 s1, s2，那我就可以在 K 步之内从 src 到达 dst。
 * 也就是如下关系式：
 * dp(dst, k) = min(
 *     dp(s1, k - 1) + w1,
 *     dp(s2, k - 1) + w2
 * )
 * 这就是新的状态转移方程，如果你能看懂这个算式，就已经可以解决这道题了。
 * @author lilibo
 * @create 2022-03-03 12:34 AM
 */
public class 最省钱旅游问题Demo {

    public static void main(String[] args) {
        最省钱旅游问题Demo findCheapestPriceDemo = new 最省钱旅游问题Demo();
        int[][] flights = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        int cheapestPrice = findCheapestPriceDemo.findCheapestPrice(3, flights, 0, 2, 3);
        System.out.println("从0到2最短路径和为: " + cheapestPrice);
    }

    /*
     *  根据上述思路，我怎么知道 s1, s2 是指向 dst 的相邻节点，他们之间的权重是 w1, w2？
        我希望给一个节点，就能知道有谁指向这个节点，还知道它们之间的权重，对吧。
        专业点说，得用一个数据结构记录每个节点的「入度」indegree：
     */
    // 哈希表记录每个点的入度
    // to -> [from, price]
    private HashMap<Integer, List<int[]>> indegree;
    private int src, dst;

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        // 将中转站个数转化成边的条数
        K++;
        this.src = src;
        this.dst = dst;

        indegree = new HashMap<>();
        for (int[] f : flights) {
            int from = f[0];
            int to = f[1];
            int price = f[2];
            // 记录谁指向该节点，以及之间的权重
            indegree.putIfAbsent(to, new LinkedList<>());
            indegree.get(to).add(new int[] {from, price});
        }

        return dp(dst, K);
    }

    // 定义：从 src 出发，k 步之内到达 s 的最短路径权重
    public int dp(int s, int k) {
        // base case
        if (s == src) {
            return 0;
        }
        if (k == 0) {
            return -1;
        }
        // 初始化为最大值，方便等会取最小值
        int res = Integer.MAX_VALUE;
        if (indegree.containsKey(s)) {
            // 当 s 有入度节点时，分解为子问题
            for (int[] v : indegree.get(s)) {
                int from = v[0];
                int price = v[1];
                // 从 src 到达相邻的入度节点所需的最短路径权重
                int subProblem = dp(from, k - 1);
                // 跳过无解的情况
                if (subProblem != -1) {
                    res = Math.min(res, subProblem + price);
                }
            }
        }
        // 如果还是初始值，说明此节点不可达
        return res == Integer.MAX_VALUE ? -1 : res;
    }

}
