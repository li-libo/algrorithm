package recursion;

/**
 * @author lilibo
 * @create 2022-01-28 12:18 AM
 */
public class Queue {

    private final int numOfQueue;

    private final int[] positionArray;

    private int resolveCount = 0;

    private int judgeConflictCount = 0;

    private int conflictCount = 0;

    public static final String format1 = "resolveCount = %s, judgeConflictCount = %s, conflictCount = %s";

    public static void main(String[] args) {
        Queue queue = new Queue(8);
        queue.putQueue(0);
        System.out.printf(format1, queue.resolveCount, queue.judgeConflictCount, queue.conflictCount);
    }

    public Queue(int numOfQueue) {
        this.numOfQueue = numOfQueue;
        positionArray = new int[numOfQueue];
    }

    public void putQueue(int indexOfQueue) {
        if(indexOfQueue == numOfQueue) { // 当放置到numOfQueue时,已经放置完毕
            resolveCount++;
            return;
        }
        for(int colIndex = 0; colIndex < numOfQueue; ) {
            positionArray[indexOfQueue] = colIndex;
            if(!judgeConflict(indexOfQueue)) {
                // 无冲突继续放置后续皇后
                putQueue(indexOfQueue + 1);
            }
            // 冲突或者放置后续皇后完毕继续下1列
            colIndex++;
        }
    }

    private boolean judgeConflict(int indexOfQueue) {
        judgeConflictCount++;
        int curPosition = positionArray[indexOfQueue];
        // 判断第indexOfQueue个皇后与前indexOfQueue-1个皇后是否冲突
        for(int numOfQueue = 0; numOfQueue < indexOfQueue; numOfQueue++) {
            if (curPosition == positionArray[numOfQueue] || Math.abs(curPosition - positionArray[numOfQueue]) == Math.abs(indexOfQueue - numOfQueue)) {
                conflictCount++;
                return true;
            }
        }
        return false;
    }
}
