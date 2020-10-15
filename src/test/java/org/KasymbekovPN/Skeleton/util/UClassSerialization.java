package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SKClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandlerOld;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandlerOld;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.OLdContextTask;

import java.lang.reflect.Field;

public class UClassSerialization {

    private static final String TASK_COMMON = "common";
    private static final String HANDLER_SIGNATURE = "signature";
    private static final String HANDLER_SPECIFIC = "specific";
    private static final String HANDLER_CUSTOM = "custom";
    private static final String HANDLER_COLLECTION = "collection";
    private static final String HANDLER_MAP = "map";

    static public ClassContext createClassContext(CollectorPath classPath,
                                                  CollectorPath membersPath,
                                                  Class<?> instance,
                                                  ClassHeaderPartHandler classHeaderPartHandler,
                                                  ClassMembersPartHandler classMembersPartHandler){
        SKSimpleContextIds contextIds = new SKSimpleContextIds(
                TASK_COMMON,
                HANDLER_SIGNATURE,
                HANDLER_SPECIFIC,
                HANDLER_CUSTOM,
                HANDLER_COLLECTION,
                HANDLER_MAP
        );

        return new SKClassContext(
                contextIds,
                new AnnotationExtractor(),
                classPath.getPath(),
                membersPath.getPath(),
                instance,
                new SKCollector(),
                classHeaderPartHandler,
                classMembersPartHandler
        );
    }

    static public OldContextProcessor<ClassContext> createClassProcessor(ClassHeaderPartHandler classHeaderPartHandler,
                                                                         SKSimpleChecker<Class<?>> specificChecker,
                                                                         SKSimpleChecker<String> customChecker,
                                                                         SimpleChecker<Field> collectionTypeChecker,
                                                                         SimpleChecker<Field> mapTypeChecker){

        OLdContextTask<ClassContext> commonTask = new OLdContextTask<>(TASK_COMMON);
        commonTask.add(new ClassSignatureTaskHandlerOld(HANDLER_SIGNATURE, classHeaderPartHandler))
                .add(new ClassSpecificTaskHandlerOld(HANDLER_SPECIFIC, specificChecker))
                .add(new ClassCustomTaskHandlerOld(HANDLER_CUSTOM, customChecker))
                .add(new ClassContainerTaskHandlerOld(HANDLER_COLLECTION, collectionTypeChecker))
                .add(new ClassContainerTaskHandlerOld(HANDLER_MAP, mapTypeChecker));

        OldContextProcessor<ClassContext> processor
                = new OldContextProcessor<>();
        processor.add(commonTask);

        return processor;
    }
}
