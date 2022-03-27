package dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用1维数组优化
 *
 * @author lilibo
 * @create 2022-02-25 1:12 AM
 */
public class KnapsackProblem1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("吉他", 1, 1500));
        productList.add(new Product("音响", 4, 3000));
        productList.add(new Product("电脑", 3, 2000));
        int volume = 4;

        int[] f = new int[volume + 1];
        for (int i = 1; i < f.length; i++) {    //必装满则f[0]=0,f[1...m]都初始化为无穷小
            f[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < productList.size(); i++) {
            for (int j = f.length - 1; j >= productList.get(i).getWeight(); j--) {
                f[j] = Math.max(f[j], f[j - productList.get(i).getWeight()] + productList.get(i).getPrice());
            }
        }
        for (int i = 0; i < f.length; i++) {
            System.out.print(f[i] + " ");
        }
        System.out.println();
        System.out.println("最大价值为" + f[f.length - 1]);
    }

}
