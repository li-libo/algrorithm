package tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，
 * 同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化
 * 为一个字符串并且将这个字符串反序列化为原始的树结构。
 *
 * 提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，
 * 你也可以采用其他的方法解决这个问题。
 *
 * 输入：root = [1,2,3,null,null,4,5]
 * 输出：[1,2,3,null,null,4,5]

 * @author lilibo
 * @create 2022-03-12 18:25
 */
public class 二叉树的序列化与反序列化Demo {

    @Test
    public void test1() {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        String serializedStr = serialize(node1);
        System.out.println("序列化根节点后得到的编码为: " + serializedStr);
        System.out.println("开始反序列化...");
        TreeNode root = deserialize(serializedStr);
        System.out.println("开始前序遍历反序列化树...");
        printPreOrder(root);
    }

    private void printPreOrder(TreeNode node) {
        if(node == null) {
            return;
        }
        System.out.println(node);
        printPreOrder(node.left);
        printPreOrder(node.right);
    }

    public String serialize(TreeNode node) {
        return serialize(node, "");
    }

    private String serialize(TreeNode node, String str) {
        if(node == null) {
            str += "null,";
        }else {
            str += node.val + ",";
            str = serialize(node.left, str);
            str = serialize(node.right, str);
        }
        return str;
    }

    public TreeNode deserialize(String data) {
        String[] dataArray = data.split(",");
        List<String> dataList = new LinkedList<>(Arrays.asList(dataArray));
        return deserialize(dataList);
    }

    private TreeNode deserialize(List<String> dataList) {
        if(dataList.get(0).equals("null")) {
            dataList.remove(0);
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(dataList.get(0)));
        dataList.remove(0);
        node.left = deserialize(dataList);
        node.right = deserialize(dataList);
        return node;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
