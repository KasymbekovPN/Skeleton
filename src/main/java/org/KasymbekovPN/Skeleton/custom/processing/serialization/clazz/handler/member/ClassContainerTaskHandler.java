package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;

/**
 * For collections and maps
 */
public class ClassContainerTaskHandler extends BaseContextTaskHandler<ClassContext> {

    private static final Logger log = LoggerFactory.getLogger(ClassContainerTaskHandler.class);

    private static final String NOT_CONTAIN_CLASS_PART = "Not contain class part";
    private static final String NO_ONE_CONTAINER_FIELD = "No one container field";

    private final Function<Field, Boolean> fieldChecker;

    private final Set<Field> containerFields = new HashSet<>();

    public ClassContainerTaskHandler(Function<Field, Boolean> fieldChecker, String id) {
        super(id);
        this.fieldChecker = fieldChecker;
    }

    public ClassContainerTaskHandler(Function<Field, Boolean> fieldChecker, String id, SimpleResult simpleResult) {
        super(id, simpleResult);
        this.fieldChecker = fieldChecker;
    }

    @Override
    protected void check(ClassContext context) throws ContextStateCareTakerIsEmpty {
        ClassContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        checkClassPart(context);
        extractSpecificFields(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{}", simpleResult.getStatus());
        }
    }

    private void checkValidation(ClassContextStateMemento memento){
        SimpleResult validationResult = memento.getValidationResult();
        if (simpleResult.isSuccess() && !validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    private void checkClassPart(ClassContext context){
        if (simpleResult.isSuccess()){
            Optional<Node> maybeClassPart
                    = context.getCollector().getNode().getChild(context.getClassPartPath());
            if (maybeClassPart.isEmpty()){
                simpleResult.setFailStatus(NOT_CONTAIN_CLASS_PART);
            }
        }
    }

    private void extractSpecificFields(ClassContextStateMemento memento){
        if (simpleResult.isSuccess()){
            containerFields.clear();
            Set<Field> remainingFields = memento.getRemainingFields();
            for (Field field : remainingFields) {
                if (fieldChecker.apply(field)){
                    containerFields.add(field);
                }
            }

            if (containerFields.size() > 0){
                for (Field field : containerFields) {
                    remainingFields.remove(field);
                }
            } else {
                simpleResult.setFailStatus(NO_ONE_CONTAINER_FIELD);
            }
        }
    }

    @Override
    protected void doIt(ClassContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath().getPath());

        for (Field field : containerFields) {
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
