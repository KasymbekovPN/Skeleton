package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClassCustomTaskHandler extends BaseContextTaskHandler<ClassContext> {

    private static final Logger log = LoggerFactory.getLogger(ClassCustomTaskHandler.class);

    private static final String NOT_CONTAIN_CLASS_PART = "Not contain class part";
    private static final String NO_ONE_CUSTOM_FIELD = "No one custom field";

    private final SimpleChecker<String> classNameChecker;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;

    private Set<Pair<String, Field>> data = new HashSet<>();

    public ClassCustomTaskHandler(SimpleChecker<String> classNameChecker,
                                  Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                                  String id) {
        super(id);
        this.classNameChecker = classNameChecker;
        this.annotationExtractor = annotationExtractor;
    }

    public ClassCustomTaskHandler(SimpleChecker<String> classNameChecker,
                                  Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                                  String id,
                                  SimpleResult simpleResult) {
        super(id, simpleResult);
        this.classNameChecker = classNameChecker;
        this.annotationExtractor = annotationExtractor;
    }

    @Override
    protected void check(ClassContext context) throws ContextStateCareTakerIsEmpty {
        ClassContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        checkClassPart(context);
        extractSpecificFields(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{}", simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(ClassContext context) {

        Collector collector = context.getCollector();
        ClassMembersPartHandler classMembersPartHandler = context.getClassMembersPartHandler();
        ArrayList<String> path = new ArrayList<>(context.getMembersPartPath().getPath());

        for (Pair<String, Field> datum : data) {
            String className = datum.getLeft();
            Field field = datum.getRight();
            String name = field.getName();
            String type = field.getType().getTypeName();
            int modifiers = field.getModifiers();

            path.add(name);

            ObjectNode target = (ObjectNode) collector.setTarget(path);
            classMembersPartHandler.setKind(target, id);
            classMembersPartHandler.setType(target, type);
            classMembersPartHandler.setClassName(target, className);
            classMembersPartHandler.setModifiers(target, modifiers);
            collector.reset();

            path.remove(path.size() - 1);
        }

        data.clear();
    }

    private void checkValidation(ClassContextStateMemento memento){
        SimpleResult validationResult = memento.getValidationResult();
        if (simpleResult.isSuccess() && !validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    private void checkClassPart(ClassContext context){
        if (simpleResult.isSuccess()){
            Optional<Node> maybeClassPart
                    = context.getCollector().getNode().getChild(context.getClassPartPath());
            if (maybeClassPart.isEmpty()){
                simpleResult.setFailStatus(NOT_CONTAIN_CLASS_PART);
            }
        }
    }

    private void extractSpecificFields(ClassContextStateMemento memento){
        if (simpleResult.isSuccess()){
            data.clear();
            Set<Field> remainingFields = memento.getRemainingFields();
            for (Field field : remainingFields) {
                Optional<Annotation> maybeAnnotation
                        = annotationExtractor.extract(new MutablePair<>(SkeletonMember.class, field.getAnnotations()));
                if (maybeAnnotation.isPresent()){
                    String className = ((SkeletonMember) maybeAnnotation.get()).name();
                    if (classNameChecker.check(className)){
                        data.add(
                                new MutablePair<>(className, field)
                        );
                    }
                }
            }

            if (data.size() > 0){
                for (Pair<String, Field> datum : data) {
                    remainingFields.remove(datum.getRight());
                }
            }
            else {
                simpleResult.setFailStatus(NO_ONE_CUSTOM_FIELD);
            }
        }
    }
}
