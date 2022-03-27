package huffmantree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * 霍夫曼树
 * 1) 给定 n 个权值作为 n 个叶子结点，构造一棵二叉树，若该树的带权路径长度(wpl)达到最小，称这样的二叉树为最优二叉树,
 * 也称为哈夫曼树(Huffman Tree), 还有的书翻译为霍夫曼树。
 * 2) 赫夫曼树是带权路径长度最短的树，权值较大的结点离根较近
 *
 * 构成赫夫曼树的步骤：
 * 1) 将每一个数据从小到大进行排序, 每个数据都是一个节点, 每个节点可以看成是一颗最简单的二叉树
 * 2) 取出根节点权值最小的两颗二叉树
 * 3) 组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
 * 4) 再将这颗新的二叉树，以根节点的权值大小再次排序，不断重复1-2-3-4 的步骤，直到数列中，所有的数据都被处理，就得到一颗赫夫曼树
 * @author lilibo
 * @create 2021-08-14 8:01 PM
 */
public class HuffmanTreeDemo {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        HuffmanTree tree = new HuffmanTree(array);
        System.out.println("根节点权值为: " + tree.getRoot().getWeight());
        tree.preOrder();
    }

}

class HuffmanTree {

    private HuffmanTreeNode root;

    public HuffmanTree(int[] weightArray) {
        List<HuffmanTreeNode> nodeList = new ArrayList<>();
        Arrays.stream(weightArray).forEach(weight -> {
            HuffmanTreeNode node = new HuffmanTreeNode(weight);
            nodeList.add(node);
        });
        while (nodeList.size() > 1){
            Collections.sort(nodeList);
            HuffmanTreeNode left = nodeList.get(0);
            HuffmanTreeNode right = nodeList.get(1);
            // 构建新节点
            HuffmanTreeNode parent = new HuffmanTreeNode(left.getWeight() + right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);
            // 以新节点代替旧节点
            nodeList.remove(left);
            nodeList.remove(right);
            nodeList.add(parent);
        }
        root = nodeList.get(0);
    }

    public HuffmanTreeNode getRoot() {
        return root;
    }

    public void preOrder(){
        if(root == null) {
            System.out.println("该树为空树!");
            return;
        }
        System.out.println("开始前序遍历...");
        preOrder(root);
    }

    private void preOrder(HuffmanTreeNode node) {
        if(node == null) {
            return;
        }
        System.out.println(node);
        if(node.getLeft()!=null){
            preOrder(node.getLeft());
        }
        if(node.getRight()!=null){
            preOrder(node.getRight());
        }
    }
}

class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{

    private int weight;

    private HuffmanTreeNode left;

    private HuffmanTreeNode right;

    public HuffmanTreeNode(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public HuffmanTreeNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanTreeNode left) {
        this.left = left;
    }

    public HuffmanTreeNode getRight() {
        return right;
    }

    public void setRight(HuffmanTreeNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanTreeNode o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "HuffmanTreeNode{" +
                "weight=" + weight +
                '}';
    }
}