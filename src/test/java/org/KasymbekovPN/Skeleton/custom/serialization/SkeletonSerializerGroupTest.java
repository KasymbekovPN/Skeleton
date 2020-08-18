package org.KasymbekovPN.Skeleton.custom.serialization;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.checker.CollectionInstanceChecker;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.filter.string.IgnoreStringFilter;
import org.KasymbekovPN.Skeleton.custom.format.offset.SkeletonOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.NodeProcessHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.ClassPartExistingChecker;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.NodeTypeChecker;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.extracting.NodeClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing.JsonArrayTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing.JsonObjectTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing.JsonPrimitiveTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.node.processor.NodeProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.node.task.NodeTask;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.ClassPartExistingCheckerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.NodeTypeCheckerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.extracting.NodeClassNameExtractorHandlerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.writing.json.WritingResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.processor.NodeProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.task.NodeTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.group.SerializationGroupResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC0;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC1;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ServiceSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DisplayName("SkeletonSerializerGroup: Testing of:")
public class SkeletonSerializerGroupTest {

    private WritingFormatterHandler createWFH() throws Exception {

        SkeletonOffset offset = new SkeletonOffset("    ");

        WritingFormatterHandler wfh = new JsonWritingFormatterHandler.Builder(offset)
                .addFormatter(ObjectNode.ei(), new JsonObjectWritingFormatter(offset))
                .addFormatter(ArrayNode.ei(), new JsonArrayWritingFormatter(offset))
                .addFormatter(BooleanNode.ei(), new JsonBooleanWritingFormatter(offset))
                .addFormatter(CharacterNode.ei(), new JsonCharacterWritingFormatter(offset))
                .addFormatter(NumberNode.ei(), new JsonNumberWritingFormatter(offset))
                .addFormatter(StringNode.ei(), new JsonStringWritingFormatter(offset))
                .build();

        return wfh;
    }

    private Serializer createSerializer(Collector collector) throws Exception {

        Processor<Node> serializerNodeProcessor = createSerializerNodeProcessor();

        AnnotationChecker sac = new SkeletonAnnotationChecker();
        CollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(int.class, float.class);
        AllowedStringChecker allowedStringChecker = new AllowedStringChecker("SerializerGroupTC0", "SerializerGroupTC1");

        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
        CollectionInstanceChecker collectionInstanceChecker = new CollectionInstanceChecker(types, argumentTypes);

        Serializer serializer = new SkeletonSerializer.Builder(collector, "common")
                .addClassHandler(new ServiceSEH(sac))
                .addClassHandler(new ClassSignatureSEH(sac))
                .addMemberHandler(new SpecificTypeMemberSEH(allowedClassChecker, sac, cch))
                .addMemberHandler(new CustomMemberSEH(allowedStringChecker, sac, cch))
                .addMemberHandler(new ContainerMemberSEH(collectionInstanceChecker, sac, serializerNodeProcessor))
                .build();

        return serializer;
    }

    private Processor<Node> createSerializerNodeProcessor(){

        NodeTask classExistTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
        new NodeProcessHandlerWrapper(
                classExistTask,
                new ClassPartExistingChecker(new ClassPartExistingCheckerResult()),
                ObjectNode.ei(),
                new WrongResult()
        );

        NodeProcessor nodeProcessor = new NodeProcessor(new NodeProcessorResult(new WrongResult()));
        nodeProcessor.add(
                ContainerMemberSEH.CLASS_EXIST_TASK,
                classExistTask
        );

        return nodeProcessor;
    }

    private Processor<Node> createClassNameExtractProcessor(Collector collector){
        NodeTask nodeClassNameExtractorTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
        new NodeProcessHandlerWrapper(
                nodeClassNameExtractorTask,
                new NodeClassNameExtractor(
                        new NodeClassNameExtractorHandlerResult()
                ),
                ObjectNode.ei(),
                new WrongResult()
        );

        NodeProcessor nodeProcessor = new NodeProcessor(new NodeProcessorResult(new WrongResult()));
        nodeProcessor.add(
                SkeletonSerializerGroup.EXTRACT_CLASS_NAME,
                nodeClassNameExtractorTask
        );

        return nodeProcessor;
    }

    private SerializerGroup createSerializeGroup(Serializer serializer, String serializerId, Processor<Node> nodeProcessor){
        SkeletonSerializerGroup serializerGroup = new SkeletonSerializerGroup(nodeProcessor, new SerializationGroupResult());
        serializerGroup.attach(serializerId, serializer);
        return serializerGroup;
    }

    private Task<Node> createCheckNodeTypeTask(SimpleChecker<String> systemTypeChecker){
        NodeTask checkNodeTypeTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
        new NodeProcessHandlerWrapper(
                checkNodeTypeTask,
                new NodeTypeChecker(systemTypeChecker, new NodeTypeCheckerResult()),
                ObjectNode.ei(),
                new WrongResult()
        );

        return checkNodeTypeTask;
    }

    private Task<Node> createWritingTask(WritingFormatterHandler wfh){
        NodeTask nodeTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
        new NodeProcessHandlerWrapper(
                nodeTask,
                new JsonArrayTaskHandler(wfh, new WritingResult()),
                ArrayNode.ei(),
                new WrongResult()
        );
        new NodeProcessHandlerWrapper(
                nodeTask,
                new JsonPrimitiveTaskHandler(wfh, new WritingResult()),
                BooleanNode.ei(),
                new WrongResult()
        );
        new NodeProcessHandlerWrapper(
                nodeTask,
                new JsonPrimitiveTaskHandler(wfh, new WritingResult()),
                CharacterNode.ei(),
                new WrongResult()
        );
        new NodeProcessHandlerWrapper(
                nodeTask,
                new JsonPrimitiveTaskHandler(wfh, new WritingResult()),
                NumberNode.ei(),
                new WrongResult()
        );
        new NodeProcessHandlerWrapper(
                nodeTask,
                new JsonObjectTaskHandler(
                        wfh,
                        new IgnoreStringFilter("annotation", "__service"),
                        new WritingResult()
                ),
                ObjectNode.ei(),
                new WrongResult()
        );
        new NodeProcessHandlerWrapper(
                nodeTask,
                new JsonPrimitiveTaskHandler(wfh, new WritingResult()),
                StringNode.ei(),
                new WrongResult()
        );

        return nodeTask;
    }

    @Test
    void test() throws Exception {

        Collector collector = Utils.createCollector();
        Serializer serializer = createSerializer(collector);

        Processor<Node> nodeProcessor = createClassNameExtractProcessor(collector);
        
        String serializerId = serializer.getId();
        SerializerGroup serializerGroup = createSerializeGroup(serializer, serializerId, nodeProcessor);
        serializerGroup.serialize(serializerId, SerializerGroupTC0.class);
        serializerGroup.serialize(serializerId, SerializerGroupTC1.class);

        ObjectNode groupRootNode = serializerGroup.getGroupRootNode();

        AllowedStringChecker systemTypeChecker = new AllowedStringChecker("int", "float", "java.util.Set");
        Task<Node> checkNodeTypeTask = createCheckNodeTypeTask(systemTypeChecker);

        groupRootNode.apply(checkNodeTypeTask);

        Result checkNodeTypeResult = checkNodeTypeTask.getResult(ObjectNode.ei());

        if (checkNodeTypeResult.isSuccess()){
            WritingFormatterHandler wfh = createWFH();
            Task<Node> writingTask = createWritingTask(wfh);
            groupRootNode.apply(writingTask);

            System.out.println(wfh.getDecoder().getString());
        }
    }
}
