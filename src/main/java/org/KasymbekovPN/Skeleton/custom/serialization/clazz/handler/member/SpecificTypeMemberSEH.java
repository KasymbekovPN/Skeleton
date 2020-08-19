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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Optional;

public class SpecificTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SpecificTypeMemberSEH.class);

    private String name;
    private String typeName;
    private int modifiers;

    private final SimpleChecker<Class<?>> clazzChecker;
    private final AnnotationChecker annotationChecker;
    private final Processor<Node> nodeProcessor;
    private final String taskName;

    public SpecificTypeMemberSEH(SimpleChecker<Class<?>> clazzChecker,
                                 AnnotationChecker annotationChecker,
                                 Processor<Node> nodeProcessor,
                                 String taskName) {
        this.clazzChecker = clazzChecker;
        this.annotationChecker = annotationChecker;
        this.nodeProcessor = nodeProcessor;
        this.taskName = taskName;
    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        Class<?> type = field.getType();
        boolean success = clazzChecker.check(type);
        success &= annotationChecker.check(field.getDeclaredAnnotations(), SkeletonMember.class).isPresent();
        success &= checkCollectorContent(collector);

        if (success){
            name = field.getName();
            typeName = type.getCanonicalName();
            modifiers = field.getModifiers();
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
        collector.reset();

        return true;
    }
}
