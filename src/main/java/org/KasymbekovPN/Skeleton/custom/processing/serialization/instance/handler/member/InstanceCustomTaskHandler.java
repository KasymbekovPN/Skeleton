package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class InstanceCustomTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(InstanceCustomTaskHandler.class);

    private static final String VALUES_IS_EMPTY = "Values by '%s' are empty";
    private static final String ANNOTATION_NAMES_IS_EMPTY = "Annotation names by %s are empty ";

    private Map<String, Object> values;
    private Map<String, String> annotationNames;

    public InstanceCustomTaskHandler(String id) {
        super(id);
    }

    public InstanceCustomTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContext context) throws ContextStateCareTakerIsEmpty {
        InstanceContextStateMemento memento = context.getContextStateCareTaker().peek();
        checkValidation(memento);
        extractValues(memento);
        extractAnnotationNames(memento);

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
            Object value = entry.getValue();

            if (annotationNames.containsKey(name) &&
                context.getClassNodes().containsKey(annotationNames.get(name))){

                context.getContextStateCareTaker().push(
                        new SKInstanceContextStateMemento(
                                value,
                                context.getClassNodes().get(annotationNames.get(name)),
                                context
                        )
                );

                Pair<Node, Node> old = collector.attach(new ObjectNode(null), new ObjectNode(null));
                context.runProcessor();
                context.getContextStateCareTaker().pop();

                Pair<Node, Node> newPair = collector.attach(old.getLeft(), old.getRight());
                old.getRight().addChild(name, newPair.getLeft());
            }
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

    private void extractAnnotationNames(InstanceContextStateMemento memento){
        if (simpleResult.isSuccess()){
            annotationNames = memento.getAnnotationNames(id);
            if (annotationNames.size() == 0){
                simpleResult.setFailStatus(String.format(ANNOTATION_NAMES_IS_EMPTY, id));
            }
        }
    }
}
