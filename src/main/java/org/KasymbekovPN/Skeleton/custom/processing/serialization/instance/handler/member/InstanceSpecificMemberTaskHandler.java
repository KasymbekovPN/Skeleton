package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//< use ctx
public class InstanceSpecificMemberTaskHandler implements TaskHandler<InstanceContext> {

//    private static final String CLASS_NAME_IS_NOT_EXIST = "Class Name isn't exist";
//    private static final String CLASS_NODE_IS_NOT_EXIST = "Class node '%s' isn't exist";
//    private static final String MEMBERS_PATH_IS_NOT_EXIST = "Members path isn't exist";
//    private static final String MEMBERS_PART_IS_NOT_EXIST = "Members part isn't exist";
    //<
    private static final String CLASS_NAME_IS_NOT_EXIST = "Class name isn't exist";
    private static final String MEMBERS_PART_IS_NOT_EXIST = "Class part isn't exist";
    private static final String MEMBERS_PATH_IS_NOT_EXIST = "Class path isn't exist";

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
    public Result handle(InstanceContext object, Task<InstanceContext> task) {

//        MutablePair<Boolean, String> state = new MutablePair<>(true, "");
//        extractClassName(object, state);
//        extractClassNode(object, state);
//        extractMembersPart(state);
//        fillCollector(object, state);
//
//        result = result.createNew();
//        result.setSuccess(state.getLeft());
//        result.setStatus(state.getRight());
//
//        return result;
        //<
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

    //<
//    private void extractClassName(InstanceContext instanceContext, MutablePair<Boolean, String> state){
//        if (state.getLeft()){
//            Optional<String> mayBeClassName = instanceContext.getClassName();
//            if (mayBeClassName.isPresent()){
//                className = mayBeClassName.get();
//            } else {
//                state.setLeft(false);
//                state.setRight(CLASS_NAME_IS_NOT_EXIST);
//            }
//        }
//    }
//
//    private void extractClassNode(InstanceContext instanceContext, MutablePair<Boolean, String> state){
//        if (state.getLeft()){
//            Optional<ObjectNode> mayBeClassNode = instanceContext.getClassNode(className);
//            if (mayBeClassNode.isPresent()){
//                classNode = mayBeClassNode.get();
//            } else {
//                state.setLeft(false);
//                state.setRight(String.format(CLASS_NODE_IS_NOT_EXIST, className));
//            }
//        }
//    }
//
//    private void extractMembersPart(MutablePair<Boolean, String> state){
//        if (state.getLeft()){
//            Optional<Node> mayBeMembersPath = classNode.getChild(serviceMembersPath);
//            if (mayBeMembersPath.isPresent()){
//                ArrayList<String> membersPath = new ArrayList<>();
//                ArrayNode membersPathNode = (ArrayNode) mayBeMembersPath.get();
//                for (Node child : membersPathNode.getChildren()) {
//                    membersPath.add(((StringNode) child).getValue());
//                }
//
//                objectPath.setPath(membersPath);
//                objectPath.setEi(ObjectNode.ei());
//
//                Optional<Node> mayBeMembersPartNode = classNode.getChild(objectPath);
//                if (mayBeMembersPartNode.isPresent()){
//                    memberNodes = ((ObjectNode) mayBeMembersPartNode.get()).getChildren();
//                } else {
//                    state.setLeft(false);
//                    state.setRight(MEMBERS_PART_IS_NOT_EXIST);
//                }
//            } else {
//                state.setLeft(false);
//                state.setRight(MEMBERS_PATH_IS_NOT_EXIST);
//            }
//        }
//    }

    private void fillCollector(InstanceContext instanceContext, Map<String, Node> memberNodes, List<String> path){
        Map<String, Object> values = extractValues(instanceContext, memberNodes);
        if (values.size() > 0){
            Collector collector = instanceContext.getCollector();
//            ObjectNode targetNode = (ObjectNode) collector.setTarget(objectPath.getPath());
            //<
            ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String member = entry.getKey();
                Object value = entry.getValue();
                instanceMembersHandler.set(targetNode, member, value);
            }
            collector.reset();
        }
    }
//    private void fillCollector(InstanceContext instanceContext, MutablePair<Boolean, String> state){
//        if (state.getLeft()){
//
//            Map<String, Object> values = extractValues(instanceContext);
//            if (values.size() > 0){
//                Collector collector = instanceContext.getCollector();
//                ObjectNode targetNode = (ObjectNode) collector.setTarget(objectPath.getPath());
//                for (Map.Entry<String, Object> entry : values.entrySet()) {
//                    String member = entry.getKey();
//                    Object value = entry.getValue();
//                    instanceMembersHandler.set(targetNode, member, value);
//                }
//                collector.reset();
//            }
//        }
//    }

    private Map<String, Object> extractValues(InstanceContext instanceContext, Map<String, Node> memberNodes){

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

        Optional<String> mayBeType = classMembersHandler.getType(memberNode);
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
