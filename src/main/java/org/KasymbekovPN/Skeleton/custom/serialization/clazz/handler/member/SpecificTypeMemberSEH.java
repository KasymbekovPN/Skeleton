package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.filter.Filter;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SpecificTypeMemberSEH extends BaseSEH {

    private final SimpleChecker<Class<?>> clazzChecker;
    private final Filter<Annotation> annotationFilter;
    private final Processor<Node> nodeProcessor;
    private final String taskName;
    private final CollectorPath collectorServicePath;
    private final ClassMembersHandler classMembersHandler;
    private final String kind;

    private String name;
    private String typeName;
    private int modifiers;
    private List<String> membersPath;

    public SpecificTypeMemberSEH(SimpleChecker<Class<?>> clazzChecker,
                                 Filter<Annotation> annotationFilter,
                                 Processor<Node> nodeProcessor,
                                 String taskName,
                                 CollectorPath collectorServicePath,
                                 ClassMembersHandler classMembersHandler,
                                 String kind) {
        this.clazzChecker = clazzChecker;
        this.annotationFilter = annotationFilter;
        this.nodeProcessor = nodeProcessor;
        this.taskName = taskName;
        this.collectorServicePath = collectorServicePath;
        this.classMembersHandler = classMembersHandler;
        this.kind = kind;
    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        Class<?> type = field.getType();
        boolean success = clazzChecker.check(type);
        Deque<Annotation> filteredAnnotations
                = annotationFilter.filter(new ArrayDeque<>(Arrays.asList(field.getDeclaredAnnotations())));
        success &= filteredAnnotations.size() > 0;
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
        List<String> path = new ArrayList<>(membersPath);
        path.add(name);
        ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
        classMembersHandler.setKind(targetNode, kind);
        classMembersHandler.setType(targetNode, typeName);
        classMembersHandler.setClassName(targetNode, typeName);
        classMembersHandler.setModifiers(targetNode, modifiers);
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
