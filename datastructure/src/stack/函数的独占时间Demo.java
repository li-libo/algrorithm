package stack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 636. 函数的独占时间
 * 有一个单线程 CPU 正在运行一个含有 n 道函数的程序。每道函数都有一个位于  0 和 n-1 之间的唯一标识符。
 * 函数调用 存储在一个 调用栈 上 ：当一个函数调用开始时，它的标识符将会推入栈中。而当一个函数调用结束时，它的标识符将会从栈中弹出。
 * 标识符位于栈顶的函数是 当前正在执行的函数 。每当一个函数开始或者结束时，将会记录一条日志，包括函数标识符、是开始还是结束、以及相应的时间戳。
 * 给你一个由日志组成的列表 logs ，其中 logs[i] 表示第 i 条日志消息，该消息是一个按 "{function_id}:{"start" | "end"}:{timestamp}"
 * 进行格式化的字符串。例如，"0:start:3" 意味着标识符为 0 的函数调用在时间戳 3 的 起始开始执行 ；而 "1:end:2" 意味着标识符为 1 的函数调
 * 用在时间戳 2 的 末尾结束执行。注意，函数可以 调用多次，可能存在递归调用 。
 * 函数的 独占时间 定义是在这个函数在程序所有函数调用中执行时间的总和，调用其他函数花费的时间不算该函数的独占时间。例如，如果一个函数被调用两次，
 * 一次调用执行 2 单位时间，另一次调用执行 1 单位时间，那么该函数的 独占时间 为 2 + 1 = 3 。
 * 以数组形式返回每个函数的 独占时间 ，其中第 i 个下标对应的值表示标识符 i 的函数的独占时间。
 *
 * 示例1:
 * 输入：n = 2, logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
 * 输出：[3,4]
 * 解释：
 * 函数 0 在时间戳 0 的起始开始执行，执行 2 个单位时间，于时间戳 1 的末尾结束执行。
 * 函数 1 在时间戳 2 的起始开始执行，执行 4 个单位时间，于时间戳 5 的末尾结束执行。
 * 函数 0 在时间戳 6 的开始恢复执行，执行 1 个单位时间。
 * 所以函数 0 总共执行 2 + 1 = 3 个单位时间，函数 1 总共执行 4 个单位时间。
 *
 * @author lilibo
 * @create 2022-03-12 19:34
 */
public class 函数的独占时间Demo {

    @Test
    public void test1() {
        int n = 2;
        String[] logs = {"0:start:0","1:start:2","1:end:5","0:end:6"};
        int[] exclusiveTimes = exclusiveTime(2, new ArrayList<>(Arrays.asList(logs)));
        System.out.println(Arrays.toString(exclusiveTimes));
    }

    /**
     * 方法一：栈
     * 我们可以使用栈来模拟函数的调用，即在遇到一条包含 start 的日志时，我们将对应的函数 id 入栈；在遇到一条包含 end 的日志时，
     * 我们将对应的函数 id 出栈。在每一个时刻，栈中的所有函数均为被调用的函数，而栈顶的函数为正在执行的函数。
     * 在每条日志的时间戳后，栈顶的函数会独占执行，直到下一条日志的时间戳，因此我们可以根据相邻两条日志的时间戳差值，来计算函数的独占时间。
     * 我们依次遍历所有的日志，对于第 i 条日志，如果它包含 start，那么栈顶函数从其时间戳 time[i] 开始运行，即 prev = time[i]；如果它包含
     * end，那么栈顶函数从其时间戳 time[i] 的下一个时间开始运行，即 prev = time[i] + 1。对于第 i + 1 条日志，如果它包含 start，那么在时
     * 间戳 time[i + 1] 时，有新的函数被调用，因此原来的栈顶函数的独占时间为 time[i + 1] - prev；如果它包含 end，那么在时间戳 time[i + 1] 时，
     * 原来的栈顶函数执行结束，独占时间为 time[i + 1] - prev + 1。在这之后，我们更新 prev 并遍历第 i + 2 条日志。在遍历结束后，我们就可以得到所
     * 有函数的独占时间。
     * @param n
     * @param logs
     * @return
     */
    public int[] exclusiveTime(int n, List< String > logs) {
        Stack<Integer> stack = new Stack <> ();
        int[] res = new int[n];
        String[] s = logs.get(0).split(":");
        stack.push(Integer.parseInt(s[0]));
        int i = 1, prev = Integer.parseInt(s[2]);
        while (i < logs.size()) {
            s = logs.get(i).split(":");
            if (s[1].equals("start")) {
                if (!stack.isEmpty()) {
                    res[stack.peek()] += Integer.parseInt(s[2]) - prev;
                }
                stack.push(Integer.parseInt(s[0]));
                prev = Integer.parseInt(s[2]);
            } else {
                res[stack.peek()] += Integer.parseInt(s[2]) - prev + 1;
                stack.pop();
                prev = Integer.parseInt(s[2]) + 1;
            }
            i++;
        }
        return res;
    }

}
