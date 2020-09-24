package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SkeletonDes2InstanceContext implements Des2InstanceContext {

    private static final Logger log = LoggerFactory.getLogger(SkeletonDes2InstanceContext.class);

    private final ContextIds contextIds;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationNameExtractor;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;

    private Object instance;
    private boolean valid;
    private String className;
    private Map<String, Set<Pair<Field, ObjectNode>>> preparedFieldsData = new HashMap<>();

    public SkeletonDes2InstanceContext(ContextIds contextIds,
                                       Map<String, ObjectNode> classNodes,
                                       Extractor<String, Annotation[]> annotationNameExtractor,
                                       Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor) {
        this.contextIds = contextIds;
        this.classNodes = classNodes;
        this.annotationNameExtractor = annotationNameExtractor;
        this.annotationExtractor = annotationExtractor;
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
        checkValidAndGetClassName();
        prepareFieldsData();

        return oldInstance;
    }

    @Override
    public Object getInstance() {
        return instance;
    }

    private void checkValidAndGetClassName(){
        MutablePair<Boolean, String> checkingStatus = new MutablePair<>(true, "");
        checkInstance(checkingStatus);
        checkAnnotation(checkingStatus);
        checkClassNodes(checkingStatus);

        valid = checkingStatus.getLeft();
        if (!valid){
            log.error("{}", checkingStatus.getRight());
        }
    }

    private void checkInstance(MutablePair<Boolean, String> status){
        if (status.getLeft() && instance == null){
            status.setLeft(false);
            status.setRight("The instance is null.");
        }
    }

    private void checkAnnotation(MutablePair<Boolean, String> status){
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

    private void checkClassNodes(MutablePair<Boolean, String> status){
        if (status.getLeft() && !classNodes.containsKey(className)){
            status.setLeft(false);
            status.setRight("Class Node for '" + className + "' doesn't exist.");
        }
    }

    private void prepareFieldsData(){
        Map<String, Field> fields = extractInstanceFields();
        Map<String, ObjectNode> members = extractClassNodeMembers();

        //<
    }

    private Map<String, Field> extractInstanceFields(){
        HashMap<String, Field> fields = new HashMap<>();

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

        return fields;
    }

    private Map<String, ObjectNode> extractClassNodeMembers(){
        return null;
    }
}
