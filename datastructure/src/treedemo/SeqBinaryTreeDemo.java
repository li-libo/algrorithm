package treedemo;

/**
 * @author lilibo
 * @create 2022-01-30 2:31 PM
 */
public class SeqBinaryTreeDemo {

    public static void main(String[] args) {
        int[] array = {1, 3, 6, 8, 10, 14};
        SeqBinaryTree seqBinaryTree = new SeqBinaryTree(array);
        seqBinaryTree.preOrder();
        seqBinaryTree.midOrder();
    }
}

class SeqBinaryTree {
    private int[] array;

    public SeqBinaryTree(int[] array) {
        this.array = array;
    }

    public void preOrder() {
        if(array == null || array.length == 0) {
            return;
        }
        System.out.println("开始前序遍历...");
        preOrder(0);
        System.out.print("\n");
    }

    private void preOrder(int index) {
        System.out.print(array[index] + " -> ");
        if(index * 2 + 1 < array.length) {
            preOrder(index * 2 + 1);
        }
        if(index * 2 + 2 < array.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void midOrder() {
        if(array == null || array.length == 0) {
            return;
        }
        System.out.println("开始中序遍历...");
        midOrder(0);
        System.out.println("\n");
    }

    private void midOrder(int index) {
        if(index * 2 + 1 < array.length) {
            midOrder(index * 2 + 1);
        }
        System.out.print(array[index] + " -> ");
        if(index * 2 + 2 < array.length) {
            midOrder(index * 2 + 2);
        }
    }
}
