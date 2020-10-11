package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceSpecificTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

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
    protected void check(Des2InstanceContext context) {
        String status = "";
        if (context.isValid()){
            members = context.getMembers(id);
            if (members.size() > 0){
                instance = context.getInstance();
            } else {
                status = "Number of member is zero";
            }
        } else {
            status = "Context isn't valid";
        }

        if (!status.isEmpty()){
            simpleResult.setSuccess(false);
            simpleResult.setStatus(status);

            log.warn(status);
        }
    }

    @Override
    protected void doIt(Des2InstanceContext context) {

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            Node memberNode = member.getMiddle();

            Optional<Object> maybeValue = extractValue(memberNode);
            if (maybeValue.isPresent()){
                field.setAccessible(true);
                try {
                    field.set(instance, maybeValue.get());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            } else {
                log.warn("{}: Member '{}' has wrong type", id, field.getName());
            }
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
}
