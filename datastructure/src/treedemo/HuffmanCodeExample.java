package treedemo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lilibo
 * @create 2022-01-31 12:11 AM
 */
public class HuffmanCodeExample {

    public static final String content = "i like like like java do you like a java";

    public static final String format1 = "%s原始字节数组长度为%d";

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] contentBytes = content.getBytes("utf-8");
        System.out.println(String.format(format1, content, contentBytes.length));
        // 根据contentBytes中不同字节出现的次数(作为nodeList权值)构建nodeList
        List<HuffmanNode> leafNodeList = getLeafNodeList(contentBytes);
        System.out.println("得到的霍夫曼叶子节点List = " + leafNodeList);
        System.out.println("准备根据nodeList创建霍夫曼树...");
        HuffmanNode rootNode = createHuffmanCodeTree(leafNodeList);
        System.out.println("创建的霍夫曼树根节点为: " + rootNode);
        System.out.println("前序遍历霍夫曼编码树...");
        preOrder(rootNode);
        System.out.println("根据霍夫曼编码树构建霍夫曼编码...");
        Map<Byte, String> huffmanCodeMap = getHuffmanCodeMap(rootNode);
        System.out.println("huffmanCodeMap = " + huffmanCodeMap);
        int wpl = getWpl(rootNode);
        System.out.println("霍夫曼编码树wpl = " + wpl);
        byte[] encodeBytes = encode(contentBytes, huffmanCodeMap);
        System.out.println("encodeBytes = " + Arrays.toString(encodeBytes));
        byte[] decodeBytes = decode(huffmanCodeMap, encodeBytes);
        System.out.println("还原的字符串为: " + new String(decodeBytes, StandardCharsets.UTF_8));
    }

    private static byte[] decode(Map<Byte, String> huffmanCodeMap, byte[] encodeBytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < encodeBytes.length; i++) {
            // 是否需要补全, 最后1位不需要补齐
            boolean flag = !(i == encodeBytes.length - 1);
            String str = byteToBinaryStr(flag, encodeBytes[i]);
            sb.append(str);
        }
        String radixStr = sb.toString();
        System.out.println("根据编码字节数组还原的字符串为: " + radixStr);
        Map<String, Byte> reverseHuffmanCodeMap = new ConcurrentHashMap<>();
        huffmanCodeMap.forEach((key, value) -> reverseHuffmanCodeMap.put(value, key));
        List<Byte> list = new ArrayList<>();
        for(int i = 0; i < radixStr.length(); ) {
            for(int count = i + 1; count <= radixStr.length(); ) {
                String match = radixStr.substring(i, count);
                Byte b = reverseHuffmanCodeMap.get(match);
                if(b == null) {
                    //没匹配上
                    count++;
                }else {
                    // 匹配上了
                    list.add(b);
                    i = count;
                    break;
                }
            }
        }
        byte[] newBytes = new byte[list.size()];
        for(int index = 0; index < newBytes.length; index++) {
            newBytes[index] = list.get(index);
        }
        return newBytes;
    }

    private static String byteToBinaryStr(boolean flag, byte encodeByte) {
        int temp = encodeByte;
        if(flag) {
            temp |= 256;
        }
        String str = Integer.toBinaryString(temp);
        if(flag) {
            return str.substring(str.length() - 8);
        }else {
            return str;
        }
    }

    private static byte[] encode(byte[] contentBytes, Map<Byte, String> huffmanCodeMap) {
        StringBuilder sb = new StringBuilder();
        for(byte b : contentBytes) {
            sb.append(huffmanCodeMap.get(b));
        }
        String radixStr = sb.toString();
        System.out.println("根据霍夫曼字符集编码的二级制字符串为: " + radixStr);
        byte[] newBytes = new byte[(radixStr.length() + 7) / 8];
        int t = 0;
        for(int i = 0; i< radixStr.length(); i+=8) {
            if(i + 8 < radixStr.length()) {
                newBytes[t] = (byte) Integer.parseInt(radixStr.substring(i, i+8), 2);
            }else {
                newBytes[t] = (byte) Integer.parseInt(radixStr.substring(i), 2);
            }
            t++;
        }
        return newBytes;
    }


    public static int getWpl(HuffmanNode rootNode) {
        Map<Byte, Integer> weightMap = new ConcurrentHashMap<>();
        getWpl(rootNode, weightMap, 0, 0);
        AtomicInteger wplAtomicInteger = new AtomicInteger();
        System.out.println("wplMap = " + weightMap);
        weightMap.forEach((key, value) -> {
            wplAtomicInteger.addAndGet(value);
        });
        return wplAtomicInteger.get();
    }

    private static void getWpl(HuffmanNode node, Map<Byte, Integer> weightMap, int count, int delta) {
        if(node == null) {
            return;
        }
        count+=delta;
        if(node.getLeft()!= null) {
            getWpl(node.getLeft(), weightMap, count, 1);
        }
        if(node.getRight()!=null) {
            getWpl(node.getRight(), weightMap, count, 1);
        }
        if(node.getLeft() == null && node.getRight() == null) {
            weightMap.put(node.getValue(), node.getWeight() * count);
        }
    }

    private static Map<Byte, String> getHuffmanCodeMap(HuffmanNode rootNode) {
        Map<Byte, String> huffmanCodeMap = new ConcurrentHashMap<>();
        getHuffmanCodeMap(rootNode, huffmanCodeMap, new StringBuilder(), "");
        return huffmanCodeMap;
    }

    private static void getHuffmanCodeMap(HuffmanNode node, Map<Byte, String> huffmanCodeMap, StringBuilder sb, String path) {
        if(node == null){
            return;
        }
        StringBuilder sb1 = new StringBuilder(sb);
        sb1.append(path);
        if(node.getLeft()!=null){
            getHuffmanCodeMap(node.getLeft(), huffmanCodeMap, sb1, "0");
        }
        if(node.getRight()!=null){
            getHuffmanCodeMap(node.getRight(), huffmanCodeMap, sb1, "1");
        }
        // 叶子节点
        if(node.getLeft() == null && node.getRight() == null) {
            huffmanCodeMap.put(node.getValue(), sb1.toString());
        }
    }


    private static void preOrder(HuffmanNode node) {
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

    private static HuffmanNode createHuffmanCodeTree(List<HuffmanNode> leafNodeList) {
        if(leafNodeList == null || leafNodeList.size() == 0) {
            return null;
        }
        List<HuffmanNode> list = new ArrayList<>(leafNodeList);
        while (list.size() > 1) {
            Collections.sort(list);
            HuffmanNode min1Node = list.get(0);
            HuffmanNode min2Node = list.get(1);
            HuffmanNode newNode = new HuffmanNode(min1Node.getWeight() + min2Node.getWeight(), null);
            newNode.setLeft(min1Node);
            newNode.setRight(min2Node);
            list.remove(min1Node);
            list.remove(min2Node);
            list.add(newNode);
        }
        return list.get(0);
    }

    private static List<HuffmanNode> getLeafNodeList(byte[] contentBytes) {
        Map<Byte, AtomicInteger> byteCountMap = new ConcurrentHashMap<>();
        for(byte b : contentBytes) {
            byteCountMap.compute(b, (k, v)-> {
               if(v == null) {
                   return new AtomicInteger(1);
               }else {
                   v.incrementAndGet();
                   return v;
               }
            });
        }
        List<HuffmanNode> nodeList = new ArrayList<>();
        byteCountMap.forEach((k, v) -> {
            int count = v.get();
            HuffmanNode node = new HuffmanNode(count, k);
            nodeList.add(node);
        });
        Collections.sort(nodeList);
        return nodeList;
    }
}

class HuffmanNode implements Comparable<HuffmanNode>{

    private int weight;
    private Byte value;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(int weight, Byte value) {
        this.weight = weight;
        this.value = value;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight - o.weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "weight=" + weight +
                ", value=" + value +
                '}';
    }
}