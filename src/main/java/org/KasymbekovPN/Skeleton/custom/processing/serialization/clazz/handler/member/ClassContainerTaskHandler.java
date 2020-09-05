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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * For collections and maps
 */
public class ClassContainerTaskHandler extends BaseContextTaskHandler {

    private final SimpleChecker<Field> fieldChecker;
    private final String kind;

    private Set<Field> fields = new HashSet<>();

    public ClassContainerTaskHandler(SimpleChecker<Field> fieldChecker,
                                     String kind,
                                     Result result) {
        super(result);
        this.fieldChecker = fieldChecker;
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
                boolean fieldCheckingResult = fieldChecker.check(remainingField);
                Optional<Annotation> maybeAnnotation
                        = extractor.extract(new MutablePair<>(SkeletonMember.class, remainingField.getDeclaredAnnotations()));

                if (fieldCheckingResult && maybeAnnotation.isPresent()){
                    fields.add(remainingField);
                }
            }

            for (Field field : fields) {
                remainingFields.remove(field);
            }
        }
    }

    @Override
    protected void fillCollector(Context context) {
        ClassContext classContext = (ClassContext) context;

        Collector collector = classContext.getCollector();
        ClassMembersPartHandler classMembersPartHandler = classContext.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(classContext.getMembersPartPath());

        for (Field field : fields) {
            String name = field.getName();
            String type = field.getType().getCanonicalName();
            int modifiers = field.getModifiers();
            List<String> argumentTypes = extractArgTypeAsString(field);

            path.add(name);

            ObjectNode target = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(target, kind);
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
