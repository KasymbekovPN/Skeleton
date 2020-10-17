package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

public class SKDes2InstanceContextStateMemento implements Des2InstanceContextStateMemento {

    private static final Logger log = LoggerFactory.getLogger(SKDes2InstanceContextStateMemento.class);

    private static final Class<? extends SimpleResult> RESULT_CLASS = SKSimpleResult.class;

    private final Object instance;
    private final ObjectNode serData;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationNameExtractor;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final CollectorPath classPath;
    private final CollectorPath membersPath;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;

    private SimpleResult result;
    private String className;
    private ObjectNode classNode;
    private Map<String, Set<Triple<Field, Node, ObjectNode>>> preparedFieldsData = new HashMap<>();

    public SKDes2InstanceContextStateMemento(Object instance,
                                             ObjectNode serData,
                                             Map<String, ObjectNode> classNodes,
                                             Extractor<String, Annotation[]> annotationNameExtractor,
                                             ClassHeaderPartHandler classHeaderPartHandler,
                                             ClassMembersPartHandler classMembersPartHandler,
                                             CollectorPath classPath,
                                             CollectorPath membersPath,
                                             Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor) {
        this.instance = instance;
        this.serData = serData;
        this.classNodes = classNodes;
        this.annotationNameExtractor = annotationNameExtractor;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classPath = classPath;
        this.annotationExtractor = annotationExtractor;
        this.membersPath = membersPath;
        this.classMembersPartHandler = classMembersPartHandler;
    }

    @Override
    public void validate() throws InvocationTargetException,
                                  NoSuchMethodException,
                                  InstantiationException,
                                  IllegalAccessException {
        result = createResultInstance();

        checkInstance();
        extractClassName();
        extractClassNode();
        checkSerializedDataClassName();
        prepareFieldsData();
    }

    @Override
    public SimpleResult getValidationResult() {
        return result;
    }

    @Override
    public Set<Triple<Field, Node, ObjectNode>> getMembersData(String kind) {
        return preparedFieldsData.getOrDefault(kind, new HashSet<>());
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

    private void checkSerializedDataClassName(){
        if (result.isSuccess()){
            Optional<Node> maybeClassPart = serData.getChild(classPath);
            if (maybeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) maybeClassPart.get();
                Optional<String> maybeClassName = classHeaderPartHandler.getName(classPart);
                if (maybeClassName.isPresent()){
                    String serDataClassName = maybeClassName.get();
                    if (!serDataClassName.equals(className)){
                        result.setFailStatus("Instance and Ser. Data class names aren't matching");
                    }
                } else {
                    result.setFailStatus("Class '"+className+"'; class part doesn't contain 'name'");
                }
            } else {
                result.setFailStatus("Class '"+className+"'; serialized data doesn't contain class part");
            }
        }
    }

    private void prepareFieldsData(){
        Map<String, Field> fields = extractInstanceFields();
        Map<String, ObjectNode> classNodeMembers = extractClassNodeMembers();
        Map<String, Node> serDataMembers = extractSerDataMembers();

        if (result.isSuccess()){
            Set<String> memberNames = new HashSet<>(fields.keySet());
            memberNames.addAll(classNodeMembers.keySet());
            memberNames.addAll(serDataMembers.keySet());
            for (String memberName : memberNames) {
                boolean completely = true;
                if (!fields.containsKey(memberName)){
                    completely = false;
                    log.warn("Class '{}': field '{}' doesn't exist", className, memberName);
                }
                if (!classNodeMembers.containsKey(memberName)){
                    completely = false;
                    log.warn("Class '{}': class node member part doesn't contain member '{}'", className, memberName);
                }
                if (!serDataMembers.containsKey(memberName)){
                    completely = false;
                    log.warn("Class '{}'; ser. data member part doesn't contain member '{}'", className, memberName);
                }

                if (completely){
                    fillPreparedFieldsData(
                            memberName,
                            fields.get(memberName),
                            classNodeMembers.get(memberName),
                            serDataMembers.get(memberName)
                    );
                }
            }

            if (preparedFieldsData.size() == 0){
                result.setFailStatus("Prepared data is empty");
            }
        }
    }

    private Map<String, Field> extractInstanceFields(){
        HashMap<String, Field> fields = new HashMap<>();

        if (result.isSuccess()){
            Field[] declaredFields = instance.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!Modifier.isStatic(declaredField.getModifiers())){
                    Optional<Annotation> maybeAnnotation = annotationExtractor.extract(new MutablePair<>(
                            SkeletonMember.class,
                            declaredField.getDeclaredAnnotations()
                    ));
                    if (maybeAnnotation.isPresent()){
                        fields.put(declaredField.getName(), declaredField);
                    }
                }
            }

            if (fields.size() == 0){
                result.setFailStatus("Class '"+ className +"' hasn't any member with annotation");
            }
        }

        return fields;
    }

    private Map<String, ObjectNode> extractClassNodeMembers(){
        HashMap<String, ObjectNode> memberNodes = new HashMap<>();

        if (result.isSuccess()){
            Optional<Node> maybeMembersPart = classNode.getChild(membersPath);
            if (maybeMembersPart.isPresent()){
                Map<String, Node> children = ((ObjectNode) maybeMembersPart.get()).getChildren();
                if (children.size() > 0){
                    children.forEach((key, value) -> {memberNodes.put(key, (ObjectNode) value);});
                } else {
                    result.setFailStatus("Class '"+className+"'; class node member part is empty");
                }
            } else {
                result.setFailStatus("Class '"+className+ "'; member part doesn't exist");
            }
        }

        return memberNodes;
    }

    private Map<String, Node> extractSerDataMembers(){
        Map<String, Node> memberNodes = new HashMap<>();

        if (result.isSuccess()){
            Optional<Node> maybeMembersPart = serData.getChild(membersPath);
            if (maybeMembersPart.isPresent()){
                memberNodes = ((ObjectNode) maybeMembersPart.get()).getChildren();
                if (memberNodes.isEmpty()){
                    result.setFailStatus("Serialized data: class node member part is empty");
                }
            } else {
                result.setFailStatus("Serialized data; member part doesn't exist");
            }
        }

        return memberNodes;
    }

    private void fillPreparedFieldsData(String memberName,
                                        Field field,
                                        ObjectNode classMember,
                                        Node serDataMember){
        Optional<String> maybeKind = classMembersPartHandler.getKind(classMember);
        if (maybeKind.isPresent()){
            String kind = maybeKind.get();
            if (!preparedFieldsData.containsKey(kind)){
                preparedFieldsData.put(kind, new HashSet<>());
            }
            preparedFieldsData.get(kind).add(new MutableTriple<>(field, serDataMember, classMember));
        } else {
            log.warn("Member '{}' doesn't contain 'kind'", memberName);
        }
    }
}
