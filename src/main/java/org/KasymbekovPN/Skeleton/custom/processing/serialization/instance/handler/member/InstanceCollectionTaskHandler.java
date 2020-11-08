package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class InstanceCollectionTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(InstanceCollectionTaskHandler.class);

    private static final String VALUES_IS_EMPTY = "Values by '%s' are empty";

    private Map<String, Object> values;

    public InstanceCollectionTaskHandler(String id) {
        super(id);
    }

    public InstanceCollectionTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContext context) throws ContextStateCareTakerIsEmpty {
        InstanceContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        extractValues(memento);

        if (!simpleResult.isSuccess()){
            log.warn("{}", simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        CollectorPath membersPartPath = context.getMembersCollectorPath();
        Collector collector = context.getCollector();

        collector.setTarget(membersPartPath.getPath());
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String name = entry.getKey();
            Collection<?> collection = (Collection<?>) entry.getValue();
            ArrayNode target = (ArrayNode) collector.beginArray(name);
            for (Object value : collection) {
                Optional<Node> maybeNode = convertObjectToNode(value, target, context);
                if (maybeNode.isPresent()){
                    target.addChild(maybeNode.get());
                } else {
                    log.warn("Wrong value : {}", value);
                }
            }
            collector.end();
        }

        collector.reset();
    }

    private void checkValidation(InstanceContextStateMemento memento){
        if (simpleResult.isSuccess() && !memento.getValidationResult().isSuccess()){
            simpleResult.setFailStatus(memento.getValidationResult().getStatus());
        }
    }

    private void extractValues(InstanceContextStateMemento memento){
        if (simpleResult.isSuccess()){
            values = memento.getFieldValues(id);
            if (values.size() == 0){
                simpleResult.setFailStatus(String.format(VALUES_IS_EMPTY, id));
            }
        }
    }

    // todo: remove to Converter
    private Optional<Node> convertObjectToNode(Object object, Node parent, InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Class<?> type = object.getClass();
        if (type.equals(String.class)){
            return Optional.of(new StringNode(parent, (String) object));
        } else if (type.equals(Boolean.class)){
            return Optional.of(new BooleanNode(parent, (Boolean) object));
        } else if (type.equals(Character.class)){
            return Optional.of(new CharacterNode(parent, (Character) object));
        } else if (Number.class.isAssignableFrom(type)){
            return Optional.of(new NumberNode(parent, (Number) object));
        } else {

            Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor
                    = context.getAnnotationExtractor();
            Optional<Annotation> maybeAnnotation = annotationExtractor.extract(
                    new MutablePair<>(SkeletonClass.class, object.getClass().getDeclaredAnnotations())
            );
            if (maybeAnnotation.isPresent()){
                String name = ((SkeletonClass) maybeAnnotation.get()).name();
                if (context.getClassNodes().containsKey(name)){
                    context.getContextStateCareTaker().push(
                            new SKInstanceContextStateMemento(
                                    object,
                                    context.getClassNodes().get(name),
                                    context
                            )
                    );

                    Pair<Node, Node> old = context.getCollector().attach(new ObjectNode(parent), new ObjectNode(parent));
                    context.runProcessor();
                    context.getContextStateCareTaker().pop();

                    Pair<Node, Node> newPair = context.getCollector().attach(old.getLeft(), old.getRight());
                    return Optional.of(newPair.getLeft());
                }
            }
        }

        return Optional.empty();
    }
}
