package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class DataChecker {
    protected Map<String, Integer> subStringCounters = new HashMap<>();

    public boolean check(String line){
        for (Map.Entry<String, Integer> entry : subStringCounters.entrySet()) {
            if (!subStringCounters.get(entry.getKey()).equals(StringUtils.countMatches(line, entry.getKey()))){
                return false;
            }
        }
        return true;
    }
}
