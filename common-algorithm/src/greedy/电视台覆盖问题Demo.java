package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 贪心算法介绍
 * <p>
 * 1) 贪婪算法(贪心算法)是指在对问题进行求解时，在每一步选择中都采取最好或者最优(即最有利)的选择，从而希望能够导致结果是最好或者最优的算法
 * 2) 贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是相对近似(接近)最优解的结果
 * <p>
 * 贪心算法注意事项和细节
 * 1) 贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是相对近似(接近)最优解的结果
 * 2) 比如下题的算法选出的是 K1, K2, K3, K5， 符合覆盖了全部的地区
 * 3) 但是我们发现 K2, K3,K4,K5 也可以覆盖全部地区，如果 K2 的使用成本低于 K1,那么我们上题的 K1, K2, K3, K5 虽然是满足条件，但是并不是最优的.
 *
 * @author lilibo
 * @create 2021-08-30 8:40 PM
 */
public class 电视台覆盖问题Demo {

    public static void main(String[] args) {
        String[] k1Areas = {"北京", "上海", "天津"};
        Set<String> k1AreaSet = new HashSet<>(Arrays.asList(k1Areas));
        String[] k2Areas = {"广州", "北京", "深圳"};
        Set<String> k2AreaSet = new HashSet<>(Arrays.asList(k2Areas));
        String[] k3Areas = {"成都", "上海", "杭州"};
        Set<String> k3AreaSet = new HashSet<>(Arrays.asList(k3Areas));
        String[] k4Areas = {"上海", "天津"};
        Set<String> k4AreaSet = new HashSet<>(Arrays.asList(k4Areas));
        String[] k5Areas = {"杭州", "大连"};
        Set<String> allAreaSet = new HashSet<>();
        Set<String> k5AreaSet = new HashSet<>(Arrays.asList(k5Areas));
        allAreaSet.addAll(k1AreaSet);
        allAreaSet.addAll(k2AreaSet);
        allAreaSet.addAll(k3AreaSet);
        allAreaSet.addAll(k4AreaSet);
        allAreaSet.addAll(k5AreaSet);
        Map<String, Set<String>> broadCastMap = new HashMap<>();
        broadCastMap.put("k1", k1AreaSet);
        broadCastMap.put("k2", k2AreaSet);
        broadCastMap.put("k3", k3AreaSet);
        broadCastMap.put("k4", k4AreaSet);
        broadCastMap.put("k5", k5AreaSet);

        // 开始贪心算法,每一步选择最优的(覆盖地区最多的电台)
        Set<String> noCoverAreaSet = new HashSet<>(allAreaSet);
        Map<String, Set<String>> remainderBroadCastMap = new HashMap<>(broadCastMap);
        List<String> selectBroadCastList = new ArrayList<>();
        while (noCoverAreaSet.size() > 0 && remainderBroadCastMap.size() > 0) {
            String bestBroadCast = null;
            int maxOfCover = 0;
            Set<Map.Entry<String, Set<String>>> broadCastEntryMap = remainderBroadCastMap.entrySet();
            for (Map.Entry<String, Set<String>> broadCastEntry : broadCastEntryMap) {
                Set<String> broadCastSet = broadCastEntry.getValue();
                Set<String> tempNoCoverAreaSet = new HashSet<>(noCoverAreaSet);
                tempNoCoverAreaSet.retainAll(broadCastSet);
                if (tempNoCoverAreaSet.size() > maxOfCover) {
                    maxOfCover = tempNoCoverAreaSet.size();
                    bestBroadCast = broadCastEntry.getKey();
                }
            }
            if (bestBroadCast != null) {
                selectBroadCastList.add(bestBroadCast);
                noCoverAreaSet.removeAll(remainderBroadCastMap.get(bestBroadCast));
                remainderBroadCastMap.remove(bestBroadCast);
            }
        }
        System.out.println("选择的电台为: " + Arrays.toString(selectBroadCastList.toArray()));
    }

}
