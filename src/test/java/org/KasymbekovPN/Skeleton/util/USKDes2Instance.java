package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2CollectionOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2MapOptConverter;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.SKDes2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

import java.util.Map;

public class USKDes2Instance {

    private static final String TASK_COMMON = "common";
    private static final String HANDLER_HEADER = "header";
    private static final String HANDLER_SIGNATURE = "signature";
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
                                                 ContextStateCareTaker<Des2InstanceContextStateMemento> contextStateCareTaker){

        return new SKDes2InstanceCxt(
                contextIds,
                classNodes,
                new AnnotationExtractor(),
                USKClassMembersPartHandler.DEFAULT,
                new StrType2CollectionOptConverter(USKClassMembersPartHandler.DEFAULT),
                new StrType2MapOptConverter(USKClassMembersPartHandler.DEFAULT),
                className2InstanceConverter,
                toInstanceConverter,
                processor,
                contextStateCareTaker
        );
    }

    static public ContextProcessor<Des2InstanceCxt> createProcessor(){
        return new ContextProcessor<>();
    }
}