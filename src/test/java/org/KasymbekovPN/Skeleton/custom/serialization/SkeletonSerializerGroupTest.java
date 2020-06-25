package org.KasymbekovPN.Skeleton.custom.serialization;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.serialization.group.SerializerGroupEI;
import org.KasymbekovPN.Skeleton.custom.serialization.group.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.ClassAnnotationDataSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor.ConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.SkeletonCollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.group.SerializerGroup;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.utils.checking.TypeChecker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.SkeletonCAC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
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
                .build();

        SerializerGroup serializerGroup = new SkeletonSerializerGroup.Builder(new SkeletonCollectorProcess())
                .addSerializer(SerializerGroupEI.commonEI(), serializer)
                .build();
    }
}
