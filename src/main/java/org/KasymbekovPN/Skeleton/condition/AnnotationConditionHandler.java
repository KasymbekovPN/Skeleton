package org.KasymbekovPN.Skeleton.condition;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

/**
 * {{@link #getParentPath(Annotation[])}} : if result is present then list must be non-empty
 */
public interface AnnotationConditionHandler {
    default MemberCheckResult check(String name) {return MemberCheckResult.NONE;};
    default MemberCheckResult check(int modifiers) {return MemberCheckResult.NONE;};
    default MemberCheckResult check(Annotation[] annotations) {return MemberCheckResult.NONE;};
    Optional<List<String>> getParentPath(Annotation[] annotations);
    default Annotation getAnnotation() { return null;};
}
