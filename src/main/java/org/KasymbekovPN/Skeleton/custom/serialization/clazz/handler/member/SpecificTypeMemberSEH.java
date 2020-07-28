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
//import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.util.Map;
//import java.util.Optional;
//
//public class SpecificTypeMemberSEH extends BaseSEH {
//
//    private static final Logger log = LoggerFactory.getLogger(SpecificTypeMemberSEH.class);
//
//    private static String EXIST_PROCESS = "exist";
//    private static String ANNOTATION_PROCESS = "annotation";
//
//    private final Class<?> specificType;
//    private final AnnotationChecker annotationChecker;
//    private final CollectorCheckingHandler collectorCheckingHandler;
//
//    private String name;
//    private String typeName;
//    private int modifiers;
//
//    public SpecificTypeMemberSEH(Class<?> specificType,
//                                 AnnotationChecker annotationChecker,
//                                 CollectorCheckingHandler collectorCheckingHandler) {
//        this.specificType = specificType;
//        this.annotationChecker = annotationChecker;
//
//        this.collectorCheckingHandler = collectorCheckingHandler;
//        this.collectorCheckingHandler.add(EXIST_PROCESS);
//        this.collectorCheckingHandler.add(ANNOTATION_PROCESS);
//    }
//
//    @Override
//    protected boolean checkData(Field field, Collector collector) {
//
//        boolean result = false;
//
//        Class<?> type = field.getType();
//        if (type.equals(specificType)){
//            Optional<CollectorCheckingProcess> maybeExistProcess = collectorCheckingHandler.get(EXIST_PROCESS);
//            Optional<CollectorCheckingProcess> maybeAnnotationProcess = collectorCheckingHandler.get(ANNOTATION_PROCESS);
//
//            if (maybeExistProcess.isPresent() && maybeAnnotationProcess.isPresent()){
//
//                CollectorCheckingProcess existProcess = maybeExistProcess.get();
//                new ClassExistCheckingHandler(
//                        existProcess,
//                        ObjectNode.ei(),
//                        collector.getCollectorStructure().getPath(CollectorStructureEI.classEI()));
//
//                CollectorCheckingProcess annotationProcess = maybeAnnotationProcess.get();
//                new ClassAnnotationCheckingHandler(
//                        field.getModifiers(),
//                        field.getName(),
//                        annotationProcess,
//                        ObjectNode.ei(),
//                        collector.getCollectorStructure().getPath(CollectorStructureEI.annotationEI()));
//
//                Map<String, CollectorCheckingResult> collectorCheckingResults = collectorCheckingHandler.handle(collector);
//
//                Optional<Annotation> maybeAnnotation = annotationChecker.check(field.getDeclaredAnnotations(), SkeletonMember.class);
//
//                if (collectorCheckingResults.get(EXIST_PROCESS).equals(CollectorCheckingResult.INCLUDE)){
//
//                    if (collectorCheckingResults.get(ANNOTATION_PROCESS).equals(CollectorCheckingResult.INCLUDE) ||
//                            (!collectorCheckingResults.get(ANNOTATION_PROCESS).equals(CollectorCheckingResult.EXCLUDE) &&
//                                    maybeAnnotation.isPresent())){
//                        result = true;
//                        name = field.getName();
//                        typeName = type.getCanonicalName();
//                        modifiers = field.getModifiers();
//                    }
//                }
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    protected boolean fillCollector(Collector collector) {
//        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()));
//        collector.beginObject(name);
//        collector.addProperty("type", typeName);
//        collector.addProperty("modifiers", modifiers);
//        collector.reset();
//
//        return true;
//    }
//}
