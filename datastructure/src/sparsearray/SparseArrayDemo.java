package sparsearray;

import java.io.*;

/**
 * 当1个数组中大部分元素为0,或者为同一个值时,可以使用稀疏数组来保存该数组
 * 稀疏数组的处理方法是:
 *    (1) 记录数组一共有几行几列,有多少个不同值
 *    (2) 把具有不同值的元素的行列及值记录在一个小规模的数组中,从而缩小程序规模
 * @author lilibo
 * @create 2021-08-06 9:17 PM
 */
public class SparseArrayDemo {

    public static void main(String[] args) throws Exception {
        SparseArrayDemo sparseArrayDemo = new SparseArrayDemo();
        int[][] originalArray = new int[6][7];
        originalArray[0][3] = 22;
        originalArray[0][6] = 15;
        originalArray[1][1] = 11;
        originalArray[1][5] = 17;
        originalArray[2][3] = -6;
        originalArray[3][5] = 39;
        originalArray[4][0] = 91;
        originalArray[5][2] = 28;
        System.out.println("---原始二维数组为:");
        printArray(originalArray);
        int[][] sparseArray = transformToSparseArray(originalArray);
        System.out.println("---保存的稀疏矩阵为:");
        printArray(sparseArray);
        saveSparseArray(sparseArray, "save.txt");
        int[][] sparseArray1 = readSparseArray("save.txt");
        System.out.println("---读取存盘的稀疏矩阵为:");
        printArray(sparseArray1);
        System.out.println("---复原的二维矩阵为:");
        printArray(recoverOriginalArray(sparseArray1));
    }

    private static int[][] recoverOriginalArray(int[][] sparseArray) {
        int[][] originalArray = new int[sparseArray[0][0]][sparseArray[0][1]];
        for(int rowIndex = 1; rowIndex < sparseArray.length; rowIndex++){
            originalArray[sparseArray[rowIndex][0]][sparseArray[rowIndex][1]] = sparseArray[rowIndex][2];
        }
        return originalArray;
    }

    private static int[][] transformToSparseArray(int[][] originalArray) {
        int count = 0;
        for(int[] row:originalArray){
            for(int o : row){
                if(o!=0){
                    count++;
                }
            }
        }
        int[][] sparseArray = new int[count + 1][3];
        sparseArray[0][0] = originalArray.length;
        sparseArray[0][1] = originalArray[0].length;
        sparseArray[0][2] = count;
        int rowIndexOfSparseArray = 1;
        for(int rowIndex = 0; rowIndex< originalArray.length; rowIndex++){
            for(int colIndex = 0; colIndex < originalArray[rowIndex].length; colIndex++){
                if(originalArray[rowIndex][colIndex]!=0){
                    sparseArray[rowIndexOfSparseArray][0] = rowIndex;
                    sparseArray[rowIndexOfSparseArray][1] = colIndex;
                    sparseArray[rowIndexOfSparseArray][2] = originalArray[rowIndex][colIndex];
                    rowIndexOfSparseArray++;
                }
            }
        }
        return sparseArray;
    }

    private static int[][] readSparseArray(String saveFileName) throws Exception {
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(new File(SparseArrayDemo.class.getResource(saveFileName).toURI())))){
            Object readObject = oin.readObject();
            return (int[][])readObject;
        }
    }

    /**
     * 保存数组至当前目录下某文件
     * @param originalArray
     * @param saveFileName
     * @throws Exception
     */
    private static void saveSparseArray(int[][] originalArray, String saveFileName) throws Exception {
        System.out.println(SparseArrayDemo.class.getResource(saveFileName).toURI());
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(SparseArrayDemo.class.getResource(saveFileName).toURI())))) {
            oos.writeObject(originalArray);
        }
    }



    public static void printArray(int[][] originalArray) {
        for(int[] row : originalArray){
            for (int e : row){
                System.out.printf("%8d", e);
            }
            System.out.printf("\n");
        }
    }

}
