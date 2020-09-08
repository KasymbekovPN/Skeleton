package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.*;

public class ClassSignatureTaskHandler extends BaseContextTaskHandler {

    private final static String NON_ANNOTATION = "Annotation isn't exist";

    private final ClassHeaderPartHandler classHeaderPartHandler;

    private String type;
    private String name;
    private int modifiers;

    public ClassSignatureTaskHandler(ClassHeaderPartHandler classHeaderPartHandler,
                                     Result result) {
        super(result);

        //< !!! take from context
        this.classHeaderPartHandler = classHeaderPartHandler;
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        ClassContext classContext = (ClassContext) context;
        Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> extractor
                = classContext.getAnnotationExtractor();
        Class<?> clazz = classContext.getClazz();
        Optional<Annotation> maybeExtractionResult
                = extractor.extract(new MutablePair<>(SkeletonClass.class, clazz.getDeclaredAnnotations()));

        if (maybeExtractionResult.isPresent()){
            SkeletonClass annotation = (SkeletonClass) maybeExtractionResult.get();
            name = annotation.name();
            type = clazz.getTypeName();
            modifiers = clazz.getModifiers();
        } else {
            success = false;
            status = NON_ANNOTATION;
        }
    }

    @Override
    protected void doIt(Context context) {
        ClassContext classContext = (ClassContext) context;
        Collector collector = classContext.getCollector();

        ObjectNode targetNode = (ObjectNode) collector.setTarget(classContext.getClassPartPath());
        classHeaderPartHandler.setType(targetNode, type);
        classHeaderPartHandler.setName(targetNode, name);
        classHeaderPartHandler.setModifiers(targetNode, modifiers);
        collector.reset();
    }
}
