package queue;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lilibo
 * @create 2022-01-25 8:44 PM
 */
public class StackDemo {

    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void test1() throws InterruptedException {
        ThreadGroup testGroup = new ThreadGroup("testGroup");
        MyStack<Integer> myStack = new MyStack<>(5);
        new Thread(testGroup, ()-> {
            for(int i = 0; i < 10; i++) {
                try {
                    myStack.push(atomicInteger.incrementAndGet());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "push-Thread").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(testGroup, ()-> {
            for(int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("准备弹出数据前size = " + myStack.size());
                    System.out.println("弹出数据为: " + myStack.pop() + ", size = " + myStack.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "pop-Thread").start();

        while (testGroup.activeCount() > 0) {
            Thread.yield();
        }
    }

}

class MyStack<E>{

    private Lock lock = new ReentrantLock();

    private Condition pushCondition = lock.newCondition();

    private Condition popCondition = lock.newCondition();

    private int index = -1;

    private final int capacity;

    private Object[] array;

    public MyStack(int capacity) {
        this.capacity = capacity;
        this.array = new Object[capacity];
    }

    private boolean isFull() {
        return index == capacity - 1;
    }

    private boolean isEmpty() {
        return index == -1;
    }

    public void push(E e) throws InterruptedException {
        lock.lock();
        try{
            while (isFull()) {
                pushCondition.await();
            }
            index++;
            array[index] = e;
            popCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public E pop() throws InterruptedException {
        lock.lock();
        try{
            while (isEmpty()) {
                popCondition.await();
            }
            Object top = array[index];
            index--;
            pushCondition.signalAll();
            return (E) top;
        }finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try{
            return index + 1;
        }finally {
            lock.unlock();
        }
    }
}
