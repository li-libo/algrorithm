package dynamic;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * @author lilibo
 * @create 2022-03-13 12:55 AM
 */
public class 无重复字符的最长子串Demo {

    @Test
    public void test1() {
        String s = "abcabcbb";
        int lengthOfLongestSubString = getLengthOfLongestSubString(s);
        System.out.println(s + "的最长子串长度为: " + lengthOfLongestSubString);
    }

    /**
     * 滑动窗口法求解
     *
     * @param s
     * @return
     */
    public int getLengthOfLongestSubString(String s) {
        // 记录每个字符是否出现过
        Set<Character> cSet = new HashSet<>();
        int n = s.length();
        // 右指针, 初始值为-1, 相当于在字符串左边界的左侧,还没开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                cSet.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !cSet.contains(s.charAt(rk + 1))) {
                cSet.add(s.charAt(rk + 1));
                rk++;
            }
            // 第i到第rk个字符是个极长的无重复子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }
}
