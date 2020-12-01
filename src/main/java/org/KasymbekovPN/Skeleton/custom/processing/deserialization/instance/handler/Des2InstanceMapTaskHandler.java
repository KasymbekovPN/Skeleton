package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class Des2InstanceMapTaskHandler extends Des2InstanceBaseTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceMapTaskHandler.class);

    public Des2InstanceMapTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceMapTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        OptFunction<String, Map<Object, Object>> mapGenerator = context.getMapGenerator();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            String fieldName = field.getName();
            Node memberNode = member.getMiddle();
            ObjectNode classMember = member.getRight();

            Optional<String> maybeClassName = classMembersPartHandler.getClassName(classMember);
            if (maybeClassName.isPresent()){
                Optional<Map<Object, Object>> maybeMap = mapGenerator.apply(maybeClassName.get());
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
            else {
                log.warn("'{}' : failure attempt of getting of classname", fieldName);
            }
        }
    }

    private void fillMap(Map<Object, Object> map, ArrayNode arrayNode, Des2InstanceCxt context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        for (Node child : arrayNode.getChildren()) {
            Optional<ObjectNode> maybeObjectNode = checkChildNode(child);
            if (maybeObjectNode.isPresent()){
                Map<String, Node> children = maybeObjectNode.get().getChildren();
                Optional<Object> value = extractValue(children.get("value"), context);
                Optional<Object> key = extractValue(children.get("key"), context);
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
}
