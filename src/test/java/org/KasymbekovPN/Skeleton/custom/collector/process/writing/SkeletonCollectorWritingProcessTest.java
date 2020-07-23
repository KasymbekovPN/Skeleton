package org.KasymbekovPN.Skeleton.custom.collector.process.writing;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonArrayWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonObjectWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonPrimitiveWCPH;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.filter.string.IgnoreStringFilter;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter.*;
import org.KasymbekovPN.Skeleton.custom.format.writing.json.handler.JsonWritingFormatterHandler;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassAnnotationDataSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.constructor.ConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.method.ToStringMethodSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonCollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonWCPH;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.utils.checking.TypeChecker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.SkeletonCAC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@DisplayName("SkeletonCollectorWritingProcess. Testing of:")
public class SkeletonCollectorWritingProcessTest {

    @Test
    void test() throws Exception {

        WritingFormatterHandler wfh = new JsonWritingFormatterHandler.Builder()
                .addFormatter(ObjectNode.ei(), new JsonObjectWritingFormatter())
                .addFormatter(ArrayNode.ei(), new JsonArrayWritingFormatter())
                .addFormatter(BooleanNode.ei(), new JsonBooleanWritingFormatter())
                .addFormatter(CharacterNode.ei(), new JsonCharacterWritingFormatter())
                .addFormatter(NumberNode.ei(), new JsonNumberWritingFormatter())
                .addFormatter(StringNode.ei(), new JsonStringWritingFormatter())
                .build();

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

        Collector collector = Utils.createCollector();
        AnnotationChecker sac = new SkeletonAnnotationChecker();
        CollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);
        SkeletonCAC skeletonCAC = new SkeletonCAC(
                new TypeChecker(
                        new HashSet<>(),
                        new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class, Double.class))
                )
        );

        Serializer serializer = new SkeletonSerializer.Builder(collector)
                .addClassHandler(new ClassSignatureSEH(sac))
                .addClassHandler(new ClassAnnotationDataSEH(sac))
                .addConstructorHandler(new ConstructorClassSEH(sac, cch))
                .addMemberHandler(new ContainerMemberSEH(Set.class, skeletonCAC, sac, cch))
                .addMemberHandler(new CustomMemberSEH(sac, cch))
                .addMethodHandler(new ToStringMethodSEH(sac, cch))
                .build();

        serializer.serialize(TC0.class);

        //<
        System.out.println(collector);
        //<

        collector.apply(process);

        //<
        System.out.println(wfh.getDecoder().getString());
        ///<

    }

    @SkeletonClass
    private static class TC0{
        @SkeletonMember
        public int inValue;
    }
}
