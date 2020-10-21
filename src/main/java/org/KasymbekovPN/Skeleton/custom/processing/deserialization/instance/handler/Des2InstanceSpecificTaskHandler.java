package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceSpecificTaskHandler extends BaseContextTaskHandler<Des2InstanceCxt> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceSpecificTaskHandler.class);

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceSpecificTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceSpecificTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        checkValid(context);
        getMembers(context);
        getInstance(context);

        if (!simpleResult.isSuccess()){
            log.warn("{}", simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            Node memberNode = member.getMiddle();

            Optional<Object> maybeValue = extractValue(memberNode);
            if (maybeValue.isPresent()){
                fillField(field, instance, maybeValue.get());
            } else {
                log.warn("{}: Member '{}' has wrong type", id, field.getName());
            }
        }
    }

    private void checkValid(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        SimpleResult validationResult = context.getContextStateCareTaker().peek().getValidationResult();
        if (!validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    private void getMembers(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        if (simpleResult.isSuccess()){
            members = context.getContextStateCareTaker().peek().getMembersData(id);
            if (members.size() == 0){
                simpleResult.setFailStatus("Number of members are zero");
            }
        }
    }

    private void getInstance(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        if (simpleResult.isSuccess()){
            instance = context.getContextStateCareTaker().peek().getInstance();
        }
    }

    private Optional<Object> extractValue(Node node) {
        if (node.is(BooleanNode.ei())) {
            return Optional.of(((BooleanNode) node).getValue());
        } else if (node.is(CharacterNode.ei())) {
            return Optional.of(((CharacterNode) node).getValue());
        } else if (node.is(NumberNode.ei())) {
            return Optional.of(((NumberNode) node).getValue());
        } else if (node.is(StringNode.ei())){
            return Optional.of(((StringNode) node).getValue());
        }

        return Optional.empty();
    }

    private void fillField(Field field, Object instance, Object value){
        field.setAccessible(true);
        try{
            field.set(instance, value);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
    }
}
