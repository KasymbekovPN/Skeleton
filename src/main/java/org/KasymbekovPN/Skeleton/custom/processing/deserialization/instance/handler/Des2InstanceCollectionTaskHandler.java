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
import java.util.Collection;
import java.util.Optional;

public class Des2InstanceCollectionTaskHandler extends Des2InstanceBaseTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceCollectionTaskHandler.class);

    public Des2InstanceCollectionTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceCollectionTaskHandler(String id,
                                             SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2InstanceCxt context) throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, ContextStateCareTakerIsEmpty {

        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        OptFunction<String, Collection<Object>> collectionGenerator = context.getCollectionGenerator();

        for (Triple<Field, Node, ObjectNode> member : members) {
                Field field = member.getLeft();
                Node memberNode = member.getMiddle();
                ObjectNode classMember = member.getRight();
                String name = field.getName();

                Optional<String> maybeClassName = classMembersPartHandler.getClassName(classMember);
                if (maybeClassName.isPresent()){
                    Optional<Collection<Object>> maybeCollection = collectionGenerator.apply(maybeClassName.get());
                    if (maybeCollection.isPresent()){
                        if (memberNode.is(ArrayNode.ei())){
                            Collection<Object> collection = maybeCollection.get();
                            ArrayNode arrayNode = (ArrayNode) memberNode;

                            fillCollection(collection, arrayNode, context);
                            setField(field, collection);
                        } else {
                            log.warn("'{}' : memberNode has wrong type - isn't ArrayNode", name);
                        }
                    } else {
                        log.warn("'{}' : failure attempt of collection creation", name);
                    }
                } else {
                    log.warn("'{}' : failure attempt of getting of classname", name);
                }
            }
    }

    private void fillCollection(Collection<Object> collection, ArrayNode arrayNode, Des2InstanceCxt context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
        for (Node child : arrayNode.getChildren()) {
            Optional<Object> maybeValue = extractValue(child, context);
            if (maybeValue.isPresent()){
                collection.add(maybeValue.get());
            } else {
                log.warn("Failure attempt of conversion : {}", child);
            }
        }
    }
}
