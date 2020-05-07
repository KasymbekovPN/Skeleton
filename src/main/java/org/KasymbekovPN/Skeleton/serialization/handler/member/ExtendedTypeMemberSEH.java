package org.KasymbekovPN.Skeleton.serialization.handler.member;

import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking.ClassAnnotationCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking.ClassExistCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.serialization.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExtendedTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(ExtendedTypeMemberSEH.class);

    //< skel-30
    private static List<String> PATH = new ArrayList<>(){{add("member");}};
    private static String EXIST_PROCESS = "exist";
    private static String ANNOTATION_PROCESS = "annotation";

    private final Class<?> extendableType;
    private final AnnotationHandler annotationHandler;
    private final CollectorCheckingHandler collectorCheckingHandler;

    private String name;
    private String typeName;
    private int modifiers;

    public ExtendedTypeMemberSEH(Class<?> extendableType,
                                 AnnotationHandler annotationHandler,
                                 CollectorCheckingHandler collectorCheckingHandler) {
        this.extendableType = extendableType;
        this.annotationHandler = annotationHandler;

        this.collectorCheckingHandler = collectorCheckingHandler;
        this.collectorCheckingHandler.add(EXIST_PROCESS);
        this.collectorCheckingHandler.add(ANNOTATION_PROCESS);
    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        boolean result = false;

        Class<?> type = field.getType();
        if (extendableType.isAssignableFrom(type)) {

            Optional<CollectorCheckingProcess> maybeExistProcess = collectorCheckingHandler.get(EXIST_PROCESS);
            Optional<CollectorCheckingProcess> maybeAnnotationProcess = collectorCheckingHandler.get(ANNOTATION_PROCESS);

            if (maybeAnnotationProcess.isPresent() && maybeExistProcess.isPresent()){

                CollectorCheckingProcess existProcess = maybeExistProcess.get();
                new ClassExistCheckingHandler(existProcess, ObjectNode.class);

                CollectorCheckingProcess annotationProcess = maybeAnnotationProcess.get();
                new ClassAnnotationCheckingHandler(field.getModifiers(), field.getName(), annotationProcess, ObjectNode.class);

                Map<String, SkeletonCheckResult> collectorCheckingResults = collectorCheckingHandler.handle(collector, true);

                Optional<Annotation> maybeAnnotation = annotationHandler.check(field.getDeclaredAnnotations(), SkeletonMember.class);

                if (collectorCheckingResults.get(EXIST_PROCESS).equals(SkeletonCheckResult.INCLUDE)){

                    if (collectorCheckingResults.get(ANNOTATION_PROCESS).equals(SkeletonCheckResult.INCLUDE) ||
                            (!collectorCheckingResults.get(ANNOTATION_PROCESS).equals(SkeletonCheckResult.EXCLUDE) &&
                                    maybeAnnotation.isPresent())) {

                        result = true;
                        name = field.getName();
                        typeName = type.getCanonicalName();
                        modifiers = field.getModifiers();
                    }
                }
            }
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(PATH);
        collector.beginObject(name);
        collector.addProperty("type", typeName);
        collector.addProperty("modifiers", modifiers);
        collector.reset();

        return true;
    }
}