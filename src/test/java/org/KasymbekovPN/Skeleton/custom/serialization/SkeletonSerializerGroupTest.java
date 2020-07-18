package org.KasymbekovPN.Skeleton.custom.serialization;

import org.KasymbekovPN.Skeleton.custom.checker.IgnoredPropertyNameChecker;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonBooleanWritingHandler;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonArrayWritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.json.JsonObjectWritingCollectorProcessHandler;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.TC0;
import org.KasymbekovPN.Skeleton.custom.serialization.classes.TC1;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SerializerGroupEI;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.custom.serialization.group.handler.SkeletonSerializerGroupVisitor;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassAnnotationDataSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.constructor.ConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.method.ToStringMethodSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.SkeletonCollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.utils.checking.TypeChecker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.SkeletonCAC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@DisplayName("SkeletonSerializerGroup: Testing of:")
public class SkeletonSerializerGroupTest {

    @Test
    void test() throws Exception {

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

        EntityItem sgKey = SerializerGroupEI.commonEI();

        SerializerGroup serializerGroup = new SkeletonSerializerGroup.Builder(new SkeletonCollectorProcess())
                .addSerializer(sgKey, serializer)
                .build();

        serializerGroup.handle(sgKey, TC0.class);
        serializerGroup.handle(sgKey, TC1.class);

        Set<String> systemTypes = new HashSet<>(){{
            add("int");
        }};

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class, new IgnoredPropertyNameChecker("annotation"));
        new JsonArrayWritingCollectorProcessHandler(process, ArrayNode.class);
        new JsonBooleanWritingHandler(process, BooleanNode.class);
        new CharacterWritingHandler(process, CharacterNode.class);
        new NumberWritingHandler(process, NumberNode.class);
        new StringWritingHandler(process, StringNode.class);

        SkeletonSerializerGroupVisitor visitor = new SkeletonSerializerGroupVisitor(
                new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class),
                process,
                systemTypes
        );
        serializerGroup.accept(visitor);

        Optional<String> mayBeData = visitor.getData();
        mayBeData.ifPresent(System.out::println);
    }
}
