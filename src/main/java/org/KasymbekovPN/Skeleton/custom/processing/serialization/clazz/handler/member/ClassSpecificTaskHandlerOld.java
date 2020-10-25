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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClassSpecificTaskHandlerOld extends OldBaseContextTaskHandler<OldClassContext> {

    private final static Logger log = LoggerFactory.getLogger(ClassSpecificTaskHandlerOld.class);

    private final SimpleChecker<Class<?>> fieldChecker;

    private Set<Field> specificFields = new HashSet<>();

    public ClassSpecificTaskHandlerOld(String id, SimpleChecker<Class<?>> fieldChecker) {
        super(id);
        this.fieldChecker = fieldChecker;
    }

    public ClassSpecificTaskHandlerOld(String id, SimpleResult simpleResult, SimpleChecker<Class<?>> fieldChecker) {
        super(id, simpleResult);
        this.fieldChecker = fieldChecker;
    }

    @Override
    protected void check(OldClassContext context) {

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
    protected void doIt(OldClassContext context) {

        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath());

        for (Field specificField : specificFields) {
            String name = specificField.getName();
            String type = specificField.getType().getTypeName();
            int modifiers = specificField.getModifiers();

            path.add(name);
            ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(targetNode, id);
            classMembersPartHandler.setType(targetNode, type);
            classMembersPartHandler.setClassName(targetNode, type);
            classMembersPartHandler.setModifiers(targetNode, modifiers);
            collector.reset();

            path.remove(path.size() - 1);
        }

        specificFields.clear();
    }
}
