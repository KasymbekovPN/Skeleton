package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SKClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

public class UClassSerialization {

    public static ContextIds createContextIds(){
        return new SKSimpleContextIds(
                UTaskIds.COMMON,
                UHandlerIds.SIGNATURE,
                UHandlerIds.SPECIFIC,
                UHandlerIds.CUSTOM,
                UHandlerIds.COLLECTION,
                UHandlerIds.MAP
        );
    }

    public static ContextStateCareTaker<ClassContextStateMemento> createCateTaker(){
        return new SKContextStateCareTaker<>();
    }

    public static ClassContext createClassContext(Collector collector,
                                                  ContextStateCareTaker<ClassContextStateMemento> cateTaker){
        return new SKClassContext(
                createContextIds(),
                cateTaker,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                collector,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );
    }

    public static Processor<ClassContext> createProcessor(Function<Class<?>, Boolean> specificChecker,
                                                          Function<String, Boolean> customChecker,
                                                          OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor,
                                                          Function<Field, Boolean> collectionChecker,
                                                          Function<Field, Boolean> mapChecker){

        Task<ClassContext> task = new ContextTask<>(UTaskIds.COMMON);
        task
                .add(new ClassSignatureTaskHandler(UHandlerIds.SIGNATURE))
                .add(new ClassSpecificTaskHandler(specificChecker, UHandlerIds.SPECIFIC))
                .add(new ClassCustomTaskHandler(customChecker, annotationExtractor, UHandlerIds.CUSTOM))
                .add(new ClassContainerTaskHandler(collectionChecker, UHandlerIds.COLLECTION))
                .add(new ClassContainerTaskHandler(mapChecker, UHandlerIds.MAP));

        return new ContextProcessor<ClassContext>().add(task);
    }
}
