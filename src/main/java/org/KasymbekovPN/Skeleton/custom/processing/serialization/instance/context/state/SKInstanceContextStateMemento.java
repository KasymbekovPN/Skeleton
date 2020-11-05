package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

public class SKInstanceContextStateMemento implements InstanceContextStateMemento {

    private static final Logger log = LoggerFactory.getLogger(SKInstanceContextStateMemento.class);

    private static final Class<? extends SimpleResult> RESULT_CLASS = SKSimpleResult.class;

    private final Object instance;
    private final ObjectNode classNode;
    private final InstanceContext instanceContext;

    private SimpleResult result;
    private String className;
    private Number classModifiers;
    private Map<String, Map<String, Object>> fieldValuesByKind = new HashMap<>();

    public SKInstanceContextStateMemento(Object instance,
                                         ObjectNode classNode,
                                         InstanceContext instanceContext) {
        this.instance = instance;
        this.classNode = classNode;
        this.instanceContext = instanceContext;
    }

    @Override
    public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        result = createResultInstance();

        checkInstance();
        checkClassNode();
        checkInstanceContext();
        extractClassNameAndModifiers();
        extractFieldValues();

        if (!result.isSuccess()){
            log.error("{}", result.getStatus());
        }
    }

    @Override
    public SimpleResult getValidationResult() {
        return result;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public Number getClassModifiers() {
        return classModifiers;
    }

    @Override
    public Map<String, Object> getFieldValues(String kind) {
        return fieldValuesByKind.getOrDefault(kind, new HashMap<>());
    }

    private SimpleResult createResultInstance() throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<? extends SimpleResult> constructor = RESULT_CLASS.getConstructor();
        return constructor.newInstance();
    }

    void checkInstance(){
        if (result.isSuccess() && instance == null){
            result.setFailStatus("The instance is null");
        }
    }

    void checkClassNode(){
        if (result.isSuccess()){
            if (classNode == null){
                result.setFailStatus("The class node is null");
            }
        }
    }

    void checkInstanceContext(){
        if (result.isSuccess() && instanceContext == null){
            result.setFailStatus("The instance context is null");
        }
    }

    void extractClassNameAndModifiers(){
        if (result.isSuccess()){
            CollectorPath classCollectorPath = instanceContext.getClassCollectorPath();
            ClassHeaderPartHandler classHeaderPartHandler = instanceContext.getClassHeaderPartHandler();

            Optional<Node> maybeClassPart = classNode.getChild(classCollectorPath);
            if (maybeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) maybeClassPart.get();
                Optional<String> maybeClassName = classHeaderPartHandler.getName(classPart);
                Optional<Number> maybeModifiers = classHeaderPartHandler.getModifiers(classPart);
                if (maybeClassName.isPresent() && maybeModifiers.isPresent()){
                    className = maybeClassName.get();
                    classModifiers = maybeModifiers.get();
                } else {
                    result.setFailStatus("The class part doesn't contain name or/and modifiers");
                }
            } else {
                result.setFailStatus("The class node doesn't contain class part");
            }
        }
    }

    void extractFieldValues(){
        ObjectNode membersPart = getMembersPart();
        Map<String, Set<String>> memberNamesByKind = extractMemberNamesByKind(membersPart);
        Map<String, Field> fields = extractFields();
        fieldValuesByKind = extractFieldValues(memberNamesByKind, fields);
    }

    private ObjectNode getMembersPart(){
        if (result.isSuccess()){
            CollectorPath membersCollectorPath = instanceContext.getMembersCollectorPath();

            Optional<Node> maybeMembersPart = classNode.getChild(membersCollectorPath);
            if (maybeMembersPart.isPresent()){
                return  (ObjectNode) maybeMembersPart.get();
            } else {
                result.setFailStatus("The class node doesn't contain members part");
            }
        }

        return new ObjectNode(null);
    }

    private Map<String, Set<String>> extractMemberNamesByKind(ObjectNode memberPart){
        HashMap<String, Set<String>> memberNamesByKind = new HashMap<>();

        if (result.isSuccess()){
            ClassMembersPartHandler classMembersPartHandler = instanceContext.getClassMembersPartHandler();
            for (Map.Entry<String, Node> entry : memberPart.getChildren().entrySet()) {
                String name = entry.getKey();
                ObjectNode memberObject = (ObjectNode) entry.getValue();
                Optional<String> maybeKind = classMembersPartHandler.getKind(memberObject);
                if (maybeKind.isPresent()){
                    String kind = maybeKind.get();
                    if (memberNamesByKind.containsKey(kind)){
                        memberNamesByKind.get(kind).add(name);
                    } else {
                        memberNamesByKind.put(kind, new HashSet<>(){{add(name);}});
                    }
                }
            }
        }
        return memberNamesByKind;
    }

    private Map<String, Field> extractFields(){
        HashMap<String, Field> fields = new HashMap<>();
        if (result.isSuccess()){
            Field[] declaredFields = instance.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!Modifier.isStatic(declaredField.getModifiers())){
                    fields.put(declaredField.getName(), declaredField);
                }
            }
        }

        return fields;
    }

    private Map<String, Map<String, Object>> extractFieldValues(Map<String, Set<String>> memberNamesByKind,
                                                                Map<String, Field> fields){
        HashMap<String, Map<String, Object>> values = new HashMap<>();
        if (result.isSuccess()){
            for (Map.Entry<String, Set<String>> entry : memberNamesByKind.entrySet()) {
                String kind = entry.getKey();
                Set<String> names = entry.getValue();

                for (String name : names) {
                    if (fields.containsKey(name)){
                        Optional<Object> maybeValue = getValue(fields.get(name));
                        if (maybeValue.isPresent()){
                            if (!values.containsKey(kind)){
                                values.put(kind, new HashMap<>());
                            }
                            values.get(kind).put(name, maybeValue.get());
                        }
                    }
                }
            }
        }

        return values;
    }

    private Optional<Object> getValue(Field field){
        Optional<Object> maybeValue = Optional.empty();
        field.setAccessible(true);
        try{
            Object object = field.get(instance);
            if (object != null){
                maybeValue = Optional.of(object);
            }
        } catch (IllegalAccessException ex){
            ex.printStackTrace();
        } finally {
            field.setAccessible(false);
        }

        return maybeValue;
    }
}
