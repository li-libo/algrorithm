package example;

/**
 * 字符串最后一个单词的长度
 * @author lilibo
 * @create 2022-02-15 8:33 PM
 */
public class LastStringLength {
    public static void main(String[] args) {
        try(java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            String line = scanner.nextLine();
            String[] array = line.split(" ");
            System.out.println(array[array.length - 1].length());
        }
    }
}