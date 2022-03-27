package tree;

import java.util.LinkedList;

/**
 * leeCode---给定一个完美二叉树,填充所有节点的next指针，指向它右兄弟节点。如果没有右兄弟节点，则应该将next指针设置为NULL。
 * 初始时，所有的next指针都为NULL
 *
 * @author lilibo
 * @create 2022-03-01 15:04
 */
public class AddNextForTreeNode {

    public static void main(String[] args) {
        int[] values = {2, 1, 3};
        FullBinaryTree binaryTree = new FullBinaryTree();
        for (int value : values) {
            binaryTree.addNode(value);
        }
        binaryTree.addNext1();
        binaryTree.midPrint();
    }

}

class FullBinaryTree {

    private Node root;

    public void addNode(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            return;
        }
        addNode(root, newNode);
    }

    private void addNode(Node node, Node newNode) {
        if (node == null) {
            return;
        }
        if (newNode.getValue() < node.getValue()) {
            if (node.getLeft() == null) {
                node.setLeft(newNode);
                return;
            } else {
                addNode(node.getLeft(), newNode);
            }
        } else {
            if (node.getRight() == null) {
                node.setRight(newNode);
            } else {
                addNode(node.getRight(), newNode);
            }
        }
    }

    public void midPrint() {
        System.out.println("开始中序遍历...");
        if (root == null) {
            return;
        }
        midPrint(root);
        System.out.println("中序遍历结束...");
    }

    private void midPrint(Node node) {
        if (node == null) {
            return;
        }
        if (node.getLeft() != null) {
            midPrint(node.getLeft());
        }
        System.out.println("node = " + node + ", node.next = " + node.getNext());
        if (node.getRight() != null) {
            midPrint(node.getRight());
        }
    }

    /**
     * 借助1个队列完成添加next指针
     */
    public void addNext() {
        if(root == null || root.getLeft() == null) {
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Node head = queue.poll();
                if(head.getLeft() != null) {
                    queue.offer(head.getLeft());
                }
                if(head.getRight()!=null) {
                    queue.offer(head.getRight());
                }
                if(i != size - 1) {
                    head.setNext(queue.peek());
                }
            }
        }
    }

    /**
     * 仅适用于完美二叉树
     */
    public void addNext1() {
        if(root == null || root.getLeft() == null) {
            return;
        }
        addNext1(root);
    }

    private void addNext1(Node node) {
        if(node == null) {
            return;
        }
        if(node.getLeft()!=null) {
            node.getLeft().setNext(node.getRight());
        }
        if(node.getRight()!=null) {
            node.getRight().setNext(node.getNext() == null ? null : node.getNext().getLeft());
        }
        addNext1(node.getLeft());
        addNext1(node.getRight());
    }
}

class Node {
    private int value;
    private Node left;
    private Node right;

    private Node next;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}