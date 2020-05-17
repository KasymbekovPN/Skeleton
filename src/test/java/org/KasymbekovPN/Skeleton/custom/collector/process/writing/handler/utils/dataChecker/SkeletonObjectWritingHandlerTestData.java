package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkeletonObjectWritingHandlerTestData extends DataChecker {

    public SkeletonObjectWritingHandlerTestData(Set<String> subObjectNames) {
        count(new ArrayList<>(subObjectNames));
    }

    public SkeletonObjectWritingHandlerTestData(Map<String, Set<String>> subObjectNames){
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : subObjectNames.entrySet()) {
            list.add(entry.getKey());
            list.addAll(entry.getValue());
        }

        count(list);
    }

    private void count(List<String> subObjectNames){
        subStringCounters.put("{", 1);
        subStringCounters.put("}", 1);
        subStringCounters.put(":", 0);

        for (String subObjectName : subObjectNames) {
            subStringCounters.put(":", 1 + subStringCounters.get(":"));
            subStringCounters.put("{", 1 + subStringCounters.get("{"));
            subStringCounters.put("}", 1 + subStringCounters.get("}"));

            String name = "\"" + subObjectName + "\"";
            subStringCounters.put(
                    name,
                    subStringCounters.containsKey(name) ? subStringCounters.get(name) + 1 : 1
            );
        }
    }
}
