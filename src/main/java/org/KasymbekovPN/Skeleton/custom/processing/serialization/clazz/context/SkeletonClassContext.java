package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.List;

public class SkeletonClassContext implements ClassContext {

    private final List<String> taskIds;
    private final List<String> wrapperIds;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;
    private final List<String> classPartPath;
    private final List<String> membersPartPath;
    private final Class<?> clazz;
    private final Collector collector;

    public SkeletonClassContext(List<String> taskIds,
                                List<String> wrapperIds,
                                Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                                List<String> classPartPath,
                                List<String> membersPartPath,
                                Class<?> clazz,
                                Collector collector) {
        this.taskIds = taskIds;
        this.wrapperIds = wrapperIds;
        this.annotationExtractor = annotationExtractor;
        this.classPartPath = classPartPath;
        this.membersPartPath = membersPartPath;
        this.clazz = clazz;
        this.collector = collector;
    }

    @Override
    public List<String> getTaskIds() {
        return taskIds;
    }

    @Override
    public List<String> getWrapperIds() {
        return wrapperIds;
    }

    @Override
    public Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> getAnnotationExtractor() {
        return annotationExtractor;
    }

    @Override
    public List<String> getClassPartPath() {
        return classPartPath;
    }

    @Override
    public List<String> getMembersPartPath() {
        return membersPartPath;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public Collector getCollector() {
        return collector;
    }
}
