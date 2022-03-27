package cn.com.huawei;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lilibo
 * @create 2022-02-19 9:33 PM
 */
public class T2 {

    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args)
    {
        int i;
        i = x(8);
        System.out.println(count.get());
    }
    static int x(int n)
    {
        count.incrementAndGet();
        if (n <= 3)
            return 1;
        else
            return x(n - 2) + x(n - 4) + 1;
    }
}
