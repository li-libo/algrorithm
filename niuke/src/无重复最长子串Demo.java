import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
 * 示例1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * @author lilibo
 * @create 2022-03-13 17:51
 */
public class 无重复最长子串Demo {

    public static final String format = "%s的无重复最长子串长度为%d";

    @Test
    public void test1() {
        String s = "abcabcbb";
        System.out.println(String.format(format, s, getLengthOfLongestSubStr(s)));
    }

    /**
     * 滑动窗口方法求解无重复最长子串
     * @param str
     * @return
     */
    public int getLengthOfLongestSubStr(String str) {
        int length = str.length();
        Set<Character> cSet = new HashSet<>();
        // 右指针r, 初始值-1, 表示在字符串左侧边界左边,还没开始移动
        int r = -1;
        // 无重复最长子串长度max, 初始值为0
        int max = 0;
        // 左指针不断左移
        for(int l = 0; l < str.length(); l++) {
            if(l != 0) {
                cSet.remove(l - 1);
            }
            while (r + 1 < length && !cSet.contains(str.charAt(r + 1))) {
                cSet.add(str.charAt(r + 1));
                r++;
            }
            max = Math.max(max, r - l + 1);
        }
        return max;
    }


}
