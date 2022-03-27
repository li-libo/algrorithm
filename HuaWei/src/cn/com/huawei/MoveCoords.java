package cn.com.huawei;

import java.util.Scanner;

/**
 * 坐标移动
 * 描述
 *
 * 开发一个坐标计算工具， A表示向左移动，D表示向右移动，W表示向上移动，S表示向下移动。从（0,0）点开始移动，从输入字符串里面读取一些坐标，并将最终输入结果输出到输出文件里面。
 *
 * 输入：
 *
 * 合法坐标为A(或者D或者W或者S) + 数字（两位以内）
 *
 * 坐标之间以;分隔。
 *
 * 非法坐标点需要进行丢弃。如AA10;  A1A;  $%$;  YAD; 等。
 *
 * 下面是一个简单的例子 如：
 *
 * A10;S20;W10;D30;X;A1A;B10A11;;A10;
 *
 * 处理过程：
 *
 * 起点（0,0）
 *
 * +   A10   =  （-10,0）
 *
 * +   S20   =  (-10,-20)
 *
 * +   W10  =  (-10,-10)
 *
 * +   D30  =  (20,-10)
 *
 * +   x    =  无效
 *
 * +   A1A   =  无效
 *
 * +   B10A11   =  无效
 *
 * +  一个空 不影响
 *
 * +   A10  =  (10,-10)
 * 结果 （10， -10）
 *
 * 数据范围：每组输入的字符串长度满足 1≤n≤10000 1\le n \le 10000 \ 1≤n≤10000  ，坐标保证满足 −231≤x,y≤231−1 -2^{31} \le x,y \le 2^{31}-1 \ −231≤x,y≤231−1  ，且数字部分仅含正数
 *
 * 注意请处理多组输入输出
 * 输入描述：
 *
 * 一行字符串
 * 输出描述：
 *
 * 最终坐标，以逗号分隔
 * @author lilibo
 * @create 2022-02-16 5:45 PM
 */
public class MoveCoords {

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            while(scanner.hasNext()) {
                int x = 0;
                int y = 0;
                String line = scanner.nextLine();
                String[] cmdArray = line.split(";");
                for(String cmd : cmdArray) {
                    if(cmd.equals("")) {
                        continue;
                    }
                    if(cmd.charAt(0) == 'A' && cmd.substring(1).matches("\\d+")) {
                        x-=Integer.parseInt(cmd.substring(1), 10);
                    }else if(cmd.charAt(0) == 'D' && cmd.substring(1).matches("\\d+")) {
                        x+=Integer.parseInt(cmd.substring(1), 10);
                    }else if(cmd.charAt(0) == 'W' && cmd.substring(1).matches("\\d+")) {
                        y+=Integer.parseInt(cmd.substring(1), 10);
                    }else if(cmd.charAt(0) == 'S' && cmd.substring(1).matches("\\d+")) {
                        y-=Integer.parseInt(cmd.substring(1), 10);
                    }
                }
                System.out.println(x + "," + y);
            }
        }
    }
}
