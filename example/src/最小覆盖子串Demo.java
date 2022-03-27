import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * 注意：
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * 示例 1：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 *
 * @author lilibo
 * @create 2022-03-07 11:21 PM
 */
public class 最小覆盖子串Demo {

    @Test
    public void test1() {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minStrWindow(s, t));
    }

    public String minStrWindow(String source, String target) {
        Map<Character, Integer> needMap = new HashMap<>();
        Map<Character, Integer> windowMap = new HashMap<>();
        char[] targetChars = target.toCharArray();
        char[] sourceChars = source.toCharArray();
        for (char c : targetChars) {
            needMap.compute(c, (k, v) -> {
                if (v == null) {
                    return 1;
                } else {
                    return v + 1;
                }
            });
        }
        int left = 0, right = 0;
        int valid = 0;
        // 记录最小覆盖子串的起始索引以及长度
        int start = 0, len = Integer.MAX_VALUE;
        while (right < source.length()) {
            // c 是移入窗口的字符
            char c = sourceChars[right];
            // 右移窗口
            right++;
            // 进行窗口内数据的一系列更新
            if (needMap.containsKey(c)) {
                windowMap.compute(c, (k, v) -> {
                    if (v == null) {
                        return 1;
                    } else {
                        return v + 1;
                    }
                });
                if (windowMap.get(c).equals(needMap.get(c))) {
                    valid++;
                }
            }
            /* debug 输出的位置 */
            System.out.println(String.format("window: [%d, %d)\n", left, right));
            // 判断左侧窗口是否需要收缩
            while (valid == needMap.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d是将移出窗口的字符
                char d = sourceChars[left];
                // 左移串口
                left++;
                // 进行窗口内数据更新
                if (needMap.containsKey(d)) {
                    if (windowMap.get(d).equals(needMap.get(d))) {
                        valid--;
                    }
                    windowMap.put(d, windowMap.get(d) - 1);
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : source.substring(start, start + len);
    }
}
