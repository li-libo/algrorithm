package search;

/**
 * 顺序查找/线性查找(无所谓数组是否有序)
 * @author lilibo
 * @create 2021-08-12 3:58 PM
 */
public class SeqSearchDemo {

    public static void main(String[] args) {
        int[] array = {1, 3, -1, 10, 20, 5, 6, 7, 33, 21, 11};
        int target = 20;
        int targetIndex = seqSearch(array, target);
        System.out.printf("target = %d 的下标为%d", target, targetIndex);
    }

    private static int seqSearch(int[] array, int target) {
        for(int i = 0; i <array.length; i++){
            if(array[i] == target){
                return i;
            }
        }
        return -1;
    }

}
