package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClassCustomTaskHandler extends BaseContextTaskHandler {

    private final SimpleChecker<String> classNameChecker;
    private final String kind;

    private Set<Pair<String, Field>> data = new HashSet<>();

    public ClassCustomTaskHandler(SimpleChecker<String> classNameChecker,
                                  String kind,
                                  Result result) {
        super(result);
        this.classNameChecker = classNameChecker;
        this.kind = kind;
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        ClassContext classContext = (ClassContext) context;

        if (classContext.checkClassPart()){
            Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> extractor
                    = classContext.getAnnotationExtractor();
            Set<Field> remainingFields = classContext.getRemainingFields();

            for (Field remainingField : remainingFields) {
                Optional<Annotation> maybeAnnotation = extractor.extract(
                        new MutablePair<>(SkeletonMember.class, remainingField.getDeclaredAnnotations())
                );
                if (maybeAnnotation.isPresent()){
                    SkeletonMember annotation = (SkeletonMember) maybeAnnotation.get();
                    String className = annotation.name();
                    if (classNameChecker.check(className)){
                        success = true;

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
    protected void fillCollector(Context context) {
        ClassContext classContext = (ClassContext) context;

        Collector collector = classContext.getCollector();
        ClassMembersPartHandler classMembersPartHandler = classContext.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(classContext.getMembersPartPath());

        for (Pair<String, Field> datum : data) {
            String className = datum.getLeft();
            Field field = datum.getRight();
            String name = field.getName();
            String type = field.getType().getTypeName();
            int modifiers = field.getModifiers();

            path.add(name);

            ObjectNode target = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(target, kind);
            classMembersPartHandler.setType(target, type);
            classMembersPartHandler.setClassName(target, className);
            classMembersPartHandler.setModifiers(target, modifiers);
            collector.reset();

            path.remove(path.size() - 1);
        }

        data.clear();
    }
}
