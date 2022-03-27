package example;

/**
 * 暴力匹配
 *
 * @author lilibo
 * @create 2021-08-29 7:11 PM
 */
public class ViolenceMatchDemo {

    public static void main(String[] args) {
        String source = "暴力匹配算法";
        String target = "匹配";
        int match = violenceMatch(source, target);
        System.out.println(String.format("%s 在 %s 的位置为%d", source, target, match));
    }

    private static int violenceMatch(String source, String target) {
        char[] c1 = source.toCharArray();
        char[] c2 = target.toCharArray();
        int index1 = 0;
        int index2 = 0;
        int c1Length = c1.length;
        int c2Length = c2.length;
        while (index1 < c1Length && index2 < c2Length) {
            if(c1[index1] == c2[index2]) {
                index1++;
                index2++;
            }else {
                index1 = index1 - (index2 - 1);
                index2 = 0;
            }
        }
        if(index2 == c2Length) {
            return index1 - index2;
        }
        return -1;
    }

}