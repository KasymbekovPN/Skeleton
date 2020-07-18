package org.KasymbekovPN.Skeleton.custom.filter.string;

import org.KasymbekovPN.Skeleton.lib.filter.Filter;
import org.KasymbekovPN.Skeleton.lib.filter.string.BaseStringFilter;

import java.util.*;

public class PriorityStringFilter extends BaseStringFilter {

    private Map<String, Integer> priorityMap = new HashMap<>();

    Filter<String> setPriorityString(String string, Integer priority){
        priorityMap.put(string, priority);
        return this;
    }

    @Override
    protected Deque<String> filterImpl(Deque<String> rawDeq) {
        int lowestPriority = calculateLowestPriority();
        Map<Integer, Set<String>> mapForSorting = restructureDeqDataWithClearing(lowestPriority, rawDeq);
        fillDeque(mapForSorting, rawDeq);
        return rawDeq;
    }

    private int calculateLowestPriority(){
        Optional<Integer> mayBeMin = priorityMap.values().stream().max(Comparator.naturalOrder());
        return mayBeMin.map(priority -> priority + 1).orElse(0);
    }

    private Map<Integer, Set<String>> restructureDeqDataWithClearing(int lowestPriority, Deque<String> rawDeq){

        Map<Integer, Set<String>> mapForSorting = new HashMap<>();

        while (rawDeq.size() > 0){
            String string = rawDeq.poll();

            int priority = priorityMap.getOrDefault(string, lowestPriority);
            if (mapForSorting.containsKey(priority)){
                mapForSorting.get(priority).add(string);
            } else {
                mapForSorting.put(priority, new HashSet<>(Collections.singleton(string)));
            }
        }

        return mapForSorting;
    }

    private void fillDeque(Map<Integer, Set<String>> mapForSorting, Deque<String> rawDeq){
        for (Map.Entry<Integer, Set<String>> entry : mapForSorting.entrySet()) {
            Set<String> strings = entry.getValue();
            for (String string : strings) {
                rawDeq.addLast(string);
            }
        }
    }
}
