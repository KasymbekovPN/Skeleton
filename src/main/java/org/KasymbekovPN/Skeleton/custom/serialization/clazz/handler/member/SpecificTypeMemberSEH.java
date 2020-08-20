package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecificTypeMemberSEH extends BaseSEH {

    private final SimpleChecker<Class<?>> clazzChecker;
    private final AnnotationChecker annotationChecker;
    private final Processor<Node> nodeProcessor;
    private final String taskName;
    private final CollectorPath collectorServicePath;

    private String name;
    private String typeName;
    private int modifiers;
    private List<String> membersPath;

    public SpecificTypeMemberSEH(SimpleChecker<Class<?>> clazzChecker,
                                 AnnotationChecker annotationChecker,
                                 Processor<Node> nodeProcessor,
                                 String taskName,
                                 CollectorPath collectorServicePath) {
        this.clazzChecker = clazzChecker;
        this.annotationChecker = annotationChecker;
        this.nodeProcessor = nodeProcessor;
        this.taskName = taskName;
        this.collectorServicePath = collectorServicePath;
    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        Class<?> type = field.getType();
        boolean success = clazzChecker.check(type);
        success &= annotationChecker.check(field.getDeclaredAnnotations(), SkeletonMember.class).isPresent();
        success &= checkCollectorContent(collector);

        Optional<List<String>> mayBeMembersPath = extractMembersPath(collector.getNode());
        success &= mayBeMembersPath.isPresent();

        if (success){
            name = field.getName();
            typeName = type.getCanonicalName();
            modifiers = field.getModifiers();
            membersPath = mayBeMembersPath.get();
        }

        return success;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(membersPath);
        collector.beginObject(name);
        collector.addProperty("custom", false);
        collector.addProperty("type", typeName);
        collector.addProperty("modifiers", modifiers);
        collector.reset();

        return true;
    }

    private boolean checkCollectorContent(Collector collector){

        boolean result = false;
        Optional<Task<Node>> mayBeTask = nodeProcessor.get(taskName);
        if (mayBeTask.isPresent()){
            Task<Node> nodeTask = mayBeTask.get();
            Node rootNode = collector.getNode();
            rootNode.apply(nodeTask);

            result = nodeTask.getResult(ObjectNode.ei()).isSuccess();
        }

        return result;
    }

    private Optional<List<String>> extractMembersPath(Node node){
        if (collectorServicePath.getEi().equals(ArrayNode.ei())){
            Optional<Node> mayBeServicePathNode = node.getChild(collectorServicePath);
            if (mayBeServicePathNode.isPresent()){
                List<String> classPath = new ArrayList<>();
                ArrayNode servicePathNode = (ArrayNode) mayBeServicePathNode.get();
                for (Node pathPart : servicePathNode.getChildren()) {
                    classPath.add(((StringNode)pathPart).getValue());
                }

                return Optional.of(classPath);
            }
        }

        return Optional.empty();
    }
}
