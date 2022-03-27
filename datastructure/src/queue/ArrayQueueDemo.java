package queue;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 1) 队列是一个有序列表，可以用数组或是链表来实现。
 * 2) 遵循先入先出的原则。即：先存入队列的数据，要先取出。后存入的要后取出
 * <p>
 * 数组模拟队列思路
 * (1)队列本身是有序列表，若使用数组的结构来存储队列的数据，则队列数组的声明如下图, 其中 maxSize 是该队列的最大容量。
 * (2)因为队列的输出、输入是分别从前后端来处理，因此需要两个变量 front 及 rear 分别记录队列前后端的下标， front 会随着数据输出而改变，而 rear 则是随着数据输入而改变。
 * 当我们将数据存入队列时称为”addQueue”，addQueue 的处理需要有两个步骤：思路分析
 * (1) 将尾指针往后移：rear+1 , 当 front == rear 【空】
 * (2) 若尾指针 rear 小于队列的最大下标 maxSize-1，则将数据存入 rear 所指的数组元素中，否则无法存入数据。 == maxSize - 1[队列满]
 *
 * @author lilibo
 * @create 2021-08-07 2:14 PM
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ArrayQueue<String> arrayQueue = new ArrayQueue<String>(3);
            boolean loop = true;
            while (loop) {
                try {
                    printCommandHint();
                    String command = scanner.nextLine();
                    switch (command) {
                        case "offer":
                            System.out.println("请输入offer队列的数据: ");
                            arrayQueue.offer(scanner.nextLine());
                            break;
                        case "pool":
                            System.out.println("从队列pool的数据为: " + arrayQueue.pool());
                            break;
                        case "show":
                            arrayQueue.showQueue();
                            break;
                        case "peek":
                            System.out.println("从队列peek数据为: " + arrayQueue.peek());
                            break;
                        case "size":
                            System.out.println("队里元素个数为:" + arrayQueue.size());
                            break;
                        case "exit":
                            loop = false;
                            break;
                        default:
                            System.out.println("无效命令: " + command);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printCommandHint() {
        System.out.println("show: 显示队列");
        System.out.println("exit: 退出程序");
        System.out.println("offer: 添加数据到队列");
        System.out.println("pool: 从队列取出数据");
        System.out.println("peek: 查看队列头的数据");
        System.out.println("size: 查看队列元素个数");
    }

}

class ArrayQueue<E> {
    private final int capacity;
    private int front;
    private int rear;
    private Object[] array;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        array = new Object[capacity];
    }

    public E pool() {
        if(isEmpty()) {
            System.out.println("队里已空!");
        }
        E returnValue = (E) array[front];
        array[front] = null;
        front++;
        return returnValue;
    }

    public void showQueue() {
        System.out.println(Arrays.toString(array));
    }

    public E peek() {
        return (E) array[front];
    }

    public int size() {
        return rear - front;
    }


    public boolean isFull() {
        return rear >= capacity ? true : false;
    }

    public void offer(E value) {
        if(isFull()) {
            System.out.println("队列已满!");
        }
        array[rear] = value;
        rear++;
    }

    private boolean isEmpty() {
        return rear - front == 0 ? true : false;
    }
}

/**
 * ArrayQueue问题分析并优化
 * 1) 目前数组使用一次就不能用, 没有达到复用的效果
 * 2) 将这个数组使用算法，改进成一个环形的队列, 取模：%
 *
 * 对前面的数组模拟队列的优化，充分利用数组. 因此将数组看做是一个环形的。(通过取模的方式来实现即可)
 * 分析说明： 1) 尾索引的下一个为头索引时表示队列满，即将队列容量空出一个作为约定, 这个在做判断队列满的时候需要注意 (rear + 1) % maxSize == front 满]
 *          2) rear == front [空]
 */
class CircleArrayQueue<E> {

    private final int capacity;

    private Object[] array;

    // front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素, front 的初始值 = 0
    private int front;
    // rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定, rear 的初始值 = 0
    private int rear; // 队列尾

    CircleArrayQueue(int capacity) {
        this.capacity = capacity + 1;
        array = new Object[this.capacity];
    }

    private boolean isFull() {
        // 队列满了的条件 rear + 1 = n * capacity + front
        return (rear + 1) % capacity == front;
    }

    private boolean isEmpty() {
        return rear == front;
    }

    public void offer(E e){
        if(isFull()){
            System.out.println("队列已满!!!");
            return;
        }
        array[rear] = e;
        rear = (rear + 1) % capacity;
    }

    public E pool() {
        if(isEmpty()){
            System.out.println("队列已空!!!");
            return null;
        }
        E returnValue = (E) array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        return returnValue;
    }

    public E peek() {
        if(isEmpty()){
            System.out.println("队列已空!!!");
            return null;
        }
        return (E) array[front];
    }

    public int size(){
        return (rear + capacity - front) % capacity;
    }

    public void showQueue(){
        System.out.println("array = " + Arrays.toString(array));
    }

}