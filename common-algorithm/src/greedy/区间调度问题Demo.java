package greedy;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-03-08 12:11 AM
 */
public class 区间调度问题Demo {

    @Test
    public void test1() {
        int[][] intvs = {{1, 3}, {2, 4}, {3, 6}};
        int num = intervalSchedule(intvs);
        System.out.println("不相交的区间最多有: " + num);
    }

    /**
     * 给你很多形如 [start, end] 的闭区间，请你设计一个算法，算出这些区间中最多有几个互不相交的区间。
     * int intervalSchedule(int[][] intvs);
     * 举个例子，intvs = [[1,3], [2,4], [3,6]]，这些区间最多有 2 个区间互不相交，即 [[1,3], [3,6]]，你的算法应该返回 2。
     * 注意边界相同并不算相交。
     * 这个问题在生活中的应用广泛，比如你今天有好几个活动，每个活动都可以用区间 [start, end] 表示开始和结束的时间，请问你今天最多能参加几个活动呢？
     * 显然你一个人不能同时参加两个活动，所以说这个问题就是求这些时间区间的最大不相交子集。
     *
     * 正确的思路其实很简单，可以分为以下三步：
     * 1、从区间集合 intvs 中选择一个区间 x，这个 x 是在当前所有区间中结束最早的（end 最小）。
     * 2、把所有与 x 区间相交的区间从区间集合 intvs 中删除。
     * 3、重复步骤 1 和 2，直到 intvs 为空为止。之前选出的那些 x 就是最大不相交子集。
     * @param intvs
     * @return
     */
    public int intervalSchedule(int[][] intvs) {
        if(intvs == null || intvs.length == 0) {
            return 0;
        }
        // 按end升序排序
        Arrays.sort(intvs, (o1, o2) -> {
            return o1[1] - o2[1];
        });
        // 至少有个区间不相交
        int count = 1;
        // 排序后第1个区间就是x
        int x_end = intvs[0][1];
        for(int[] interval : intvs) {
            int start = interval[0];
            // 不相交则start >= x_end
            if(start >= x_end) {
                count++;
                x_end = interval[1]; // 更新x_end
            }
        }
        return count;
    }

    /**
     * 输入一个区间的集合，请你计算，要想使其中的区间都互不重叠，至少需要移除几个区间？函数签名如下：
     * int eraseOverlapIntervals(int[][] intvs);
     * 其中，可以假设输入的区间的终点总是大于起点，另外边界相等的区间只算接触，但并不算相互重叠。
     * 比如说输入是 intvs = [[1,2],[2,3],[3,4],[1,3]]，算法返回 1，因为只要移除 [1,3] 后，剩下的区间就没有重叠了。
     * 我们已经会求最多有几个区间不会重叠了，那么剩下的不就是至少需要去除的区间吗？
     * @param intvs
     * @return
     */
    public int eraseOverlapIntervals(int[][] intvs) {
        int n = intvs.length;
        return n - intervalSchedule(intvs);
    }

    /**
     * 再说说力扣第 452 题「 用最少的箭头射爆气球」，我来描述一下题目：
     * 假设在二维平面上有很多圆形的气球，这些圆形投影到 x 轴上会形成一个个区间对吧。那么给你输入这些区间，你沿着 x 轴前进，可以垂直向上射箭，
     * 请问你至少要射几箭才能把这些气球全部射爆呢？
     * 函数签名如下：
     * int findMinArrowShots(int[][] intvs);
     * 比如说输入为 [[10,16],[2,8],[1,6],[7,12]]，算法应该返回 2，因为我们可以在 x 为 6 的地方射一箭，射爆 [2,8] 和 [1,6] 两个气球，
     * 然后在 x 为 10，11 或 12 的地方射一箭，射爆 [10,16] 和 [7,12] 两个气球。
     * 其实稍微思考一下，这个问题和区间调度算法一模一样！如果最多有 n 个不重叠的区间，那么就至少需要 n 个箭头穿透所有区间：
     *
     * 只是有一点不一样，在 intervalSchedule 算法中，如果两个区间的边界触碰，不算重叠；而按照这道题目的描述，箭头如果碰到气球的边界气球也会爆炸，
     * 所以说相当于区间的边界触碰也算重叠：
     * @param intvs
     * @return
     */
    public int findMinArrowShots(int[][] intvs) {
        if(intvs == null || intvs.length == 0) {
            return 0;
        }
        // 按end升序排序
        Arrays.sort(intvs, (o1, o2) -> {
            return o1[1] - o2[1];
        });
        // 至少有个区间不相交
        int count = 1;
        // 排序后第1个区间就是x
        int x_end = intvs[0][1];
        for(int[] interval : intvs) {
            int start = interval[0];
            // 不相交则start > x_end
            if(start > x_end) {
                count++;
                x_end = interval[1]; // 更新x_end
            }
        }
        return count;
    }

}
