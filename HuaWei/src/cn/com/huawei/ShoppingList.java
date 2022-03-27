package cn.com.huawei;

import java.util.Scanner;

/**
 * 动态规划
 * 描述
 * 王强今天很开心，公司发给N元的年终奖。王强决定把年终奖用于购物，他把想买的物品分为两类：主件与附件，附件是从属于某个主件的，下表就是一些主件与附件的例子：
 *  如果要买归类为附件的物品，必须先买该附件所属的主件，且每件物品只能购买一次。每个主件可以有 0 个、 1 个或 2 个附件。附件不再有从属于自己的附件。
 *  王强想买的东西很多，为了不超出预算，他把每件物品规定了一个重要度，分为 5 等：用整数 1 ~ 5 表示，第 5 等最重要。他还从因特网上查到了每件物品的价格（
 *  都是 10 元的整数倍）。他希望在不超过 N 元（可以等于 N 元）的前提下，使每件物品的价格与重要度的乘积的总和最大。
 * 设第 j 件物品的价格为 v[j] ，重要度为 w[j] ，共选中了 k 件物品，编号依次为 j 1 ， j 2 ，……， j k ，则所求的总和为：
 * v[j 1 ]*w[j 1 ]+v[j 2 ]*w[j 2 ]+ … +v[j k ]*w[j k ] 。（其中 * 为乘号）
 * 请你帮助王强设计一个满足要求的购物单。
 *
 * 输入描述：
 *
 * 输入的第 1 行，为两个正整数，用一个空格隔开：N m
 * （其中 N （ <32000 ）表示总钱数， m （ <60 ）为希望购买物品的个数。）
 * 从第 2 行到第 m+1 行，第 j 行给出了编号为 j-1 的物品的基本数据，每行有 3 个非负整数 v p q
 * （其中 v 表示该物品的价格（ v<10000 ）， p 表示该物品的重要度（ 1 ~ 5 ）， q 表示该物品是主件还是附件。
 * 如果 q=0 ，表示该物品为主件，如果 q>0 ，表示该物品为附件， q 是所属主件的编号）
 * 输出描述：
 *  输出文件只有一个正整数，为不超过总钱数的物品的价格与重要度乘积的总和的最大值（ <200000 ）。
 * @author lilibo
 * @create 2022-02-16 10:28 AM
 */
public class ShoppingList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 总钱数
        int N = scanner.nextInt();
        // 购买物品个数
        int m = scanner.nextInt();
        // 商品的价格与重要度乘积数组, 数组下标代表所花费的钱数
        int[] f = new int[N + 1];
        // 分组，goods[i][0]为主件，goods[i][1]为附件1，goods[i][2]为附件2
        Good[][] goods1= new Good[60][4];

        for (int i = 1; i <= m; i++) {
            int v = scanner.nextInt();
            int p = scanner.nextInt();
            int q = scanner.nextInt();

            Good t = new Good(v, v * p);
            if (q == 0) {
                goods1[i][0] = t;
            } else {
                if (goods1[q][1] == null) {
                    goods1[q][1] = t;
                } else {
                    goods1[q][2] = t;
                }
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = N; j >= 0 && goods1[i][0] != null; j--) {
                //以下代码从分组中选择价值最大的。共五种情况：不选主件，选主件，选附件1和主件，选附件2和主件，选附件1和附件2和主件
                Good master = goods1[i][0];
                int max = f[j];
                if (j >= master.v && max < f[j - master.v] + master.vp) {
                    max = f[j - master.v] + master.vp;
                }
                int vt;
                if (goods1[i][1] != null) {
                    if (j >= (vt = master.v + goods1[i][1].v)
                            && max <  f[j - vt] + master.vp + goods1[i][1].vp) {
                        max = f[j - vt] + master.vp + goods1[i][1].vp;
                    }
                }
                if (goods1[i][2] != null) {
                    if (j >= (vt = master.v + goods1[i][1].v + goods1[i][2].v)
                            && max < f[j - vt] + master.vp + goods1[i][1].vp + goods1[i][2].vp) {
                        max = f[j - vt] + master.vp + goods1[i][1].vp + goods1[i][2].vp;
                    }
                }
                f[j] = max;
            }
        }

        System.out.println(f[N]);
    }
}

class Good {
    int v;
    int vp;
    public Good(int v, int vp) {
        this.v = v;
        this.vp = vp;
    }
}
