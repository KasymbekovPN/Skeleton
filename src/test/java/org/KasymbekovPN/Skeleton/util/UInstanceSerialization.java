package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.Map;

public class UInstanceSerialization {

    static public ContextIds createContextIds(){
        return new SKSimpleContextIds(
                UTaskIds.COMMON,
                UHandlerIds.HEADER,
                UHandlerIds.SPECIFIC,
                UHandlerIds.CUSTOM,
                UHandlerIds.COLLECTION,
                UHandlerIds.MAP
        );
    }

    static public InstanceContext createContext(ContextStateCareTaker<InstanceContextStateMemento> careTaker,
                                                Map<String, ObjectNode> classNodes,
                                                Collector collector){
        return new SKInstanceContext(
                createContextIds(),
                careTaker,
                classNodes,
                collector,
                createProcessor(),
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                new AnnotationExtractor()
        );
    }

    static public InstanceContext createContext(ContextStateCareTaker<InstanceContextStateMemento> careTaker,
                                                Map<String, ObjectNode> classNodes,
                                                Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                                                Collector collector){
        return new SKInstanceContext(
                createContextIds(),
                careTaker,
                classNodes,
                collector,
                createProcessor(),
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                annotationExtractor
        );
    }

    static public ContextProcessor<InstanceContext> createProcessor(){

        ContextTask<InstanceContext> task = new ContextTask<>(UTaskIds.COMMON);
        task
                .add(new InstanceHeaderTaskHandler(UHandlerIds.HEADER))
                .add(new InstanceSpecificTaskHandler(UHandlerIds.SPECIFIC))
                .add(new InstanceCustomTaskHandler(UHandlerIds.CUSTOM))
                .add(new InstanceCollectionTaskHandler(UHandlerIds.COLLECTION))
                .add(new InstanceMapTaskHandler(UHandlerIds.MAP));

        return new ContextProcessor<InstanceContext>().add(task);
    }
}
