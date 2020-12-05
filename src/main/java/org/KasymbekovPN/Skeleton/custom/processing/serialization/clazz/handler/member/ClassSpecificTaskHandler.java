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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ClassSpecificTaskHandler extends BaseContextTaskHandler<ClassContext> {

    private static final Logger log = LoggerFactory.getLogger(ClassSpecificTaskHandler.class);

    private static final String NOT_CONTAIN_CLASS_PART = "Not contain class part";
    private static final String NO_ONE_SPECIFIC_FIELD = "No one specific ";

    private final Function<Class<?>, Boolean> fieldChecker;

    private final Set<Field> specificFields = new HashSet<>();

    public ClassSpecificTaskHandler(Function<Class<?>, Boolean> fieldChecker, String id) {
        super(id);
        this.fieldChecker = fieldChecker;
    }

    public ClassSpecificTaskHandler(Function<Class<?>, Boolean> fieldChecker,
                                    String id,
                                    SimpleResult simpleResult) {
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

    @Override
    protected void doIt(ClassContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath().getPath());

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
            specificFields.clear();
            Set<Field> remainingFields = memento.getRemainingFields();
            for (Field field : remainingFields) {
                if (fieldChecker.apply(field.getType())){
                    specificFields.add(field);
                }
            }

            if (specificFields.size() > 0){
                for (Field specificField : specificFields) {
                    remainingFields.remove(specificField);
                }
            } else {
                simpleResult.setFailStatus(NO_ONE_SPECIFIC_FIELD);
            }
        }
    }
}
