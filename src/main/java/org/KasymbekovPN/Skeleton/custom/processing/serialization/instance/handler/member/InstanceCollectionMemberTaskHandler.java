package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class InstanceCollectionMemberTaskHandler implements TaskHandler<InstanceContext> {

    private static final String CLASS_NAME_IS_NOT_EXIST = "Class name isn't exist";
    private static final String MEMBERS_PART_IS_NOT_EXIST = "Class part isn't exist";
    private static final String MEMBERS_PATH_IS_NOT_EXIST = "Class path isn't exist";

    private final InstanceMembersHandler instanceMembersHandler;
    private final ClassMembersHandler classMembersHandler;
    private final String kind;

    private Result result;

    public InstanceCollectionMemberTaskHandler(InstanceMembersHandler instanceMembersHandler,
                                               ClassMembersHandler classMembersHandler,
                                               String kind,
                                               Result result) {
        this.instanceMembersHandler = instanceMembersHandler;
        this.classMembersHandler = classMembersHandler;
        this.kind = kind;
        this.result = result;
    }

    @Override
    public Result handle(InstanceContext object, Task<InstanceContext> task) {

        boolean success = false;
        String status = "";

        Optional<String> mayBeClassName = object.getClassName();
        if (mayBeClassName.isPresent()){
            String className = mayBeClassName.get();
            Optional<ObjectNode> mayBeMembersPart = object.getMembersPart(className);
            Optional<List<String>> maybeMembersPath = object.getMembersPath(className);
            if (mayBeMembersPart.isPresent() && maybeMembersPath.isPresent()){
                success = true;
                fillCollector(object, mayBeMembersPart.get().getChildren(), maybeMembersPath.get());
            }
            else {
                if (maybeMembersPath.isEmpty()){
                    status += MEMBERS_PATH_IS_NOT_EXIST + "; ";
                }
                if (maybeMembersPath.isEmpty()){
                    status += MEMBERS_PART_IS_NOT_EXIST;
                }
            }
        } else {
            status = CLASS_NAME_IS_NOT_EXIST;
        }

        result = result.createNew();
        result.setSuccess(success);
        result.setStatus(status);

        return result;
    }

    @Override
    public Result getResult() {
        return result;
    }

    private void fillCollector(InstanceContext instanceContext, Map<String, Node> memberNodes, List<String> path){
        Map<String, Collection<?>> values = getValues(instanceContext, memberNodes);

        if (values.size() > 0){
            Collector collector = instanceContext.getCollector();
            collector.setTarget(path);
            for (Map.Entry<String, Collection<?>> entry : values.entrySet()) {
                String member = entry.getKey();
                Collection<?> collection = entry.getValue();
                ArrayNode target = (ArrayNode) collector.beginArray(member);
                for (Object o : collection) {
                    instanceMembersHandler.set(target, o);
                }
                collector.end();
            }
            collector.reset();
        }
    }

    private Map<String, Collection<?>> getValues(InstanceContext instanceContext, Map<String, Node> memberNodes) {
        Map<String, Collection<?>> values = new HashMap<>();

        Object instance = instanceContext.getInstance();
        Map<String, Field> fields = instanceContext.getFields(kind);
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String member = entry.getKey();
            Field field = entry.getValue();

            if (memberNodes.containsKey(member)){
                ObjectNode memberNode = (ObjectNode) memberNodes.get(member);
                Optional<Collection<?>> mayBeValue = getValue(field, memberNode, instance);
                mayBeValue.ifPresent(value -> values.put(member, value));
            }
        }

        return values;
    }

    private Optional<Collection<?>> getValue(Field field, ObjectNode memberNode, Object instance){

        Optional<Collection<?>> ret = Optional.empty();

        if (checkType(field, memberNode) && checkArguments(field, memberNode)){
            field.setAccessible(true);
            try {
                Collection<?> value = (Collection<?>) field.get(instance);
                ret = Optional.of(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
        }

        return ret;
    }

    private boolean checkType(Field field, ObjectNode memberNode){
        Optional<String> maybeType = classMembersHandler.getType(memberNode);
        if (maybeType.isPresent()){
            String type = maybeType.get();
            return type.equals(field.getType().getName());
        }

        return false;
    }

    private boolean checkArguments(Field field, ObjectNode memberNode){
        Optional<List<String>> maybeArguments = classMembersHandler.getArguments(memberNode);
        if (maybeArguments.isPresent()){
            List<String> arguments = maybeArguments.get();

            List<String> fieldArgs = new ArrayList<>();
            Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            for (Type type : types) {
                fieldArgs.add(((Class<?>) type).getCanonicalName());
            }

            return arguments.equals(fieldArgs);
        }

        return false;
    }
}
