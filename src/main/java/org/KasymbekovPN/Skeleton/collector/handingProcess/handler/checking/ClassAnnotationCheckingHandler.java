package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ClassAnnotationCheckingHandler implements CollectorHandlingProcessHandler {

    private static final Logger log = LoggerFactory.getLogger(ClassAnnotationCheckingHandler.class);
    private static final int UNCHECKABLE_MODIFIERS = -1;
    private static final int INVALID_INTERSECTION = 0;

    //< skel-30
    private static final String ANNOTATION_OBJECT_PROPERTY = "annotation";



    private final String name;
    private final int modifiers;
    private final CollectorCheckingProcess collectorCheckingProcess;

    public ClassAnnotationCheckingHandler(int modifiers,
                                          String name,
                                          CollectorCheckingProcess collectorCheckingProcess,
                                          Class<? extends Node> clazz) {

        this.name = name;
        this.modifiers = modifiers;

        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {
        SkeletonCheckResult result = SkeletonCheckResult.NONE;

        if (node.isObject()){
            ObjectNode objectNode = (ObjectNode) node;
            if (objectNode.containsKey(ANNOTATION_OBJECT_PROPERTY)){
                ObjectNode annotationNode = (ObjectNode) objectNode.getChildren().get(ANNOTATION_OBJECT_PROPERTY);

                int includeByModifiers = extractIntProperty(annotationNode, "includeByModifiers");
                int excludeByModifiers = extractIntProperty(annotationNode, "excludeByModifiers");
                List<String> includeByName = extractStringArrayProperty(annotationNode, "includeByName");
                List<String> excludeByName = extractStringArrayProperty(annotationNode, "excludeByName");

                result = checkByModifiers(modifiers, includeByModifiers, excludeByModifiers);
                if (result.equals(SkeletonCheckResult.NONE)){
                    result = checkByName(name, includeByName, excludeByName);
                }
            }
        }

        collectorCheckingProcess.setResult(result);
    }

    private int extractIntProperty(ObjectNode node, String property){
        int result = -1;

        if (node.containsKey(property)){
            Node child = node.getChildren().get(property);
            if (child.isNumber()){
                result = (int) ((NumberNode) child).getValue();
            }
        }

        return result;
    }

    private List<String> extractStringArrayProperty(ObjectNode node, String property){
        List<String> result = new ArrayList<>();
        if (node.containsKey(property)){
            Node child = node.getChildren().get(property);
            if (child.isArray()){
                for (Node childItem : ((ArrayNode) child).getChildren()) {
                    if (childItem.isString()){
                        result.add(((StringNode) childItem).getValue());
                    }
                }
            }
        }

        return result;
    }

    private SkeletonCheckResult checkByModifiers(int modifiers, int includeModifiers, int excludeModifiers){

        if (UNCHECKABLE_MODIFIERS != excludeModifiers &&
                INVALID_INTERSECTION != (modifiers & excludeModifiers)){
            return SkeletonCheckResult.EXCLUDE;
        } else if (UNCHECKABLE_MODIFIERS != includeModifiers &&
                    INVALID_INTERSECTION != (modifiers & includeModifiers)){
            return SkeletonCheckResult.INCLUDE;
        }

        return SkeletonCheckResult.NONE;
    }

    private SkeletonCheckResult checkByName(String name, List<String> includeNames, List<String> excludeNames)
    {
        if (excludeNames.contains(name)){
            return SkeletonCheckResult.EXCLUDE;
        } else if (includeNames.contains(name)){
            return SkeletonCheckResult.INCLUDE;
        }

        return SkeletonCheckResult.NONE;
    }
}
