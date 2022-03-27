package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lilibo
 * @create 2021-08-17 8:12 PM
 */
public class GraphDemo {

    public static void main(String[] args) {
        String[] vertexArray = {"A", "B", "C", "D", "E", "F", "G"};
        //邻接矩阵的关系使用二维数组表示,Graph.BLOCK这个大数，表示两个点不联通
        int[][] matrix = new int[][]{
                {0, 5, 7, Graph.BLOCK, Graph.BLOCK, Graph.BLOCK, 2},
                {5, 0, Graph.BLOCK, 9, Graph.BLOCK, Graph.BLOCK, 3},
                {7, Graph.BLOCK, 0, Graph.BLOCK, 8, Graph.BLOCK, Graph.BLOCK},
                {Graph.BLOCK, 9, Graph.BLOCK, 0, Graph.BLOCK, 4, Graph.BLOCK},
                {Graph.BLOCK, Graph.BLOCK, 8, Graph.BLOCK, 0, 5, 4},
                {Graph.BLOCK, Graph.BLOCK, Graph.BLOCK, 4, 5, 0, 6},
                {2, 3, Graph.BLOCK, Graph.BLOCK, 4, 6, 0}};
        Graph graph = new Graph(vertexArray, matrix);
        int startIndex = 0;
        graph.dfs(startIndex);
        graph.bfs(startIndex);

        // 最小生成树算法
        // 普利姆算法(从点的角度构建最小生成树)
        List<Edge> selectedEdgeList = graph.prim(startIndex);
        printSelectedEdgeList(selectedEdgeList);
        // 克鲁斯卡尔算法(从边的角度构建最小生成树)
        selectedEdgeList = graph.kruskal();
        printSelectedEdgeList(selectedEdgeList);

        // 最短路径算法
        // 迪杰斯特拉算法
        startIndex = 6;
        RecordArray recordArray = graph.dijkstra(startIndex);
        printRecordArray(recordArray);
        // 弗洛伊德算法
        FloydResult floydResult = graph.floyd();
        printFloydResult(floydResult);
    }

    private static void printFloydResult(FloydResult floydResult) {
        System.out.println("弗洛伊德算法结果...");
        int[][] disMatrix = floydResult.getDisMatrix();
        String[] vertexArray = floydResult.getVertexArray();
        for (int i = 0; i < disMatrix.length; i++) {
            for (int j = 0; j < disMatrix[i].length; j++) {
                System.out.printf("从%s到%s的最短路径为%d  ", vertexArray[i], vertexArray[j], disMatrix[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    private static void printRecordArray(RecordArray recordArray) {
        System.out.println("迪杰斯特拉算法结果...");
        int[] disArray = recordArray.getDisArray();
        String[] vertexArray = recordArray.getVertexArray();
        for (int j = 0; j < disArray.length; j++) {
            System.out.printf("从%s到%s的最短距离为: %d  ", vertexArray[recordArray.getStartIndex()], vertexArray[j], disArray[j]);
        }
        System.out.printf("\n");
    }

    private static void printSelectedEdgeList(List<Edge> selectedEdgeList) {
        System.out.println("最小生成树算法结果...");
        for (Edge edge : selectedEdgeList) {
            System.out.printf(edge + "  ");
        }
        System.out.printf("\n");
        int sum = selectedEdgeList.stream().mapToInt(edge -> edge.getWeight()).sum();
        System.out.println("最小生成树权值为: " + sum);
    }

}

class Graph {

    public final static int BLOCK = 65535;

    private String[] vertexArray;

    private int[][] matrix;

    private int numOfVertex;

    public Graph(String[] vertexArray, int[][] matrix) {
        this.vertexArray = vertexArray;
        this.matrix = matrix;
        numOfVertex = vertexArray.length;
    }


    /**
     * 图的深度优先搜索(Depth First Search) 。
     * 1) 深度优先遍历，从初始访问结点出发，初始访问结点可能有多个邻接结点，深度优先遍历的策略就是首先访问第一个邻接结点，
     * 然后再以这个被访问的邻接结点作为初始结点，访问它的第一个邻接结点， 可以这样理解： 每次都在访问完当前结点后首先访问当前结点的第一个邻接结点。
     * 2) 我们可以看到，这样的访问策略是优先往纵向挖掘深入，而不是对一个结点的所有邻接结点进行横向访问。
     * 3) 显然，深度优先搜索是一个递归的过程
     *
     * @param startIndex
     */
    public void dfs(int startIndex) {
        System.out.println("开始深度优先遍历... 初始访问节点: " + vertexArray[startIndex]);
        boolean[] isVisitedArray = new boolean[numOfVertex];
        dfs(startIndex, isVisitedArray);
        for (int i = 0; i < numOfVertex; i++) {
            if (!isVisitedArray[i] && i != startIndex) {
                dfs(i, isVisitedArray);
            }
        }
        System.out.printf("\n");
    }

    /**
     * 图的广度优先搜索(Broad First Search)
     * 类似于一个分层搜索的过程, 广度优先遍历需要使用一个队列以保持访问过的结点的顺序, 以便按这个顺序来访问这些结点的邻接结点
     *
     * @param startIndex
     */
    public void bfs(int startIndex) {
        System.out.println("开始广度优先遍历, 初始节点: " + vertexArray[startIndex]);
        boolean[] isVisited = new boolean[numOfVertex];
        bfs(startIndex, isVisited);
        for (int i = 0; i < numOfVertex; i++) {
            bfs(i, isVisited);
        }
        System.out.printf("\n");
    }

    /**
     * 普利姆算法(从点的角度构建最小生成树，n个顶点具有n-1条边)
     *
     * @param startIndex
     * @return
     */
    public List<Edge> prim(int startIndex) {
        System.out.println("****开始普利姆算法... 初始访问节点: " + vertexArray[startIndex]);
        List<Edge> selectedEdgeList = new ArrayList<>();
        boolean[] isVisited = new boolean[numOfVertex];
        isVisited[startIndex] = true;
        for (int count = 1; count < numOfVertex; count++) { // 所有顶点都被访问过,有n-1条边
            int minWeight = BLOCK;
            Edge selectedEdge = null;
            // 寻找此次权值最小且不构成回路的边
            for (int i = 0; i < numOfVertex; i++) {
                for (int j = 0; j < numOfVertex; j++) {
                    if ((matrix[i][j] < minWeight && matrix[i][j] != 0) && (isVisited[i] && !isVisited[j])) {
                        minWeight = matrix[i][j];
                        selectedEdge = new Edge(i, j, matrix[i][j], vertexArray);
                    }
                }
            }
            if (selectedEdge != null) {
                // 标记已访问
                isVisited[selectedEdge.getEndIndex()] = true;
                selectedEdgeList.add(selectedEdge);
            }
        }
        return selectedEdgeList;
    }

    /**
     * 克鲁斯卡尔算法(以边的角度构建最小生成树)
     * 基本思想: 按照权值按从小到大顺序选择n-1条边,并保证这n-1条边不构成回路
     * 具体做法: 首先构造一个只含n个顶点的森林, 然后按照权值从小到大从联通网中选择边加入到森林中, 并使森林中不产生回路,直到森林变成一颗树为止
     *
     * @return
     */
    public List<Edge> kruskal() {
        System.out.println("****克鲁斯卡尔算法...");
        List<Edge> allEdgeList = new ArrayList<>();
        for (int i = 0; i < numOfVertex; i++) {
            for (int j = i + 1; j < numOfVertex; j++) {
                Edge edge = new Edge(i, j, matrix[i][j], vertexArray);
                allEdgeList.add(edge);
            }
        }
        allEdgeList.sort((a, b) -> a.getWeight() - b.getWeight());
        Map<Integer, Integer> finalIndexMap = new HashMap<>();
        List<Edge> selectedEdgeList = new ArrayList<>();
        while (selectedEdgeList.size() <= numOfVertex - 2) { // 选择n-1条边
            Edge selectedEdge = null;
            for (Edge edge : allEdgeList) {
                int finalIndexOfStart = getFinalIndex(finalIndexMap, edge.getStartIndex());
                int finalIndexOfEnd = getFinalIndex(finalIndexMap, edge.getEndIndex());
                if (finalIndexOfStart != finalIndexOfEnd) {
                    selectedEdge = edge;
                    finalIndexMap.put(finalIndexOfStart, finalIndexOfEnd);
                    break;
                }
            }
            if (selectedEdge != null) {
                selectedEdgeList.add(selectedEdge);
                allEdgeList.remove(selectedEdge);
            }
        }
        return selectedEdgeList;
    }

    /**
     * 最短距离-迪杰斯特拉算法
     * 迪杰斯特拉(Dijkstra)算法是典型最短路径算法, 用于计算一个结点到其他结点的最短路径。 它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想), 直到扩展到终点为止。
     * 1) 设置出发顶点为 v，顶点集合 V{v1,v2,vi...}，v 到 V 中各顶点的距离构成距离集合 Dis，Dis{d1,d2,di...}， Dis 集合记录着 v 到图中各顶点的距离(到自身可以看作 0，v 到 vi 距离对应为 di)
     * 2) 从 Dis 中选择值最小的 di 并移出 Dis 集合，同时移出 V 集合中对应的顶点 vi，此时的 v 到 vi 即为最短路径
     * 3) 更新 Dis 集合，更新规则为：比较 v 到 V 集合中顶点的距离值，与 v 通过 vi 到 V 集合中顶点的距离值，保留 值较小的一个(同时也应该更新顶点的前驱节点为 vi，表明是通过 vi 到达的)
     * 4) 重复执行两步骤，直到最短路径顶点为目标顶点即可结束
     *
     * @param startIndex
     * @return
     */
    public RecordArray dijkstra(int startIndex) {
        System.out.println("开始迪杰斯特拉算法...");
        RecordArray recordArray = new RecordArray(startIndex, matrix, vertexArray);
        int visitCount = 1;
        while (visitCount < numOfVertex) {
            int nextMinIndex = getNextMinIndex(recordArray);
            if (nextMinIndex != -1) {
                recordArray.update(nextMinIndex);
                visitCount++;
            }
        }
        return recordArray;
    }

    /**
     * 最短路径, 弗洛伊德算法
     * 弗洛伊德算法(Floyd)计算图中各个顶点之间的最短路径
     * 迪杰斯特拉算法用于计算图中某一个顶点到其他顶点的最短路径。
     * 弗洛伊德算法 VS 迪杰斯特拉算法：迪杰斯特拉算法通过选定的被访问顶点，求出从出发访问顶点到其他顶点的最短路径；弗洛伊德算法中每一个顶点都是出发访问点，
     * 所以需要将每一个顶点看做被访问顶点，求出从每一个顶点到其他顶点的最短路径。
     * <p>
     * 弗洛伊德算法分析:
     * 1) 设置顶点 vi 到顶点 vk 的最短路径已知为 Lik， 顶点 vk 到 vj 的最短路径已知为 Lkj， 顶点 vi 到 vj 的路径为 Lij， 则 vi 到 vj 的最短路径为：
     * min((Lik+Lkj),Lij)， vk 的取值为图中所有顶点，则可获得 vi 到 vj 的最短路径
     * 2) 至于 vi 到 vk 的最短路径 Lik 或者 vk 到 vj 的最短路径 Lkj， 是以同样的方式获得
     *
     * @return
     */
    public FloydResult floyd() {
        System.out.println("开始弗洛伊德算法...");
        FloydResult floydResult = new FloydResult(matrix, vertexArray);
        int[][] disMatrix = floydResult.getDisMatrix();
        int[][] preMatrix = floydResult.getPreMatrix();
        for (int k = 0; k < numOfVertex; k++) { // k为中间节点
            for (int i = 0; i < numOfVertex; i++) {
                for (int j = 0; j < numOfVertex; j++) {
                    int dis = disMatrix[i][k] + disMatrix[k][j];
                    if (dis < disMatrix[i][j]) {
                        disMatrix[i][j] = dis;
                        preMatrix[i][j] = k;
                    }
                }
            }
        }
        return floydResult;
    }

    private int getNextMinIndex(RecordArray recordArray) {
        int[] disArray = recordArray.getDisArray();
        boolean[] isVisited = recordArray.getIsVisited();
        int minWeight = BLOCK;
        int minIndex = -1;
        for (int j = 0; j < disArray.length; j++) {
            if (!isVisited[j] && (disArray[j] < minWeight && disArray[j] != 0)) {
                minIndex = j;
                minWeight = disArray[j];
            }
        }
        return minIndex;
    }

    private int getFinalIndex(Map<Integer, Integer> finalIndexMap, int index) {
        while (finalIndexMap.get(index) != null) {
            index = finalIndexMap.get(index);
        }
        return index;
    }

    /*
     * 1) 访问初始结点v并标记结点v为已访问。
     * 2) 结点v入队列
     * 3) 当队列非空时，继续执行，否则算法结束(指的是对v算法结束)。
     * 4) 出队列，取得队头结点 u。
     * 5) 查找结点 u 的第一个邻接结点 w。
     * 6) 若结点 u 的邻接结点 w 不存在，则转到步骤 3；否则循环执行以下三个步骤：
     *      6.1 若结点 w 尚未被访问，则访问结点 w 并标记为已访问。
     *      6.2 结点 w 入队列
     *      6.3 查找结点 u 的继 w 邻接结点后的下一个邻接结点 w，转到步骤 6。
     */
    private void bfs(int startIndex, boolean[] isVisited) {
        if (isVisited[startIndex]) {
            return;
        }
        System.out.printf(vertexArray[startIndex] + " -> ");
        isVisited[startIndex] = true;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(startIndex);
        while (queue.size() > 0) {
            int head = queue.poll();
            int neighbor = getFirstNeighbor(head, isVisited);
            while (neighbor != -1) {
                if (!isVisited[neighbor]) {
                    System.out.printf(vertexArray[neighbor] + " -> ");
                    isVisited[neighbor] = true;
                    queue.offer(neighbor);
                }
                // 获取下一邻接节点
                neighbor = getNextNeighbor(head, neighbor, isVisited);
            }
        }
    }

    /*
     * 1) 访问初始结点v, 并标记结点v为已访问。 2) 查找结点v的第一个邻接结点w。3) 若w存在, 则继续执行, 如果w不存在，则回到第1步，将从v的下一个结点继续。
     * 4) 若w未被访问，对w进行深度优先遍历递归（即把w当做另一个v, 然后进行步骤 123。 5) 查找结点v的w邻接结点的下一个邻接结点，转到步骤3。
     */
    private void dfs(int startIndex, boolean[] isVisitedArray) {
        if (isVisitedArray[startIndex]) {
            return;
        }
        System.out.printf(vertexArray[startIndex] + " -> ");
        isVisitedArray[startIndex] = true;
        int neighbor = getFirstNeighbor(startIndex, isVisitedArray);
        while (neighbor != -1) {
            // 以neighbor作为下一次递归的初始节点
            dfs(neighbor, isVisitedArray);
            // 递归出来后以startIndex关于neighbor的下一邻接节点开始继续循环
            neighbor = getNextNeighbor(startIndex, neighbor, isVisitedArray);
        }
    }

    /**
     * 获取startIndex的第一个邻接节点下标
     *
     * @param startIndex
     * @return
     */
    private int getFirstNeighbor(int startIndex, boolean[] isVisitedArray) {
        for (int j = 0; j < matrix[startIndex].length; j++) {
            if (!isVisitedArray[j] && (matrix[startIndex][j] != 0 && matrix[startIndex][j] != BLOCK)) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 获取startIndex关于neighborIndex的下一个邻接节点下标
     *
     * @param startIndex
     * @param neighborIndex
     * @param isVisited
     * @return
     */
    private int getNextNeighbor(int startIndex, int neighborIndex, boolean[] isVisited) {
        for (int j = neighborIndex + 1; j < matrix[startIndex].length; j++) {
            if (!isVisited[j] && (matrix[startIndex][j] != 0 && matrix[startIndex][j] != BLOCK)) {
                return j;
            }
        }
        return -1;
    }

}

class Edge {

    private int startIndex;

    private int endIndex;

    private int weight;

    private String[] vertexArray;

    public Edge(int startIndex, int endIndex, int weight, String[] vertexArray) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.weight = weight;
        this.vertexArray = vertexArray;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String[] getVertexArray() {
        return vertexArray;
    }

    public void setVertexArray(String[] vertexArray) {
        this.vertexArray = vertexArray;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "startPoint=" + vertexArray[startIndex] +
                ", endPoint=" + vertexArray[endIndex] +
                ", weight=" + weight +
                '}';
    }
}

/**
 * 迪杰斯特拉算法记录类
 */
class RecordArray {

    /**
     * 记录startIndex到各顶点距离
     */
    private int[] disArray;

    /**
     * 记录各顶点的前驱节点
     */
    private int[] preArray;

    /**
     * 记录各顶点是否访问
     */
    private boolean[] isVisited;

    private int[][] matrix;

    private int numOfVertex;

    private String[] vertexArray;

    private int startIndex;

    public RecordArray(int startIndex, int[][] matrix, String[] vertexArray) {
        numOfVertex = vertexArray.length;
        this.vertexArray = vertexArray;
        this.startIndex = startIndex;
        this.matrix = matrix;
        disArray = Arrays.copyOf(matrix[startIndex], matrix[startIndex].length);
        isVisited = new boolean[numOfVertex];
        isVisited[startIndex] = true;
        preArray = new int[numOfVertex];
        Arrays.fill(preArray, startIndex);
    }

    public void update(int tempIndex) {
        isVisited[tempIndex] = true;
        for (int j = 0; j < numOfVertex; j++) {
            int dis = disArray[tempIndex] + matrix[tempIndex][j];
            if (dis < disArray[j]) {
                disArray[j] = dis; // 更新距离
                preArray[j] = tempIndex; // 更新前驱节点下标
            }
        }
    }

    public int[] getDisArray() {
        return disArray;
    }

    public void setDisArray(int[] disArray) {
        this.disArray = disArray;
    }

    public int[] getPreArray() {
        return preArray;
    }

    public void setPreArray(int[] preArray) {
        this.preArray = preArray;
    }

    public boolean[] getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean[] isVisited) {
        this.isVisited = isVisited;
    }

    public int getNumOfVertex() {
        return numOfVertex;
    }

    public void setNumOfVertex(int numOfVertex) {
        this.numOfVertex = numOfVertex;
    }

    public String[] getVertexArray() {
        return vertexArray;
    }

    public void setVertexArray(String[] vertexArray) {
        this.vertexArray = vertexArray;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}

class FloydResult {

    private int[][] disMatrix;

    private int[][] preMatrix;

    private String[] vertexArray;

    public FloydResult(int[][] matrix, String[] vertexArray) {
        int numOfVertex = matrix.length;
        disMatrix = new int[numOfVertex][numOfVertex];
        preMatrix = new int[numOfVertex][numOfVertex];
        for (int i = 0; i < matrix.length; i++) {
            disMatrix[i] = Arrays.copyOf(matrix[i], numOfVertex);
            Arrays.fill(preMatrix[i], i);
        }
        this.vertexArray = vertexArray;
    }

    public int[][] getDisMatrix() {
        return disMatrix;
    }

    public void setDisMatrix(int[][] disMatrix) {
        this.disMatrix = disMatrix;
    }

    public int[][] getPreMatrix() {
        return preMatrix;
    }

    public void setPreMatrix(int[][] preMatrix) {
        this.preMatrix = preMatrix;
    }

    public String[] getVertexArray() {
        return vertexArray;
    }

    public void setVertexArray(String[] vertexArray) {
        this.vertexArray = vertexArray;
    }

}