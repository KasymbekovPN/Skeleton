import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.condition.ClassACH;
import org.KasymbekovPN.Skeleton.condition.MemberACH;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.generator.SimpleGenerator;
import org.KasymbekovPN.Skeleton.generator.formatter.JsonFormatter;
import org.KasymbekovPN.Skeleton.generator.node.*;
import org.KasymbekovPN.Skeleton.generator.writeHandler.*;
import org.KasymbekovPN.Skeleton.generator.writer.SimpleWriter;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.serialization.handler.clazz.SignatureClassSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.BiContainerMemberSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.ExtendedTypeMemberSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.SimpleContainerMemberSEH;
import org.KasymbekovPN.Skeleton.serialization.handler.member.SpecificTypeMemberSEH;
import org.KasymbekovPN.Skeleton.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.serialization.serializer.SimpleSerializer;
import org.KasymbekovPN.Skeleton.utils.Checker;
import org.KasymbekovPN.Skeleton.utils.TypeChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    void test4(){
//        ClassCondition condition = new SimpleClassCondition();
//        SimpleCondition condition = new SimpleCondition();
        AnnotationConditionHandler classACH = new ClassACH();
        AnnotationConditionHandler memberACH = new MemberACH(classACH);
        Generator generator = new SimpleGenerator();

        SerializationElementHandler classSEH = new SignatureClassSEH();

        Checker<Class<?>> checker = new TypeChecker(
                new HashSet<>(Arrays.asList(Number.class)),
                new HashSet<>(Arrays.asList(String.class, Boolean.class)));

        SerializationElementHandler memberSEH = new SpecificTypeMemberSEH(String.class)
                .setNext(new ExtendedTypeMemberSEH(Number.class))
                .setNext(new SpecificTypeMemberSEH(byte.class))
                .setNext(new SpecificTypeMemberSEH(short.class))
                .setNext(new SpecificTypeMemberSEH(int.class))
                .setNext(new SpecificTypeMemberSEH(long.class))
                .setNext(new SpecificTypeMemberSEH(float.class))
                .setNext(new SpecificTypeMemberSEH(double.class))
                .setNext(new SpecificTypeMemberSEH(char.class))
                .setNext(new SpecificTypeMemberSEH(boolean.class))
                .setNext(new SpecificTypeMemberSEH(Boolean.class))
                .setNext(new SpecificTypeMemberSEH(Character.class))
                .setNext(new SimpleContainerMemberSEH(List.class, checker))
                .setNext(new SimpleContainerMemberSEH(Set.class, checker))
                .setNext(new BiContainerMemberSEH(Map.class, checker));

        Serializer serializer = new SimpleSerializer(classSEH, memberSEH, generator, classACH, memberACH);
        serializer.serialize(TestClass4.class);

        Writer writer = new SimpleWriter(new JsonFormatter());
        new ObjectWritingHandler(writer, ObjectNode.class);
        new ArrayWritingHandler(writer, ArrayNode.class);
        new StringWritingHandler(writer, StringNode.class);
        new CharacterWritingHandler(writer, CharacterNode.class);
        new BooleanWritingHandler(writer, BooleanNode.class);
        new NumberWritingHandler(writer, NumberNode.class);
        generator.write(writer);

        log.info("\n{}", writer.getBuffer());
    }
}
