package dynamic;

/**
 * 给定1个字符串,找到其中最长的回文子序列,假设s的最大长度为1000
 *
 * @author lilibo
 * @create 2022-02-28 11:37 PM
 */
public class 最长回文子序列子串Demo {

    public static final String format = "%s的最长回文子序列为%d";

    public static void main(String[] args) {
        String str = "bbbab";
        int longest = getLongestPalindrome2(str);
        System.out.println(String.format(format, str, longest));
    }

    /**
     * 递归求解最长回文子序列
     *
     * @param str
     */
    public static int getLongestPalindrome(String str) {
        if (str == null || str.length() == 0) return 0;
        int len = str.length();
        char[] charArray = str.toCharArray();
        return fun(charArray, 0, len - 1);
    }

    private static int fun(char[] charArray, int i, int j) {
        if (i == j) return 1;
        if (i == j - 1) return charArray[i] == charArray[j] ? 2 : 1;
        if (charArray[i] == charArray[j]) {
            return 2 + fun(charArray, i + 1, j - 1);
        } else {
            return Math.max(fun(charArray, i + 1, j), fun(charArray, i, j - 1));
        }
    }

    /**
     * 利用动态规划求出最长子序列
     * <p>
     * 设状态方程dp[i, j] 为 i到j的最长子序列函数
     * 则有charArray[i] == charArray[j]: dp[i, j] = dp[i+1,j] + dp[i,j-1] + 2;
     * 否则dp[i][j] = Math.max(dp[i+1,j], dp[i,j-1]
     *
     * @param str
     * @return
     */
    public static int getLongestPalindrome1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int n = str.length();
        char[] charArray = str.toCharArray();
        int[][] dp = new int[n][n];
        // base case
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        /*
        为了保证每次计算 dp[i][j] ，左下右⽅向的位置已经被计算出来，只能斜着遍历或者反着遍历：
         */
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n ; j++) {
                if (charArray[i] == charArray[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 空间压缩
     * @param str
     * @return
     */
    public static int getLongestPalindrome2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int n = str.length();
        char[] charArray = str.toCharArray();
        int[] dp = new int[n];
        // base case
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            int pre = 0;
            for (int j = i + 1; j < n ; j++) {
                int temp = dp[j];
                if (charArray[i] == charArray[j]) {
                    dp[j] = pre + 2;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                pre = temp;
            }
        }
        return dp[n - 1];
    }

    /**
     * 使⽤双指针。
     * 寻找回⽂串的问题核⼼思想是：从中间开始向两边扩散来判断回⽂串。对于最⻓回⽂⼦串，就是这个意思：
     * for 0 <= i < len(s):
     * 找到以 s[i] 为中⼼的回⽂串 更新答案
     * 但是呢，我们刚才也说了，回⽂串的⻓度可能是奇数也可能是偶数，如果是 abba 这种情况，没有⼀个中⼼字符，上⾯的算法就没辙了。
     * 所以我们可以修改⼀下：
     * for 0 <= i < len(s):
     * 找到以 s[i] 为中⼼的回⽂串,找到以 s[i] 和 s[i+1] 为中⼼的回⽂串,更新答案
     * @param str
     * @return
     */
    public static int 中心法获取最长回文子串(String str) {
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            // 以 s[i] 为中⼼的最⻓回⽂⼦串
            String s1 = palindrome(str, i, i);
            // 以 s[i] 和 s[i+1] 为中⼼的最⻓回⽂⼦串
            String s2 = palindrome(str, i, i + 1);
            res =  res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res.length();
    }

    private static String palindrome(String str, int l, int r) {
        char[] chars = str.toCharArray();
        // 防⽌索引越界
        while (l >= 0 && r < str.length() && chars[l] == chars[r]) {
            // 向两边展开
            l--;
            r++;
        }
        // 返回以 s[l] 和 s[r] 为中⼼的最⻓回⽂串
        return str.substring(l + 1, r);
    }

    /**
     * 给定一个包含大写字母和小写字母的字符串 s ，返回 通过这些字母构造成的 最长的回文串 。
     * 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。
     * @param str
     * @return
     */
    public int longestPalindrome(String str) {
        int[] count = new int[128];
        int length = str.length();
        for(int i = 0; i < length; i++) {
            char c = str.charAt(i);
            count[c]++;
        }

        int ans = 0;
        for(int v : count) {
            ans += v / 2 * 2;
            if(v % 2 == 1 && ans % 2 == 0) {
                ans++;
            }
        }
        return ans;
    }
}
