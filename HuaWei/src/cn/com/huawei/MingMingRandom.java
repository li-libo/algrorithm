package cn.com.huawei;

/**
 * @author lilibo
 * @create 2022-02-15 9:21 PM
 */
public class MingMingRandom {

    public static void main(String[] args) {
        try(java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            java.util.TreeSet<Integer> treeSet = new java.util.TreeSet<>();
            while(scanner.hasNext()){
                int size = scanner.nextInt();
                for(int i = 0; i < size; i++){
                    treeSet.add(scanner.nextInt());
                }
                java.util.Iterator<Integer> it = treeSet.iterator();
                while(it.hasNext()) {
                    System.out.println(it.next());
                }
                treeSet.clear();
            }
        }
    }
}
