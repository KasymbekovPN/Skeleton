package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class InstanceSpecificTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(InstanceSpecificTaskHandler.class);

    private static final String IS_EMPTY = "Values by '%s' is empty";

    private Map<String, Object> values;

    public InstanceSpecificTaskHandler(String id) {
        super(id);
    }

    public InstanceSpecificTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContext context) throws ContextStateCareTakerIsEmpty {
        InstanceContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        extractValues(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{}", simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        CollectorPath membersPartPath = context.getMembersCollectorPath();
        Collector collector = context.getCollector();

        ObjectNode target = (ObjectNode) collector.setTarget(membersPartPath.getPath());
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            Optional<Node> maybeNode = convertObjectToNode(value, target);
            if (maybeNode.isPresent()){
                target.addChild(name, maybeNode.get());
            } else {
                log.warn("Wrong value : {}", value);
            }
        }

        collector.reset();
    }

    private void checkValidation(InstanceContextStateMemento memento){
        if (simpleResult.isSuccess() && !memento.getValidationResult().isSuccess()){
            simpleResult.setFailStatus(memento.getValidationResult().getStatus());
        }
    }

    private void extractValues(InstanceContextStateMemento memento){
        if (simpleResult.isSuccess()){
            values = memento.getFieldValues(id);
            if (values.size() == 0){
                simpleResult.setFailStatus(String.format(IS_EMPTY, id));
            }
        }
    }

    // todo: remove to Converter
    private Optional<Node> convertObjectToNode(Object object, ObjectNode parent){
        Class<?> type = object.getClass();
        if (type.equals(String.class)){
            return Optional.of(new StringNode(parent, (String) object));
        } else if (type.equals(Boolean.class)){
            return Optional.of(new BooleanNode(parent, (Boolean) object));
        } else if (type.equals(Character.class)){
            return Optional.of(new CharacterNode(parent, (Character) object));
        } else if (Number.class.isAssignableFrom(type)){
            return Optional.of(new NumberNode(parent, (Number) object));
        }

        return Optional.empty();
    }
}
