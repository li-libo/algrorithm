package greedy;

import java.util.Arrays;

/**
 * @author lilibo
 * @create 2022-03-08 12:46 AM
 */
public class 安排会议室Demo {

    /**
     * 我们首先把这些会议的时间区间进行投影：
     * 红色的点代表每个会议的开始时间点，绿色的点代表每个会议的结束时间点。
     * 现在假想有一条带着计数器的线，在时间线上从左至右进行扫描，每遇到红色的点，计数器 count 加一，每遇到绿色的点，计数器 count 减一：
     * 这样一来，每个时刻有多少个会议在同时进行，就是计数器 count 的值，count 的最大值，就是需要申请的会议室数量。
     * 对差分数组技巧熟悉的读者一眼就能看出来了，这个扫描线其实就是差分数组的遍历过程，所以我们说这是差分数组技巧衍生出来的解法。
     *
     * 这里使用的是 双指针技巧，根据 i, j 的相对位置模拟扫描线前进的过程。
     * @param meetings
     * @return
     */
    public int minMeetingRooms(int[][] meetings) {
        int n = meetings.length;
        int[] begin = new int[n];
        int[] end = new int[n];
        for(int i = 0; i < n; i++) {
            begin[i] = meetings[i][0];
            end[i] = meetings[i][1];
        }
        Arrays.sort(begin);
        Arrays.sort(end);

        // 扫描过程中的计数器
        int count = 0;
        // 双指针技巧
        int res = 0, i = 0, j = 0;
        while (i < n && j < n) {
            if (begin[i] < end[j]) {
                // 扫描到一个红点
                count++;
                i++;
            } else {
                // 扫描到一个绿点
                count--;
                j++;
            }
            // 记录扫描过程中的最大值
            res = Math.max(res, count);
        }

        return res;
    }

}
