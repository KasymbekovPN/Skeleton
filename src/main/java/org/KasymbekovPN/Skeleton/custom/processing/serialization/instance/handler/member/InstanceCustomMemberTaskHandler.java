package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.Field;
import java.util.*;

public class InstanceCustomMemberTaskHandler implements TaskHandler<InstanceContext> {

    private static final String CLASS_NAME_IS_NOT_EXIST = "Class name isn't exist";
    private static final String MEMBERS_PART_IS_NOT_EXIST = "Class part isn't exist";
    private static final String MEMBERS_PATH_IS_NOT_EXIST = "Class path isn't exist";

    private final InstanceMembersHandler instanceMembersHandler;
    private final String kind;

    private Result result;

    public InstanceCustomMemberTaskHandler(InstanceMembersHandler instanceMembersHandler,
                                           String kind,
                                           Result result) {
        this.instanceMembersHandler = instanceMembersHandler;
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
            } else {
                if (maybeMembersPath.isEmpty()){
                    status += MEMBERS_PATH_IS_NOT_EXIST + "; ";
                }
                if (maybeMembersPath.isEmpty()){
                    status += MEMBERS_PART_IS_NOT_EXIST;
                }
            }
        }
        else {
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
        Map<String, Object> values = getValues(instanceContext, memberNodes);

        if (values.size() > 0){
            Collector collector = instanceContext.getCollector();
            ObjectNode target = (ObjectNode) collector.setTarget(path);
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String member = entry.getKey();
                Object value = entry.getValue();
                instanceMembersHandler.set(target, member, value);
                collector.reset();
            }
        }
    }

    private Map<String, Object> getValues(InstanceContext instanceContext, Map<String, Node> memberNodes){
        HashMap<String, Object> values = new HashMap<>();

        Object instance = instanceContext.getInstance();
        Map<String, Field> fields = instanceContext.getFields(kind);
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String member = entry.getKey();
            Field field = entry.getValue();

            if (memberNodes.containsKey(member)){
                getValue(field, instance).ifPresent(value -> values.put(member, value));
            }
        }

        return values;
    }

    private Optional<Object> getValue(Field field, Object instance){

        Optional<Object> ret = Optional.empty();

        field.setAccessible(true);
        try {
            ret = Optional.of(field.get(instance));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
        return ret;
    }
}
