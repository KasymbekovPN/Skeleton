package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker;

import java.util.Map;

public class SkeletonNumberWritingHandlerTestData extends DataChecker {

    public SkeletonNumberWritingHandlerTestData(Map<String, Number> objects) {
        subStringCounters.put("{", 1);
        subStringCounters.put("}", 1);
        subStringCounters.put(":", 0);

        for (Map.Entry<String, Number> entry : objects.entrySet()) {
            subStringCounters.put(":", 1 + subStringCounters.get(":"));
            String valueKey = String.valueOf(entry.getValue());
            subStringCounters.put(
                    valueKey,
                    subStringCounters.containsKey(valueKey) ? subStringCounters.get(valueKey) + 1 : 1
            );
            String name = "\"" + entry.getKey() + "\"";
            subStringCounters.put(
                    name,
                    subStringCounters.containsKey(name) ? subStringCounters.get(name) + 1 : 1
            );
        }
    }
}
