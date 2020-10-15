package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.node.InstanceDataMembersExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.SKInstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header.InstanceHeaderTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCollectionTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceMapTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member.InstanceSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.OLdContextTask;

import java.util.Map;

public class UInstanceSerialization {

    private final static String TASK_COMMON = "common";
    private final static String HANDLER_HEADER = "header";
    private final static String HANDLER_SPECIFIC = "specific";
    private final static String HANDLER_COLLECTION = "collection";
    private final static String HANDLER_MAP = "map";
    private final static String HANDLER_CUSTOM = "custom";

    static public OldContextProcessor<InstanceContext> createInstanceProcessor(){
        OLdContextTask<InstanceContext> task = new OLdContextTask<>(TASK_COMMON);
        task.add(new InstanceHeaderTaskHandlerOld(HANDLER_HEADER))
                .add(new InstanceSpecificTaskHandlerOld(HANDLER_SPECIFIC))
                .add(new InstanceCustomTaskHandlerOld(HANDLER_CUSTOM))
                .add(new InstanceCollectionTaskHandlerOld(HANDLER_COLLECTION))
                .add(new InstanceMapTaskHandlerOld(HANDLER_MAP));

        OldContextProcessor<InstanceContext> processor = new OldContextProcessor<>();
        processor.add(task);

        return processor;
    }

    static public InstanceContext createInstanceContext(Map<String, ObjectNode> classNodes,
                                                        OldContextProcessor<InstanceContext> processor,
                                                        Object instance,
                                                        CollectorPath classPath,
                                                        CollectorPath membersPath,
                                                        ClassHeaderPartHandler classHeaderPartHandler,
                                                        ClassMembersPartHandler classMembersPartHandler,
                                                        InstanceMembersPartHandler instanceMembersPartHandler){

        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                HANDLER_HEADER,
                HANDLER_SPECIFIC,
                HANDLER_CUSTOM,
                HANDLER_COLLECTION,
                HANDLER_MAP
        );

        return new SKInstanceContext(
                contextIds,
                classNodes,
                new SKCollector(),
                processor,
                instance,
                classPath,
                membersPath,
                classHeaderPartHandler,
                classMembersPartHandler,
                instanceMembersPartHandler,
                new ClassNameExtractor(),
                new InstanceDataMembersExtractor()
        );
    }
}
