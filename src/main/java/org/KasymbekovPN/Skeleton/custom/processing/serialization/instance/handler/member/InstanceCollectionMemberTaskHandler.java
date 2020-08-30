package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.BaseInstanceTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class InstanceCollectionMemberTaskHandler extends BaseInstanceTaskHandler {

    private final String kind;
    private final CollectorPath collectorPath;
    private final ClassMembersHandler classMembersHandler;
    private final InstanceMembersHandler instanceMembersHandler;

    private Map<String, Node> memberNodes;

    public InstanceCollectionMemberTaskHandler(String kind,
                                               CollectorPath collectorPath,
                                               ClassMembersHandler classMembersHandler,
                                               InstanceMembersHandler instanceMembersHandler,
                                               Result result) {
        super(result);
        this.kind = kind;
        this.collectorPath = collectorPath;
        this.classMembersHandler = classMembersHandler;
        this.instanceMembersHandler = instanceMembersHandler;
    }

    @Override
    protected void check(InstanceContext instanceContext, Task<InstanceContext> task) {
        Triple<Boolean, String, ObjectNode> membersPartResult = instanceContext.getMembersPart1();
        success = membersPartResult.getLeft();
        status = membersPartResult.getMiddle();
        if (success){
            ObjectNode membersNode = membersPartResult.getRight();
            memberNodes = membersNode.getChildren();
        }
    }

    @Override
    protected void fillCollector(InstanceContext instanceContext) {
        Map<String, Collection<?>> values = getValues(instanceContext);

        if (values.size() > 0){
            Collector collector = instanceContext.getCollector();
            collector.setTarget(collectorPath.getPath());
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

    private Map<String, Collection<?>> getValues(InstanceContext instanceContext) {
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
