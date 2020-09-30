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
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceCollectionTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceCollectionTaskHandler.class);

    private final String kind;

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceCollectionTaskHandler(SimpleResult simpleResult,
                                             String kind) {
        super(simpleResult);
        this.kind = kind;
    }

    @Override
    protected void check(Des2InstanceContext context, Task<Des2InstanceContext> task) {
        String status = "";
        if (context.isValid()){
            members = context.getMembers(kind);
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

        OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter
                = context.getStrType2CollectionConverter();

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            Node memberNode = member.getMiddle();
            ObjectNode classMember = member.getRight();
            String name = field.getName();

            Optional<Collection<Object>> maybeCollection = strType2CollectionConverter.convert(classMember);
            if (maybeCollection.isPresent()){
                if (memberNode.is(ArrayNode.ei())){
                    Collection<Object> collection = maybeCollection.get();
                    ArrayNode arrayNode = (ArrayNode) memberNode;

                    fillCollection(collection, arrayNode);
                    setField(field, collection);
                } else {
                    log.warn("'{}' : memberNode has wrong type - isn't ArrayNode", name);
                }
            } else {
                log.warn("'{}' : failure attempt of collection creation", name);
            }
        }
    }

    private void fillCollection(Collection<Object> collection, ArrayNode arrayNode){
        for (Node child : arrayNode.getChildren()) {
            Optional<Object> maybeValue = extractValue(child);
            if (maybeValue.isPresent()){
                collection.add(maybeValue.get());
            } else {
                log.warn("Failure attempt of conversion : {}", child);
            }
        }
    }

    private Optional<Object> extractValue(Node node){
        if (node.is(BooleanNode.ei())){
            return Optional.of(((BooleanNode) node).getValue());
        } else if (node.is(CharacterNode.ei())){
            return Optional.of(((CharacterNode) node).getValue());
        } else if (node.is(NumberNode.ei())){
            return Optional.of(((NumberNode) node).getValue());
        } else if (node.is(StringNode.ei())){
            return Optional.of(((StringNode) node).getValue());
        }

        //< !!! other node types

        return Optional.empty();
    }

    private void setField(Field field, Collection<Object> collection){
        field.setAccessible(true);
        try {
            field.set(instance, collection);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
    }
}
