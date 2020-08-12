package org.KasymbekovPN.Skeleton.custom.serialization;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonArrayWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonObjectWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonPrimitiveWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.filter.string.IgnoreStringFilter;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.format.offset.SkeletonOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.NodeProcessHandlerWrapper;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking.NodeTypeChecker;
import org.KasymbekovPN.Skeleton.custom.processing.node.handler.extracting.NodeClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.node.processor.NodeProcessor;
import org.KasymbekovPN.Skeleton.custom.processing.node.task.NodeTask;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.NodeClassNameExtractorHandlerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.NodeTypeCheckerResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.processor.NodeProcessorResult;
import org.KasymbekovPN.Skeleton.custom.result.processing.task.NodeTaskResult;
import org.KasymbekovPN.Skeleton.custom.result.serialization.group.SerializationGroupResult;
import org.KasymbekovPN.Skeleton.custom.result.wrong.WrongResult;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC0;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC1;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ServiceSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonCollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonWCPH;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    private CollectorProcess createProcess(WritingFormatterHandler wfh) throws Exception {


        CollectorProcess process = new SkeletonCollectorWritingProcess();
        new SkeletonWCPH(
                new JsonArrayWCPH(),
                wfh,
                process,
                ArrayNode.ei()
        );
        new SkeletonWCPH(
                new JsonPrimitiveWCPH(),
                wfh,
                process,
                BooleanNode.ei()
        );
        new SkeletonWCPH(
                new JsonPrimitiveWCPH(),
                wfh,
                process,
                CharacterNode.ei()
        );
        new SkeletonWCPH(
                new JsonPrimitiveWCPH(),
                wfh,
                process,
                NumberNode.ei()
        );
        new SkeletonWCPH(
                new JsonObjectWCPH(new IgnoreStringFilter("annotation")),
                wfh,
                process,
                ObjectNode.ei()
        );
        new SkeletonWCPH(
                new JsonPrimitiveWCPH(),
                wfh,
                process,
                StringNode.ei()
        );

        return process;
    }

    private Serializer createSerializer(Collector collector) throws Exception {

        AnnotationChecker sac = new SkeletonAnnotationChecker();
        CollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);
        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(int.class);
        AllowedStringChecker allowedStringChecker = new AllowedStringChecker("SerializerGroupTC0", "SerializerGroupTC1");

        Serializer serializer = new SkeletonSerializer.Builder(collector)
                .addClassHandler(new ServiceSEH(sac))
                .addClassHandler(new ClassSignatureSEH(sac))
                .addMemberHandler(new SpecificTypeMemberSEH(allowedClassChecker, sac, cch))
                .addMemberHandler(new CustomMemberSEH(allowedStringChecker, sac, cch))
                .build();

        return serializer;
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

    @Test
    void test() throws Exception {

        Collector collector = Utils.createCollector();
        Serializer serializer = createSerializer(collector);

        Processor<Node> nodeProcessor = createClassNameExtractProcessor(collector);
        //<
//        NodeTask nodeClassNameExtractorTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
//        new NodeProcessHandlerWrapper(
//                nodeClassNameExtractorTask,
//                new NodeClassNameExtractor(
//                        collector.getCollectorStructure().getPath(CollectorStructureEI.classEI()),
//                        new NodeClassNameExtractorHandlerResult()
//                ),
//                ObjectNode.ei(),
//                new WrongResult()
//        );
//
//        NodeProcessor nodeProcessor = new NodeProcessor(new NodeProcessorResult(new WrongResult()));
//        nodeProcessor.add(
//                SkeletonSerializerGroup.EXTRACT_CLASS_NAME,
//                nodeClassNameExtractorTask
//        );

        String serializerId = "common";
        SkeletonSerializerGroup serializerGroup = new SkeletonSerializerGroup(nodeProcessor, new SerializationGroupResult());
        serializerGroup.attach(serializerId, serializer);

        serializerGroup.serialize(serializerId, SerializerGroupTC0.class);
        serializerGroup.serialize(serializerId, SerializerGroupTC1.class);

        ObjectNode groupRootNode = serializerGroup.getGroupRootNode();
        System.out.println(groupRootNode);

        NodeTask checkNodeTask = new NodeTask(new NodeTaskResult(new WrongResult()), new WrongResult());
        new NodeProcessHandlerWrapper(
                checkNodeTask,
                new NodeTypeChecker(new NodeTypeCheckerResult()),
                ObjectNode.ei(),
                new WrongResult()
        );
        groupRootNode.apply(checkNodeTask);

        //<
//        Set<String> systemTypes = new HashSet<>(){{
//            add("int");
//        }};
//        WritingFormatterHandler wfh = createWFH();
//        CollectorProcess process = createProcess(wfh);
//        SkeletonSerializerGroupVisitor visitor = new SkeletonSerializerGroupVisitor(
//                new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class),
//                process,
//                wfh,
//                systemTypes
//        );
//        //<
////        serializerGroup.accept(visitor);
//
//        Optional<String> mayBeData = visitor.getData();
//        mayBeData.ifPresent(System.out::println);
    }
}
