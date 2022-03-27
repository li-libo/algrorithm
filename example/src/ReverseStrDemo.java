/**
 * 反转字符串或句子
 *
 * @author lilibo
 * @create 2022-03-01 11:06
 */
public class ReverseStrDemo {

    public static void main(String[] args) {
        String str = "abcdef";
        System.out.println("反转" + str + "为: " + reverseStr(str));
        String sentence = "hello world?";
        System.out.println("反转" + sentence + "的句子并且反转句子中每个单词为: " + reverseSentence(sentence));
    }

    /**
     * 反转句子并且反转句子中每个单词
     *
     * @param str
     * @return
     */
    public static String reverseSentence(String str) {
        String[] sentenceArray = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String sentence : sentenceArray) {
            char[] chars = sentence.toCharArray();
            for (int i = 0; i < chars.length / 2; i++) {
                char temp = chars[i];
                chars[i] = chars[chars.length - 1 - i];
                chars[chars.length - 1 - i] = temp;
            }
            sb.append(chars);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String reverseSentenceOne(String sentence) {
        if (null == sentence || sentence.isEmpty()) {
            return "";
        }
        String[] s = sentence.split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = s.length - 1; i > 0; --i) {
            stringBuffer.append(s[i] + " ");
        }
        stringBuffer.append(s[0]);
        return stringBuffer.toString();
    }

    /**
     * 反转String串
     *
     * @param str
     * @return
     */
    public static String reverseStr(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length / 2; i++) {
            char temp = charArray[i];
            charArray[i] = charArray[length - 1 - i];
            charArray[length - 1 - i] = temp;
        }
        return new String(charArray);
    }

}
