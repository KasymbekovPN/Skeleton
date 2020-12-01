package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class SKDes2InstanceCxt implements Des2InstanceCxt {

    private static final Logger log = LoggerFactory.getLogger(SKDes2InstanceCxt.class);

    private final ContextIds contextIds;
    private final Map<String, ObjectNode> classNodes;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final OptFunction<String, Collection<Object>> collectionGenerator;
    private final OptFunction<String, Map<Object,Object>> mapGenerator;
    private final OptFunction<String, Object> instanceGenerator;
    private final CollectorPath classPath;
    private final ContextProcessor<Des2InstanceCxt> processor;
    private final ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker;

    public SKDes2InstanceCxt(ContextIds contextIds,
                             Map<String, ObjectNode> classNodes,
                             ClassHeaderPartHandler classHeaderPartHandler,
                             ClassMembersPartHandler classMembersPartHandler,
                             OptFunction<String, Collection<Object>> collectionGenerator,
                             OptFunction<String, Map<Object,Object>> mapGenerator,
                             OptFunction<String, Object> instanceGenerator,
                             CollectorPath classPath,
                             ContextProcessor<Des2InstanceCxt> processor,
                             ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker) {
        this.contextIds = contextIds;
        this.classNodes = classNodes;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classMembersPartHandler = classMembersPartHandler;
        this.collectionGenerator = collectionGenerator;
        this.mapGenerator = mapGenerator;
        this.instanceGenerator = instanceGenerator;
        this.classPath = classPath;
        this.processor = processor;
        this.contextStateCareTaker = contextStateCareTaker;
    }

    @Override
    public Map<String, ObjectNode> getClassNodes() {
        return classNodes;
    }

    @Override
    public OptFunction<String, Collection<Object>> getCollectionGenerator() {
        return collectionGenerator;
    }

    @Override
    public OptFunction<String, Map<Object, Object>> getMapGenerator() {
        return mapGenerator;
    }

    @Override
    public OptFunction<String, Object> getInstanceGenerator() {
        return instanceGenerator;
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
    public CollectorPath getClassPath() {
        return classPath;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty {
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
}
