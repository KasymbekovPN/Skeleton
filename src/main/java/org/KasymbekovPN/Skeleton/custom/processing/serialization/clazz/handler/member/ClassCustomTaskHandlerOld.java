package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.OldClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClassCustomTaskHandlerOld extends OldBaseContextTaskHandler<OldClassContext> {

    private final SimpleChecker<String> classNameChecker;

    private Set<Pair<String, Field>> data = new HashSet<>();

    public ClassCustomTaskHandlerOld(String id, SimpleChecker<String> classNameChecker) {
        super(id);
        this.classNameChecker = classNameChecker;
    }

    public ClassCustomTaskHandlerOld(String id, SimpleResult simpleResult, SimpleChecker<String> classNameChecker) {
        super(id, simpleResult);
        this.classNameChecker = classNameChecker;
    }

    @Override
    protected void check(OldClassContext context) {

        if (context.checkClassPart()){
            Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> extractor
                    = context.getAnnotationExtractor();
            Set<Field> remainingFields = context.getRemainingFields();

            for (Field remainingField : remainingFields) {
                Optional<Annotation> maybeAnnotation = extractor.extract(
                        new MutablePair<>(SkeletonMember.class, remainingField.getDeclaredAnnotations())
                );
                if (maybeAnnotation.isPresent()){
                    SkeletonMember annotation = (SkeletonMember) maybeAnnotation.get();
                    String className = annotation.name();
                    if (classNameChecker.check(className)){
                        simpleResult.setSuccess(true);
                        data.add(
                                new MutablePair<>(className, remainingField)
                        );
                    }
                }
            }

            for (Pair<String, Field> datum : data) {
                remainingFields.remove(datum.getRight());
            }
        }
    }

    @Override
    protected void doIt(OldClassContext context) {

        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath());

        for (Pair<String, Field> datum : data) {
            String className = datum.getLeft();
            Field field = datum.getRight();
            String name = field.getName();
            String type = field.getType().getTypeName();
            int modifiers = field.getModifiers();

            path.add(name);

            ObjectNode target = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(target, id);
            classMembersPartHandler.setType(target, type);
            classMembersPartHandler.setClassName(target, className);
            classMembersPartHandler.setModifiers(target, modifiers);
            collector.reset();

            path.remove(path.size() - 1);
        }

        data.clear();
    }
}
