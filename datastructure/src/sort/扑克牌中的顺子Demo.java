package sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 61. 扑克牌中的顺子
 * 从若干副扑克牌中随机抽 5 张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，
 * 可以看成任意数字。A 不能视为 14。
 * @author lilibo
 * @create 2022-03-15 9:20 PM
 */
public class 扑克牌中的顺子Demo {

    @Test
    public void test1() {
        int[] nums = {1,2,3,4,5};
        boolean b = isStraight(nums);
        System.out.println(Arrays.toString(nums) + "是顺子么? " + b);
    }

    /**
     * 根据题意，此 5张牌是顺子的 充分条件 如下：
     *     除大小王外，所有牌无重复 ；
     *     设此 5 张牌中最大的牌为 max ，最小的牌为 min （大小王除外），则需满足：
     * max−min<5
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {
       Set<Integer> repeatSet = new HashSet<>();
       int max = 0, min = 14;
       for(int num : nums) {
           if(num == 0) {
               continue;
           }
           max = Math.max(max, num);
           min = Math.min(min, num);
           if(repeatSet.contains(num)) {
               return false;
           }
           repeatSet.add(num);
       }
       return max - min < 5;
    }
}
