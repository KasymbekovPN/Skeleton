package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
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

abstract public class Des2InstanceBaseTaskHandler extends BaseContextTaskHandler<Des2InstanceCxt> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceBaseTaskHandler.class);

    protected Object instance;
    protected Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceBaseTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceBaseTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        checkValid(context);
        getMembers(context);
        getInstance(context);

        if (!simpleResult.isSuccess()){
            log.warn("{} : {}", id, simpleResult.getStatus());
        }
    }

    protected void checkValid(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        SimpleResult validationResult = context.getContextStateCareTaker().peek().getValidationResult();
        if (!validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    protected void getMembers(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        if (simpleResult.isSuccess()){
            members = context.getContextStateCareTaker().peek().getMembersData(id);
            if (members.size() == 0){
                simpleResult.setFailStatus("Number of members are zero");
            }
        }
    }

    protected void getInstance(Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty {
        if (simpleResult.isSuccess()){
            instance = context.getContextStateCareTaker().peek().getInstance();
        }
    }

    protected Optional<Object> extractValue(Node node, Des2InstanceCxt context) throws ContextStateCareTakerIsEmpty, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (node.is(BooleanNode.ei())){
            return Optional.of(((BooleanNode) node).getValue());
        } else if (node.is(CharacterNode.ei())){
            return Optional.of(((CharacterNode) node).getValue());
        } else if (node.is(NumberNode.ei())){
            return Optional.of(((NumberNode) node).getValue());
        } else if (node.is(StringNode.ei())){
            return Optional.of(((StringNode) node).getValue());
        } else if (node.is(ObjectNode.ei())){

            ObjectNode objectNode = (ObjectNode) node;

            OptFunction<String, Object> instanceGenerator = context.getInstanceGenerator();
            CollectorPath classPath = context.getClassPath();
            ClassHeaderPartHandler classHeaderPartHandler = context.getClassHeaderPartHandler();
            Optional<Node> maybeClassPart = objectNode.getChild(classPath);

            if (maybeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) maybeClassPart.get();
                Optional<String> maybeClassName = classHeaderPartHandler.getName(classPart);
                if (maybeClassName.isPresent()){
                    String className = maybeClassName.get();

                    Optional<Object> maybeInstance = instanceGenerator.apply(className);
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
            }
        }

        return Optional.empty();
    }

    protected void setField(Field field, Object member){
        field.setAccessible(true);
        try{
            field.set(instance, member);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
    }
}
