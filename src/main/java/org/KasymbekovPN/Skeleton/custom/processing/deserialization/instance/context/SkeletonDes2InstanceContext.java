package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class SkeletonDes2InstanceContext implements Des2InstanceContext {

    private static final Logger log = LoggerFactory.getLogger(SkeletonDes2InstanceContext.class);

    private final ContextIds contextIds;
    private final ObjectNode serializedData;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationNameExtractor;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final CollectorPath membersPath;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final CollectorPath classPath;

    private Object instance;
    private boolean valid;
    private String className;
    private ObjectNode classNode;
    private Map<String, Set<Pair<Field, Node>>> preparedFieldsData = new HashMap<>();

    public SkeletonDes2InstanceContext(ContextIds contextIds,
                                       ObjectNode serializedData,
                                       Map<String, ObjectNode> classNodes,
                                       Extractor<String, Annotation[]> annotationNameExtractor,
                                       Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                                       ClassMembersPartHandler classMembersPartHandler,
                                       CollectorPath membersPath,
                                       ClassHeaderPartHandler classHeaderPartHandler,
                                       CollectorPath classPath) {
        this.contextIds = contextIds;
        this.serializedData = serializedData;
        this.classNodes = classNodes;
        this.annotationNameExtractor = annotationNameExtractor;
        this.annotationExtractor = annotationExtractor;
        this.classMembersPartHandler = classMembersPartHandler;
        this.membersPath = membersPath;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classPath = classPath;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public Object attachInstance(Object instance) {
        Object oldInstance = this.instance;
        this.instance = instance;

        MutablePair<Boolean, String> handlingStatus = new MutablePair<>(true, "");
        handleAttaching(handlingStatus);
        valid = handlingStatus.getLeft();
        if (!valid){
            log.error("{}", handlingStatus.getRight());
        }

        return oldInstance;
    }

    @Override
    public Object getInstance() {
        return instance;
    }

    @Override
    public Set<Pair<Field, Node>> getMembers(String kind) {
        return preparedFieldsData.getOrDefault(kind, new HashSet<>());
    }

    private void handleAttaching(MutablePair<Boolean, String> handlingStatus){
        checkInstance(handlingStatus);
        getClassName(handlingStatus);
        checkClassName(handlingStatus);
        checkSerializedDataClassName(handlingStatus);
        prepareFieldsData(handlingStatus);
    }

    private void checkInstance(MutablePair<Boolean, String> status){
        if (status.getLeft() && instance == null){
            status.setLeft(false);
            status.setRight("The instance is null.");
        }
    }

    private void getClassName(MutablePair<Boolean, String> status){
        if (status.getLeft()){
            Optional<String> maybeClassName
                    = annotationNameExtractor.extract(instance.getClass().getDeclaredAnnotations());
            if (maybeClassName.isPresent()) {
                className = maybeClassName.get();
            } else {
                status.setLeft(false);
                status.setRight("Class of this instance doesn't contain annotation.");
            }
        }
    }

    private void checkClassName(MutablePair<Boolean, String> status){
        if (status.getLeft()){
            if (classNodes.containsKey(className)){
                classNode = classNodes.get(className);
            } else {
                status.setLeft(false);
                status.setRight("Class Node for '" + className + "' doesn't exist.");
            }
        }
    }

    private void checkSerializedDataClassName(MutablePair<Boolean, String> status){
        if (status.getLeft()){
            Optional<Node> maybeClassPart = serializedData.getChild(classPath);
            if (maybeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) maybeClassPart.get();
                Optional<String> maybeClassName = classHeaderPartHandler.getName(classPart);
                if (maybeClassName.isPresent()){
                    String serDataClassName = maybeClassName.get();
                    if (!serDataClassName.equals(className)){
                        status.setLeft(false);
                        status.setRight("Instance and Ser. Data class names aren't matching");
                    }
                } else {
                    status.setLeft(false);
                    status.setRight("Class '"+className+"'; class part doesn't contain 'name'");
                }
            } else {
                status.setLeft(false);
                status.setRight("Class '"+className+"'; serialized data doesn't contain class part");
            }
        }
    }

    private void prepareFieldsData(MutablePair<Boolean, String> status){
        Map<String, Field> fields = extractInstanceFields(status);
        Map<String, ObjectNode> classNodeMembers = extractClassNodeMembers(status);
        Map<String, Node> serDataMembers = extractSerDataMembers(status);

        if (status.getLeft()){
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
                    log.warn("Class '{}'; ser/ data member part doesn't contain member '{}'", className, memberName);
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
                status.setLeft(false);
                status.setRight("Prepared data is empty");
            }
        }
    }

    private Map<String, Field> extractInstanceFields(MutablePair<Boolean, String> status){
        HashMap<String, Field> fields = new HashMap<>();

        if (status.getLeft()){
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
                status.setLeft(false);
                status.setRight("Class '"+ className +"' hasn't any member with annotation");
            }
        }

        return fields;
    }

    private Map<String, ObjectNode> extractClassNodeMembers(MutablePair<Boolean, String> status){
        HashMap<String, ObjectNode> memberNodes = new HashMap<>();

        if (status.getLeft()){
            Optional<Node> maybeMembersPart = classNode.getChild(membersPath);
            if (maybeMembersPart.isPresent()){
                Map<String, Node> children = ((ObjectNode) maybeMembersPart.get()).getChildren();
                if (children.size() > 0){
                    children.forEach((key, value) -> {memberNodes.put(key, (ObjectNode) value);});
                } else {
                    status.setLeft(false);
                    status.setRight("Class '"+className+"'; class node member part is empty");
                }
            } else {
                status.setLeft(false);
                status.setRight("Class '"+className+ "'; member part doesn't exist");
            }
        }

        return memberNodes;
    }

    private Map<String, Node> extractSerDataMembers(MutablePair<Boolean, String> status){
        Map<String, Node> memberNodes = new HashMap<>();

        if (status.getLeft()){
            Optional<Node> maybeMembersPart = serializedData.getChild(membersPath);
            if (maybeMembersPart.isPresent()){
                memberNodes = ((ObjectNode) maybeMembersPart.get()).getChildren();
                if (memberNodes.isEmpty()){
                    status.setLeft(false);
                    status.setRight("Serialized data: class node member part is empty");
                }
            } else {
                status.setLeft(false);
                status.setRight("Serialized data; member part doesn't exist");
            }
        }

        return memberNodes;
    }

    private void fillPreparedFieldsData(String memberName, Field field, ObjectNode classMember, Node serDataMember){
        Optional<String> maybeKind = classMembersPartHandler.getKind(classMember);
        if (maybeKind.isPresent()){
            String kind = maybeKind.get();
            if (!preparedFieldsData.containsKey(kind)){
                preparedFieldsData.put(kind, new HashSet<>());
            }
            preparedFieldsData.get(kind).add(new MutablePair<>(field, serDataMember));
        } else {
            log.warn("Class '{}': member '{}' doesn't contain 'kind'", className, memberName);
        }
    }
}
