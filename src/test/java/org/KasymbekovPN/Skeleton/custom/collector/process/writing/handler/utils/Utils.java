package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils;

import org.KasymbekovPN.Skeleton.custom.format.collector.SkeletonCollectorStructure;
import org.KasymbekovPN.Skeleton.custom.format.writing.SkeletonJsonFormatter;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonCollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;

import java.util.Map;
import java.util.Set;

public class Utils {

    public static Collector createCollector() throws Exception {
        CollectorStructure collectorStructure = new SkeletonCollectorStructure.Builder()
                .setClassPath("class")
                .setMembersPath("members")
                .setAnnotationPath("annotation")
                .setConstructorPath("constructors")
                .setMethodPath("methods")
                .setProtocolPath("protocol")
                .build();

        return new SkeletonCollector(collectorStructure, false);
    }

    public static CollectorWritingProcess createCollectorWritingProcess(){
        return new SkeletonCollectorWritingProcess(new SkeletonJsonFormatter());
    }

    public static void fillCollectorWithObjectLevel0(Collector collector, Set<String> subObjectNames){
        for (String subObjectName : subObjectNames) {
            collector.beginObject(subObjectName);
            collector.end();
        }
    }

    public static void fillCollectorWithObjectLevel1(Collector collector, Map<String, Set<String>> subObjectNames){
        for (Map.Entry<String, Set<String>> entry : subObjectNames.entrySet()) {
            collector.beginObject(entry.getKey());
            for (String sub : entry.getValue()) {
                collector.beginObject(sub);
                collector.end();
            }
            collector.end();
        }
    }

    public static void fillCollectorWithArrayLevel0(Collector collector, Set<String> subArrayNames){
        for (String subArrayName : subArrayNames) {
            collector.beginArray(subArrayName);
            collector.end();
        }
    }

    public static void fillCollectorWithArrayLevel1(Collector collector, Map<String, Integer> subArrayNames){
        for (Map.Entry<String, Integer> entry : subArrayNames.entrySet()) {
            collector.beginArray(entry.getKey());
            for (int i = 0; i < entry.getValue(); i++) {
                collector.beginArray();
                collector.end();
            }
            collector.end();
        }
    }

    public static void fillCollectorWithBoolean(Collector collector, Map<String, Boolean> objects){
        for (Map.Entry<String, Boolean> entry : objects.entrySet()) {
            collector.addProperty(entry.getKey(), entry.getValue());
        }
    }

    public static void fillCollectorWithCharacter(Collector collector, Map<String, Character> objects){
        for (Map.Entry<String, Character> entry : objects.entrySet()) {
            collector.addProperty(entry.getKey(), entry.getValue());
        }
    }

    public static void fillCollectorWithNumber(Collector collector, Map<String, Number> objects){
        for (Map.Entry<String, Number> entry : objects.entrySet()) {
            collector.addProperty(entry.getKey(), entry.getValue());
        }
    }

    public static void fillCollectorWithString(Collector collector, Map<String, String> objects){
        for (Map.Entry<String, String> entry : objects.entrySet()) {
            collector.addProperty(entry.getKey(), entry.getValue());
        }
    }
}
