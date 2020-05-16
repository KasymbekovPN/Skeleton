package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SkeletonClassAnnotationCheckingHandler implements CollectorProcessHandler {

    private static final Logger log = LoggerFactory.getLogger(SkeletonClassAnnotationCheckingHandler.class);
    private static final int UNCHECKABLE_MODIFIERS = -1;
    private static final int INVALID_INTERSECTION = 0;

    private final String name;
    private final int modifiers;
    private final CollectorCheckingProcess collectorCheckingProcess;
    private final Class<? extends Node> clazz;
    private final List<String> path;

    public SkeletonClassAnnotationCheckingHandler(int modifiers,
                                                  String name,
                                                  CollectorCheckingProcess collectorCheckingProcess,
                                                  Class<? extends Node> clazz,
                                                  List<String> path) {

        this.name = name;
        this.modifiers = modifiers;
        this.clazz = clazz;
        this.path = path;
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {
        CollectorCheckingResult result = CollectorCheckingResult.NONE;

        Map<String, CollectorCheckingResult> results = new HashMap<>();

        if (node.isObject()){
            SkeletonObjectNode skeletonObjectNode = (SkeletonObjectNode) node;
            Optional<Node> maybeChild = skeletonObjectNode.getChild(path, SkeletonObjectNode.class);

            if (maybeChild.isPresent()){
                SkeletonObjectNode annotationNode = (SkeletonObjectNode) maybeChild.get();

                int includeByModifiers = extractIntProperty(annotationNode, "includeByModifiers");
                int excludeByModifiers = extractIntProperty(annotationNode, "excludeByModifiers");
                List<String> includeByName = extractStringArrayProperty(annotationNode, "includeByName");
                List<String> excludeByName = extractStringArrayProperty(annotationNode, "excludeByName");

                results.put("modifiers", checkByModifiers(modifiers, includeByModifiers, excludeByModifiers));
                results.put("name", checkByName(name, includeByName, excludeByName));
            }
        }

        if (results.containsValue(CollectorCheckingResult.EXCLUDE)){
            result = CollectorCheckingResult.EXCLUDE;
        } else if (results.containsValue(CollectorCheckingResult.INCLUDE)){
            result = CollectorCheckingResult.INCLUDE;
        }

        collectorCheckingProcess.setResult(clazz, result);
    }

    private int extractIntProperty(SkeletonObjectNode node, String property){
        int result = -1;

        if (node.containsKey(property)){
            Node child = node.getChildren().get(property);
            if (child.isNumber()){
                result = (int) ((SkeletonNumberNodeSkeleton) child).getValue();
            }
        }

        return result;
    }

    private List<String> extractStringArrayProperty(SkeletonObjectNode node, String property){
        List<String> result = new ArrayList<>();
        if (node.containsKey(property)){
            Node child = node.getChildren().get(property);
            if (child.isArray()){
                for (Node childItem : ((SkeletonArrayNode) child).getChildren()) {
                    if (childItem.isString()){
                        result.add(((SkeletonStringNodeSkeleton) childItem).getValue());
                    }
                }
            }
        }

        return result;
    }

    private CollectorCheckingResult checkByModifiers(int modifiers, int includeModifiers, int excludeModifiers){
        if (UNCHECKABLE_MODIFIERS != excludeModifiers &&
                INVALID_INTERSECTION != (modifiers & excludeModifiers)){
            return CollectorCheckingResult.EXCLUDE;
        } else if (UNCHECKABLE_MODIFIERS != includeModifiers &&
                    INVALID_INTERSECTION != (modifiers & includeModifiers)){
            return CollectorCheckingResult.INCLUDE;
        }

        return CollectorCheckingResult.NONE;
    }

    private CollectorCheckingResult checkByName(String name, List<String> includeNames, List<String> excludeNames) {
        if (excludeNames.contains(name)){
            return CollectorCheckingResult.EXCLUDE;
        } else if (includeNames.contains(name)){
            return CollectorCheckingResult.INCLUDE;
        }

        return CollectorCheckingResult.NONE;
    }
}
