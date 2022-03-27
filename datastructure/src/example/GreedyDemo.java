package example;

import java.util.*;

/**
 * @author lilibo
 * @create 2022-02-05 4:43 PM
 */
public class GreedyDemo {

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
        Set<String> k5AreaSet = new HashSet<>(Arrays.asList(k5Areas));
        Set<String> allAreaSet = new HashSet<>();
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

        // 开始贪心算法,每1步选择最优的
        System.out.println("开始贪心算法...");
        Set<String> noCoverAreaSet = new HashSet<>(allAreaSet);
        Map<String, Set<String>> remainderBroadCastMap = new HashMap<>(broadCastMap);
        List<String> selectedBroadCastList = new ArrayList<>();
        while (noCoverAreaSet.size() > 0 && remainderBroadCastMap.size() > 0) {
            int maxOfCover = 0;
            String bestKey = null;
            Set<Map.Entry<String, Set<String>>> remainderBroadCastEntrySet = remainderBroadCastMap.entrySet();
            for(Map.Entry<String, Set<String>> remainderBroadCastEntry : remainderBroadCastEntrySet) {
                Set<String> broadCastSet = remainderBroadCastEntry.getValue();
                String broadCast = remainderBroadCastEntry.getKey();
                Set<String> tempNoCoverAreaSet = new HashSet<>(noCoverAreaSet);
                tempNoCoverAreaSet.retainAll(broadCastSet);
                if(tempNoCoverAreaSet.size() > maxOfCover) {
                    bestKey = broadCast;
                    maxOfCover = tempNoCoverAreaSet.size();
                }
            }
            if(bestKey!=null) {
                selectedBroadCastList.add(bestKey);
                noCoverAreaSet.removeAll(remainderBroadCastMap.get(bestKey));
                remainderBroadCastMap.remove(bestKey);
            }
        }
        System.out.println("选中的电台为: " + selectedBroadCastList);
    }

}
