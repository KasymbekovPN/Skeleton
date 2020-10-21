package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * For collections and maps
 */
public class ClassContainerTaskHandlerOld extends OldBaseContextTaskHandler<ClassContext> {

    private final SimpleChecker<Field> fieldChecker;

    private Set<Field> fields = new HashSet<>();

    public ClassContainerTaskHandlerOld(String id,
                                        SimpleChecker<Field> fieldChecker) {
        super(id);
        this.fieldChecker = fieldChecker;
    }

    public ClassContainerTaskHandlerOld(String id,
                                        SimpleResult simpleResult,
                                        SimpleChecker<Field> fieldChecker) {
        super(id, simpleResult);
        this.fieldChecker = fieldChecker;
    }

    @Override
    protected void check(ClassContext context) {

        if (context.checkClassPart()){
            Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> extractor
                    = context.getAnnotationExtractor();
            Set<Field> remainingFields = context.getRemainingFields();

            for (Field remainingField : remainingFields) {
                Optional<Annotation> maybeAnnotation
                        = extractor.extract(new MutablePair<>(SkeletonMember.class, remainingField.getDeclaredAnnotations()));
                if (maybeAnnotation.isPresent() && fieldChecker.check(remainingField)){
                    fields.add(remainingField);
                }
            }

            for (Field field : fields) {
                remainingFields.remove(field);
            }
        }
    }

    @Override
    protected void doIt(ClassContext context) {

        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath());

        for (Field field : fields) {
            String name = field.getName();
            String type = field.getType().getCanonicalName();
            int modifiers = field.getModifiers();
            List<String> argumentTypes = extractArgTypeAsString(field);

            path.add(name);

            ObjectNode target = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(target, id);
            classMembersPartHandler.setType(target, type);
            classMembersPartHandler.setClassName(target, type);
            classMembersPartHandler.setModifiers(target, modifiers);
            classMembersPartHandler.setArguments(target, argumentTypes);
            collector.reset();

            path.remove(path.size() - 1);
        }

        fields.clear();
    }

    private List<String> extractArgTypeAsString(Field field) {
        List<String> argumentTypes = new ArrayList<>();
        Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        for (Type type : types) {
            argumentTypes.add(((Class<?>) type).getCanonicalName());
        }

        return argumentTypes;
    }
}
