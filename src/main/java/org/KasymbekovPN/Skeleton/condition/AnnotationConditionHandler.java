package org.KasymbekovPN.Skeleton.condition;

import org.KasymbekovPN.Skeleton.collector.Collector;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

/**
 * {{@link #getParentPath(Annotation[])}} : if result is present then list must be non-empty
 */
public interface AnnotationConditionHandler {
    default SkeletonCheckResult check(String name) {return SkeletonCheckResult.NONE;}
    default SkeletonCheckResult check(int modifiers) {return SkeletonCheckResult.NONE;}
    default SkeletonCheckResult check(Annotation[] annotations) {return SkeletonCheckResult.NONE;}
    default SkeletonCheckResult check(Collector collector) {return SkeletonCheckResult.NONE;}
    Optional<List<String>> getParentPath(Annotation[] annotations);
    default Annotation getAnnotation() { return null;}
}
