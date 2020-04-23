package org.KasymbekovPN.Skeleton.utils;

import java.lang.annotation.Annotation;

public interface GeneralCondition {
    boolean check(String name, int modifiers);
    void setData(Annotation annotation);
}
