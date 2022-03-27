package recursion;

/**
 * @author lilibo
 * @create 2020-09-26 8:22 PM
 */
public class EightQueue {

    // 皇后的个数
    private int numOfQueue;

    // 记录皇后摆放位置的一维数组,下标表示第几个皇后,对应的值为该皇后摆放到第几列
    private int[] array;

    // 摆放解法次数
    private int resolveCount;

    // 判断冲突次数
    private int judgeCount;

    public EightQueue(int numOfQueue) {
        this.numOfQueue = numOfQueue;
        array = new int[numOfQueue];
    }

    public static void main(String[] args) {
        EightQueue queue = new EightQueue(8);
        queue.putQueue(0);
        System.out.printf("8皇后问题的解法有%d,判断冲突次数为%d", queue.getResolveCount(), queue.getJudgeCount());
    }

    public void putQueue(int indexOfQueue) {
        if (indexOfQueue == numOfQueue) { //说明0-numOfQueue-1个皇后已然摆好
            resolveCount++;
            return;
        }
        for (int colIndex = 0; colIndex < numOfQueue; ) {
            array[indexOfQueue] = colIndex;
            if (!judgeConflict(indexOfQueue)) {
                //如果不冲突继续放indexOfQueue+1个皇后
                putQueue(indexOfQueue + 1);
            }
            //冲突了或放置完毕继续换列重试
            colIndex++;
        }
    }

    private boolean judgeConflict(int curIndexQueue) {
        judgeCount++;
        int curColIndex = array[curIndexQueue];
        //分析同一行不用比较,需比较皇后是否在同一列或对角线上
        for (int indexQueue = 0; indexQueue < curIndexQueue; indexQueue++) {
            if (array[indexQueue] == curColIndex || Math.abs(curIndexQueue - indexQueue) == Math.abs(curColIndex - array[indexQueue])) {
                return true;
            }
        }
        return false;
    }

    public int getResolveCount() {
        return resolveCount;
    }

    public int getJudgeCount() {
        return judgeCount;
    }
}
