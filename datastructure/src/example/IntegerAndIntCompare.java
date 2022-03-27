package example;

/**
 * @author lilibo
 * @create 2022-02-18 3:40 PM
 */
public class IntegerAndIntCompare {

    public static void main(String[] args) {
        int i1 = 128;
        Integer i2 = 128;
        Integer i3 = new Integer(128);
        System.out.println(i1 == i2);//true
        System.out.println(i1 == i3);//true
        System.out.println("**************************************");
        Integer i4 = 127;
        Integer i5 = 127;
        Integer i6 = 128;
        Integer i7 = 128;
        System.out.println(i4 == i5);//true
        System.out.println(i6 == i7);//false
        System.out.println("**************************************");
        Integer i8 = new Integer(127);
        Integer i9 = new Integer(127);
        System.out.println(i8 == i9);//false
        System.out.println(i8.equals(i9));//true
        System.out.println(i4 == i8);//false
    /* Output:
        true
        true
        **************************************
        true
        false
        **************************************
        false
        true
        false
     */
    }
}
