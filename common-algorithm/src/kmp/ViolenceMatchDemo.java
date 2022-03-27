package kmp;

/**
 * 暴力匹配
 *
 * @author lilibo
 * @create 2021-08-29 7:11 PM
 */
public class ViolenceMatchDemo {

    public static void main(String[] args) {
        String str1 = "测试案例:测试暴力匹配";
        String str2 = "测试暴力";
        int matchIndex = violenceMatch(str1, str2);
        System.out.printf("%s 在 %s 中位值为:%d\n", str2, str1, matchIndex);
    }

    /**
     * 测试暴力匹配
     * @param source 源
     * @param target 目标
     * @return
     */
    private static int violenceMatch(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        char[] chars1 = source.toCharArray();
        char[] chars2 = target.toCharArray();
        // 定义2个辅助指针
        int s1 = 0;
        int s2 = 0;
        while (s1 < sourceLength && s2 < targetLength) {
            if(chars1[s1] == chars2[s2]) {
                s1++;
                s2++;
            } else {
                s1 = s1 - (s2 - 1); // 回退之前开始匹配s1的下一个位置
                s2 = 0;
            }
        }
        // 如果匹配完target,则s2 = targetLength
        if(s2 == targetLength) {
            return s1 - s2;
        }
        return -1;
    }

}