package graphexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lilibo
 * @create 2022-02-02 10:38 AM
 */
public class GraphExample {

    public static void main(String[] args) {
        String[] vertexArray = {"A", "B", "C", "D", "E", "F", "G"};
        //邻接矩阵的关系使用二维数组表示,BLOCK这个大数，表示两个点不联通
        int[][] matrix = new int[][]{
                {0, 5, 7, Graph.BLOCK, Graph.BLOCK, Graph.BLOCK, 2},
                {5, 0, Graph.BLOCK, 9, Graph.BLOCK, Graph.BLOCK, 3},
                {7, Graph.BLOCK, 0, Graph.BLOCK, 8, Graph.BLOCK, Graph.BLOCK},
                {Graph.BLOCK, 9, Graph.BLOCK, 0, Graph.BLOCK, 4, Graph.BLOCK},
                {Graph.BLOCK, Graph.BLOCK, 8, Graph.BLOCK, 0, 5, 4},
                {Graph.BLOCK, Graph.BLOCK, Graph.BLOCK, 4, 5, 0, 6},
                {2, 3, Graph.BLOCK, Graph.BLOCK, 4, 6, 0}};
        Graph graph = new Graph(vertexArray, matrix);
        int startIndex = 6;
        graph.dfs(startIndex);
        graph.bfs(startIndex);
        List<Edge> selectedEdgeList = graph.prim(startIndex);
        int sum = selectedEdgeList.stream().mapToInt(edge -> edge.getWeight()).sum();
        System.out.println("普利姆算法获取的最小生成树边为: " + selectedEdgeList + "\n最小生成树边之和为: " + sum);
        selectedEdgeList = graph.kruskal(startIndex);
        sum = selectedEdgeList.stream().mapToInt(edge -> edge.getWeight()).sum();
        System.out.println("克鲁斯卡尔算法获取的最小生成树边为: " + selectedEdgeList + "\n最小生成树边之和为: " + sum);
        Record record = graph.dijkstra(startIndex);
        int[] disArray = record.getDisArray();
        for(int colIndex = 0; colIndex < vertexArray.length; colIndex++) {
            System.out.printf(vertexArray[startIndex] + "到" + vertexArray[colIndex] + "的最短距离为: " + disArray[colIndex] + "  ");
            if(colIndex == vertexArray.length - 1) {
                System.out.printf("\n");
            }
        }
        FloydResult floydResult = graph.floyd();
        floydResult.print();
    }

}

class Graph {

    public static final int BLOCK = 65535;

    private final String[] vertexArray;

    private final int[][] matrix;

    public Graph(String[] vertexArray, int[][] matrix) {
        this.vertexArray = vertexArray;
        this.matrix = matrix;
    }

    /**
     * 深度优先遍历
     * @param startIndex
     */
    public void dfs(int startIndex) {
        System.out.println("开始深度优先遍历...");
        boolean[] isVisited = new boolean[vertexArray.length];
        for(int i = 0; i < vertexArray.length; i++) {
            if(!isVisited[i]) {
                dfs(startIndex, isVisited);
            }
        }
        System.out.println("深度优先遍历结束...");
    }

    /**
     * 广度优先遍历
     * @param startIndex
     */
    public void bfs(int startIndex) {
        System.out.println("开始广度优先遍历...");
        boolean[] isVisited = new boolean[vertexArray.length];
        bfs(startIndex, isVisited);
        for(int i = 0; i < vertexArray.length; i++) {
            if(!isVisited[i]) {
                bfs(i, isVisited);
            }
        }
        System.out.println("广度优先遍历结束...");
    }

    /**
     * 普利姆算法,最小生成树问题(从点的角度构建最小生成树)
     * @param startIndex
     * @return
     */
    public List<Edge> prim(int startIndex) {
        boolean[] isVisited = new boolean[vertexArray.length];
        isVisited[startIndex] = true;
        List<Edge> selectedEdgeList = new ArrayList<>();
        while (selectedEdgeList.size() < vertexArray.length - 1) { // n个顶点构建n-1条边
            int minWeight = BLOCK;
            int minStartIndex = -1;
            int minEndIndex = -1;
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[i].length; j++) {
                    if((matrix[i][j] != 0 && matrix[i][j] != BLOCK) && (isVisited[i] && !isVisited[j])) { // 注意不构成回路
                        // 选取权重最小的边
                        if(matrix[i][j] < minWeight) {
                            minWeight = matrix[i][j];
                            minStartIndex = i;
                            minEndIndex = j;
                        }
                    }
                }
            }
            if(minWeight != BLOCK) {
                Edge selectedEdge = new Edge(minStartIndex, minEndIndex, minWeight);
                selectedEdgeList.add(selectedEdge);
                isVisited[minEndIndex] = true;
            }
        }
        return selectedEdgeList;
    }

    /**
     * 克鲁斯卡尔算法, 从边的角度构建最小生成树
     * @param startIndex
     */
    public List<Edge> kruskal(int startIndex) {
        List<Edge> allEdgeList = new ArrayList<>();
        for(int i = 0; i < matrix.length; i++) {
            for(int j = i + 1; j < matrix[i].length; j++) {
                if(matrix[i][j] != 0 && matrix[i][j] != BLOCK) {
                    Edge edge = new Edge(i, j, matrix[i][j]);
                    allEdgeList.add(edge);
                }
            }
        }
        // 按边的权值大小进行排序
        allEdgeList.sort((a, b) -> a.getWeight() - b.getWeight());
        Map<Integer, Integer> finalIndexMap = new HashMap<>();
        List<Edge> selectedEdgeList = new ArrayList<>();
        while (selectedEdgeList.size() < vertexArray.length - 1) {
            Edge selectedEdge = null;
            for(Edge edge : allEdgeList) {
                int finalIndexOfStart = getFinalIndex(finalIndexMap, edge.getStartIndex());
                int finalIndexOfEnd = getFinalIndex(finalIndexMap, edge.getEndIndex());
                if(finalIndexOfStart != finalIndexOfEnd) {
                    selectedEdge = edge;
                    finalIndexMap.put(finalIndexOfStart, finalIndexOfEnd);
                    break;
                }
            }
            if(selectedEdge != null) {
                allEdgeList.remove(selectedEdge);
                selectedEdgeList.add(selectedEdge);
            }
        }
        return selectedEdgeList;
    }

    public Record dijkstra(int startIndex) {
        System.out.println("开始迪杰斯特拉算法...");
        Record record = new Record(matrix, startIndex, vertexArray);
        int count = 1;
        while (count < vertexArray.length - 1) {
            int minIndex = getMinIndex(record);
            if(minIndex != -1) {
                record.update(minIndex);
                count++;
            }
        }
        return record;
    }

    public FloydResult floyd() {
        System.out.println("开始弗洛伊德算法...");
        FloydResult floydResult = new FloydResult(matrix, vertexArray);
        int[][] disArray = floydResult.getDisArray();
        int[][] preIndexArray = floydResult.getPreIndexArray();
        for(int k = 0; k < vertexArray.length; k++) {
            for(int i = 0; i < vertexArray.length; i++) {
                for (int j = 0; j < vertexArray.length; j++) {
                    int dis = disArray[i][k] + disArray[k][j];
                    if(dis < disArray[i][j]) {
                        disArray[i][j] = dis;
                        preIndexArray[i][j] = k;
                    }
                }
            }
        }
        return floydResult;
    }

    private int getMinIndex(Record record) {
        int[] disArray = record.getDisArray();
        boolean[] isVisited = record.getIsVisited();
        int minWeight = BLOCK;
        int minIndex = -1;
        for(int colIndex = 0; colIndex < disArray.length; colIndex++) {
            if(!isVisited[colIndex] && disArray[colIndex] < minWeight) {
                minIndex = colIndex;
                minWeight = disArray[colIndex];
            }
        }
        isVisited[minIndex] = true;
        return minIndex;
    }

    /**
     * 获取某1顶点的终点finalIndex
     * @param finalIndexMap
     * @param index
     */
    private int getFinalIndex(Map<Integer, Integer> finalIndexMap, int index) {
        while (finalIndexMap.get(index) != null) {
            index = finalIndexMap.get(index);
        }
        return index;
    }

    private void bfs(int startIndex, boolean[] isVisited) {
        if(isVisited[startIndex]) {
            return;
        }
        System.out.printf(vertexArray[startIndex] + " -> ");
        isVisited[startIndex] = true;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(startIndex);
        while (queue.size() > 0) {
            int head = queue.poll();
            int neighborhood = getFirstNeighborhood(head, isVisited);
            while (neighborhood != -1) {
                if(!isVisited[neighborhood]) {
                    System.out.printf(vertexArray[neighborhood] + " -> ");
                    isVisited[neighborhood] = true;
                    queue.offer(neighborhood);
                }
                neighborhood = getNextNeighborhood(startIndex, neighborhood, isVisited); // 体现广度优先遍历
            }
        }
    }

    private void dfs(int startIndex, boolean[] isVisited) {
        if(isVisited[startIndex]) {
            return;
        }
        System.out.printf(vertexArray[startIndex] + " -> ");
        isVisited[startIndex] = true;
        int neighborhood = getFirstNeighborhood(startIndex, isVisited);
        while (neighborhood != -1) {
            dfs(neighborhood, isVisited); // 递归深度挖掘
            neighborhood = getNextNeighborhood(startIndex, neighborhood, isVisited);
        }
    }

    private int getNextNeighborhood(int startIndex, int neighborhood, boolean[] isVisited) {
        for(int colIndex = neighborhood; colIndex < matrix[startIndex].length; colIndex++) {
            if(matrix[startIndex][colIndex] != 0 && matrix[startIndex][colIndex] != BLOCK && !isVisited[colIndex]) {
                return colIndex;
            }
        }
        return -1;
    }

    private int getFirstNeighborhood(int startIndex, boolean[] isVisited) {
        for(int colIndex = 0; colIndex < matrix[startIndex].length; colIndex++) {
            if(matrix[startIndex][colIndex] != 0 && matrix[startIndex][colIndex] != BLOCK && !isVisited[colIndex]) {
                return colIndex;
            }
        }
        return -1;
    }

}

class Edge {

    private int startIndex;

    private int endIndex;

    private int weight;

    public Edge(int startIndex, int endIndex, int weight) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.weight = weight;
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

    @Override
    public String toString() {
        return "Edge{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", weight=" + weight +
                '}';
    }
}

class Record {

    private final int[] disArray;

    private final boolean[] isVisited;

    private final int[] preIndexArray;

    private final String[] vertexArray;

    private final int[][] matrix;

    public Record(int[][] matrix, int startIndex, String[] vertexArray) {
        isVisited = new boolean[matrix.length];
        isVisited[startIndex] = true;
        disArray = new int[matrix[startIndex].length];
        System.arraycopy(matrix[startIndex], 0, disArray, 0, disArray.length);
        preIndexArray = new int[matrix[startIndex].length];
        Arrays.fill(preIndexArray, startIndex);
        this.vertexArray = vertexArray;
        this.matrix = matrix.clone();
    }

    public void update(int minIndex) {
        for(int colIndex = 0; colIndex < vertexArray.length; colIndex++) {
            if(disArray[minIndex] + matrix[minIndex][colIndex] < disArray[colIndex]) {
                disArray[colIndex] = disArray[minIndex] + matrix[minIndex][colIndex];
                preIndexArray[colIndex] = minIndex;
            }
        }
    }

    public int[] getDisArray() {
        return disArray;
    }

    public boolean[] getIsVisited() {
        return isVisited;
    }

    public int[] getPreIndexArray() {
        return preIndexArray;
    }

    public String[] getVertexArray() {
        return vertexArray;
    }
}

class FloydResult {

    private final int[][] disArray;

    private final int[][] preIndexArray;

    private final String[] vertexArray;

    public FloydResult(int[][] matrix, String[] vertexArray) {
        disArray = matrix.clone();
        preIndexArray = new int[matrix.length][matrix[0].length];
        for(int rowIndex = 0; rowIndex < preIndexArray.length; rowIndex++) {
            Arrays.fill(preIndexArray[rowIndex], rowIndex);
        }
        this.vertexArray = vertexArray;
    }

    public void print() {
        for(int i = 0; i < disArray.length; i++) {
            for (int j = 0; j < disArray[i].length; j++) {
                System.out.printf(vertexArray[i] + "到" + vertexArray[j] + "的最短距离为: " + disArray[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public int[][] getDisArray() {
        return disArray;
    }

    public int[][] getPreIndexArray() {
        return preIndexArray;
    }

}