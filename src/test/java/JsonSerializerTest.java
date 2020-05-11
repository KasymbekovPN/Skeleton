import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.*;
import org.KasymbekovPN.Skeleton.custom.format.collector.SkeletonCollectorStructure;
import org.KasymbekovPN.Skeleton.custom.format.writing.SkeletonJsonFormatter;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.SkeletonClassAnnotationDataSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.SkeletonClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor.SkeletonConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonCustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonExtendedTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonSpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.method.SkeletonToStringMethodSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonCollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.utils.checking.Checker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.TypeChecker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.SkeletonCAC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testPack.inner.TestClass4;
import testPack.inner.TestClass41;

import java.util.*;

@DisplayName("Testing of JsonSerializer")
public class JsonSerializerTest {

    private static final Logger log = LoggerFactory.getLogger(JsonSerializerTest.class);

    @Test
    void test() throws Exception {

        CollectorStructure collectorStructure = new SkeletonCollectorStructure.Builder()
                .setClassPath("class")
                .setMembersPath("members")
                .setAnnotationPath("annotation")
                .setConstructorPath("constructors")
                .setMethodPath("methods")
                .setProtocolPath("protocol")
                .build();

        Collector collector = new SkeletonCollector(collectorStructure);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        Checker<Class<?>> checker = new TypeChecker(
                new HashSet<>(Arrays.asList(Number.class)),
                new HashSet<>(Arrays.asList(String.class, Boolean.class)));

        SkeletonCAC oneArg = new SkeletonCAC(checker);
        SkeletonCAC twoArgs = new SkeletonCAC(checker, checker);

        Serializer serializer = new SkeletonSerializer.Builder(collector)
                .addClassHandler(new SkeletonClassSignatureSEH(ah))
                .addClassHandler(new SkeletonClassAnnotationDataSEH(ah))
                .addConstructorHandler(new SkeletonConstructorClassSEH(ah, cch))
                .addMethodHandler(new SkeletonToStringMethodSEH(ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(String.class, ah, cch))
                .addMemberHandler(new SkeletonExtendedTypeMemberSEH(Number.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(byte.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(short.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(int.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(long.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(float.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(double.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(char.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(boolean.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(Boolean.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(Character.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(byte.class, ah, cch))
                .addMemberHandler(new SkeletonSpecificTypeMemberSEH(byte.class, ah, cch))
                .addMemberHandler(new SkeletonContainerMemberSEH(List.class, oneArg, ah, cch))
                .addMemberHandler(new SkeletonContainerMemberSEH(Set.class, oneArg, ah, cch))
                .addMemberHandler(new SkeletonContainerMemberSEH(Map.class, twoArgs, ah, cch))
                .addMemberHandler(new SkeletonCustomMemberSEH(ah, cch))
                .build();

        serializer.serialize(TestClass4.class);

        CollectorWritingProcess collectorWritingProcess = new SkeletonCollectorWritingProcess(new SkeletonJsonFormatter());
        new SkeletonObjectWritingHandler(collectorWritingProcess, SkeletonObjectNode.class);
        new SkeletonArrayWritingHandler(collectorWritingProcess, SkeletonArrayNode.class);
        new StringWritingHandler(collectorWritingProcess, SkeletonStringNodeSkeleton.class);
        new SkeletonCharacterWritingHandler(collectorWritingProcess, SkeletonCharacterNodeSkeleton.class);
        new SkeletonBooleanWritingHandler(collectorWritingProcess, SkeletonBooleanNodeSkeleton.class);
        new SkeletonNumberWritingHandler(collectorWritingProcess, SkeletonNumberNodeSkeleton.class);
        collector.apply(collectorWritingProcess);

        log.info("{}", collector);

        collector.clear();

        log.info("{}", collector);

        log.info("\n{}", collectorWritingProcess.getBuffer());

        collectorWritingProcess.clearBuffer();

        serializer.serialize(TestClass41.class);
        collector.apply(collectorWritingProcess);

        log.info("--------------------");
        log.info("\n{}", collectorWritingProcess.getBuffer());
    }
}
