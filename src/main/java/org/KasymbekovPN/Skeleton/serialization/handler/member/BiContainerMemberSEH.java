package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking.ClassAnnotationCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.utils.containerArgumentChecker.ContainerArgumentChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BiContainerMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(BiContainerMemberSEH.class);

    //< SKEL-30
    private static List<String> PATH = new ArrayList<>(){{add("member");}};

    private final Class<?> specificType;
    private final ContainerArgumentChecker containerArgumentChecker;
    private final AnnotationHandler annotationHandler;
    private final CollectorCheckingProcess collectorCheckingProcess;

    public BiContainerMemberSEH(Class<?> specificType,
                                ContainerArgumentChecker containerArgumentChecker,
                                AnnotationHandler annotationHandler,
                                CollectorCheckingProcess collectorCheckingProcess) {
        this.specificType = specificType;
        this.containerArgumentChecker = containerArgumentChecker;
        this.annotationHandler = annotationHandler;
        this.collectorCheckingProcess = collectorCheckingProcess;
    }

    @Override
    protected boolean runHandlingImplementation(Field field, Collector collector) {

        Class<?> type = field.getType();
        if (type.equals(specificType)){
            String name = field.getName();
            int modifiers = field.getModifiers();
            Annotation[] annotations = field.getDeclaredAnnotations();

            new ClassAnnotationCheckingHandler(modifiers, name, collectorCheckingProcess, ObjectNode.class);

            collector.doIt(collectorCheckingProcess);


//
//            Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
//            Optional<List<Class<?>>> maybeArguments = containerArgumentChecker.check(actualTypeArguments);
//            //<
//            log.info("maybeArguments : {}", maybeArguments);
//            //<
//
//            collectorCheckingHandler.doIt(collector);
//            SkeletonCheckResult collectorCheckingResult = collectorCheckingHandler.getCheckingResult();
//            //<
//            log.info("collectorCheckingHandler : {}", collectorCheckingResult);
//            //<
//
//            Optional<Annotation> maybeAnnotation = annotationHandler.check(annotations);
//            //<
////            annotationHandler.check(annotations);
////            SkeletonCheckResult annotationCheckingResult = annotationHandler.getCheckResult();
//            //<
//            log.info("annotationCheckingResult : {}", maybeAnnotation);
//            //<
//
//            if (maybeArguments.isPresent() &&
//                maybeAnnotation.isPresent() &&
//                collectorCheckingResult.equals(SkeletonCheckResult.INCLUDE)){
//
//                collector.setTarget(PATH);
//                collector.beginObject(name);
//                collector.addProperty("type", type.getCanonicalName());
//                collector.addProperty("modifiers", modifiers);
//                collector.beginArray("arguments");
//
//                for (Class<?> argumentType : maybeArguments.get()) {
//                    collector.addProperty(argumentType.getCanonicalName());
//                }
//
//                collector.reset();
//
//                return true;
//            }
        }



        //<
//        Class<?> type = field.getType();
//        if (type.equals(specificType)){
//            String name = field.getName();
//            int modifiers = field.getModifiers();
//            Annotation[] annotations = field.getDeclaredAnnotations();
//
//            Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
//            Optional<List<Class<?>>> maybeArguments = containerArgumentChecker.check(actualTypeArguments);
//
//            annotationHandler.check(name);
//            annotationHandler.check(modifiers);
//            annotationHandler.check(annotations);
//            annotationHandler.check(collector);
//
//            if (annotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE) &&
//                    maybeArguments.isPresent()){
//
//                annotationHandler.getContainer().addMemberName(name);
//
//                collector.setTarget(annotationHandler.getPath());
//                collector.beginObject(name);
//                collector.addProperty("type", type.getCanonicalName());
//                collector.addProperty("modifiers", modifiers);
//                collector.beginArray("arguments");
//
//                for (Class<?> argumentType : maybeArguments.get()) {
//                    collector.addProperty(argumentType.getCanonicalName());
//                }
//
//                collector.reset();
//
//                return true;
//            }
//        }

        return false;
    }
}
