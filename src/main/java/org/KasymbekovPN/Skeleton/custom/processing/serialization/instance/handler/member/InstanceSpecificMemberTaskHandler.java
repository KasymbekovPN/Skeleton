package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InstanceSpecificMemberTaskHandler extends BaseContextTaskHandler {

    private final String kind;
    private final CollectorPath collectorPath;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final InstanceMembersPartHandler instanceMembersPartHandler;

    private Map<String, Node> memberNodes;

    public InstanceSpecificMemberTaskHandler(String kind,
                                             CollectorPath collectorPath,
                                             ClassMembersPartHandler classMembersPartHandler,
                                             InstanceMembersPartHandler instanceMembersPartHandler,
                                             Result result) {
        super(result);
        this.kind = kind;
        this.collectorPath = collectorPath;
        this.classMembersPartHandler = classMembersPartHandler;
        this.instanceMembersPartHandler = instanceMembersPartHandler;
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        InstanceContext instanceContext = (InstanceContext) context;
        Triple<Boolean, String, ObjectNode> membersPartResult = instanceContext.getMembersPart();
        success = membersPartResult.getLeft();
        status = membersPartResult.getMiddle();
        if (success){
            ObjectNode membersNode = membersPartResult.getRight();
            memberNodes = membersNode.getChildren();
        }
    }

    @Override
    protected void fillCollector(Context context) {
        InstanceContext instanceContext = (InstanceContext) context;
        Map<String, Object> values = extractValues(instanceContext);
        if (values.size() > 0){
            Collector collector = instanceContext.getCollector();
            ObjectNode targetNode = (ObjectNode) collector.setTarget(collectorPath.getPath());
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String member = entry.getKey();
                Object value = entry.getValue();
                instanceMembersPartHandler.set(targetNode, member, value);
            }
            collector.reset();
        }
    }

    private Map<String, Object> extractValues(InstanceContext instanceContext){

        HashMap<String, Object> values = new HashMap<>();

        Object instance = instanceContext.getInstance();
        Map<String, Field> fields = instanceContext.getFields(kind);
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String member = entry.getKey();
            Field field = entry.getValue();

            if (memberNodes.containsKey(member)){
                ObjectNode memberNode = (ObjectNode) memberNodes.get(member);
                Optional<Object> mayBeValue = extractValue(field, memberNode, instance);
                mayBeValue.ifPresent(o -> values.put(member, o));
            }
        }

        return values;
    }

    private Optional<Object> extractValue(Field field, ObjectNode memberNode, Object instance){

        Optional<Object> ret = Optional.empty();

        Optional<String> mayBeType = classMembersPartHandler.getType(memberNode);
        if (mayBeType.isPresent()){
            String type = mayBeType.get();
            if (type.equals(field.getType().toString())){
                field.setAccessible(true);
                try {
                    ret = Optional.of(field.get(instance));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }
        }

        return ret;
    }
}
