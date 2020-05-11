import org.KasymbekovPN.Skeleton.custom.format.writing.SkeletonJsonFormatter;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.*;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.SkeletonClassAnnotationDataSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.SkeletonClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor.SkeletonConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonContainerMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonCustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonExtendedTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.SkeletonSpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.method.SkeletonToStringMethodSEH;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SkeletonCollector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.SkeletonCollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.format.collector.SkeletonCollectorStructure;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.lib.utils.Checker;
import org.KasymbekovPN.Skeleton.lib.utils.TypeChecker;
import org.KasymbekovPN.Skeleton.lib.utils.containerArgumentChecker.SkeletonCAC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testPack.inner.TestClass4;
import testPack.inner.TestClass41;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@DisplayName("Testing of JsonSerializer")
public class JsonSerializerTest {

    private static final Logger log = LoggerFactory.getLogger(JsonSerializerTest.class);

    @Test
    void test(){

//        Generator generator = new SimpleGenerator();
//        SimpleHSE headerVE = new SimpleHSE(new HeaderSEH(), generalCondition);
//        MemberSE memberVE = new SimpleMSE(new SpecificTypeMemberSEH(String.class), generalCondition)
//                .setNext(new SimpleMSE(new ExtendedTypeMemberSEH(Number.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(byte.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(short.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(int.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(long.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(float.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(double.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(char.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(boolean.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(Boolean.class), generalCondition))
//                .setNext(new SimpleMSE(new SpecificTypeMemberSEH(Character.class), generalCondition));
//
//        Serializer serializer = new SimpleSerializer(headerVE, memberVE, generator);
//        serializer.serialize(TestClass2.class);
//
//        Writer writer = new SimpleWriter(new JsonFormatter());
//        new ObjectWritingHandler(writer, ObjectNode.class);
//        new ArrayWritingHandler(writer, ArrayNode.class);
//        new StringWritingHandler(writer, StringNode.class);
//        new CharacterWritingHandler(writer, CharacterNode.class);
//        new BooleanWritingHandler(writer, BooleanNode.class);
//        new NumberWritingHandler(writer, NumberNode.class);
//        generator.write(writer);
//
//        log.info("\n{}", writer.getBuffer());
    }

    @Test
    void test1() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

//        Generator generator = new SimpleGenerator();
//        SimpleHSE headerSE = new SimpleHSE(new HeaderSEH(), generalCondition);
//
//        MemberSE memberSE = new SimpleMSE(new SpecificTypeMemberSEH(String.class), generalCondition)
//                .setNativeNext(new ExtendedTypeMemberSEH(Number.class))
//                .setNativeNext(new SpecificTypeMemberSEH(byte.class))
//                .setNativeNext(new SpecificTypeMemberSEH(short.class))
//                .setNativeNext(new SpecificTypeMemberSEH(int.class))
//                .setNativeNext(new SpecificTypeMemberSEH(long.class))
//                .setNativeNext(new SpecificTypeMemberSEH(float.class))
//                .setNativeNext(new SpecificTypeMemberSEH(double.class))
//                .setNativeNext(new SpecificTypeMemberSEH(char.class))
//                .setNativeNext(new SpecificTypeMemberSEH(boolean.class))
//                .setNativeNext(new SpecificTypeMemberSEH(Boolean.class))
//                .setNativeNext(new SpecificTypeMemberSEH(Character.class));
//
//        TypeChecker checker = new TypeChecker(
//                new HashSet<>(Arrays.asList(Number.class)),
//                new HashSet<>(Arrays.asList(String.class, Boolean.class)));
//
//        memberSE.setNativeNext(new SimpleContainerMemberSEH(List.class, checker));
//        memberSE.setNativeNext(new SimpleContainerMemberSEH(Set.class, checker));
//
//        Serializer serializer = new SimpleSerializer(headerSE, memberSE, generator);
//        serializer.serialize(TestList.class);
//
//        Writer writer = new SimpleWriter(new JsonFormatter());
//        new ObjectWritingHandler(writer, ObjectNode.class);
//        new ArrayWritingHandler(writer, ArrayNode.class);
//        new StringWritingHandler(writer, StringNode.class);
//        new CharacterWritingHandler(writer, CharacterNode.class);
//        new BooleanWritingHandler(writer, BooleanNode.class);
//        new NumberWritingHandler(writer, NumberNode.class);
//        generator.write(writer);
//
//        log.info("\n{}", writer.getBuffer());
    }

    //<
//    @Test
////    void test2() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
////
////        GeneralCondition generalCondition = new SimpleGeneralCondition();
////        Generator generator = new SimpleGenerator();
////        SimpleHSE headerSE = new SimpleHSE(new HeaderSEH(), generalCondition);
////
////        MemberSE memberSE = new SimpleMSE(new SpecificTypeMemberSEH(String.class), generalCondition)
////                .setNativeNext(new ExtendedTypeMemberSEH(Number.class))
////                .setNativeNext(new SpecificTypeMemberSEH(byte.class))
////                .setNativeNext(new SpecificTypeMemberSEH(short.class))
////                .setNativeNext(new SpecificTypeMemberSEH(int.class))
////                .setNativeNext(new SpecificTypeMemberSEH(long.class))
////                .setNativeNext(new SpecificTypeMemberSEH(float.class))
////                .setNativeNext(new SpecificTypeMemberSEH(double.class))
////                .setNativeNext(new SpecificTypeMemberSEH(char.class))
////                .setNativeNext(new SpecificTypeMemberSEH(boolean.class))
////                .setNativeNext(new SpecificTypeMemberSEH(Boolean.class))
////                .setNativeNext(new SpecificTypeMemberSEH(Character.class));
////
////        TypeChecker checker = new TypeChecker(
////                new HashSet<>(Arrays.asList(Number.class)),
////                new HashSet<>(Arrays.asList(String.class, Boolean.class)));
////
////        memberSE.setNativeNext(new SimpleContainerMemberSEH(List.class, checker))
////                .setNativeNext(new SimpleContainerMemberSEH(Set.class, checker))
////                .setNativeNext(new BiContainerMemberSEH(Map.class, checker));
////
////        Serializer serializer = new SimpleSerializer(headerSE, memberSE, generator);
////        serializer.serialize(BiContainerTest.class);
////
////        Writer writer = new SimpleWriter(new JsonFormatter());
////        new ObjectWritingHandler(writer, ObjectNode.class);
////        new ArrayWritingHandler(writer, ArrayNode.class);
////        new StringWritingHandler(writer, StringNode.class);
////        new CharacterWritingHandler(writer, CharacterNode.class);
////        new BooleanWritingHandler(writer, BooleanNode.class);
////        new NumberWritingHandler(writer, NumberNode.class);
////        generator.write(writer);
////
////        log.info("\n{}", writer.getBuffer());
////    }
    //<

//    @Test
//    void test3(){
//        ClassCondition condition = new SimpleClassCondition();
//        Generator generator = new SimpleGenerator();
//
//        SerializationElementHandler classSEH = new SignatureClassSEH();
//
//        Checker<Class<?>> checker = new TypeChecker(
//                new HashSet<>(Arrays.asList(Number.class)),
//                new HashSet<>(Arrays.asList(String.class, Boolean.class)));
//
//        SerializationElementHandler memberSEH = new SpecificTypeMemberSEH(String.class)
//                .setNext(new ExtendedTypeMemberSEH(Number.class))
//                .setNext(new SpecificTypeMemberSEH(byte.class))
//                .setNext(new SpecificTypeMemberSEH(short.class))
//                .setNext(new SpecificTypeMemberSEH(int.class))
//                .setNext(new SpecificTypeMemberSEH(long.class))
//                .setNext(new SpecificTypeMemberSEH(float.class))
//                .setNext(new SpecificTypeMemberSEH(double.class))
//                .setNext(new SpecificTypeMemberSEH(char.class))
//                .setNext(new SpecificTypeMemberSEH(boolean.class))
//                .setNext(new SpecificTypeMemberSEH(Boolean.class))
//                .setNext(new SpecificTypeMemberSEH(Character.class))
//                .setNext(new SimpleContainerMemberSEH(List.class, checker))
//                .setNext(new SimpleContainerMemberSEH(Set.class, checker))
//                .setNext(new BiContainerMemberSEH(Map.class, checker));
//
//        Serializer serializer = new SimpleSerializer(classSEH, memberSEH, generator, condition);
//        serializer.serialize(BiContainerTest.class);
//
//        Writer writer = new SimpleWriter(new JsonFormatter());
//        new ObjectWritingHandler(writer, ObjectNode.class);
//        new ArrayWritingHandler(writer, ArrayNode.class);
//        new StringWritingHandler(writer, StringNode.class);
//        new CharacterWritingHandler(writer, CharacterNode.class);
//        new BooleanWritingHandler(writer, BooleanNode.class);
//        new NumberWritingHandler(writer, NumberNode.class);
//        generator.write(writer);
//
//        log.info("\n{}", writer.getBuffer());
//    }

    @Test
    void test4() throws Exception {
//        ClassCondition condition = new SimpleClassCondition();
//        SimpleCondition condition = new SimpleCondition();
//        AnnotationConditionHandler classACH = new ClassACH();
//        AnnotationConditionHandler memberACH = new MemberACH(classACH);

//        SimpleCollectorCheckingProcess collectorChecker = new SimpleCollectorCheckingProcess();
//        new ClassExistCheckingHandler(collectorChecker, ObjectNode.class);

//        SimpleCollectorCheckingHandler simpleCollectorCheckingHandler = new SimpleCollectorCheckingHandler();
//        simpleCollectorCheckingHandler.addProcess(collectorChecker);

//        AnnotationHandlerContainer simpleAHC = new SimpleAHC();
//        ClassAH classAH = new ClassAH(simpleAHC);
//        MemberAH memberAH = new MemberAH(simpleAHC, collectorChecker);
//        ConstructorAH constructorAH = new ConstructorAH(simpleAHC);
        //<
//        ClassAH classAH = new ClassAH();
//        MemberAH memberAH = new MemberAH();
//        ConstructorAH constructorAH = new ConstructorAH();
        //<

        CollectorStructure collectorStructure = new SkeletonCollectorStructure.Builder()
                .setClassPath("class")
                .setMembersPath("members")
                .setAnnotationPath("annotation")
                .setConstructorPath("constructors")
                .setMethodPath("methods")
                .setProtocolPath("protocol")
                .build();

        Collector collector = new SkeletonCollector(collectorStructure);

        SkeletonAnnotationChecker simpleAnnotationHandler = new SkeletonAnnotationChecker();

        SerializationElementHandler classSEH = new SkeletonClassSignatureSEH(simpleAnnotationHandler)
                .setNext(new SkeletonClassAnnotationDataSEH(simpleAnnotationHandler));

        Checker<Class<?>> checker = new TypeChecker(
                new HashSet<>(Arrays.asList(Number.class)),
                new HashSet<>(Arrays.asList(String.class, Boolean.class)));

        SkeletonCAC oneArg = new SkeletonCAC(checker);
        SkeletonCAC twoArgs = new SkeletonCAC(checker, checker);

//        SimpleCollectorCheckingProcess checkingProcess = new SimpleCollectorCheckingProcess();


//        CollectorCheckingProcess existSimpleCollectorCheckingProcess = new SimpleCollectorCheckingProcess();
//        CollectorCheckingProcess annotationSimpleCollectorCheckingProcess = new SimpleCollectorCheckingProcess();
//        OldCollectorCheckingHandler simpleOldCollectorCheckingHandler = new SimpleOldCollectorCheckingHandler();
//        simpleOldCollectorCheckingHandler.addProcess("exist", existSimpleCollectorCheckingProcess);
//        simpleOldCollectorCheckingHandler.addProcess("annotation", annotationSimpleCollectorCheckingProcess);
//
//        SimpleOldCollectorCheckingHandler ctrSimpleCollectorCheckingHandler = new SimpleOldCollectorCheckingHandler();
//        ctrSimpleCollectorCheckingHandler.addProcess("members", new SimpleCollectorCheckingProcess());

        SerializationElementHandler constructorSEH = new SkeletonConstructorClassSEH(new SkeletonAnnotationChecker(), new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class));


        SerializationElementHandler memberSEH = new SkeletonSpecificTypeMemberSEH(String.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class))
                .setNext(new SkeletonExtendedTypeMemberSEH(Number.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(byte.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(short.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(int.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(long.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(float.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(double.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(char.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(boolean.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(Boolean.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonSpecificTypeMemberSEH(Character.class, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonContainerMemberSEH(List.class, oneArg, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonContainerMemberSEH(Set.class, oneArg, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonContainerMemberSEH(Map.class, twoArgs, simpleAnnotationHandler, new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)))
                .setNext(new SkeletonCustomMemberSEH(new SkeletonAnnotationChecker(), new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)));

        SerializationElementHandler methodSEH = new SkeletonToStringMethodSEH(new SkeletonAnnotationChecker(), new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class));

        Serializer serializer = new SkeletonSerializer(classSEH, memberSEH, constructorSEH, methodSEH, collector);
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
