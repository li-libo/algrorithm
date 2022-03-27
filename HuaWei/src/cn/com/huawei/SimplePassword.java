package cn.com.huawei;

import java.util.Scanner;

/**
 * @author lilibo
 * @create 2022-02-16 9:57 PM
 */
public class SimplePassword {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            String line = scanner.nextLine();
            char[] charArray = line.toCharArray();
            for(int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if(c >= 'a' && c <= 'z') {
                    charArray[i] = '2';
                }else if(c >= 'd' && c <= 'f') {
                    charArray[i] = '3';
                }else if(c >= 'g' && c <= 'i') {
                    charArray[i] = '4';
                }else if(c >= 'j' && c <= 'l') {
                    charArray[i] = '5';
                }else if(c >= 'm' && c <= 'o') {
                    charArray[i] = '6';
                }else if(c >= 'p' && c <= 's') {
                    charArray[i] = '7';
                }else if(c >= 't' && c <= 'v') {
                    charArray[i] = '8';
                }else if(c >= 'w' && c <= 'z') {
                    charArray[i] = '9';
                }else if(c >= 'A' && c <= 'Z') {
                    charArray[i] = (char) ((c + 32 + 1 - 'a') % 26 + 'a');
                }
            }
            System.out.println(String.valueOf(charArray));
        }
    }
}
