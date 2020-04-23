package org.KasymbekovPN.Skeleton.utils;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;

import java.lang.annotation.Annotation;

public class SimpleGeneralCondition implements GeneralCondition {

    private Skeleton annotation;

    @Override
    public boolean check(String name, int modifiers) {
//        boolean include = false;
//        boolean excluse =
//
//        include = (-1 != modifiers) && ((annotation.includeByModifiers() & modifiers) != 0);
//        if (!include){
//            List<String> names = Arrays.asList(annotation.includeByName());
//            include = names.contains(name);
//        }
//
//

        return true;
    }

    @Override
    public void setData(Annotation annotation) {
        this.annotation = (Skeleton) annotation;
    }
}
