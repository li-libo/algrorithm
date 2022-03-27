package huffmancode;
import java.io.*;
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
 * @create 2021-08-14 9:34 PM
 */
public class HuffmanCodeDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        System.out.printf("%s的原始字节数组长度为%d\n", content, contentBytes.length);
        // 根据contentBytes中不同字节出现的次数(作为node的权值)构建nodeList
        List<HuffmanTreeNode> nodeList = getNodeList(contentBytes);
        System.out.println("根据contentBytes构建的nodeList为: " + Arrays.toString(nodeList.toArray()));
        // 根据nodeList构建霍夫曼树
        HuffmanTreeNode rootNode = createHuffmanTree(nodeList);
        System.out.println("根据contentBytes构建霍夫曼树的根节点权值为: " + rootNode.getWeight());
        System.out.println("前序遍历霍夫曼树...");
        preOrder(rootNode);
        // 根据霍夫曼树构建霍夫曼编码
        Map<Byte, String> huffmancCodeMap = getHuffmanCode(rootNode);
        System.out.println("遍历霍夫曼编码为...");
        huffmancCodeMap.entrySet().stream().forEach(e -> {
            System.out.println("key = " + e.getKey() + ", value = " + e.getValue());
        });
        System.out.println("霍夫曼编码树wpl = " + getWpl(rootNode));
        // 霍夫曼编码
        byte[] huffmanCodeBytes = encode(contentBytes, huffmancCodeMap);
        System.out.printf("%s的原始字节数组长度为%d,霍夫曼编码字节数组长度为%d\n", content, contentBytes.length, huffmanCodeBytes.length);
        // 霍夫曼解码
        byte[] decodeBytes = decode(huffmancCodeMap, huffmanCodeBytes);
        System.out.println("霍夫曼字节数组解码得到的原内容为: " + new String(decodeBytes, "utf-8"));
        // 测试压缩文件
        // zipFile("/Users/lilibo/intellij-idea workplace/algorithm/DataStructures/src/com/atguigu/huffmancode/High Sierra.jpg", "/Users/lilibo/intellij-idea workplace/algorithm/DataStructures/src/com/atguigu/huffmancode/High Sierra.zip");
        //unZipFile("/Users/lilibo/intellij-idea workplace/algorithm/DataStructures/src/com/atguigu/huffmancode/High Sierra.zip", "/Users/lilibo/intellij-idea workplace/algorithm/DataStructures/src/com/atguigu/huffmancode/High Sierra1.jpg");
    }

    public static int getWpl(HuffmanTreeNode rootNode) {
        Map<Byte, Integer> weightMap = new ConcurrentHashMap<>();
        getWpl(rootNode, weightMap, 0, 0);
        AtomicInteger wplAtomicInteger = new AtomicInteger();
        System.out.println("wplMap = " + weightMap);
        weightMap.forEach((key, value) -> {
            wplAtomicInteger.addAndGet(value);
        });
        return wplAtomicInteger.get();
    }

    private static void getWpl(HuffmanTreeNode node, Map<Byte, Integer> weightMap, int count, int delta) {
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

    public static void unZipFile(String zipFilePath, String destFilePath) throws FileNotFoundException, IOException, ClassNotFoundException {
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            throw new RuntimeException("压缩文件不存在!");
        }
        try (FileInputStream fis = new FileInputStream(zipFilePath); ObjectInputStream ois = new ObjectInputStream(fis); FileOutputStream fos = new FileOutputStream(destFilePath)) {
            byte[] huffmanByteArray = (byte[]) ois.readObject();
            Map<Byte, String> huffmanCodeMap = (Map<Byte, String>) ois.readObject();
            byte[] sourceBytes = decode(huffmanCodeMap, huffmanByteArray);
            fos.write(sourceBytes);
        }
    }

    /**
     * 采用霍夫曼编码压缩文件
     *
     * @param srcPath
     * @param destPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static Map<Byte, String> zipFile(String srcPath, String destPath) throws FileNotFoundException, IOException {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            throw new RuntimeException(srcPath + "不存在!");
        }
        Map<Byte, String> huffmanCodeMap = null;
        try (FileInputStream is = new FileInputStream(srcPath); FileOutputStream os = new FileOutputStream(destPath); ObjectOutputStream oos = new ObjectOutputStream(os)) {
            int byteArrayLength = is.available();
            byte[] originalBytes = new byte[byteArrayLength];
            is.read(originalBytes);
            huffmanCodeMap = getHuffmanCode(createHuffmanTree(getNodeList(originalBytes)));
            byte[] zipBytes = encode(originalBytes, huffmanCodeMap);
            // 小技巧: 以对象流形式写入,到时以对象流形式读出
            oos.writeObject(zipBytes);
            //记得写入对应的霍夫曼编码,防止丢失
            oos.writeObject(huffmanCodeMap);
        }
        return huffmanCodeMap;
    }

    /**
     * @param bytes           这时原始的字符串对应的 byte[]
     * @param huffmancCodeMap 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的 byte[] 举例： String content = "i like like like java do you
     * like a java"; =》 byte[] contentBytes = content.getBytes(); 返回的是字符串
     * "1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     * => 对应的 byte[] huffmanCodeBytes, 即8位对应一个 byte,放入到 huffmanCodeBytes
     * huffmanCodeBytes[0] = 10101000(补码) => byte [推导 10101000=> 10101000 -
     * 1 => 10100111(反码)=> 11011000= -88 ] huffmanCodeBytes[1] = -88
     */
    public static byte[] encode(byte[] bytes, Map<Byte, String> huffmancCodeMap) {
        // 根据bytes和huffmancCodeMap构建字符编码
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(huffmancCodeMap.get(b));
        }
        String huffmanCodeStr = sb.toString();
        // 将huffmanCodeStr转为字节数组
        int newLength = (huffmanCodeStr.length() + 7) / 8;
        byte[] huffmanCodeBytes = new byte[newLength];
        int index = 0;
        String subStr = null;
        for (int i = 0; i < huffmanCodeStr.length(); i += 8) {
            if ((i + 8) < huffmanCodeStr.length()) {
                subStr = huffmanCodeStr.substring(i, i + 8);
            } else {
                subStr = huffmanCodeStr.substring(i);
            }
            byte b = (byte) Integer.parseInt(subStr, 2);
            huffmanCodeBytes[index] = b;
            index++;
        }
        return huffmanCodeBytes;
    }

    // 完成数据的解压
    // 思路
    // 1. 将huffmanCodeBytes [-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24,
    // -14, -117, -4, -60, -90, 28]
    // 重写先转成 赫夫曼编码对应的二进制的字符串 "1010100010111..."
    // 2. 赫夫曼编码对应的二进制的字符串 "1010100010111..." =》 对照 赫夫曼编码 =》 解码字符串

    /**
     * 编写一个方法，完成对压缩数据的解码
     *
     * @param huffmanCodeMap 赫夫曼编码表 map
     * @param huffmanBytes   赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodeMap, byte[] huffmanBytes) {
        StringBuilder sb = new StringBuilder();
        // 先将huffmanBytes转为对应的二进制字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            // 最后1位不需补码
            boolean flag = (i == huffmanBytes.length - 1);
            String binaryStr = byteToBitString(!flag, huffmanBytes[i]);
            sb.append(binaryStr);
        }
        String binaryStr = sb.toString();
        // System.out.println("二级制编码字符串为" + binaryStr);
        // 根据二进制编码字符串对照赫夫曼编码表获取对应的byte数组
        // 将赫夫曼编码表翻转
        Map<String, Byte> reverseHuffmanCodeMap = new ConcurrentHashMap<>();
        huffmanCodeMap.entrySet().stream().forEach(e -> {
            reverseHuffmanCodeMap.put(e.getValue(), e.getKey());
        });
        // 匹配二进制编码为byte并收集
        List<Byte> byteList = new ArrayList<>();
        for (int i = 0; i < binaryStr.length(); ) {
            int count = 1; // 小指针
            while (i + count <= binaryStr.length()) {
                String key = binaryStr.substring(i, i + count);
                Byte match = reverseHuffmanCodeMap.get(key);
                if (match == null) {
                    count++;
                } else {
                    byteList.add(match);
                    i += count; //更新i
                    break;
                }
            }
        }
        byte[] sourceByteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            sourceByteArray[i] = byteList.get(i);
        }
        return sourceByteArray;
    }

    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     *
     * @param b    传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b) {
        // 使用变量保存 b
        int temp = b; // 将 b 转成 int
        // 如果是正数我们还存在补高位
        if (flag) {
            temp |= 256; // 按位与 256 1 0000 0000 | 0000 0001 => 1 0000 0001
        }
        String str = Integer.toBinaryString(temp); // 返回的是temp对应的二进制的补码
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

    public static Map<Byte, String> getHuffmanCode(HuffmanTreeNode rootNode) {
        Map<Byte, String> huffmanCodeMap = new ConcurrentHashMap<>();
        constructHuffmanCode(rootNode, huffmanCodeMap, new StringBuilder(), "");
        return huffmanCodeMap;
    }

    private static void constructHuffmanCode(HuffmanTreeNode node, Map<Byte, String> huffmanCodeMap, StringBuilder sb, String code) {
        if (node == null) {
            return;
        }
        StringBuilder sb1 = new StringBuilder(sb);
        sb1.append(code);
        // 非叶子节点,规定向左路径为0,向右路径为1
        if (node.getLeft() != null) {
            constructHuffmanCode(node.getLeft(), huffmanCodeMap, sb1, "0");
        }
        if (node.getRight() != null) {
            constructHuffmanCode(node.getRight(), huffmanCodeMap, sb1, "1");
        }
        // 叶子节点
        if (node.getLeft() == null && node.getRight() == null) {
            huffmanCodeMap.put(node.getValue(), sb1.toString());
        }
    }

    public static void preOrder(HuffmanTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        if (node.getLeft() != null) {
            preOrder(node.getLeft());
        }
        if (node.getRight() != null) {
            preOrder(node.getRight());
        }
    }

    public static HuffmanTreeNode createHuffmanTree(List<HuffmanTreeNode> nodeList) {
        if (nodeList == null && nodeList.size() == 0) {
            System.out.println("nodeList为空!无法构建霍夫曼树!");
            return null;
        }
        while (nodeList.size() > 1) {
            Collections.sort(nodeList);
            // 先取出权值最小的两个节点构建它的父节点
            HuffmanTreeNode left = nodeList.get(0);
            HuffmanTreeNode right = nodeList.get(1);
            HuffmanTreeNode parent = new HuffmanTreeNode(left.getWeight() + right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);
            // 用parent替代原先nodeList中的left/right节点
            nodeList.remove(left);
            nodeList.remove(right);
            nodeList.add(parent);
        }
        // 返回霍夫曼树的根节点
        return nodeList.get(0);
    }

    public static List<HuffmanTreeNode> getNodeList(byte[] contentBytes) {
        List<HuffmanTreeNode> nodeList = new ArrayList<>();
        Map<Byte, AtomicInteger> byteCountMap = new ConcurrentHashMap<>();
        for (byte b : contentBytes) {
            byteCountMap.compute(b, (k, v) -> {
                if (v == null) {
                    return new AtomicInteger(1);
                } else {
                    v.incrementAndGet();
                    return v;
                }
            });
        }
        byteCountMap.entrySet().stream().forEach(e -> {
            byte b = e.getKey();
            int weight = e.getValue().get();
            HuffmanTreeNode huffmanTreeNode = new HuffmanTreeNode(weight);
            huffmanTreeNode.setValue(b);
            nodeList.add(huffmanTreeNode);
        });
        Collections.sort(nodeList);
        return nodeList;
    }

}

class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {

    private byte value;

    private int weight;

    private HuffmanTreeNode left;

    private HuffmanTreeNode right;

    public HuffmanTreeNode(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
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
                "value=" + value +
                ", weight=" + weight +
                '}';
    }

}