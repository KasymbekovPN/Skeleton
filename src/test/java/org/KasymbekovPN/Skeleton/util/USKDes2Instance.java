package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.CollectionGenerator;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2MapOptConverter;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.SKDes2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceMapTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler.Des2InstanceSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class USKDes2Instance {

    private static final String TASK_COMMON = "common";
    private static final String HANDLER_SPECIFIC = "specific";
    private static final String HANDLER_CUSTOM = "custom";
    private static final String HANDLER_COLLECTION = "collection";
    private static final String HANDLER_MAP = "map";

    static public ContextIds createContextIds(){
        return new SKSimpleContextIds(
                TASK_COMMON,
                HANDLER_SPECIFIC,
                HANDLER_COLLECTION,
                HANDLER_CUSTOM,
                HANDLER_MAP
        );
    }

    static public Des2InstanceCxt createContext(ContextIds contextIds,
                                                 Map<String, ObjectNode> classNodes,
                                                 OptionalConverter<Object, String> className2InstanceConverter,
                                                 OptionalConverter<Object, ObjectNode> toInstanceConverter,
                                                 ContextProcessor<Des2InstanceCxt> processor,
                                                 ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker) throws CollectionGeneratorBuildNoOneGenerator, CollectionGeneratorBuildSomeGeneratorsReturnNull {

        return new SKDes2InstanceCxt(
                contextIds,
                classNodes,
                new AnnotationExtractor(),
                USKClassMembersPartHandler.DEFAULT,
                createCollectionGenerator(),
                new StrType2MapOptConverter(USKClassMembersPartHandler.DEFAULT),
                className2InstanceConverter,
                toInstanceConverter,
                processor,
                contextStateCareTaker
        );
    }

    public static OptionalConverter<Collection<Object>, String> createCollectionGenerator() throws CollectionGeneratorBuildNoOneGenerator, CollectionGeneratorBuildSomeGeneratorsReturnNull {
        return new CollectionGenerator.Builder()
                .add("java.util.Set", HashSet::new)
                .add("java.util.List", ArrayList::new)
                .build();
    }

    static public ContextProcessor<Des2InstanceCxt> createProcessor(){
        ContextTask<Des2InstanceCxt> task = new ContextTask<>(TASK_COMMON);
        task.add(new Des2InstanceSpecificTaskHandler(HANDLER_SPECIFIC))
                .add(new Des2InstanceCustomTaskHandler(HANDLER_CUSTOM))
                .add(new Des2InstanceCollectionTaskHandler(HANDLER_COLLECTION))
                .add(new Des2InstanceMapTaskHandler(HANDLER_MAP));

        return new ContextProcessor<Des2InstanceCxt>().add(task);
    }
}
