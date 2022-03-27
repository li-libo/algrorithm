package stack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * 实现 MinStack 类:
 *
 *     MinStack() 初始化堆栈对象。
 *     void push(int val) 将元素val推入堆栈。
 *     void pop() 删除堆栈顶部的元素。
 *     int top() 获取堆栈顶部的元素。
 *     int getMin() 获取堆栈中的最小元素。

 * @author lilibo
 * @create 2022-03-13 19:41
 */
public class 最小栈Demo {

    @Test
    public void test1() {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }

    /**
     * 自己解法
     */
    class MinStack {

        int min = Integer.MAX_VALUE;

        int secMin = min;

        List<Integer> list = new ArrayList<>();

        int index = -1;

        public void push(int val) {
            index++;
            if(val < min) {
                secMin = min;
                min = val;
            }
            list.add(index, val);
        }

        public void pop() {
            int removedE = list.remove(index);
            index--;
            if(removedE == min) {
                min = secMin;
            }
        }

        public int top() {
            if(index < 0) {
                throw new RuntimeException("栈中没有元素!");
            }
            return list.get(index);
        }

        public int getMin() {
            return min;
        }
    }

    class MinStack1 {
        Deque<Integer> xStack;
        Deque<Integer> minStack;

        public MinStack1() {
            xStack = new LinkedList<Integer>();
            minStack = new LinkedList<Integer>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            xStack.push(x);
            minStack.push(Math.min(minStack.peek(), x));
        }

        public void pop() {
            xStack.pop();
            minStack.pop();
        }

        public int top() {
            return xStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
