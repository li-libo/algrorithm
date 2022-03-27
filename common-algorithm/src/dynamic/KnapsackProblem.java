package dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 背包0-1问题
 * 1) 动态规划(Dynamic Programming)算法的核心思想是：将大问题划分为小问题进行解决，从而一步步获取最优解的处理算法
 * 2) 动态规划算法与分治算法类似，其基本思想也是将待求解问题分解成若干个子问题，先求解子问题，然后从这些子问题的解得到原问题的解。
 * 3) 与分治法不同的是，适合于用动态规划求解的问题，经分解得到子问题往往不是互相独立的。 (即下一个子阶段的求解是建立在上一个子阶段的解的基础上，进行进一步的求解)
 * 4) 动态规划可以通过填表的方式来逐步推进，得到最优解.
 *
 * @author lilibo
 * @create 2021-08-25 9:01 PM
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("吉他", 1, 1500));
        productList.add(new Product("音响", 4, 3000));
        productList.add(new Product("电脑", 3, 2000));
        int volume = 4;
        // 构建商品价值表, tab[i][j]表示前i个物品能够装入容量为j的背包中最大价值
        int[][] tab = new int[productList.size() + 1][volume + 1];
        // 记录路径
        boolean[][] records = new boolean[productList.size() + 1][volume + 1];
        // 挨个放入商品
        for (int i = 1; i < tab.length; i++) {
            for (int j = 1; j < tab[i].length; j++) {
                if (productList.get(i - 1).getWeight() > j) {
                    tab[i][j] = tab[i - 1][j];
                } else {
                    if (productList.get(i - 1).getPrice() + tab[i - 1][j - productList.get(i - 1).getWeight()] > tab[i - 1][j]) {
                        tab[i][j] = productList.get(i - 1).getPrice() + tab[i - 1][j - productList.get(i - 1).getWeight()];
                        records[i][j] = true;
                    } else {
                        tab[i][j] = tab[i - 1][j];
                    }
                }
            }
        }
        printValueTab(tab);
        printPath(productList, volume, tab, records);
    }

    private static void printPath(List<Product> productList, int volume, int[][] tab, boolean[][] records) {
        System.out.println("构建的路径为:");
        Stack<String> stack = new Stack<>();
        // 记录的路径为
        int recordI = tab.length - 1;
        int recordJ = volume;
        // 从后向前输出(不能直接输出,否则输出从第0个商品开始的构建过程)
        while (recordI >= 0 && recordJ >= 0) {
            if (records[recordI][recordJ]) {
                stack.push(productList.get(recordI - 1).getName());
                recordJ = recordJ - productList.get(recordI - 1).getWeight();
            }
            recordI--;
        }
        while (stack.size() > 0) {
            System.out.printf(stack.pop() + "->");
        }
        System.out.printf("\n");
    }

    private static void printValueTab(int[][] tab) {
        System.out.println("构建的商品价值表为:");
        for (int[] row : tab) {
            System.out.println(Arrays.toString(row));
        }
    }

}

class Product {

    private String name;

    private int weight;

    private int price;

    public Product(String name, int weight, int price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
