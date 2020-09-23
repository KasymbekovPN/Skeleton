package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SkeletonInstanceContext implements InstanceContext {

    private final Logger log = LoggerFactory.getLogger(SkeletonInstanceContext.class);

    private final ContextIds contextIds;
    private final Map<String, ObjectNode> classNodes;
    private final Collector collector;
    private final Processor<InstanceContext> processor;
    private final CollectorPath classPartCollectorPath;
    private final CollectorPath membersPartCollectorPath;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final InstanceMembersPartHandler instanceMembersPartHandler;
    private final Extractor<String, Annotation[]> annotationClassNameExtractor;
    private final Extractor<Set<String>, Pair<String, ObjectNode>> memberExtractor;

    private Object instance;
    private String className;
    private boolean valid;

    public SkeletonInstanceContext(ContextIds contextIds,
                                   Map<String, ObjectNode> classNodes,
                                   Collector collector,
                                   Processor<InstanceContext> processor,
                                   Object instance,
                                   CollectorPath classPartCollectorPath,
                                   CollectorPath membersPartCollectorPath,
                                   ClassHeaderPartHandler classHeaderPartHandler,
                                   ClassMembersPartHandler classMembersPartHandler,
                                   InstanceMembersPartHandler instanceMembersPartHandler,
                                   Extractor<String, Annotation[]> annotationClassNameExtractor,
                                   Extractor<Set<String>, Pair<String, ObjectNode>> memberExtractor) {
        this.contextIds = contextIds;
        this.classNodes = classNodes;
        this.collector = collector;
        this.processor = processor;
        this.instance = instance;
        this.classPartCollectorPath = classPartCollectorPath;
        this.membersPartCollectorPath = membersPartCollectorPath;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classMembersPartHandler = classMembersPartHandler;
        this.instanceMembersPartHandler = instanceMembersPartHandler;
        this.annotationClassNameExtractor = annotationClassNameExtractor;
        this.memberExtractor = memberExtractor;
        validate();
    }

    @Override
    public ObjectNode getClassNode() {
        ObjectNode result = new ObjectNode(null);
        if (valid){
            result = classNodes.get(className);
        }

        return result;
    }
    
    @Override
    public Collector getCollector() {
        return collector;
    }

    @Override
    public Object attachInstance(Object instance) {
        Object oldInstance = this.instance;
        this.instance = instance;
        validate();
        return oldInstance;
    }

    @Override
    public Processor<InstanceContext> getProcessor() {
        return processor;
    }

    @Override
    public Map<String, Object> getValues(String kind) {
        Map<String, Object> values = new HashMap<>();
        if (valid){
            Optional<Set<String>> maybeMembers = getMembers(kind);
            if (maybeMembers.isPresent()){
                Map<String, Field> fieldsByMembers = getFieldsByMembers(maybeMembers.get());
                for (Map.Entry<String, Field> entry : fieldsByMembers.entrySet()) {
                    String member = entry.getKey();
                    Field field = entry.getValue();
                    getValue(field).ifPresent(value -> {
                        values.put(member, value);
                    });
                }
            } else {
                log.info("'{}' There is no one member which kind of '{}'", className, kind);
            }
        } else {
            log.error("Isn't valid");
        }

        return values;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public CollectorPath getClassPartPath() {
        return classPartCollectorPath;
    }

    @Override
    public CollectorPath getMembersPartPath() {
        return membersPartCollectorPath;
    }

    @Override
    public ClassHeaderPartHandler getClassHeaderPartHandler() {
        return classHeaderPartHandler;
    }

    @Override
    public ClassMembersPartHandler getClassMembersPartHandler() {
        return classMembersPartHandler;
    }

    @Override
    public InstanceMembersPartHandler getInstanceMembersPartHandler() {
        return instanceMembersPartHandler;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    private void validate() {
        valid = false;
        if (instance != null){
            Optional<String> maybeClassName
                    = annotationClassNameExtractor.extract(instance.getClass().getDeclaredAnnotations());
            if (maybeClassName.isPresent()){
                className = maybeClassName.get();
                if (classNodes.containsKey(className)){
                    valid = true;
                } else {
                    log.error("There isn't class node for '{}'", className);
                }
            } else {
                log.error("Instance doesn't contain annotation with name");
            }
        } else {
            log.error("Instance is null");
        }
    }

    private Map<String, Field> getFieldsByMembers(Set<String> members){
        Map<String, Field> fields = new HashMap<>();
        for (Field field : instance.getClass().getDeclaredFields()) {
            String name = field.getName();
            if (members.contains(name)){
                fields.put(name, field);
            }
        }

        return fields;
    }

    private Optional<Object> getValue(Field field){
        Optional<Object> result = Optional.empty();
        field.setAccessible(true);
        try {
            Object object = field.get(instance);
            if (object != null){
                result = Optional.of(object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }

        return result;
    }

    private Optional<Set<String>> getMembers(String kind){

        ObjectNode classNode = classNodes.get(className);
        Optional<Node> maybeMembersPart = classNode.getChild(membersPartCollectorPath);
        if (maybeMembersPart.isPresent()){
            return memberExtractor.extract(
                    new MutablePair<>(
                            kind,
                            (ObjectNode) maybeMembersPart.get()
                    )
                );
        }

        log.error("There isn't member part");
        return Optional.empty();
    }
}
