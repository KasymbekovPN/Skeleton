package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member;

//<
//import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
//import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
//import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
//import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.ClassAnnotationCheckingHandler;
//import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.ClassExistCheckingHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;
//import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.ContainerArgumentChecker;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;

import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.ClassExistCheckingHandler;
import org.KasymbekovPN.Skeleton.custom.filter.string.AllowedStringFilter;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ContainerMemberSEH extends BaseSEH {

    private final SimpleChecker<Field> fieldChecker;
    private final AnnotationChecker annotationChecker;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private String name;
    private String typeName;
    private int modifiers;
    private List<String> argumentTypes;

    public ContainerMemberSEH(SimpleChecker<Field> fieldChecker,
                              AnnotationChecker annotationChecker,
                              CollectorCheckingHandler collectorCheckingHandler) {
        this.fieldChecker = fieldChecker;
        this.annotationChecker = annotationChecker;
        this.collectorCheckingHandler = collectorCheckingHandler;
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

        String processId = UUID.randomUUID().toString();
        Optional<CollectorCheckingProcess> mayBeProcess = collectorCheckingHandler.add(processId);
        if (mayBeProcess.isPresent()){
            new ClassExistCheckingHandler(
                    mayBeProcess.get(),
                    ObjectNode.ei(),
                    collector.getCollectorStructure().getPath(CollectorStructureEI.classEI())
            );

            Map<String, CollectorCheckingResult> handlingResult
                    = collectorCheckingHandler.handle(collector, new AllowedStringFilter(processId));

            collectorCheckingHandler.remove(processId);

            result = handlingResult.get(processId).equals(CollectorCheckingResult.INCLUDE);
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
