package tree;

/**
 * 顺序存储二叉树demo
 * 从数据存储来看,数组存储方式和树的存储方式可以相互转化,即数组可以转化为树,树也可以转化为数组
 * 顺序存储二叉树特点:
 *      (1) 顺序二叉树通常只考虑完全二叉树
 *      (2) 第index元素的左子节点为2 * index + 1
 *      (3) 第index元素的右子节点为2 * index + 2
 *      (4) 第index元素的父节点为 (index-1) / 2
 *      (5) index: 表示二叉树中第几个元素(按0开始编号)
 * @author lilibo
 * @create 2021-08-14 12:29 PM
 */
public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(array);
        arrayBinaryTree.preOrder();
        arrayBinaryTree.midOrder();
    }

}

class ArrayBinaryTree {

    private int[] array;

    public ArrayBinaryTree(int[] array) {
        this.array = array;
    }

    public void preOrder(){
        if(array == null) {
            System.out.println("array == null");
            return;
        }
        System.out.println("开始前序遍历...");
        int index = 0;
        preOrder(array, index);
        System.out.println();
    }

    private void preOrder(int[] array, int index) {
        if(index >= array.length) {
            return;
        }
        System.out.printf(array[index] + " ");
        if(index * 2 + 1 < array.length) {
            preOrder(array, index * 2 + 1);
        }
        if(index * 2 + 2 < array.length){
            preOrder(array, index * 2 + 2);
        }
    }

    public void midOrder() {
        if(array == null) {
            System.out.println("array == null");
            return;
        }
        System.out.println("开始中序遍历...");
        int startIndex = 0;
        midOrder(array, startIndex);
    }

    private void midOrder(int[] array, int index) {
        if(index >= array.length) {
            return;
        }
        if(index * 2 + 1 < array.length){
            midOrder(array, index * 2 + 1);
        }
        System.out.printf(array[index] + " ");
        if(index * 2 + 2 < array.length) {
            midOrder(array, index * 2 + 2);
        }
    }
}