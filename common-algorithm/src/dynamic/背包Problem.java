package dynamic;

import java.util.Stack;

/**
 * @author lilibo
 * @create 2022-02-24 10:00 PM
 */
public class 背包Problem {

    public static void main(String[] args) {
        Commodity[] commodityArray = {new Commodity(1500, 1, "吉他"), new Commodity(3000, 4, "音响"), new Commodity(2000, 3, "电脑")};
        int capacity = 4;
        int[][] tab = new int[commodityArray.length + 1][capacity + 1];
        // 记录路径数组
        int[][] records = new int[commodityArray.length + 1][capacity + 1];
        for(int i = 1; i < tab.length; i++) {
            for(int j = 1; j < tab[i].length; j++){
                if(commodityArray[i - 1].getWeight() > j) {
                    tab[i][j] = tab[i - 1][j];
                }else {
                    if((commodityArray[i - 1].getValue() + tab[i - 1][j - commodityArray[i - 1].getWeight()] > tab[i-1][j])) {
                        tab[i][j] = commodityArray[i - 1].getValue() + tab[i - 1][j - commodityArray[i - 1].getWeight()];
                        // 记录路径
                        records[i][j] = 1;
                    }else {
                        tab[i][j] = tab[i - 1][j];
                    }
                }
            }
        }
        System.out.println("最大价值为" + tab[tab.length - 1][tab[0].length - 1]);
        System.out.println("构建的路径为: ");
        Stack<String> stack = new Stack<>();
        int i = records.length - 1;
        int j = records[0].length - 1;
        // 从后向前输出
        while (i >= 0 && j >=0 ) {
            if(records[i][j] == 1) {
                stack.push(commodityArray[i - 1].getName());
                j = j - commodityArray[i - 1].getWeight();
            }
            i--;
        }
        while (stack.size() > 0) {
            System.out.print(stack.pop() + " -> ");
        }
        System.out.println("\n");
    }
}

class Commodity {

    private int value;

    private int weight;

    private String name;

    public Commodity(int value, int weight, String name) {
        this.value = value;
        this.weight = weight;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "value=" + value +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }
}