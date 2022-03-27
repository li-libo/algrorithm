package dynamic;

import java.util.Arrays;

/**
 * 求最长公共子串
 * @author lilibo
 * @create 2022-03-02 12:53 AM
 */
public class 最长公共子序列和子串Demo {

    public static void main(String[] args) {
        String str1 = "abcde";
        String str2 = "abcdefg";
        getLargestSubStr1(str1, str2);
    }

    public static void getLargestSubStr(String str1, String str2) {
        // base case
        int lengthOfStr1 = str1.length();
        int lengthOfStr2 = str2.length();
        int[][] dp = new int[lengthOfStr1][lengthOfStr2];
        /*
        dp[i][j] = dp[i - 1][j - 1] + 1;
         */
        char[] c1Array = str1.toCharArray();
        char[] c2Array = str2.toCharArray();
        int maxLen = 0, endIndex = 0;
        for(int i = 0; i < lengthOfStr1; i++) {
            for(int j = 0; j < lengthOfStr2; j++) {
                if(c1Array[i] == c2Array[j]) {
                    if(i == 0 || j == 0) {
                        dp[i][j] = 1; // 首次出现
                    }else {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    }
                }else {
                    dp[i][j] = 0;
                }
                if(dp[i][j] > maxLen) {
                    maxLen = dp[i][j]; // 获取字符子串最大长度
                    endIndex = i;
                }
            }
        }
        String subStr = str1.substring(endIndex - maxLen + 1, endIndex + 1);
        System.out.println("最长子串为: " + subStr);
    }

    public static void getLargestSubStr1(String str1, String str2) {
        // base case
        int lengthOfStr1 = str1.length();
        int lengthOfStr2 = str2.length();
        int[] dp = new int[lengthOfStr2];
        /*
        dp[i][j] = dp[i - 1][j - 1] + 1;
         */
        char[] c1Array = str1.toCharArray();
        char[] c2Array = str2.toCharArray();
        int maxLen = 0, endIndex = 0;
        for(int i = 0; i < lengthOfStr1; i++) {
            for(int j = lengthOfStr2 - 1; j >= 0; j--) {
                if(c1Array[i] == c2Array[j]) {
                    if(i == 0 || j == 0) {
                        dp[j] = 1; // 首次出现
                    }else {
                        dp[j] = dp[j - 1] + 1;
                    }
                }else {
                    dp[j] = 0;
                }
                if(dp[j] > maxLen) {
                    maxLen = dp[j]; // 获取字符子串最大长度
                    endIndex = i;
                }
            }
        }
        String subStr = str1.substring(endIndex - maxLen + 1, endIndex + 1);
        System.out.println("最长子串为: " + subStr);
    }

    /**
     * 求最长公共子序列
     * @param str1
     * @param str2
     */
    public static void getLongestSubSeq1(String str1, String str2) {
        int lengthOfStr1 = str1.length();
        int lengthOfStr2 = str2.length();
        // dp[i][j] 的含义是：对于 s1[1..i] 和 s2[1..j], 它们的 LCS ⻓度是 dp[i][j] 。
        int[][] dp = new int[lengthOfStr1 + 1][lengthOfStr2 + 1];
        char[] c1Array = str1.toCharArray();
        char[] c2Array = str2.toCharArray();
        // base case
        Arrays.fill(dp[0], 0);
        for(int rowIndex = 0; rowIndex < dp.length; rowIndex++) {
            dp[rowIndex][0] = 0;
        }
        int maxLen = 0;
        for(int i = 1; i < lengthOfStr1 + 1; i++) {
            for(int j = 1; j < lengthOfStr2 + 1; j++) {
                if(c1Array[i - 1] == c2Array[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
                if(dp[i][j] > maxLen) {
                    maxLen = dp[i][j]; // 获取公共子序列最大长度
                }
            }
        }
        System.out.println("最大公共子序列长度为: " + maxLen);
    }

}
