package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClassSpecificTaskHandler extends BaseContextTaskHandler<ClassContext> {

    private final static Logger log = LoggerFactory.getLogger(ClassSpecificTaskHandler.class);

    private final SimpleChecker<Class<?>> fieldChecker;
    private final String kind;

    private Set<Field> specificFields = new HashSet<>();

    public ClassSpecificTaskHandler(SimpleChecker<Class<?>> fieldChecker,
                                    String kind,
                                    SimpleResult simpleResult) {
        super(simpleResult);
        this.fieldChecker = fieldChecker;
        this.kind = kind;
    }

    @Override
    protected void check(ClassContext context, Task<ClassContext> task) {

        if (context.checkClassPart()){
            Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> extractor
                    = context.getAnnotationExtractor();
            Set<Field> remainingFields = context.getRemainingFields();

            for (Field remainingField : remainingFields) {
                Class<?> fieldType = remainingField.getType();
                boolean checkingResult = fieldChecker.check(fieldType);
                checkingResult &= extractor.extract(
                        new MutablePair<>(SkeletonMember.class, remainingField.getDeclaredAnnotations())
                ).isPresent();
                if (checkingResult){
                    simpleResult.setSuccess(true);

                    specificFields.add(remainingField);
                }
            }

            for (Field specificField : specificFields) {
                remainingFields.remove(specificField);
            }
        }
    }

    @Override
    protected void doIt(ClassContext context) {

        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath());

        for (Field specificField : specificFields) {
            String name = specificField.getName();
            String type = specificField.getType().getTypeName();
            int modifiers = specificField.getModifiers();

            path.add(name);
            ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(targetNode, kind);
            classMembersPartHandler.setType(targetNode, type);
            classMembersPartHandler.setClassName(targetNode, type);
            classMembersPartHandler.setModifiers(targetNode, modifiers);
            collector.reset();

            path.remove(path.size() - 1);
        }

        specificFields.clear();
    }
}
