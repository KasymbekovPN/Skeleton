package org.KasymbekovPN.Skeleton.utils;

import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;

import java.lang.annotation.Annotation;

public class SimpleClassCondition implements ClassCondition {

    private static final int UNCHECKABLE_MODIFIER = -1;
    private static final String UNCHECKABLE_NAME = "";

    private SkeletonClass annotation;

    @Override
    public boolean check(String name, int modifiers) {

        boolean exclude = checkModifiers(modifiers, annotation.excludeByModifiers());
        exclude |= UNCHECKABLE_NAME.equals(name);
        exclude |= checkName(name, annotation.excludeByName());

        boolean include = false;
        if (!exclude){
            include = checkModifiers(modifiers, annotation.includeByModifiers());
            include |= checkName(name, annotation.includeByName());
        }

        return include;
    }

    @Override
    public void setData(Annotation annotation) {
        this.annotation = (SkeletonClass) annotation;
    }

    private boolean checkModifiers(int modifiers, int annotationModifiers){
        return (UNCHECKABLE_MODIFIER != annotationModifiers) &&
               (0 != (modifiers & annotationModifiers));
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
