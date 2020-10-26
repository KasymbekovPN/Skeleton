package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.SKInstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.OldClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SKDes2InstanceContextStateMemento. Testing of:")
public class SKDes2InstanceContextStateMementoTest {

    private static final String EXTRACT_CLASS_NODE_TEMPLATE = "Class Node for '%s' doesn't exist.";
    private static final String SER_DATA_DOES_NOT_CONTAIN_CLASS_PART = "Class '%s'; serialized data doesn't contain class part";
    private static final String CLASS_PART_DOES_NOT_CONTAIN_NAME = "Class '%s'; class part doesn't contain 'name'";
    private static final String INSTANCE_AND_SER_DATA_CLASS_NAMES_ARE_NOT_MATCHING = "Instance and Ser. Data class names aren't matching";
    private static final String NOT_CONTAINS_ANY_MEMBERS_WITH_ANNOTATION = "Class '%s' hasn't any member with annotation";
    private static final String CLASS_DATA_MEMBERS_PART_DOES_NOT_EXIST = "Class '%s'; member part doesn't exist";
    private static final String CLASS_DATA_MEMBER_PART_IS_EMPTY = "Class '%s'; class node member part is empty";
    private static final String SER_DATA_MEMBER_PART_DOES_NOT_EXIST = "Serialized data; member part doesn't exist";
    private static final String SER_DATA_MEMBER_PART_IS_EMPTY = "Serialized data: class node member part is empty";

    @DisplayName("test instance is null")
    @Test
    void testInstanceIsNull() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();

        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo("The instance is null");
    }

    @DisplayName("class name extraction")
    @Test
    void testClassNameExtraction() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new TestClassWithoutAnnotation(),
                null,
                null,
                new ClassNameExtractor(),
                null,
                null,
                null,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();

        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo("Class of this instance doesn't contain annotation.");
    }

    @DisplayName("class node extraction")
    @Test
    void testClassNodeExtraction() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new EmptyTestClass(),
                null,
                new HashMap<>(),
                new ClassNameExtractor(),
                null,
                null,
                null,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(String.format(EXTRACT_CLASS_NODE_TEMPLATE, "EmptyTestClass"));
    }

    @DisplayName("check ser. data - not contains class part")
    @Test
    void testCheckSerDataNotContainsClassPart() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("EmptyTestClass", new ObjectNode(null));
        }};

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new EmptyTestClass(),
                new ObjectNode(null),
                classNodes,
                new ClassNameExtractor(),
                null,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(String.format(SER_DATA_DOES_NOT_CONTAIN_CLASS_PART, "EmptyTestClass"));
    }

    @DisplayName("check ser. data - class part doesn't contain 'name'")
    @Test
    void testCheckDesDataClassPartDoesNotContainName() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("EmptyTestClass", new ObjectNode(null));
        }};

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new EmptyTestClass(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(String.format(CLASS_PART_DOES_NOT_CONTAIN_NAME, "EmptyTestClass"));
    }

    @DisplayName("check ser.data - instance and ser.data class names aren't matching")
    @Test
    void testCheckSerDataNamesAreNotMatching() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("EmptyTestClass", new ObjectNode(null));
        }};

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "WrongClassName");

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new EmptyTestClass(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(INSTANCE_AND_SER_DATA_CLASS_NAMES_ARE_NOT_MATCHING);
    }

    @DisplayName("extractInstanceFields - class without members")
    @Test
    void testValidateExtractInstanceFields1() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("EmptyTestClass", new ObjectNode(null));
        }};

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "EmptyTestClass");

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new EmptyTestClass(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                null,
                null
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(NOT_CONTAINS_ANY_MEMBERS_WITH_ANNOTATION, "EmptyTestClass");
    }

    @DisplayName("extractInstanceFields - class without annotated members")
    @Test
    void testValidateExtractInstanceFields2() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("WithoutAnnotatedMembers", new ObjectNode(null));
        }};

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "WithoutAnnotatedMembers");

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new WithoutAnnotatedMembers(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                null,
                new AnnotationExtractor()
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(NOT_CONTAINS_ANY_MEMBERS_WITH_ANNOTATION, "WithoutAnnotatedMembers");
    }

    @DisplayName("extractClassNodeMembers - member part doesn't exist")
    @Test
    void testValidateExtractClassNodeMembers1() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("WithAnnotatedMembers", new ObjectNode(null));
        }};

        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "WithAnnotatedMembers");

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new WithAnnotatedMembers(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(CLASS_DATA_MEMBERS_PART_DOES_NOT_EXIST, "WithAnnotatedMembers");
    }

    @DisplayName("extractClassNodeMembers - member part is empty")
    @Test
    void testValidateExtractClassNodeMembers2() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("WithAnnotatedMembers", (ObjectNode) collector.detachNode());
        }};

        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "WithAnnotatedMembers");

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new WithAnnotatedMembers(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(CLASS_DATA_MEMBER_PART_IS_EMPTY, "WithAnnotatedMembers");
    }

    @DisplayName("extractSerDataMembers - members part doesn't exist")
    @Test
    void testValidateExtractSerDataMembers1() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
        collector.beginObject("intValue");
        collector.addProperty("kind", "specific");
        collector.addProperty("className", "int");
        collector.addProperty("type", "int");
        collector.addProperty("modifiers", 2);
        collector.end();

        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("WithAnnotatedMembers", (ObjectNode) collector.detachNode());
        }};

        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "WithAnnotatedMembers");

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new WithAnnotatedMembers(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(SER_DATA_MEMBER_PART_DOES_NOT_EXIST);
    }

    @DisplayName("extractSerDataMembers - members part is empty")
    @Test
    void testValidateExtractSerDataMembers2() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKCollector collector = new SKCollector();
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());
        collector.beginObject("intValue");
        collector.addProperty("kind", "specific");
        collector.addProperty("className", "int");
        collector.addProperty("type", "int");
        collector.addProperty("modifiers", 2);
        collector.end();

        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("WithAnnotatedMembers", (ObjectNode) collector.detachNode());
        }};

        collector.setTarget(USKCollectorPath.DEFAULT_CLASS_PART_PATH.getPath());
        collector.addProperty("name", "WithAnnotatedMembers");
        collector.setTarget(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH.getPath());

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                new WithAnnotatedMembers(),
                (ObjectNode) collector.getNode(),
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();
        assertThat(validationResult.isSuccess()).isFalse();
        assertThat(validationResult.getStatus()).isEqualTo(SER_DATA_MEMBER_PART_IS_EMPTY);
    }

    @DisplayName("success test")
    @Test
    void testSuccess() throws Exception {

        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(String.class, Integer.class, Float.class)
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class)
                .setValueArgumentsTypes(Integer.class)
                .build();

        OldContextProcessor<OldClassContext> classProcessor = UClassSerializationOld.createClassProcessor(
                USKClassHeaderPartHandler.DEFAULT,
                new SKSimpleChecker<Class<?>>(int.class),
                new SKSimpleChecker<String>(""),
                collectionTypeChecker,
                mapTypeChecker
        );

        OldClassContext oldClassContext = UClassSerializationOld.createClassContext(
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                null,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );

        oldClassContext.attachClass(ClassWithValue.class);
        classProcessor.handle(oldClassContext);

        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("ClassWithValue", (ObjectNode) oldClassContext.getCollector().detachNode());
        }};

        OldContextProcessor<InstanceContext> instanceProcessor = UInstanceSerialization.createInstanceProcessor();

        ClassWithValue classWithValue = new ClassWithValue();
        InstanceContext instanceContext = UInstanceSerialization.createInstanceContext(
                classNodes,
                instanceProcessor,
                classWithValue,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                new SKInstanceMembersPartHandler()
        );

        instanceProcessor.handle(instanceContext);

        ObjectNode serData = (ObjectNode) instanceContext.getCollector().detachNode();

        ClassWithValue restored = new ClassWithValue();
        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                restored,
                serData,
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );

        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();

        assertThat(validationResult.isSuccess()).isTrue();
        assertThat(validationResult.getStatus()).isEmpty();
        assertThat(mem.getMembersData("specific").size() != 0).isTrue();
    }

    @DisplayName("createNew")
    @Test
    void testCreateNew() throws Exception {

        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(String.class, Integer.class, Float.class)
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class)
                .setValueArgumentsTypes(Integer.class)
                .build();

        OldContextProcessor<OldClassContext> classProcessor = UClassSerializationOld.createClassProcessor(
                USKClassHeaderPartHandler.DEFAULT,
                new SKSimpleChecker<Class<?>>(int.class),
                new SKSimpleChecker<String>(""),
                collectionTypeChecker,
                mapTypeChecker
        );

        OldClassContext oldClassContext = UClassSerializationOld.createClassContext(
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                null,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );

        oldClassContext.attachClass(ClassWithChangedValue.class);
        classProcessor.handle(oldClassContext);

        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("ClassWithChangedValue", (ObjectNode) oldClassContext.getCollector().detachNode());
        }};

        OldContextProcessor<InstanceContext> instanceProcessor = UInstanceSerialization.createInstanceProcessor();

        InstanceContext instanceContext = UInstanceSerialization.createInstanceContext(
                classNodes,
                instanceProcessor,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                new SKInstanceMembersPartHandler()
        );

        ClassWithChangedValue classWithValue123 = new ClassWithChangedValue();
        classWithValue123.setIntValue(123);
        instanceContext.attachInstance(classWithValue123);
        instanceProcessor.handle(instanceContext);
        ObjectNode serData123 = (ObjectNode) instanceContext.getCollector().detachNode();

        ClassWithChangedValue classWithChangedValue = new ClassWithChangedValue();
        classWithChangedValue.setIntValue(456);
        instanceContext.attachInstance(classWithChangedValue);
        instanceProcessor.handle(instanceContext);
        ObjectNode serData456 = (ObjectNode) instanceContext.getCollector().detachNode();

        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                classWithValue123,
                serData123,
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );

        Des2InstanceContextStateMemento newMem = mem.createNew(classWithChangedValue, serData456);

        assertThat(classWithValue123).isNotEqualTo(classWithChangedValue);
    }

    private static class TestClassWithoutAnnotation {}

    @SkeletonClass(name = "EmptyTestClass")
    private static class EmptyTestClass {}

    @SkeletonClass(name = "WithoutAnnotatedMembers")
    private static class WithoutAnnotatedMembers{
        private int intValue;
    }

    @SkeletonClass(name = "WithAnnotatedMembers")
    private static class WithAnnotatedMembers{

        @SkeletonMember
        private int intValue;
    }

    @SkeletonClass(name = "ClassWithValue")
    private static class ClassWithValue{

        @SkeletonMember
        private int intValue = 10;
    }

    @SkeletonClass(name = "ClassWithChangedValue")
    private static class ClassWithChangedValue{

        @SkeletonMember
        private int intValue;

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassWithChangedValue that = (ClassWithChangedValue) o;
            return intValue == that.intValue;
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue);
        }
    }
}
