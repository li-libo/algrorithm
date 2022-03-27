package cn.com.huawei;
import java.util.*;
/**
 * @author lilibo
 * @create 2022-02-18 9:43 PM
 */
public class DataGroup {
    public static void resultData(String[] strR, String[] strI){
        LinkedList<Integer> result = new LinkedList<>();
        Set<Integer> setR = new TreeSet<>();
        for (int i = 1; i < strR.length; i++){
            setR.add(Integer.parseInt(strR[i]));
        }

        for (int str : setR){
            LinkedList<Integer> tmp = new LinkedList<>();
            for (int i = 1; i < strI.length; i++){
                if (strI[i].contains("" + str)){
                    tmp.add(i - 1);
                    tmp.add(Integer.parseInt(strI[i]));
                }
            }
            if (!tmp.isEmpty()){
                result.add(str);
                result.add((tmp.size() / 2));
                result.addAll(tmp);
            }
        }

        System.out.print(result.size() + " ");
        int count = result.size();
        for (int ele : result){
            System.out.print(ele + " ");
        }
        System.out.println();

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String I = sc.nextLine();
            String R = sc.nextLine();
            String[] arrR = R.split(" ");
            String[] arrI = I.split(" ");
            resultData(arrR, arrI);
        }
        sc.close();
    }
}
