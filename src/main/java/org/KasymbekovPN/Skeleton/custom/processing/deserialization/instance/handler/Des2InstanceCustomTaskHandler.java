package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceCustomTaskHandler extends BaseContextTaskHandler<Des2InstanceCxt> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceCustomTaskHandler.class);

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceCustomTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceCustomTaskHandler(String id, SimpleResult simpleResult) {
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
    protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
//        OptionalConverter<Object, String> className2InstanceConverter = context.getClassName2InstanceConverter();
        //<
        OptFunction<String, Object> instanceGenerator = context.getInstanceGenerator();

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            ObjectNode newSerData = (ObjectNode) member.getMiddle();
            ObjectNode classMember = member.getRight();
            String name = field.getName();

            Optional<String> maybeClassName = classMembersPartHandler.getClassName(classMember);
            if (maybeClassName.isPresent()){
                String className = maybeClassName.get();

//                Optional<Object> maybeInstance = className2InstanceConverter.convert(className);
                //<
                Optional<Object> maybeInstance = instanceGenerator.apply(className);
                if (maybeInstance.isPresent()){
                    Object newInstance = maybeInstance.get();
                    fillInstance(newInstance, context, newSerData);
                    setField(field, newInstance);
                } else {
                    log.warn("{} : failure attempt of instance creation for '{}'", name, className);
                }
            } else {
                log.warn("{} : classMember doesn't contain className", name );
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

    private void fillInstance(Object instance, Des2InstanceCxt context, ObjectNode serData) throws ContextStateCareTakerIsEmpty, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Des2InstanceContextStateMemento newMemento
                = context.getContextStateCareTaker().peek().createNew(instance, serData);
        context.getContextStateCareTaker().push(newMemento);
        context.runProcessor();
        context.getContextStateCareTaker().pop();
    }

    private void setField(Field field, Object member){
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
