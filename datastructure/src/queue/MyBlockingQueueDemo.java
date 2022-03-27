package queue;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * @author lilibo
 * @create 2022-01-25 2:33 PM
 */
public class MyBlockingQueueDemo {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void test1() {
        MyBlockingQueue<Integer> myBlockingQueue = new MyBlockingQueue<>(10);
        int numOfPutThread = 1;
        int numOfTakeThread = 5;
        int loop = 100000;
        ThreadGroup testGroup = new ThreadGroup("testGroup");
        Stream.iterate(0, count -> count + 1).limit(numOfPutThread).forEach(count -> {
            new Thread(testGroup, ()-> {
                Stream.iterate(0, num -> num + 1).limit(loop).forEach((num)->{
                    try {
                        myBlockingQueue.put(atomicInteger.incrementAndGet());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "put-" + count).start();
        });
        CopyOnWriteArraySet<Integer> idSet = new CopyOnWriteArraySet<>();
        Stream.iterate(0, count -> count + 1).limit(numOfTakeThread).forEach(count -> {
            new Thread(testGroup, ()-> {
                Stream.iterate(0, num -> num + 1).limit(loop).forEach((num)->{
                    try {
                        Integer takeValue = myBlockingQueue.take();
                        if(!idSet.add(takeValue)) {
                            throw new RuntimeException("出现重复数据! id = " + takeValue);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "take-" + count).start();
        });
        Thread sizeThread = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("size = " + myBlockingQueue.size());
            }
        }, "sizeThread");
        sizeThread.setDaemon(true);
        sizeThread.start();
        while (testGroup.activeCount() > 0) {
            Thread.yield();
        }
        System.out.println("atomicInteger = " + atomicInteger.get());
    }
}

class MyBlockingQueue<E> {

    public static final String format1 = "队列已满, the currentThread name = %s等待!";

    public static final String format2 = "队列已空, the currentThread name = %s等待!";

    private final Lock lock = new ReentrantLock(true);

    private Condition putCondition = lock.newCondition();

    private Condition takeCondition = lock.newCondition();

    private int capacity;

    private int count;

    private int putIndex;

    private int takeIndex;

    private Object[] array;

    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    public void put(E e) throws InterruptedException {
        try{
            lock.lock();
            while(count == capacity) {
                // System.out.println(String.format(format1, Thread.currentThread().getName()));
                putCondition.await();
            }
            array[putIndex] = e;
            putIndex++;
            if(putIndex == capacity) {
                putIndex = 0;
            }
            count++;
            takeCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        try{
            lock.lock();
            while(count == 0) {
                System.out.println(String.format(format2, Thread.currentThread().getName()));
                takeCondition.await();
            }
            Object returnValue = array[takeIndex];
            array[takeIndex] = null;
            takeIndex++;
            if(takeIndex == capacity) {
                takeIndex = 0;
            }
            count--;
            putCondition.signalAll();
            return (E) returnValue;
        }finally {
            lock.unlock();
        }
    }

    public E peek() {
        try{
            lock.lock();
            return (E) array[takeIndex];
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try{
            return count;
        }finally {
            lock.unlock();
        }
    }

}
