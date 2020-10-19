package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class SKDes2InstanceCxt implements Des2InstanceCxt {

    private static final Logger log = LoggerFactory.getLogger(SKDes2InstanceCxt.class);

    private final ContextIds contextIds;
    private final Map<String, ObjectNode> classNodes;
    private final ClassMembersPartHandler classMembersPartHandler;
    private final OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter;
    private final OptionalConverter<Map<Object, Object>, ObjectNode> strType2MapConverter;
    private final OptionalConverter<Object, String> className2InstanceConverter;
    private final OptionalConverter<Object, ObjectNode> toInstanceConverter;
    private final ContextProcessor<Des2InstanceCxt> processor;
    private final ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker;

    public SKDes2InstanceCxt(ContextIds contextIds,
                             Map<String, ObjectNode> classNodes,
                             Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                             ClassMembersPartHandler classMembersPartHandler,
                             OptionalConverter<Collection<Object>, ObjectNode> strType2CollectionConverter,
                             OptionalConverter<Map<Object, Object>, ObjectNode> strType2MapConverter,
                             OptionalConverter<Object, String> className2InstanceConverter,
                             OptionalConverter<Object, ObjectNode> toInstanceConverter,
                             ContextProcessor<Des2InstanceCxt> processor,
                             ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker) {
        this.contextIds = contextIds;
        this.classNodes = classNodes;
        this.classMembersPartHandler = classMembersPartHandler;
        this.strType2CollectionConverter = strType2CollectionConverter;
        this.strType2MapConverter = strType2MapConverter;
        this.className2InstanceConverter = className2InstanceConverter;
        this.toInstanceConverter = toInstanceConverter;
        this.processor = processor;
        this.contextStateCareTaker = contextStateCareTaker;
    }

    @Override
    public Map<String, ObjectNode> getClassNodes() {
        return classNodes;
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
