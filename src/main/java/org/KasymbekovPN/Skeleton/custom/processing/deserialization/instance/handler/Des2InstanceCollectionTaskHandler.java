package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceCollectionTaskHandler extends BaseContextTaskHandler<Des2InstanceCxt> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceCollectionTaskHandler.class);

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceCollectionTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceCollectionTaskHandler(String id,
                                             SimpleResult simpleResult) {
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
    protected void doIt(Des2InstanceCxt context) throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, ContextStateCareTakerIsEmpty {

        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
//        OptionalConverter<Collection<Object>, String> collectionGenerator
//                    = context.getCollectionGenerator();
        //<
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

    private Optional<Object> extractValue(Node node, Des2InstanceCxt context) throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, ContextStateCareTakerIsEmpty {
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
                Des2InstanceContextStateMemento newMemento
                        = context.getContextStateCareTaker().peek().createNew(instance, (ObjectNode) node);
                context.getContextStateCareTaker().push(newMemento);
                context.runProcessor();
                context.getContextStateCareTaker().pop();

                return Optional.of(instance);
            }
        }

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
