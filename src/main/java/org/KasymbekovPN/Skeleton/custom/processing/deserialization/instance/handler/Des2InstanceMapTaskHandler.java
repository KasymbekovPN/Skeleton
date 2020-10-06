package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceMapTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceMapTaskHandler.class);

    private final String kind;

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceMapTaskHandler(SimpleResult simpleResult,
                                      String kind) {
        super(simpleResult);
        this.kind = kind;
    }

    @Override
    protected void check(Des2InstanceContext context, Task<Des2InstanceContext> task) {
        checkContextValidity(context);
        getMembers(context);
        getInstance(context);

        if (!simpleResult.isSuccess()){
            log.warn(simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(Des2InstanceContext context) {
        OptionalConverter<Map<Object, Object>, ObjectNode> strType2MapConverter = context.getStrType2MapConverter();

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            String fieldName = field.getName();
            Node memberNode = member.getMiddle();
            ObjectNode classMember = member.getRight();
            
            Optional<Map<Object, Object>> maybeMap = strType2MapConverter.convert(classMember);
            if (maybeMap.isPresent()){
                if (memberNode.is(ArrayNode.ei())){
                    Map<Object, Object> map = maybeMap.get();
                    ArrayNode arrayNode = (ArrayNode) memberNode;

                    fillMap(map, arrayNode, context);
                    setField(field, map);
                } else {
                    log.warn("{} : memberNode has wrong type - isn't ArrayNode", fieldName);
                }
            } else {
                log.warn("{} : failure attempt of map creation", fieldName);
            }
        }
    }

    private void checkContextValidity(Des2InstanceContext context){
        if (simpleResult.isSuccess()){
            if (!context.isValid()){
                simpleResult.setSuccess(false);
                simpleResult.setStatus("Context isn't valid");
            }
        }
    }

    private void getMembers(Des2InstanceContext context){
        if (simpleResult.isSuccess()){
            members = context.getMembers(kind);
            if (members.size() == 0){
                simpleResult.setSuccess(false);
                simpleResult.setStatus("Number of members is zero");
            }
        }
    }

    private void getInstance(Des2InstanceContext context){
        if (simpleResult.isSuccess()){
            instance = context.getInstance();
            if (instance == null){
                simpleResult.setSuccess(false);
                simpleResult.setStatus("Instance is null");
            }
        }
    }

    private void fillMap(Map<Object, Object> map, ArrayNode arrayNode, Des2InstanceContext context){
        for (Node child : arrayNode.getChildren()) {
            Optional<ObjectNode> maybeObjectNode = checkChildNode(child);
            if (maybeObjectNode.isPresent()){
                Map<String, Node> children = maybeObjectNode.get().getChildren();
                Optional<Object> value = extract(children.get("value"), context);
                Optional<Object> key = extract(children.get("key"), context);
                if (key.isPresent() && value.isPresent()){
                    map.put(key.get(), value.get());
                }
            }
        }
    }

    private Optional<ObjectNode> checkChildNode(Node node){
        if (node.is(ObjectNode.ei())){
            ObjectNode objectNode = (ObjectNode) node;
            if (objectNode.getChildren().containsKey("key") && objectNode.getChildren().containsKey("value")){
                return Optional.of(objectNode);
            } else {
                log.warn("Child node doesn't contain some mandatory child");
            }
        } else {
            log.warn("Child node has wrong type : {}", node);
        }

        return Optional.empty();
    }

    private Optional<Object> extract(Node node, Des2InstanceContext context){
        if (node.is(BooleanNode.ei())){
            return Optional.of(((BooleanNode) node).getValue());
        } else if (node.is(CharacterNode.ei())){
            return Optional.of(((CharacterNode) node).getValue());
        } else if (node.is(NumberNode.ei())){
            return Optional.of(((NumberNode) node).getValue());
        } else if (node.is(StringNode.ei())){
            return Optional.of(((StringNode) node).getValue());
        } else if (node.is(ObjectNode.ei())){

            OptionalConverter<Object, ObjectNode> toInstanceConverter = context.getToInstanceConverter();
            Optional<Object> maybeInstance = toInstanceConverter.convert((ObjectNode) node);
            if (maybeInstance.isPresent()){
                Object instance = maybeInstance.get();
                context.push(instance, (ObjectNode) node);
                context.runProcessor();

                return Optional.of(context.pop());
            }
        }

        return Optional.empty();
    }

    private void setField(Field field, Map<Object, Object> map){
        field.setAccessible(true);
        try {
            field.set(instance, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
    }
}
