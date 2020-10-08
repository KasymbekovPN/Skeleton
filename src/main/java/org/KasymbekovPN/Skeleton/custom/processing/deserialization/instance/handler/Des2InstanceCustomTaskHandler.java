package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Set;

public class Des2InstanceCustomTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceCollectionTaskHandler.class);

    private Object instance;
    private Set<Triple<Field, Node, ObjectNode>> members;

    public Des2InstanceCustomTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceCustomTaskHandler(String id, SimpleResult simpleResult) {
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
    protected void doIt(Des2InstanceContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        OptionalConverter<Object, String> className2InstanceConverter = context.getClassName2InstanceConverter();

        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            ObjectNode newSerData = (ObjectNode) member.getMiddle();
            ObjectNode classMember = member.getRight();
            String name = field.getName();

            Optional<String> maybeClassName = classMembersPartHandler.getClassName(classMember);
            if (maybeClassName.isPresent()){
                String className = maybeClassName.get();

                Optional<Object> maybeInstance = className2InstanceConverter.convert(className);
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

    private void fillInstance(Object instance, Des2InstanceContext context, ObjectNode serData) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        context.push(instance, serData);
        context.runProcessor();
        context.pop();
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
