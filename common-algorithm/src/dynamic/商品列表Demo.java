package dynamic;

import java.util.Scanner;

/**
 * 描述
 * 王强今天很开心，公司发给N元的年终奖。王强决定把年终奖用于购物，他把想买的物品分为两类：主件与附件，附件是从属于某个主件的，下表就是一些主件与附件的例子：
 * <p>
 * <p>
 * 主件 	附件
 * 电脑 	打印机，扫描仪
 * 书柜 	图书
 * 书桌 	台灯，文具
 * 工作椅 	无
 * <p>
 * <p>
 * 如果要买归类为附件的物品，必须先买该附件所属的主件，且每件物品只能购买一次。每个主件可以有 0 个、 1 个或 2 个附件。附件不再有从属于自己的附件。王强想买的东西很多，为了不超出预算，他把每件物品规定了一个重要度，分为 5 等：用整数 1 ~ 5 表示，第 5 等最重要。他还从因特网上查到了每件物品的价格（都是 10 元的整数倍）。他希望在不超过 N 元（可以等于 N 元）的前提下，使每件物品的价格与重要度的乘积的总和最大。
 * 设第 j 件物品的价格为 v[j] ，重要度为 w[j] ，共选中了 k 件物品，编号依次为 j 1 ， j 2 ，……， j k ，则所求的总和为：
 * v[j 1 ]*w[j 1 ]+v[j 2 ]*w[j 2 ]+ … +v[j k ]*w[j k ] 。（其中 * 为乘号）
 * 请你帮助王强设计一个满足要求的购物单。
 * <p>
 * <p>
 * <p>
 * 输入描述：
 * <p>
 * 输入的第 1 行，为两个正整数，用一个空格隔开：N m
 * （其中 N （ N<32000 ）表示总钱数， m （m <60 ）为希望购买物品的个数。）
 * <p>
 * <p>
 * 从第 2 行到第 m+1 行，第 j 行给出了编号为 j-1 的物品的基本数据，每行有 3 个非负整数 v p q
 * <p>
 * <p>
 * （其中 v 表示该物品的价格（ v<10000 ）， p 表示该物品的重要度（ 1 ~ 5 ）， q 表示该物品是主件还是附件。如果 q=0 ，表示该物品为主件，如果 q>0 ，表示该物品为附件， q 是所属主件的编号）
 * <p>
 * <p>
 * <p>
 * <p>
 * 输出描述：
 * 输出文件只有一个正整数，为不超过总钱数的物品的价格与重要度乘积的总和的最大值（ <200000 ）。
 * 示例1
 * 输入：
 * <p>
 * 1000 5
 * 800 2 0
 * 400 5 1
 * 300 5 1
 * 400 3 0
 * 500 2 0
 * <p>
 * 输出：
 * <p>
 * 2200
 * <p>
 * 示例2
 * 输入：
 * <p>
 * 50 5
 * 20 3 5
 * 20 3 5
 * 10 3 0
 * 10 2 0
 * 10 1 0
 * <p>
 * 输出：
 * <p>
 * 130
 * <p>
 * 说明：
 * <p>
 * 由第1行可知总钱数N为50以及希望购买的物品个数m为5；
 * 第2和第3行的q为5，说明它们都是编号为5的物品的附件；
 * 第4~6行的q都为0，说明它们都是主件，它们的编号依次为3~5；
 * 所以物品的价格与重要度乘积的总和的最大值为10*1+20*3+20*3=130
 *
 * @author lilibo
 * @create 2022-02-24 8:23 PM
 */
public class 商品列表Demo {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                int sumMoney = scanner.nextInt();
                int size = scanner.nextInt();
                // 构建商品表GoodMatrix, goodMatix[i][0]表示主件, goodMatrix[i][1]表示附件1, goodMatix[i][2]表示附件2
                Good[][] goodMatrix = new Good[60][3];
                for (int i = 1; i <=size; i++) {
                    int v = scanner.nextInt();
                    int p = scanner.nextInt();
                    int q = scanner.nextInt();

                    Good t = new Good(v, p);
                    if (q == 0) {
                        goodMatrix[i][0] = t;
                    } else {
                        if (goodMatrix[q][1] == null) {
                            goodMatrix[q][1] = t;
                        } else {
                            goodMatrix[q][2] = t;
                        }
                    }
                }
                // 利用动态规划构建最优v*p表, 索引代表钱数, 值代表在该钱数下最优vp
                int[] bestVPArray = new int[sumMoney + 1];
                for (int i = 0; i < goodMatrix.length; i++) {
                    for (int money = sumMoney; money > 0 && goodMatrix[i][0] != null; money--) {
                        int bestVp = bestVPArray[money];
                        if (goodMatrix[i][0].getV() <= money && (bestVPArray[money - goodMatrix[i][0].getV()] + goodMatrix[i][0].getVp() > bestVp)) {
                            bestVp = bestVPArray[money - goodMatrix[i][0].getV()] + goodMatrix[i][0].getVp();
                        }
                        if (goodMatrix[i][1] != null && goodMatrix[i][1].getV() + goodMatrix[i][0].getV() <= money && (bestVPArray[money - goodMatrix[i][0].getV() - goodMatrix[i][1].getV()] + goodMatrix[i][0].getVp() + goodMatrix[i][1].getVp() > bestVp)) {
                            bestVp = bestVPArray[money - goodMatrix[i][0].getV() - goodMatrix[i][1].getV()] + goodMatrix[i][0].getVp() + goodMatrix[i][1].getVp();
                        }
                        if (goodMatrix[i][2] != null && goodMatrix[i][2].getV() + goodMatrix[i][1].getV() + goodMatrix[i][0].getV() <= money && (bestVPArray[money - goodMatrix[i][0].getV() - goodMatrix[i][1].getV() -goodMatrix[i][2].getV()] + goodMatrix[i][0].getVp() + goodMatrix[i][1].getVp() + goodMatrix[i][2].getVp() > bestVp)) {
                            bestVp = bestVPArray[money - goodMatrix[i][0].getV() - goodMatrix[i][1].getV() -goodMatrix[i][2].getV()] + goodMatrix[i][0].getVp() + goodMatrix[i][1].getVp() + goodMatrix[i][2].getVp();
                        }
                        bestVPArray[money] = bestVp;
                    }
                }
                System.out.println(bestVPArray[sumMoney]);
            }
        }
    }
}

class Good {

    private int v;

    private int p;

    private int vp;

    public Good(int v, int p) {
        this.v = v;
        this.p = p;
        this.vp = v * p;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getVp() {
        return vp;
    }

    public void setVp(int vp) {
        this.vp = vp;
    }

    @Override
    public String toString() {
        return "Good{" +
                "v=" + v +
                ", p=" + p +
                ", vp=" + vp +
                '}';
    }
}
