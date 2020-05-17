package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkeletonArrayWritingHandlerTestData extends DataChecker {

    public SkeletonArrayWritingHandlerTestData(Set<String> subObjectNames) {
        count(new ArrayList<>(subObjectNames), 0);
    }

    public SkeletonArrayWritingHandlerTestData(Map<String, Integer> subObjectNames){
        int noNameCounter = 0;
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : subObjectNames.entrySet()) {
            list.add(entry.getKey());
            noNameCounter += entry.getValue();
        }

        count(list, noNameCounter);
    }

    private void count(List<String> subObjectNames, int noNameCounter){
        subStringCounters.put("{", 1);
        subStringCounters.put("}", 1);
        subStringCounters.put(":", 0);
        subStringCounters.put("[", noNameCounter);
        subStringCounters.put("]", noNameCounter);

        for (String subObjectName : subObjectNames) {
            subStringCounters.put(":", 1 + subStringCounters.get(":"));
            subStringCounters.put("[", 1 + subStringCounters.get("["));
            subStringCounters.put("]", 1 + subStringCounters.get("]"));

            String name = "\"" + subObjectName + "\"";
            subStringCounters.put(
                    name,
                    subStringCounters.containsKey(name) ? subStringCounters.get(name) + 1 : 1
            );
        }
    }
}
