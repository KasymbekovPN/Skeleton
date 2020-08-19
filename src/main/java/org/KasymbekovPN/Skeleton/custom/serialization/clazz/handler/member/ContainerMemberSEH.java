package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContainerMemberSEH extends BaseSEH {

    private final SimpleChecker<Field> fieldChecker;
    private final AnnotationChecker annotationChecker;
    private final Processor<Node> nodeProcessor;
    private final String taskName;

    private String name;
    private String typeName;
    private int modifiers;
    private List<String> argumentTypes;

    public ContainerMemberSEH(SimpleChecker<Field> fieldChecker,
                              AnnotationChecker annotationChecker,
                              Processor<Node> nodeProcessor,
                              String taskName) {
        this.fieldChecker = fieldChecker;
        this.annotationChecker = annotationChecker;
        this.nodeProcessor = nodeProcessor;
        this.taskName = taskName;
    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        boolean success = fieldChecker.check(field);
        success &= annotationChecker.check(field.getDeclaredAnnotations(), SkeletonMember.class).isPresent();
        success &= checkCollectorContent(collector);

        if (success){
            name = field.getName();
            typeName = field.getType().getCanonicalName();
            modifiers = field.getModifiers();
            argumentTypes = new ArrayList<>();

            Type[] arguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            for (Type argument : arguments) {
                argumentTypes.add(((Class<?>) argument).getCanonicalName());
            }
        }

        return success;
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

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()));
        collector.beginObject(name);
        collector.addProperty("custom", false);
        collector.addProperty("type", typeName);
        collector.addProperty("modifiers", modifiers);
        collector.beginArray("arguments");
        for (String argumentType : argumentTypes) {
            collector.addProperty(argumentType);
        }
        collector.reset();

        return true;
    }
}
