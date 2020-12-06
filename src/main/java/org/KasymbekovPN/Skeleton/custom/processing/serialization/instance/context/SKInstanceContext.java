package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SKInstanceContext implements InstanceContext {

    private final Logger log = LoggerFactory.getLogger(SKInstanceContext.class);

    private final ContextIds contextIds;
    private final ContextStateCareTaker<InstanceContextStateMemento> careTaker;
    private final Map<String, ObjectNode> classNodes;
    private final Collector collector;
    private final Processor<InstanceContext> processor;
    private final CollectorPath classPartCollectorPath;
    private final CollectorPath membersPartCollectorPath;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor;

    public SKInstanceContext(ContextIds contextIds,
                             ContextStateCareTaker<InstanceContextStateMemento> careTaker,
                             Map<String, ObjectNode> classNodes,
                             Collector collector,
                             Processor<InstanceContext> processor,
                             CollectorPath classPartCollectorPath,
                             CollectorPath membersPartCollectorPath,
                             ClassHeaderPartHandler classHeaderPartHandler,
                             ClassMembersPartHandler classMembersPartHandler,
                             OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor) {
        this.contextIds = contextIds;
        this.careTaker = careTaker;
        this.classNodes = classNodes;
        this.collector = collector;
        this.processor = processor;
        this.classPartCollectorPath = classPartCollectorPath;
        this.membersPartCollectorPath = membersPartCollectorPath;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classMembersPartHandler = classMembersPartHandler;
        this.annotationExtractor = annotationExtractor;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public ContextStateCareTaker<InstanceContextStateMemento> getContextStateCareTaker() {
        return careTaker;
    }

    @Override
    public Map<String, ObjectNode> getClassNodes() {
        return classNodes;
    }

    @Override
    public Collector getCollector() {
        return collector;
    }

    @Override
    public CollectorPath getClassCollectorPath() {
        return classPartCollectorPath;
    }

    @Override
    public CollectorPath getMembersCollectorPath() {
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
    public OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> getAnnotationExtractor() {
        return annotationExtractor;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
        processor.handle(this);
    }
}
