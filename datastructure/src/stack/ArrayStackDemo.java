package stack;

import java.util.Scanner;

/**
 * @author lilibo
 * @create 2021-08-08 7:42 PM
 */
public class ArrayStackDemo {

    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>(5);
        boolean loop = true;
        try(Scanner scanner = new Scanner(System.in)) {
            while (loop){
                printCmdHint();
                String cmd = scanner.nextLine();
                switch (cmd){
                    case "show":
                        stack.print();
                        break;
                    case "push":
                        System.out.println("请输入push数据");
                        String data = scanner.nextLine();
                        stack.push(data);
                        break;
                    case "pop":
                        System.out.println(stack.pop());
                        break;
                    case "exit":
                       loop = false;
                       break;
                    default:
                        System.out.println("无效指令cmd = " + cmd);
                }
            }
        }
    }

    private static void printCmdHint() {
        System.out.println("show: 表示显示栈");
        System.out.println("exit: 退出程序");
        System.out.println("push: 表示添加数据到栈(入栈)");
        System.out.println("pop: 表示从栈取出数据(出栈)");
    }

}

class ArrayStack<E> {
    private int capacity;

    private int top;

    private Object[] array;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
        top = -1;
    }

    private boolean isFull() {
        return top == capacity - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(E e) {
        if (isFull()) {
            System.out.println("栈已满!");
            return;
        }
        top++;
        array[top] = e;
    }

    public E pop() {
        if (isEmpty()) {
            System.out.println("栈已空!");
            return null;
        }
        E returnValue = (E) array[top];
        array[top] = null;
        top--;
        return returnValue;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("栈已空!");
            return;
        }
        for (int i = top; i >= 0; i--) {
            if (i == 0) {
                System.out.println(array[i]);
            } else {
                System.out.printf(array[i] + "->");
            }
        }
    }

}