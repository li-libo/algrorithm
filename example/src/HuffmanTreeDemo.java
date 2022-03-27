import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lilibo
 * @create 2022-02-21 6:45 PM
 */
public class HuffmanTreeDemo {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        // 创建霍夫曼树
        HuffmanTree huffmanTree = new HuffmanTree();
        HuffmanNode rootNode = huffmanTree.createHuffman(array);
        // 霍夫曼树根节点为
        System.out.println(rootNode);
        // 计算wpl, 从根节点到叶子节点带权路径之和
        int wpl = huffmanTree.calculateWpl(rootNode);
        System.out.println("wpl = " + wpl);
    }

}

class HuffmanTree {

    public HuffmanNode createHuffman(int[] array) {
        List<HuffmanNode> nodeList = new ArrayList<>();
        for (int weight : array) {
            nodeList.add(new HuffmanNode(weight));
        }

        while (nodeList.size() > 1) {
            Collections.sort(nodeList);
            // 取出权值最小的2个节点
            HuffmanNode min1 = nodeList.get(0);
            HuffmanNode min2 = nodeList.get(1);
            HuffmanNode newNode = new HuffmanNode(min1.getWeight() + min2.getWeight());
            newNode.setLeft(min1);
            newNode.setRight(min2);
            nodeList.add(newNode);
            nodeList.remove(min1);
            nodeList.remove(min2);
        }
        return nodeList.get(0);
    }


    public int calculateWpl(HuffmanNode rootNode) {
        if(rootNode == null) {
            return 0;
        }
        Map<Integer, Integer> wplMap = new HashMap<>();
        calculateWpl(rootNode, wplMap, 0, 0);
        AtomicInteger sum = new AtomicInteger();
        wplMap.forEach((k, v) -> sum.set(sum.get() + k * v));
        return sum.get();
    }

    private void calculateWpl(HuffmanNode tempNode, Map<Integer, Integer> wplMap, int path, int step) {
        if(tempNode == null) {
            return;
        }
        int newPath = path + step;
        if(tempNode.getLeft() != null) {
            calculateWpl(tempNode.getLeft(), wplMap, newPath, 1);
        }
        if(tempNode.getRight() != null) {
            calculateWpl(tempNode.getRight(), wplMap, newPath, 1);
        }
        if(tempNode.getLeft() == null && tempNode.getRight() == null) {
            wplMap.put(tempNode.getWeight(), newPath);
        }
    }
}

class HuffmanNode implements Comparable<HuffmanNode>{

    private HuffmanNode left;

    private HuffmanNode right;

    private int weight;

    public HuffmanNode(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight - o.weight;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "weight=" + weight +
                '}';
    }

}