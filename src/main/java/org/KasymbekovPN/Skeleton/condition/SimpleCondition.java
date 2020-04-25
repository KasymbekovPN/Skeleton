package org.KasymbekovPN.Skeleton.condition;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleCondition implements Condition {

    private final int UNCHECKABLE_MODIFIER = -1;
    private final int INVALID_INTERSECTION = 0;
    private final static Class<? extends Annotation> MEMBER_ANNOTATION_TYPE = SkeletonMember.class;
    private final static Class<? extends Annotation> CLASS_ANNOTATION_TYPE = SkeletonClass.class;

    private SkeletonClass classAnnotation;

    @Override
    public MemberCheckResult checkClass(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(CLASS_ANNOTATION_TYPE)){
                classAnnotation = (SkeletonClass) annotation;
                return MemberCheckResult.INCLUDE;
            }
        }

        return MemberCheckResult.EXCLUDE;
    }

    @Override
    public MemberCheckResult checkMember(String name) {
        MemberCheckResult result = MemberCheckResult.NONE;

        if (null != classAnnotation){
            if (checkName(name, classAnnotation.excludeByName())){
                result = MemberCheckResult.EXCLUDE;
            }
            if (!result.equals(MemberCheckResult.EXCLUDE) &&
                checkName(name, classAnnotation.includeByName())){
                result = MemberCheckResult.INCLUDE;
            }
        }
        return  result;
    }

    @Override
    public MemberCheckResult checkMember(int modifiers) {
        MemberCheckResult result = MemberCheckResult.NONE;

        if (null != classAnnotation){
            if (checkModifiers(modifiers, classAnnotation.excludeByModifiers())){
                result = MemberCheckResult.EXCLUDE;
            }
            if (!result.equals(MemberCheckResult.EXCLUDE) &&
                    checkModifiers(modifiers, classAnnotation.includeByModifiers())){
                result = MemberCheckResult.INCLUDE;
            }
        }
        return result;
    }

    @Override
    public MemberCheckResult checkMember(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(MEMBER_ANNOTATION_TYPE)){
                return MemberCheckResult.INCLUDE;
            }
        }

        return MemberCheckResult.NONE;
    }

    @Override
    public List<String> getClassPath() {
        String[] parent = null != classAnnotation ? classAnnotation.parent() : new String[0];
        return Arrays.asList(parent);
    }

    @Override
    public List<String> getMemberPath(Annotation[] memberAnnotations) {
        for (Annotation memberAnnotation : memberAnnotations) {
            if (memberAnnotation.annotationType().equals(MEMBER_ANNOTATION_TYPE)){
                return Arrays.asList(((SkeletonMember) memberAnnotation).parent());
            }
        }

        return new ArrayList<>();
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
}
