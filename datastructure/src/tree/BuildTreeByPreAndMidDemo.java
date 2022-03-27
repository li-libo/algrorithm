package tree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据前序和中序遍历构建二叉树
 * 二叉树蛇形层次遍历
 * @author lilibo
 * @create 2022-03-08 8:13 PM
 */
public class BuildTreeByPreAndMidDemo {

    @Test
    public void test1() {
//        int[] pre = { 1, 5, 6, 10, 7, 2, 9, 3, 4, 8 };// 前序
//        int[] mid = { 6, 10, 5, 7, 1, 9, 2, 4, 3, 8 };// 中序
//        int[] post = { 10, 6, 7, 5, 9, 4, 8, 3, 2, 1 };// 后序
        int[] mid = {12, 11, 20, 17, 1, 15, 8, 5};
        int[] post = {12, 20, 17, 11, 15, 8, 5, 1};
        BinaryTree tree = new BinaryTree();
//        tree.buildTreeByPreMid(pre, mid);
//        tree.postPrint();
        tree.buildTreeByMidPost(mid, post);
        tree.printLevelOrder();
    }

    private class BinaryTree {

        private Node root;

        public void prePrint() {
            System.out.println("开始前序遍历...");
            if (root == null) {
                return;
            }
            prePrint(root);
            System.out.printf("\n");
            System.out.println("前序遍历结束...");
        }

        private void prePrint(Node node) {
            if (node == null) {
                return;
            }
            System.out.printf(node + " -> ");
            if (node.getLeft() != null) {
                prePrint(node.getLeft());
            }
            if (node.getRight() != null) {
                prePrint(node.getRight());
            }
        }

        public void postPrint() {
            System.out.println("开始后序遍历...");
            if (root == null) {
                return;
            }
            postPrint(root);
            System.out.printf("\n");
            System.out.println("后序遍历结束...");
        }

        private void postPrint(Node node) {
            if (node == null) {
                return;
            }
            if (node.getLeft() != null) {
                postPrint(node.getLeft());
            }
            if (node.getRight() != null) {
                postPrint(node.getRight());
            }
            System.out.printf(node + " -> ");
        }

        public void buildTreeByPreMid(int[] pre, int[] mid) {
            root = buildTreeByPreMid(pre, mid, 0, mid.length - 1, new AtomicInteger(0));
        }

        private Node buildTreeByPreMid(int[] pre, int[] mid, int midStart, int midEnd, AtomicInteger preIndex) {
            int flag = 0;
            if(preIndex.get() >= pre.length) {
                return null;
            }
            Node node = new Node(pre[preIndex.get()]);
            for(int i = midStart; i <= midEnd; i++) {
                if(mid[i] == pre[preIndex.get()]) {
                    flag = i;
                    break;
                }
            }
            preIndex.incrementAndGet();
            if(midStart < flag) {
                node.left = buildTreeByPreMid(pre, mid, midStart, flag - 1, preIndex);
            }
            if(flag < midEnd) {
                node.right = buildTreeByPreMid(pre, mid, flag + 1, midEnd, preIndex);
            }
            return node;
        }

        public void buildTreeByMidPost(int[] mid, int[] post) {
            root = buildTreeByMidPost(mid, post, 0, mid.length - 1, new AtomicInteger(mid.length - 1));
        }

        private Node buildTreeByMidPost(int[] mid, int[] post, int midStart, int midEnd, AtomicInteger postIndex) {
            int flag = 0;
            if(postIndex.get() < 0) {
                return null;
            }
            Node node = new Node(post[postIndex.get()]);
            // 遍历
            for(int i = midStart; i <= midEnd; i++) {
                if(mid[i] == post[postIndex.get()]) {
                    flag = i;
                    break;
                }
            }
            postIndex.decrementAndGet(); // postIndex索引前移
            //因为postIndex前移,因此先构建右子树再构建左子树
            if(flag < midEnd) {
                node.right = buildTreeByMidPost(mid, post, flag + 1, midEnd, postIndex);
            }
            if(midStart < flag) {
                node.left = buildTreeByMidPost(mid, post, midStart, flag - 1, postIndex);
            }
            return node;
        }

        public void printLevelOrder() {
            printLevelOrder(root);
        }

        public void printLevelOrder(Node root) {
            boolean reversed = false;//反转标志
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            int flag = 1;//用于输出格式控制
            while (!queue.isEmpty()) {
                int size = queue.size();//当前层的节点数
                LinkedList<Integer> dataLink = new LinkedList<>();//保存当前层结果，LinkedList可以前后插入
                for (int i = 0; i < size; i++) {  //对应每一层
                    Node temp = queue.poll();
                    if (reversed) {
                        dataLink.add(temp.data);
                    }else {
                        dataLink.addFirst(temp.data);
                    }
                    if (temp.left != null) queue.offer(temp.left);
                    if (temp.right != null) queue.offer(temp.right);
                }
                //	System.out.println(size);
                for (int j = 0; j < dataLink.size(); j++) {
                    if (flag == 1) {
                        System.out.print(dataLink.get(j));
                        flag = 0;
                    } else System.out.print(" " + dataLink.get(j));
                }
                reversed = !reversed;  //每次反转
            }
        }

    }

    private class Node {

        private Node left;
        private Node right;
        private int data;

        public Node(int data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

}
