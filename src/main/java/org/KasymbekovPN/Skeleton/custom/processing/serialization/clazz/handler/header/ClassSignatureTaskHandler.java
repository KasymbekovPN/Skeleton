package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ClassSignatureTaskHandler extends BaseContextTaskHandler<ClassContext> {

    private final static String NON_ANNOTATION = "Annotation isn't exist";

    private final ClassHeaderPartHandler classHeaderPartHandler;

    private String type;
    private String name;
    private int modifiers;

    public ClassSignatureTaskHandler(ClassHeaderPartHandler classHeaderPartHandler) {
        this.classHeaderPartHandler = classHeaderPartHandler;
    }

    public ClassSignatureTaskHandler(ClassHeaderPartHandler classHeaderPartHandler,
                                     SimpleResult simpleResult) {
        super(simpleResult);

        this.classHeaderPartHandler = classHeaderPartHandler;
    }

    @Override
    protected void check(ClassContext context, Task<ClassContext> task) {
        Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> extractor
                = context.getAnnotationExtractor();
        Class<?> clazz = context.getClazz();
        Optional<Annotation> maybeExtractionResult
                = extractor.extract(new MutablePair<>(SkeletonClass.class, clazz.getDeclaredAnnotations()));

        if (maybeExtractionResult.isPresent()){
            SkeletonClass annotation = (SkeletonClass) maybeExtractionResult.get();
            name = annotation.name();
            type = clazz.getTypeName();
            modifiers = clazz.getModifiers();
        } else {
            simpleResult.setStatus(NON_ANNOTATION);
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(ClassContext context) {
        Collector collector = context.getCollector();

        ObjectNode targetNode = (ObjectNode) collector.setTarget(context.getClassPartPath());
        classHeaderPartHandler.setType(targetNode, type);
        classHeaderPartHandler.setName(targetNode, name);
        classHeaderPartHandler.setModifiers(targetNode, modifiers);
        collector.reset();
    }
}
