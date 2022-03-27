package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lilibo
 * @create 2022-03-12 19:30
 */
public class 多叉树的后序遍历Demo {

    /**
     * 方法一：递归
     * 思路
     * 递归思路比较简单，N 叉树的前序遍历与二叉树的后序遍历的思路和方法基本一致，可以参考「145. 二叉树的后序遍历」的方法，
     * 每次递归时，先递归访问每个孩子节点，然后再访问根节点即可。
     * @param root
     * @return
     */
    public List<Integer> postorder(Node root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    public void helper(Node root, List<Integer> res) {
        if (root == null) {
            return;
        }
        for (Node ch : root.children) {
            helper(ch, res);
        }
        res.add(root.val);
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}
