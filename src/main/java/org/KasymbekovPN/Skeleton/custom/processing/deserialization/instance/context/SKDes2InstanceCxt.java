package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SKDes2InstanceCxt implements Des2InstanceCxt {

    private static final Logger log = LoggerFactory.getLogger(SKDes2InstanceCxt.class);

    private final ContextIds contextIds;
    //<
    private final Map<String, ObjectNode> classNodes;
//    private final Extractor<String, Annotation[]> annotationNameExtractor;
//    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;
    //<
    private final ClassMembersPartHandler classMembersPartHandler;
    //<
//    private final CollectorPath membersPath;
//    private final ClassHeaderPartHandler classHeaderPartHandler;
//    private final CollectorPath classPath;
    //<
    private final OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter;
    private final OptionalConverter<Map<Object, Object>, ObjectNode> strType2MapConverter;
    private final OptionalConverter<Object, String> className2InstanceConverter;
    private final OptionalConverter<Object, ObjectNode> toInstanceConverter;
    private final ContextProcessor<Des2InstanceCxt> processor;
    private final ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker;

    public SKDes2InstanceCxt(ContextIds contextIds,
                             Map<String, ObjectNode> classNodes,
                             Extractor<String, Annotation[]> annotationNameExtractor,
                             Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                             ClassMembersPartHandler classMembersPartHandler,
                             CollectorPath membersPath,
                             ClassHeaderPartHandler classHeaderPartHandler,
                             CollectorPath classPath,
                             OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter,
                             OptionalConverter<Map<Object, Object>, ObjectNode> strType2MapConverter,
                             OptionalConverter<Object, String> className2InstanceConverter,
                             OptionalConverter<Object, ObjectNode> toInstanceConverter,
                             ContextProcessor<Des2InstanceCxt> processor,
                             ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker) {
        this.contextIds = contextIds;
        this.classNodes = classNodes;
        //<
//        this.annotationNameExtractor = annotationNameExtractor;
//        this.annotationExtractor = annotationExtractor;
        //<
        this.classMembersPartHandler = classMembersPartHandler;
        //<
//        this.membersPath = membersPath;
//        this.classHeaderPartHandler = classHeaderPartHandler;
//        this.classPath = classPath;
        //<
        this.strType2CollectionConverter = strType2CollectionConverter;
        this.strType2MapConverter = strType2MapConverter;
        this.className2InstanceConverter = className2InstanceConverter;
        this.toInstanceConverter = toInstanceConverter;
        this.processor = processor;
        this.contextStateCareTaker = contextStateCareTaker;
    }

    @Override
    public Set<Triple<Field, Node, ObjectNode>> getMembers(String kind) {
//        return Objects.requireNonNull(storage.getPreparedFieldsData()).getOrDefault(kind, new HashSet<>());
        //<
        return null;
    }

    @Override
    public OptionalConverter<Collection<Object>, ObjectNode> getStrType2CollectionConverter() {
        return strType2CollectionConverter;
    }

    @Override
    public OptionalConverter<Map<Object, Object>, ObjectNode> getStrType2MapConverter() {
        return strType2MapConverter;
    }

    @Override
    public OptionalConverter<Object, String> getClassName2InstanceConverter() {
        return className2InstanceConverter;
    }

    @Override
    public OptionalConverter<Object, ObjectNode> getToInstanceConverter() {
        return toInstanceConverter;
    }

    @Override
    public ClassMembersPartHandler getClassMembersPartHandler() {
        return classMembersPartHandler;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        processor.handle(this);
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public ContextStateCareTaker<Des2InstanceContextStateMemento> getContextStateCareTaker() {
        return contextStateCareTaker;
    }

//    private static final Logger log = LoggerFactory.getLogger(SKOldDes2InstanceContext.class);

//    public SKOldDes2InstanceContext(ContextIds contextIds,
//                                    Map<String, ObjectNode> classNodes,
//                                    Extractor<String, Annotation[]> annotationNameExtractor,
//                                    Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
//                                    ClassMembersPartHandler classMembersPartHandler,
//                                    CollectorPath membersPath,
//                                    ClassHeaderPartHandler classHeaderPartHandler,
//                                    CollectorPath classPath,
//                                    OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter,
//                                    OptionalConverter<Map<Object, Object>, ObjectNode> strType2MapConverter,
//                                    OptionalConverter<Object, String> className2InstanceConverter,
//                                    OptionalConverter<Object, ObjectNode> toInstanceConverter,
//                                    ContextProcessor<OldDes2InstanceContext> processor) {
//        this.contextIds = contextIds;
//        this.classNodes = classNodes;
//        this.annotationNameExtractor = annotationNameExtractor;
//        this.annotationExtractor = annotationExtractor;
//        this.classMembersPartHandler = classMembersPartHandler;
//        this.membersPath = membersPath;
//        this.classHeaderPartHandler = classHeaderPartHandler;
//        this.classPath = classPath;
//        this.strType2CollectionConverter = strType2CollectionConverter;
//        this.strType2MapConverter = strType2MapConverter;
//        this.className2InstanceConverter = className2InstanceConverter;
//        this.toInstanceConverter = toInstanceConverter;
//        this.processor = processor;
//    }
//
//    @Override
//    public ContextIds getContextIds() {
//        return contextIds;
//    }
//
//    @Override
//    public boolean isValid() {
//        return storage.isValid();
//    }
//
//    @Override
//    public void push(Object instance, ObjectNode serData) {
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
//    }
//
//    @Override
//    public Object pop() {
//        SKOldDes2InstanceContext.DataChunk dataChunk = storage.pop();
//        return dataChunk != null ? dataChunk.getInstance() : null;
//    }
//
//    @Override
//    public Object getInstance() {
//        return storage.getInstance();
//    }
//
//    @Override
//    public Set<Triple<Field, Node, ObjectNode>> getMembers(String kind) {
//
//    }
//
//    @Override
//    public OptionalConverter<Collection<Object>, ObjectNode> getStrType2CollectionConverter() {
//        return strType2CollectionConverter;
//    }
//
//    @Override
//    public OptionalConverter<Map<Object, Object>, ObjectNode> getStrType2MapConverter() {
//        return strType2MapConverter;
//    }
//
//    @Override
//    public OptionalConverter<Object, String> getClassName2InstanceConverter() {
//        return className2InstanceConverter;
//    }
//
//    @Override
//    public ClassMembersPartHandler getClassMembersPartHandler() {
//        return classMembersPartHandler;
//    }
//
//    @Override
//    public OptionalConverter<Object, ObjectNode> getToInstanceConverter() {
//        return toInstanceConverter;
//    }
//
//    @Override
//    public void runProcessor() throws NoSuchMethodException,
//            InstantiationException,
//            IllegalAccessException,
//            InvocationTargetException {
//        processor.handle(this);
//    }
//
//    private void checkInstance(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data, Object instance){
//        if (data.getLeft() && instance == null){
//            data.setLeft(false);
//            data.setMiddle("The instance is null");
//        } else {
//            data.getRight().setInstance(instance);
//        }
//    }
//
//    private void extractClassName(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data){
//        if (data.getLeft()){
//            Object instance = data.getRight().getInstance();
//            Optional<String> maybeClassName
//                    = annotationNameExtractor.extract(instance.getClass().getDeclaredAnnotations());
//            if (maybeClassName.isPresent()) {
//                data.getRight().setClassName(maybeClassName.get());
//            } else {
//                data.setLeft(false);
//                data.setMiddle("Class of this instance doesn't contain annotation.");
//            }
//        }
//    }
//
//    private void extractClassNode(MutableTriple<Boolean, String, SKOldDes2InstanceContext.DataChunk> data) {
//        if (data.getLeft()){
//            String className = data.getRight().getClassName();
//            if (classNodes.containsKey(className)){
//                data.getRight().setClassNode(classNodes.get(className));
//            } else {
//                data.setLeft(false);
//                data.setMiddle("Class Node for '" + className + "' doesn't exist.");
//            }
//        }
//    }
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
