package org.KasymbekovPN.Skeleton.custom.serialization;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonArrayWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonObjectWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonPrimitiveWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.filter.string.IgnoreStringFilter;
import org.KasymbekovPN.Skeleton.custom.format.offset.SkeletonOffset;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC0;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.SerializerGroupTC1;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.custom.serialization.group.handler.SkeletonSerializerGroupVisitor;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SerializerGroupEI;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.SkeletonCollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonCollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonWCPH;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
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
                .addClassHandler(new ClassSignatureSEH(sac))
                .addMemberHandler(new SpecificTypeMemberSEH(allowedClassChecker, sac, cch))
                .addMemberHandler(new CustomMemberSEH(allowedStringChecker, sac, cch))
                .build();

        return serializer;
    }

    @Test
    void test() throws Exception {

        Collector collector = Utils.createCollector();
        Serializer serializer = createSerializer(collector);

        EntityItem sgKey = SerializerGroupEI.commonEI();

        SerializerGroup serializerGroup = new SkeletonSerializerGroup.Builder(new SkeletonCollectorProcess())
                .addSerializer(sgKey, serializer)
                .build();

        serializerGroup.handle(sgKey, SerializerGroupTC0.class);
        serializerGroup.handle(sgKey, SerializerGroupTC1.class);

        //< replace with checker
        Set<String> systemTypes = new HashSet<>(){{
            add("int");
        }};

        WritingFormatterHandler wfh = createWFH();
        CollectorProcess process = createProcess(wfh);

        SkeletonSerializerGroupVisitor visitor = new SkeletonSerializerGroupVisitor(
                new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class),
                process,
                wfh,
                systemTypes
        );
        serializerGroup.accept(visitor);

        Optional<String> mayBeData = visitor.getData();
        mayBeData.ifPresent(System.out::println);
    }
}
