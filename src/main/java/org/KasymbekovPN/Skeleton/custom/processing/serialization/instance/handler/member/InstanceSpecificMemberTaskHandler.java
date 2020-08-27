package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutablePair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InstanceSpecificMemberTaskHandler implements TaskHandler<InstanceData> {

    private static final String CLASS_NAME_IS_NOT_EXIST = "Class Name isn't exist";
    private static final String CLASS_NODE_IS_NOT_EXIST = "Class node '%s' isn't exist";
    private static final String MEMBERS_PATH_IS_NOT_EXIST = "Members path isn't exist";
    private static final String MEMBERS_PART_IS_NOT_EXIST = "Members part isn't exist";

    private final String kind;
    private final CollectorPath serviceMembersPath;
    private final CollectorPath objectPath;
    private final ClassMembersHandler classMembersHandler;
    private final InstanceMembersHandler instanceMembersHandler;

    private Result result;
    private String className;
    private ObjectNode classNode;
    private Map<String, Node> memberNodes;

    public InstanceSpecificMemberTaskHandler(String kind,
                                             CollectorPath serviceMembersPath,
                                             CollectorPath objectPath,
                                             ClassMembersHandler classMembersHandler,
                                             InstanceMembersHandler instanceMembersHandler,
                                             Result result) {
        this.kind = kind;
        this.serviceMembersPath = serviceMembersPath;
        this.objectPath = objectPath;
        this.classMembersHandler = classMembersHandler;
        this.instanceMembersHandler = instanceMembersHandler;
        this.result = result;
    }

    @Override
    public Result handle(InstanceData object, Task<InstanceData> task) {

        MutablePair<Boolean, String> state = new MutablePair<>(true, "");
        extractClassName(object, state);
        extractClassNode(object, state);
        extractMembersPart(state);
        fillCollector(object, state);

        result = result.createNew();
        result.setSuccess(state.getLeft());
        result.setStatus(state.getRight());

        return result;
    }

    @Override
    public Result getHandlerResult() {
        return result;
    }

    private void extractClassName(InstanceData instanceData, MutablePair<Boolean, String> state){
        if (state.getLeft()){
            Optional<String> mayBeClassName = instanceData.getClassName();
            if (mayBeClassName.isPresent()){
                className = mayBeClassName.get();
            } else {
                state.setLeft(false);
                state.setRight(CLASS_NAME_IS_NOT_EXIST);
            }
        }
    }

    private void extractClassNode(InstanceData instanceData, MutablePair<Boolean, String> state){
        if (state.getLeft()){
            Optional<ObjectNode> mayBeClassNode = instanceData.getClassNode(className);
            if (mayBeClassNode.isPresent()){
                classNode = mayBeClassNode.get();
            } else {
                state.setLeft(false);
                state.setRight(String.format(CLASS_NODE_IS_NOT_EXIST, className));
            }
        }
    }

    private void extractMembersPart(MutablePair<Boolean, String> state){
        if (state.getLeft()){
            Optional<Node> mayBeMembersPath = classNode.getChild(serviceMembersPath);
            if (mayBeMembersPath.isPresent()){
                ArrayList<String> membersPath = new ArrayList<>();
                ArrayNode membersPathNode = (ArrayNode) mayBeMembersPath.get();
                for (Node child : membersPathNode.getChildren()) {
                    membersPath.add(((StringNode) child).getValue());
                }

                objectPath.setPath(membersPath);
                objectPath.setEi(ObjectNode.ei());

                Optional<Node> mayBeMembersPartNode = classNode.getChild(objectPath);
                if (mayBeMembersPartNode.isPresent()){
                    memberNodes = ((ObjectNode) mayBeMembersPartNode.get()).getChildren();
                } else {
                    state.setLeft(false);
                    state.setRight(MEMBERS_PART_IS_NOT_EXIST);
                }
            } else {
                state.setLeft(false);
                state.setRight(MEMBERS_PATH_IS_NOT_EXIST);
            }
        }
    }

    private void fillCollector(InstanceData instanceData, MutablePair<Boolean, String> state){
        if (state.getLeft()){

            Map<String, Object> values = extractValues(instanceData);
            if (values.size() > 0){
                Collector collector = instanceData.getCollector();
                ObjectNode targetNode = (ObjectNode) collector.setTarget(objectPath.getPath());
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    String member = entry.getKey();
                    Object value = entry.getValue();
                    //< !!!
//                    collector.addProperty(member, value.toString());
                    //<
                    instanceMembersHandler.setSpecific(targetNode, member, value);
                }
            }
        }
    }

    private Map<String, Object> extractValues(InstanceData instanceData){

        HashMap<String, Object> values = new HashMap<>();

        Object instance = instanceData.getInstance();
        Map<String, Field> fields = instanceData.getFields(kind);
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

        Optional<String> mayBeType = classMembersHandler.getType(memberNode);
        if (mayBeType.isPresent()){
            String type = mayBeType.get();
            if (type.equals(field.getType().toString())){
                field.setAccessible(true);
                try {
                    Object value = field.get(instance);
                    return Optional.of(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }
        }

        return Optional.empty();
    }
}
