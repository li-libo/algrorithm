package linkedlist;

import java.util.stream.Stream;

/**
 * @author lilibo
 * @create 2022-01-27 10:54 AM
 */
public class ArrayStackDemo {

    public static void main(String[] args) {
        int num = 7;
        ArrayStack<Integer> arrayStack = new ArrayStack<>(5);
        Stream.iterate(0, count -> count + 1).limit(num).forEach((count -> {
            arrayStack.push(count);
        }));
        Stream.iterate(0, count -> count + 1).limit(num).forEach((count -> {
            System.out.println("取出的数据: " + arrayStack.pop());
        }));
    }
}

class ArrayStack<E> {
    private int capacity;

    private int top = -1;

    private Object[] array;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    public boolean isFull() {
        return top == capacity -1 ? true : false;
    }

    public boolean isEmpty() {
        return top == -1 ? true : false;
    }

    public void push(E e) {
        if(isFull()) {
            System.out.println("栈已满!");
            return;
        }
        top++;
        array[top] = e;
    }

    public E pop() {
        if(isEmpty()) {
            System.out.println("栈已空!");
            return null;
        }
        Object returnValue = array[top];
        array[top] = null;
        top--;
        return (E) returnValue;
    }

    public int size() {
        return top + 1;
    }

    public E peek() {
        return (E) array[top];
    }
}
