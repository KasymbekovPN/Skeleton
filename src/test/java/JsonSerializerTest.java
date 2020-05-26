import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.*;
import org.KasymbekovPN.Skeleton.custom.format.collector.SkeletonCollectorStructure;
import org.KasymbekovPN.Skeleton.custom.format.writing.JsonFormatter;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.ClassAnnotationDataSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor.ConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.ContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.ExtendedTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.method.ToStringMethodSEH;
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

        Collector collector = new SkeletonCollector(collectorStructure, true);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        Checker<Class<?>> checker = new TypeChecker(
                new HashSet<>(Arrays.asList(Number.class)),
                new HashSet<>(Arrays.asList(String.class, Boolean.class)));

        SkeletonCAC oneArg = new SkeletonCAC(checker);
        SkeletonCAC twoArgs = new SkeletonCAC(checker, checker);

        Serializer serializer = new SkeletonSerializer.Builder(collector)
                .addClassHandler(new ClassSignatureSEH(ah))
                .addClassHandler(new ClassAnnotationDataSEH(ah))
                .addConstructorHandler(new ConstructorClassSEH(ah, cch))
                .addMethodHandler(new ToStringMethodSEH(ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(String.class, ah, cch))
                .addMemberHandler(new ExtendedTypeMemberSEH(Number.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(byte.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(short.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(int.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(long.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(float.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(double.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(char.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(boolean.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(Boolean.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(Character.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(byte.class, ah, cch))
                .addMemberHandler(new SpecificTypeMemberSEH(byte.class, ah, cch))
                .addMemberHandler(new ContainerMemberSEH(List.class, oneArg, ah, cch))
                .addMemberHandler(new ContainerMemberSEH(Set.class, oneArg, ah, cch))
                .addMemberHandler(new ContainerMemberSEH(Map.class, twoArgs, ah, cch))
                .addMemberHandler(new CustomMemberSEH(ah, cch))
                .build();

        serializer.serialize(TestClass4.class);

        CollectorWritingProcess collectorWritingProcess = new SkeletonCollectorWritingProcess(new JsonFormatter());
        new ObjectWritingHandler(collectorWritingProcess, ObjectNode.class);
        new ArrayWritingHandler(collectorWritingProcess, ArrayNode.class);
        new StringWritingHandler(collectorWritingProcess, StringNode.class);
        new CharacterWritingHandler(collectorWritingProcess, CharacterNode.class);
        new BooleanWritingHandler(collectorWritingProcess, BooleanNode.class);
        new NumberWritingHandler(collectorWritingProcess, NumberNode.class);
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
