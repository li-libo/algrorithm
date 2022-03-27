package cn.com.huawei;

import java.util.Scanner;

/**
 * @author lilibo
 * @create 2022-02-16 8:48 PM
 */
public class MyValidAddress {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            int countOfA = 0;
            int countOfB = 0;
            int countOfC = 0;
            int countOfD = 0;
            int countOfE = 0;
            int countOfErrorIp = 0;
            int countOfPrivateIp = 0;
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] strArray = line.split("~");
                String ip = strArray[0];
                String maskCode = strArray[1];
                // 判断格式
                if(!isValidateFormat(ip) || !isValidateFormat(maskCode)) {
                    countOfErrorIp++;
                    continue;
                }
                if(!isValidateMaskCode(maskCode)) {
                    countOfErrorIp++;
                    continue;
                }
                // 地址划分
                String[] numArray = ip.split("\\.");
                String fn = numArray[0];
                int firstNum = Integer.parseInt(fn, 10);
                if(firstNum >= 1 && firstNum <= 126) {
                    countOfA++;
                }else if(firstNum >= 128 && firstNum <= 191) {
                    countOfB++;
                }else if(firstNum >= 192 && firstNum <= 223) {
                    countOfC++;
                }else if(firstNum >= 224 && firstNum <= 239) {
                    countOfD++;
                }else if(firstNum >= 240 && firstNum <= 255) {
                    countOfE++;
                }
                if(isPrivateIp(ip)) {
                    countOfPrivateIp++;
                }
            }
            System.out.print(countOfA + " " + countOfB + " " + countOfC + " " + countOfD + " " + countOfE + " " + countOfErrorIp + " "  + countOfPrivateIp);
        }
    }

    private static boolean isValidateFormat(String address) {
        if(!address.matches("^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)$")) {
            return false;
        }
        String[] numArray = address.split("\\.");
        for(String numStr : numArray) {
            int num = Integer.parseInt(numStr, 10);
            if(num < 0 || num >= 256) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidateMaskCode(String maskCode) {
        String[] numArray = maskCode.split("\\.");
        StringBuilder sb = new StringBuilder();
        String binaryStr = null;
        for(String numStr : numArray) {
            int num = Integer.parseInt(numStr, 10);
            binaryStr = Integer.toBinaryString(num);
            sb.append(binaryStr);
        }
        binaryStr = sb.toString();
        int firstZeroIndex = binaryStr.indexOf("0");
        int lastOneIndex = binaryStr.lastIndexOf("1");
        if(firstZeroIndex < lastOneIndex) {
            return false;
        }
        return true;
    }

    private static boolean isPrivateIp(String address) {
        String[] numArray = address.split("\\.");
        int fn = Integer.parseInt(numArray[0]);
        if(fn != 10 && (fn != 172 && fn != 192)) {
            return false;
        }
        if(fn == 10) {
            for(int i = 1; i < numArray.length; i++) {
                int n = Integer.parseInt(numArray[i]);
                if(n < 0 && n > 255) {
                    return false;
                }
            }
        }
        if(fn == 172) {
            for(int i = 1; i < numArray.length; i++) {
                int n = Integer.parseInt(numArray[i]);
                if(i == 1 && (n<16 || n>31)) {
                    return false;
                }
                if(n < 0 && n > 255) {
                    return false;
                }
            }
        }
        if(fn == 192) {
            for(int i = 1; i < numArray.length; i++) {
                int n = Integer.parseInt(numArray[i]);
                if(i == 1 && n != 168) {
                    return false;
                }
                if(n < 0 && n > 255) {
                    return false;
                }
            }
        }
        return true;
    }
}
