package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SkeletonClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.ContextHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.task.ContextTask;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.classes.ClassProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SkeletonClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.result.serialization.clazz.ClassSerializationResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor.InstanceProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.instance.task.InstanceTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ClassProcessorTest {

    private ClassHeaderPartHandler classHeaderPartHandler = new SkeletonClassHeaderPartHandler(
            "type",
            "name",
            "modifiers"
    );

    @Test
    void test(){

        SkeletonCollector collector = new SkeletonCollector();

        SkeletonClassContext context = new SkeletonClassContext(
                Arrays.asList("common"),
                Arrays.asList("signature"),
                new AnnotationExtractor(),
                Arrays.asList("class"),
                Arrays.asList("members"),
                ClassProcessorTC0.class,
                collector
        );

        ContextProcessor processor
                = new ContextProcessor(new InstanceProcessorResult(new WrongResult()), new WrongResult());

        ContextTask task = new ContextTask(new InstanceTaskResult(new WrongResult()), new WrongResult());

        processor.add("common", task);

        new ContextHandlerWrapper(
                task,
                new ClassSignatureTaskHandler(classHeaderPartHandler, new ClassSerializationResult()),
                "signature",
                new WrongResult()
        );

        processor.handle(context);

        //<
        System.out.println(collector.getNode());
        //<
    }
}
