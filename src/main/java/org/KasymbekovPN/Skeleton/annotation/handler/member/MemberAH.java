package org.KasymbekovPN.Skeleton.annotation.handler.member;

import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//<
//import org.KasymbekovPN.Skeleton.annotation.handlerContainer.AnnotationHandlerContainer;
//<

public class MemberAH implements AnnotationHandler {

    private final int UNCHECKABLE_MODIFIER = -1;
    private final int INVALID_INTERSECTION = 0;
    //<
//    private final AnnotationHandlerContainer annotationHandlerContainer;
    //<
//    private final CollectorCheckingProcess classCollectorCheckingProcess;
//    private final CollectorCheckingProcess nameCollectorCheckingProcess;
//    private final CollectorCheckingProcess modifierCollectorCheckingProcess;

    private SkeletonMember annotation;
    private List<String> path;

    private Map<Checking, SkeletonCheckResult> checkingResults = new HashMap<>();
    private SkeletonCheckResult pathChecking;

    public MemberAH() {
        resetCheckResult();
    }

    //<
//    public MemberAH(CollectorCheckingProcess classCollectorCheckingProcess,
//                    CollectorCheckingProcess nameCollectorCheckingProcess,
//                    CollectorCheckingProcess modifierCollectorCheckingProcess) {
//        this.classCollectorCheckingProcess = classCollectorCheckingProcess;
//        this.nameCollectorCheckingProcess = nameCollectorCheckingProcess;
//        this.modifierCollectorCheckingProcess = modifierCollectorCheckingProcess;
//        resetCheckResult();
//    }

    //<
    //    public MemberAH(CollectorChecker classCollectorChecker) {
//        this.classCollectorChecker = classCollectorChecker;
//        resetCheckResult();
//    }

    //    public MemberAH(AnnotationHandlerContainer annotationHandlerContainer, CollectorChecker collectorChecker) {
//        this.collectorChecker = collectorChecker;
////        this.annotationHandlerContainer = annotationHandlerContainer;
////        this.annotationHandlerContainer.setMemberAnnotationHandler(this);
//        //<
////        this.annotationHandlerContainer.setAnnotationHandler(Entity.MEMBER, this);
//        resetCheckResult();
//    }

    //<
//    @Override
//    public void check(String name) {
////        AnnotationHandler classAnnotationHandler = annotationHandlerContainer.getClassAnnotationHandler();
//        //<
//        Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
//        if (maybeAH.isPresent()){
//            AnnotationHandler classAnnotationHandler = maybeAH.get();
//            if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
//                SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
//                List<String> excludeByName = Arrays.asList(classAnnotation.excludeByName());
//                List<String> includeByName = Arrays.asList(classAnnotation.includeByName());
//                if (excludeByName.contains(name)){
//                    checkingResults.put(Checking.NAME, SkeletonCheckResult.EXCLUDE);
//                } else if (includeByName.contains(name)){
//                    checkingResults.put(Checking.NAME, SkeletonCheckResult.INCLUDE);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void check(int modifiers) {
//
////        AnnotationHandler classAnnotationHandler = annotationHandlerContainer.getClassAnnotationHandler();
//        //<
//        Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
//        if (maybeAH.isPresent()){
//            AnnotationHandler classAnnotationHandler = maybeAH.get();
//            if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
//                SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
//                int excludeModifiers = classAnnotation.excludeByModifiers();
//                int includeModifiers = classAnnotation.includeByModifiers();
//                if (checkModifiers(modifiers, excludeModifiers)){
//                    checkingResults.put(Checking.MODIFIERS, SkeletonCheckResult.EXCLUDE);
//                } else if (checkModifiers(modifiers, includeModifiers)) {
//                    checkingResults.put(Checking.MODIFIERS, SkeletonCheckResult.INCLUDE);
//                }
//            }
//        }
//    }

    @Override
    public Optional<Annotation> check(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonMember.class)){
                checkingResults.put(Checking.ANNOTATIONS, SkeletonCheckResult.INCLUDE);
                this.annotation = (SkeletonMember) annotation;

                return Optional.of(this.annotation);
            }
        }

        return Optional.empty();
    }

    //<
//    @Override
//    public void check(Collector collector) {
//        collector.doIt(classCollectorCheckingProcess);
//        checkingResults.put(Checking.COLLECTOR, classCollectorCheckingProcess.getResult());
//    }

    @Override
    public SkeletonCheckResult getCheckResult() {
//        extractPath();
        //<
        System.out.println(checkingResults);
        //<
//        if (pathChecking.equals(SkeletonCheckResult.INCLUDE)){
        //<
            return !checkingResults.containsValue(SkeletonCheckResult.EXCLUDE)
                    && checkingResults.containsValue(SkeletonCheckResult.INCLUDE)
                    ? SkeletonCheckResult.INCLUDE
                    : SkeletonCheckResult.EXCLUDE;
//        }

//        return SkeletonCheckResult.EXCLUDE;
    }

    @Override
    public void resetCheckResult() {
        for (Checking checking : Checking.values()) {
            checkingResults.put(checking, SkeletonCheckResult.NONE);
        }
        pathChecking = SkeletonCheckResult.NONE;
    }

    @Override
    public List<String> getPath() {
        return path;
    }

    //<
//    @Override
//    public AnnotationHandlerContainer getContainer() {
//        return annotationHandlerContainer;
//    }

    private boolean checkModifiers(int modifiers, int annotationModifiers) {
        return (UNCHECKABLE_MODIFIER != annotationModifiers) &&
                (INVALID_INTERSECTION != (annotationModifiers & modifiers));
    }

    private void extractPath(){

        //<
//        if (checkingResults.get(Checking.ANNOTATIONS).equals(SkeletonCheckResult.INCLUDE)){
//            if (annotation.memberParent().length != 0){
//                path = Arrays.asList(annotation.memberParent());
//                pathChecking = SkeletonCheckResult.INCLUDE;
//            }
//        }
//
//        if (!pathChecking.equals(SkeletonCheckResult.INCLUDE)){
////            AnnotationHandler classAnnotationHandler = annotationHandlerContainer.getClassAnnotationHandler();
//            //<
//            Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
//            if (maybeAH.isPresent()){
//                AnnotationHandler classAnnotationHandler = maybeAH.get();
//                if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
//                    SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
//                    if (classAnnotation.memberParent().length != 0){
//                        path = Arrays.asList(classAnnotation.memberParent());
//                        pathChecking = SkeletonCheckResult.INCLUDE;
//                    }
//                }
//            }
//        }
    }

    private enum Checking {
        NAME,
        MODIFIERS,
        ANNOTATIONS,
        COLLECTOR;
    }
}
