package org.KasymbekovPN.Skeleton.condition;

import java.lang.annotation.Annotation;
import java.util.List;

public interface Condition {
    MemberCheckResult checkClass(Annotation[] annotations);
    MemberCheckResult checkMember(String name);
    MemberCheckResult checkMember(int modifiers);
    MemberCheckResult checkMember(Annotation[] annotations);
    List<String> getClassPath();
    List<String> getMemberPath(Annotation[] memberAnnotations);
}
