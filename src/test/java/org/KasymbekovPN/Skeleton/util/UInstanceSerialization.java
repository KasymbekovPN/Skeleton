package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

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

    static public ContextProcessor<InstanceContext> createProcessor(){
        return new ContextProcessor<>();
    }
}
