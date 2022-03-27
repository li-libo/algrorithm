package stack;

/**
 * @author lilibo
 * @create 2020-09-14 2:59 PM
 */
public class Calculator {

    public static void main(String[] args) {
        String expression = "7*2*2-5+1-5+3-4"; // 18//如何处理多位数的问题？
        expression = expression.replaceAll("\\s", "");
        // 创建两个栈: 数栈和符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        int index = 0;
        int num1 = 0;
        int num2 = 0;
        char ch = ' ';
        int res = -1;
        String keepNum = "";
        // 开始构造数栈和符号栈
        while (index <= expression.length() - 1) {
            ch = expression.charAt(index);
            if (operStack.isOper(ch)) {
                if (operStack.isEmpty()) {
                    operStack.push(ch);
                } else if (operStack.getPriority(ch) <= operStack.getPriority(operStack.peek())) {
                    num1 = numStack.pop();
                    num2 = numStack.pop();
                    int oper = operStack.pop();
                    res = operStack.cal(num1, num2, oper);
                    numStack.push(res);
                    operStack.push(ch);
                } else {
                    operStack.push(ch);
                }
            } else {
                // 需要考虑多位数字
                keepNum += ch;
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    if (operStack.isOper(expression.charAt(index + 1))) {
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = ""; //清空
                    }
                }
            }
            index++;
        }
        //弹出数栈和符号栈开始计算,计算一次就把结果压入数栈,直到符号栈为空,数栈只有一个值
        while (!operStack.isEmpty()) {
            num1 = numStack.pop();
            num2 = numStack.pop();
            int oper = operStack.pop();
            res = operStack.cal(num1, num2, oper);
            numStack.push(res);
        }
        System.out.println("计算结果为" + numStack.pop());
    }

}

class ArrayStack2 {

    private int maxSize;

    private int[] array;

    private int top; //栈顶指针

    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        array = new int[maxSize];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public void push(int value) {
        if (isFull()) {
            System.out.println("栈已满,无法添加!");
        }
        top++;
        array[top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空!,无法取出!");
        }
        int popValue = array[top];
        top--;
        return popValue;
    }

    public void list() {
        if (isEmpty()) {
            System.out.println("栈为空,无法遍历!");
            return;
        }
        for (int index = top; index >= 0; index--) {
            System.out.printf(array[index] + "->");
        }
    }

    public int size() {
        return top + 1;
    }

    public int getPriority(int oper) {
        if ('+' == oper || '-' == oper) {
            return 0;
        } else if ('*' == oper || '/' == oper) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isOper(char oper) {
        if (oper == '+' || oper == '-' || oper == '*' || oper == '/') {
            return true;
        }
        return false;
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空,无法peek!");
        }
        return array[top];
    }

    public int cal(int num1, int num2, int oper) {
        int res = -1;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1; //注意数字顺序
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                throw new RuntimeException("oper错误!");
        }
        return res;
    }
}