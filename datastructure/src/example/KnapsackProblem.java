package example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author lilibo
 * @create 2022-02-04 6:13 PM
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(new Goods("吉他", 1, 1500));
        goodsList.add(new Goods("音响", 4, 3000));
        goodsList.add(new Goods("电脑", 3, 2000));
        int volume = 4;
        int[][] valueTab = new int[goodsList.size() + 1][volume + 1];
        boolean[][] recordedTab = new boolean[goodsList.size() + 1][volume + 1];
        for(int i = 1; i < valueTab.length; i++) {
            for (int j = 1; j < valueTab[i].length; j++) {
                if(goodsList.get(i - 1).getWeight() > j) {
                    valueTab[i][j] = valueTab[i-1][j];
                }else {
                    if(goodsList.get(i - 1).getPrice() + valueTab[i - 1][j - goodsList.get(i - 1).getWeight()] > valueTab[i - 1][j]) {
                        valueTab[i][j] = goodsList.get(i - 1).getPrice() + valueTab[i - 1][j - goodsList.get(i - 1).getWeight()];
                        recordedTab[i][j] = true;
                    }else {
                        valueTab[i][j] = valueTab[i - 1][j];
                    }
                }
            }
        }
        System.out.println("输出价值表为...");
        for(int[] row : valueTab) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("输出选中的商品为...");
        printPath(goodsList, valueTab, recordedTab);
    }

    private static void printPath(List<Goods> goodsList, int[][] valueTab, boolean[][] recordedTab) {
        Stack<String> stack = new Stack<>();
        int recordI = valueTab.length - 1;
        int volume = valueTab[0].length - 1;
        int recordJ = volume;
        while (recordI >= 0 && recordJ >= 0) {
            boolean recordFlag = recordedTab[recordI][recordJ];
            if(recordFlag) {
                stack.push(goodsList.get(recordI - 1).getName());
                recordJ = volume - goodsList.get(recordI - 1).getWeight();
            }
            recordI--;
        }
        while (stack.size() > 0) {
            String name = stack.pop();
            System.out.println(name + " -> ");
        }
    }
}

class Goods {

    private String name;

    private int weight;

    private int price;

    public Goods(String name, int weight, int price) {
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
        return "Goods{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}