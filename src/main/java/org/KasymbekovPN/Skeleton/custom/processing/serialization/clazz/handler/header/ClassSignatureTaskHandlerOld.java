package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.OldClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ClassSignatureTaskHandlerOld extends OldBaseContextTaskHandler<OldClassContext> {

    private final static String NON_ANNOTATION = "Annotation isn't exist";

    private final ClassHeaderPartHandler classHeaderPartHandler;

    private String type;
    private String name;
    private int modifiers;

    public ClassSignatureTaskHandlerOld(String id, ClassHeaderPartHandler classHeaderPartHandler) {
        super(id);
        this.classHeaderPartHandler = classHeaderPartHandler;
    }

    public ClassSignatureTaskHandlerOld(String id, SimpleResult simpleResult, ClassHeaderPartHandler classHeaderPartHandler) {
        super(id, simpleResult);
        this.classHeaderPartHandler = classHeaderPartHandler;
    }

    @Override
    protected void check(OldClassContext context) {
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
    protected void doIt(OldClassContext context) {
        Collector collector = context.getCollector();

        ObjectNode targetNode = (ObjectNode) collector.setTarget(context.getClassPartPath());
        classHeaderPartHandler.setType(targetNode, type);
        classHeaderPartHandler.setName(targetNode, name);
        classHeaderPartHandler.setModifiers(targetNode, modifiers);
        collector.reset();
    }
}
