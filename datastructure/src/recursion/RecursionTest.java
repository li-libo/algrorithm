package recursion;

import org.junit.Test;

/**
 * 简单的说: 递归就是方法自己调用自己,每次调用时传入不同的变量.递归有助于编程者解决复杂的问题,同时可以让代码变得简洁。
 * 递归调用规则:
 * 1. 当程序执行到一个方法时, 就会开辟一个独立空间(栈桢)
 * 2. 每个空间的数据(局部变量), 是独立的
 *
 * 递归需要遵守的重要规则
 * 1) 执行一个方法时，就创建一个新的受保护的独立空间(栈空间)
 * 2) 方法的局部变量是独立的，不会相互影响, 比如 n 变量
 * 3) 如果方法中使用的是引用类型变量(比如数组)，就会共享该引用类型的数据.
 * 4) 递归必须向退出递归的条件逼近，否则就是无限递归,出现 StackOverflowError
 * 5) 当一个方法执行完毕，或者遇到 return，就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕
 *
 * @author lilibo
 * @create 2021-08-08 8:25 PM
 */
public class RecursionTest {

    @Test
    public void test1() {
        // 递归输出测试
        printN(6);
        // 求阶乘
        System.out.println("n == 5的阶乘: " + factorialResult(5));
    }

    /**
     * 不借助遍历,递归按顺序输出1...n
     */
    public void printN(int n) {
        if(n > 1) {
            printN(n-1);
        }
        System.out.println(n);
    }

    /**
     * 求阶乘
     * @param n
     * @return
     */
    public int factorialResult(int n) {
        if(n > 1) {
            return n * factorialResult(n-1);
        }
        return 1;
    }
}


