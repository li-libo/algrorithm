package treedemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lilibo
 * @create 2022-01-30 9:50 PM
 */
public class HuffmanTreeExample {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        List<HuffmanTreeNode> list = new ArrayList<>();
        for(int weight : array) {
            list.add(new HuffmanTreeNode(weight));
        }
        while (list.size() > 1) {
            Collections.sort(list);
            HuffmanTreeNode min1Node = list.get(0);
            HuffmanTreeNode min2Node = list.get(1);
            HuffmanTreeNode newNode = new HuffmanTreeNode(min1Node.getWeight() + min2Node.getWeight());
            newNode.setLeft(min1Node);
            newNode.setRight(min2Node);
            list.remove(min1Node);
            list.remove(min2Node);
            list.add(newNode);
        }
        HuffmanTreeNode root = list.get(0);
        System.out.println("霍夫曼根节点权值为: " + root.getWeight());
        System.out.println("前序遍历霍夫曼树...");
        preOrder(root);
    }

    public static void preOrder(HuffmanTreeNode node) {
        if(node == null) {
            return;
        }
        System.out.println(node);
        if(node.getLeft() != null) {
            preOrder(node.getLeft());
        }
        if(node.getRight() != null) {
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

    @Override
    public int compareTo(HuffmanTreeNode o) {
        return this.weight - o.weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
    public String toString() {
        return "HuffmanTreeNode{" +
                "weight=" + weight +
                '}';
    }
}