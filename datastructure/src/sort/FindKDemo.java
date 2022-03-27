package sort;

/**
 * 查找数组中第k大的元素
 * @author lilibo
 * @create 2022-02-28 12:14 AM
 */
public class FindKDemo {

    public static final String format = "第%d大的元素为%d";

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 2, 2};
        int k = 3;
        int target = findK(array, 0, array.length - 1, k);
        System.out.println(String.format(format, k, target));
    }

    public static int findK(int[] array, int left, int right, int k) {
        int pivotIndex = findK(array, left, right);
        if(pivotIndex + 1 == k) {
            return array[pivotIndex + 1];
        }else if(pivotIndex + 1 > k) {
            return findK(array, left, pivotIndex - 1, k);
        }else {
            return findK(array, pivotIndex + 1, right, k);
        }
    }

    private static int findK(int[] array, int left, int right) {
        int pivotIndex = (left + right) / 2;
        int pivot = array[pivotIndex];
        while (left < right) {
            while (left <= right && array[left] < pivot) {
                left++;
            }
            while (right >= left && array[right] > pivot) {
                right--;
            }
            if(left <= right) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = left;
                left++;
                right--;
            }
        }
        return pivotIndex;
    }
}
