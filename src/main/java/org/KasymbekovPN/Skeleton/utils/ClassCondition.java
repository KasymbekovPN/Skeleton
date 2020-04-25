package org.KasymbekovPN.Skeleton.utils;

import java.lang.annotation.Annotation;

public interface ClassCondition {
    boolean check(String name, int modifiers);

    //<  del setData
    void setData(Annotation annotation);
}
