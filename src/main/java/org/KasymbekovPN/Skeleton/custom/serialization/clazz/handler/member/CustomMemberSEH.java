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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

public class CustomMemberSEH extends BaseSEH {

    private final SimpleChecker<String> classNameChecker;
    private final AnnotationChecker annotationChecker;
    private final Processor<Node> nodeProcessor;
    private final String taskName;
    //<
//    private final CollectorCheckingHandler collectorCheckingHandler;

    private String name;
    private String typeName;
    private String className;
    private int modifiers;

    public CustomMemberSEH(SimpleChecker<String> classNameChecker,
                           AnnotationChecker annotationChecker,
                           Processor<Node> nodeProcessor,
                           String taskName) {
        this.classNameChecker = classNameChecker;
        this.annotationChecker = annotationChecker;
        this.nodeProcessor = nodeProcessor;
        this.taskName = taskName;
    }

    //<
//    public CustomMemberSEH(SimpleChecker<String> classNameChecker,
//                           AnnotationChecker annotationChecker,
//                           CollectorCheckingHandler collectorCheckingHandler) {
//        this.classNameChecker = classNameChecker;
//        this.annotationChecker = annotationChecker;
//        this.collectorCheckingHandler = collectorCheckingHandler;
//    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        boolean success = false;
        String className = "";
        Optional<Annotation> mayBeAnnotation = annotationChecker.check(field.getDeclaredAnnotations(), SkeletonMember.class);
        if (mayBeAnnotation.isPresent()){
            SkeletonMember annotation = (SkeletonMember) mayBeAnnotation.get();
            className = annotation.name();

            success = classNameChecker.check(className) && checkCollectorContent(collector);
        }

        if (success){
            this.name = field.getName();
            this.typeName = field.getType().getCanonicalName();
            this.modifiers = field.getModifiers();
            this.className = className;
        }

        return success;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()));
        collector.beginObject(name);
        collector.addProperty("custom", true);
        collector.addProperty("type", typeName);
        collector.addProperty("modifiers", modifiers);
        collector.addProperty("className", className);
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

    //<
//    private boolean checkCollectorContent(Collector collector){
//        boolean result = false;
//
//        String processId = UUID.randomUUID().toString();
//        Optional<CollectorCheckingProcess> mayBeProcess = collectorCheckingHandler.add(processId);
//        if (mayBeProcess.isPresent()){
//            new ClassExistCheckingHandler(
//                    mayBeProcess.get(),
//                    ObjectNode.ei(),
//                    collector.getCollectorStructure().getPath(CollectorStructureEI.classEI())
//            );
//
//            Map<String, CollectorCheckingResult> handlingResult
//                    = collectorCheckingHandler.handle(collector, new AllowedStringFilter(processId));
//
//            collectorCheckingHandler.remove(processId);
//
//            result = handlingResult.get(processId).equals(CollectorCheckingResult.INCLUDE);
//        }
//
//        return result;
//    }
}
