package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;

import java.util.Map;
import java.util.Set;

//< !!! replace
public class Utils {

    //del
    public static Collector createCollector() throws Exception {
        return new SkeletonCollector();
    }

    //< del
//    public static CollectorProcess createCollectorWritingProcess(){
//        return new SkeletonCollectorWritingProcess(/*new JsonFormatter()*/);
//    }

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

//    public static void fillCollectorMembersPart(Collector collector, String[] memberNames){
//        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()));
//        for (String memberName : memberNames) {
//            collector.beginObject(memberName);
//            collector.end();
//        }
//        collector.reset();
//    }
//
//    public static void fillCollectorClassPath(Collector collector){
//        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.classEI()));
//    }

    //<
//    public static void fillCollectorAnnotationPart(Collector collector,
//                                                   int includeByModifiers,
//                                                   int excludeByModifiers,
//                                                   String[] includeByName,
//                                                   String[] excludeByName){
//
//        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.annotationEI()));
//
//        collector.addProperty("includeByModifiers", includeByModifiers);
//        collector.addProperty("excludeByModifiers", excludeByModifiers);
//
//        collector.beginArray("includeByName");
//        for (String name : includeByName) {
//            collector.addProperty(name);
//        }
//        collector.end();
//
//        collector.beginArray("excludeByName");
//        for (String name : excludeByName) {
//            collector.addProperty(name);
//        }
//        collector.reset();
//    }
}
