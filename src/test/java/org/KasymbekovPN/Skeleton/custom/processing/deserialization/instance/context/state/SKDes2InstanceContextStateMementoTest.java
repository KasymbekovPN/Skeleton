package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state;

import org.KasymbekovPN.Skeleton.custom.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SKDes2InstanceContextStateMemento. Testing of:")
public class SKDes2InstanceContextStateMementoTest {

    private static final String EXTRACT_CLASS_NODE_TEMPLATE = "Class Node for '%s' doesn't exist.";

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

    private static class TestClassWithoutAnnotation {}

    @SkeletonClass(name = "EmptyTestClass")
    private static class EmptyTestClass {}
}
