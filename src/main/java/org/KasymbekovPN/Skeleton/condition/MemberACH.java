package org.KasymbekovPN.Skeleton.condition;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MemberACH implements AnnotationConditionHandler {

    private final int UNCHECKABLE_MODIFIER = -1;
    private final int INVALID_INTERSECTION = 0;

    private AnnotationConditionHandler parent;

    public MemberACH(AnnotationConditionHandler parent) {
        this.parent = parent;
    }

    @Override
    public MemberCheckResult check(String name) {

        MemberCheckResult result = checkParent();
        if (result.equals(MemberCheckResult.NONE)){
            SkeletonClass parentAnnotation = (SkeletonClass) parent.getAnnotation();
            if (checkName(name, parentAnnotation.excludeByName())){
                result = MemberCheckResult.EXCLUDE;
            }
            if (!result.equals(MemberCheckResult.EXCLUDE) &&
                    checkName(name, parentAnnotation.includeByName())){
                result = MemberCheckResult.INCLUDE;
            }
        }

        return  result;
    }

    @Override
    public MemberCheckResult check(int modifiers) {

        MemberCheckResult result = checkParent();
        if (result.equals(MemberCheckResult.NONE)){
            SkeletonClass parentAnnotation = (SkeletonClass) parent.getAnnotation();
            if (checkModifiers(modifiers, parentAnnotation.excludeByModifiers())){
                result = MemberCheckResult.EXCLUDE;
            }
            if (!result.equals(MemberCheckResult.EXCLUDE) &&
                    checkModifiers(modifiers, parentAnnotation.includeByModifiers())){
                result = MemberCheckResult.INCLUDE;
            }
        }

        return result;
    }

    @Override
    public MemberCheckResult check(Annotation[] annotations) {

        MemberCheckResult result = checkParent();
        if (result.equals(MemberCheckResult.NONE) && extractAnnotation(annotations).isPresent()){
            result = MemberCheckResult.INCLUDE;
        }

        return result;
    }

    @Override
    public Optional<List<String>> getParentPath(Annotation[] annotations) {
        Optional<SkeletonMember> maybeAnnotation = extractAnnotation(annotations);
        if (maybeAnnotation.isPresent() && maybeAnnotation.get().memberParent().length != 0){
            return Optional.of(Arrays.asList(maybeAnnotation.get().memberParent()));
        }

        if (checkParent().equals(MemberCheckResult.NONE)){
            SkeletonClass parentAnnotation = (SkeletonClass) parent.getAnnotation();
            if (parentAnnotation.memberParent().length != 0){
                return Optional.of(Arrays.asList(parentAnnotation.memberParent()));
            }
        }

        return Optional.empty();
    }

    private Optional<SkeletonMember> extractAnnotation(Annotation[] annotations){
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkeletonMember.class)){
                return Optional.of((SkeletonMember) annotation);
            }
        }
        return Optional.empty();
    }

    private boolean checkModifiers(int modifiers, int annotationModifiers){
        return (UNCHECKABLE_MODIFIER != annotationModifiers) &&
                (INVALID_INTERSECTION != (annotationModifiers & modifiers));
    }

    private boolean checkName(String name, String[] names){
        for (String namesItem : names) {
            if (namesItem.equals(name)){
                return true;
            }
        }
        return false;
    }

    private MemberCheckResult checkParent(){
        return null == parent ? MemberCheckResult.EXCLUDE : MemberCheckResult.NONE;
    }
}
