package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state;

import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

//< !!! need test
public class SKDes2InstanceContextStateMemento implements Des2InstanceContextStateMemento {

    private static final Logger log = LoggerFactory.getLogger(SKDes2InstanceContextStateMemento.class);

    private static final Class<? extends SimpleResult> RESULT_CLASS = SKSimpleResult.class;

    private final Object instance;
    private final ObjectNode serData;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationNameExtractor;

    private SimpleResult result;
    private String className;
    private ObjectNode classNode;

    public SKDes2InstanceContextStateMemento(Object instance,
                                             ObjectNode serData,
                                             Map<String, ObjectNode> classNodes,
                                             Extractor<String, Annotation[]> annotationNameExtractor) {
        this.instance = instance;
        this.serData = serData;
        this.classNodes = classNodes;
        this.annotationNameExtractor = annotationNameExtractor;
    }

    //< !!! add constructor with SimpleResult param

    @Override
    public void validate() throws InvocationTargetException,
                                  NoSuchMethodException,
                                  InstantiationException,
                                  IllegalAccessException {
        result = createResultInstance();

        checkInstance();
        extractClassName();
        extractClassNode();
    }

    @Override
    public Result getValidationResult() {
        return result;
    }

    private SimpleResult createResultInstance() throws NoSuchMethodException,
                                                       IllegalAccessException,
                                                       InvocationTargetException,
                                                       InstantiationException {
        Constructor<? extends SimpleResult> constructor = RESULT_CLASS.getConstructor();
        return constructor.newInstance();
    }

    private void checkInstance(){
        if (result.isSuccess() && instance == null){
            result.setFailStatus("The instance is null");
        }
    }

    private void extractClassName(){
        if (result.isSuccess()){
            Optional<String> maybeClassName
                    = annotationNameExtractor.extract(instance.getClass().getDeclaredAnnotations());
            if (maybeClassName.isPresent()){
                className = maybeClassName.get();
            } else {
                result.setFailStatus("Class of this instance doesn't contain annotation.");
            }
        }
    }

    private void extractClassNode(){
        if (result.isSuccess()){
            if (classNodes.containsKey(className)){
                classNode = classNodes.get(className);
            } else {
                result.setFailStatus("Class Node for '" + className + "' doesn't exist.");
            }
        }
    }

//        MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data = new MutableTriple<>(true, "", new SKOldDes2InstanceContext.DataChunk());
//        checkInstance(data, instance);
//        extractClassName(data);
//        extractClassNode(data);
//        checkSerializedDataClassName(data, serData);
//        prepareFieldsData(data, serData);
//
//        SKOldDes2InstanceContext.DataChunk dataChunk = data.getRight();
//        Boolean valid = data.getLeft();
//        dataChunk.setValid(valid);
//        storage.push(dataChunk);
//
//        if (!valid){
//            log.warn("{}", data.getMiddle());
//        }
//

//

//
//    private void checkSerializedDataClassName(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data,
//                                              ObjectNode serData){
//        if (data.getLeft()){
//            String status = "";
//            String className = data.getRight().getClassName();
//            Optional<Node> maybeClassPart = serData.getChild(classPath);
//            if (maybeClassPart.isPresent()){
//                ObjectNode classPart = (ObjectNode) maybeClassPart.get();
//                Optional<String> maybeClassName = classHeaderPartHandler.getName(classPart);
//                if (maybeClassName.isPresent()){
//                    String serDataClassName = maybeClassName.get();
//                    if (!serDataClassName.equals(className)){
//                        status = "Instance and Ser. Data class names aren't matching";
//                    }
//                } else {
//                    status = "Class '"+className+"'; class part doesn't contain 'name'";
//                }
//            } else {
//                status = "Class '"+className+"'; serialized data doesn't contain class part";
//            }
//
//            if (!status.isEmpty()){
//                data.setLeft(false);
//                data.setMiddle(status);
//            }
//        }
//    }
//
//    private void prepareFieldsData(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data, ObjectNode serData){
//        Map<String, Field> fields = extractInstanceFields(data);
//        Map<String, ObjectNode> classNodeMembers = extractClassNodeMembers(data);
//        Map<String, Node> serDataMembers = extractSerDataMembers(data, serData);
//
//        if (data.getLeft()){
//            String className = data.getRight().getClassName();
//            Set<String> memberNames = new HashSet<>(fields.keySet());
//            memberNames.addAll(classNodeMembers.keySet());
//            memberNames.addAll(serDataMembers.keySet());
//            for (String memberName : memberNames) {
//                boolean completely = true;
//                if (!fields.containsKey(memberName)){
//                    completely = false;
//                    log.warn("Class '{}': field '{}' doesn't exist", className, memberName);
//                }
//                if (!classNodeMembers.containsKey(memberName)){
//                    completely = false;
//                    log.warn("Class '{}': class node member part doesn't contain member '{}'", className, memberName);
//                }
//                if (!serDataMembers.containsKey(memberName)){
//                    completely = false;
//                    log.warn("Class '{}'; ser. data member part doesn't contain member '{}'", className, memberName);
//                }
//
//                if (completely){
//                    fillPreparedFieldsData(
//                            memberName,
//                            fields.get(memberName),
//                            classNodeMembers.get(memberName),
//                            serDataMembers.get(memberName),
//                            data
//                    );
//                }
//            }
//
//            if (data.getRight().getPreparedFieldsData().size() == 0){
//                data.setLeft(false);
//                data.setMiddle("Prepared data is empty");
//            }
//        }
//    }
//
//    private Map<String, Field> extractInstanceFields(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data){
//        HashMap<String, Field> fields = new HashMap<>();
//
//        if (data.getLeft()){
//            String className = data.getRight().getClassName();
//            Object instance = data.getRight().getInstance();
//            Field[] declaredFields = instance.getClass().getDeclaredFields();
//            for (Field declaredField : declaredFields) {
//                if (!Modifier.isStatic(declaredField.getModifiers())){
//                    Optional<Annotation> maybeAnnotation = annotationExtractor.extract(new MutablePair<>(
//                            SkeletonMember.class,
//                            declaredField.getDeclaredAnnotations()
//                    ));
//                    if (maybeAnnotation.isPresent()){
//                        fields.put(declaredField.getName(), declaredField);
//                    }
//                }
//            }
//
//            if (fields.size() == 0){
//                data.setLeft(false);
//                data.setMiddle("Class '"+ className +"' hasn't any member with annotation");
//            }
//        }
//
//        return fields;
//    }
//
//    private Map<String, ObjectNode> extractClassNodeMembers(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data){
//        HashMap<String, ObjectNode> memberNodes = new HashMap<>();
//
//        if (data.getLeft()){
//            String status = "";
//            String className = data.getRight().getClassName();
//            Optional<Node> maybeMembersPart = data.getRight().getClassNode().getChild(membersPath);
//            if (maybeMembersPart.isPresent()){
//                Map<String, Node> children = ((ObjectNode) maybeMembersPart.get()).getChildren();
//                if (children.size() > 0){
//                    children.forEach((key, value) -> {memberNodes.put(key, (ObjectNode) value);});
//                } else {
//                    status = "Class '"+className+"'; class node member part is empty";
//                }
//            } else {
//                status = "Class '"+className+ "'; member part doesn't exist";
//            }
//
//            if (!status.isEmpty()){
//                data.setLeft(false);
//                data.setMiddle(status);
//            }
//        }
//
//        return memberNodes;
//    }
//
//    private Map<String, Node> extractSerDataMembers(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data,
//                                                    ObjectNode serData){
//        Map<String, Node> memberNodes = new HashMap<>();
//
//        if (data.getLeft()){
//            String status = "";
//            Optional<Node> maybeMembersPart = serData.getChild(membersPath);
//            if (maybeMembersPart.isPresent()){
//                memberNodes = ((ObjectNode) maybeMembersPart.get()).getChildren();
//                if (memberNodes.isEmpty()){
//                    status = "Serialized data: class node member part is empty";
//                }
//            } else {
//                status = "Serialized data; member part doesn't exist";
//            }
//
//            if (!status.isEmpty()){
//                data.setLeft(false);
//                data.setMiddle(status);
//            }
//        }
//
//        return memberNodes;
//    }
//
//    private void fillPreparedFieldsData(String memberName,
//                                        Field field,
//                                        ObjectNode classMember,
//                                        Node serDataMember,
//                                        MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data){
//        Optional<String> maybeKind = classMembersPartHandler.getKind(classMember);
//        if (maybeKind.isPresent()){
//            String kind = maybeKind.get();
//            Map<String, Set<Triple<Field, Node, ObjectNode>>> preparedFieldsData = data.getRight().getPreparedFieldsData();
//            if (!preparedFieldsData.containsKey(kind)){
//                preparedFieldsData.put(kind, new HashSet<>());
//            }
//            preparedFieldsData.get(kind).add(new MutableTriple<>(field, serDataMember, classMember));
//        } else {
//            log.warn("Member '{}' doesn't contain 'kind'", memberName);
//        }
//    }
}
