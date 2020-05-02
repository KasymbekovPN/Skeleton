package org.KasymbekovPN.Skeleton.annotation.handler.member;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.annotation.handlerContainer.AnnotationHandlerContainer;
import org.KasymbekovPN.Skeleton.annotation.handlerContainer.Entity;

import java.lang.annotation.Annotation;
import java.util.*;

public class MemberAH implements AnnotationHandler {

    private final int UNCHECKABLE_MODIFIER = -1;
    private final int INVALID_INTERSECTION = 0;
    private final AnnotationHandlerContainer annotationHandlerContainer;

    private SkeletonMember annotation;
    private List<String> path;

    private Map<Checking, SkeletonCheckResult> checkingResults = new HashMap<>();
    private SkeletonCheckResult pathChecking;

    public MemberAH(AnnotationHandlerContainer annotationHandlerContainer) {
        this.annotationHandlerContainer = annotationHandlerContainer;
//        this.annotationHandlerContainer.setMemberAnnotationHandler(this);
        //<
        this.annotationHandlerContainer.setAnnotationHandler(Entity.MEMBER, this);
        resetCheckResult();
    }

    @Override
    public void check(String name) {
//        AnnotationHandler classAnnotationHandler = annotationHandlerContainer.getClassAnnotationHandler();
        //<
        Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
        if (maybeAH.isPresent()){
            AnnotationHandler classAnnotationHandler = maybeAH.get();
            if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
                SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
                List<String> excludeByName = Arrays.asList(classAnnotation.excludeByName());
                List<String> includeByName = Arrays.asList(classAnnotation.includeByName());
                if (excludeByName.contains(name)){
                    checkingResults.put(Checking.NAME, SkeletonCheckResult.EXCLUDE);
                } else if (includeByName.contains(name)){
                    checkingResults.put(Checking.NAME, SkeletonCheckResult.INCLUDE);
                }
            }
        }
    }

    @Override
    public void check(int modifiers) {

//        AnnotationHandler classAnnotationHandler = annotationHandlerContainer.getClassAnnotationHandler();
        //<
        Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
        if (maybeAH.isPresent()){
            AnnotationHandler classAnnotationHandler = maybeAH.get();
            if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
                SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
                int excludeModifiers = classAnnotation.excludeByModifiers();
                int includeModifiers = classAnnotation.includeByModifiers();
                if (checkModifiers(modifiers, excludeModifiers)){
                    checkingResults.put(Checking.MODIFIERS, SkeletonCheckResult.EXCLUDE);
                } else if (checkModifiers(modifiers, includeModifiers)) {
                    checkingResults.put(Checking.MODIFIERS, SkeletonCheckResult.INCLUDE);
                }
            }
        }
    }

    @Override
    public void check(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonMember.class)){
                checkingResults.put(Checking.ANNOTATIONS, SkeletonCheckResult.INCLUDE);
                this.annotation = (SkeletonMember) annotation;
            }
        }
    }

    @Override
    public SkeletonCheckResult getCheckResult() {
        extractPath();
        if (pathChecking.equals(SkeletonCheckResult.INCLUDE)){
            return !checkingResults.containsValue(SkeletonCheckResult.EXCLUDE)
                    && checkingResults.containsValue(SkeletonCheckResult.INCLUDE)
                    ? SkeletonCheckResult.INCLUDE
                    : SkeletonCheckResult.EXCLUDE;
        }

        return SkeletonCheckResult.EXCLUDE;
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

    @Override
    public AnnotationHandlerContainer getContainer() {
        return annotationHandlerContainer;
    }

    private boolean checkModifiers(int modifiers, int annotationModifiers) {
        return (UNCHECKABLE_MODIFIER != annotationModifiers) &&
                (INVALID_INTERSECTION != (annotationModifiers & modifiers));
    }

    private void extractPath(){

        if (checkingResults.get(Checking.ANNOTATIONS).equals(SkeletonCheckResult.INCLUDE)){
            if (annotation.memberParent().length != 0){
                path = Arrays.asList(annotation.memberParent());
                pathChecking = SkeletonCheckResult.INCLUDE;
            }
        }

        if (!pathChecking.equals(SkeletonCheckResult.INCLUDE)){
//            AnnotationHandler classAnnotationHandler = annotationHandlerContainer.getClassAnnotationHandler();
            //<
            Optional<AnnotationHandler> maybeAH = annotationHandlerContainer.getAnnotationHandler(Entity.CLASS);
            if (maybeAH.isPresent()){
                AnnotationHandler classAnnotationHandler = maybeAH.get();
                if (classAnnotationHandler.getCheckResult().equals(SkeletonCheckResult.INCLUDE)){
                    SkeletonClass classAnnotation = (SkeletonClass) classAnnotationHandler.getAnnotation();
                    if (classAnnotation.memberParent().length != 0){
                        path = Arrays.asList(classAnnotation.memberParent());
                        pathChecking = SkeletonCheckResult.INCLUDE;
                    }
                }
            }
        }
    }

    private enum Checking {
        NAME,
        MODIFIERS,
        ANNOTATIONS;
    }
}
