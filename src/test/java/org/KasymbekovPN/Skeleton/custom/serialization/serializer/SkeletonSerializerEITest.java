package org.KasymbekovPN.Skeleton.custom.serialization.serializer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SkeletonSerializerEI. Testing of:")
public class SkeletonSerializerEITest {

    private static final String SUFFIX = "EI";

    private static Object[][] getTestData(){
        return new Object[][]{
                {SkeletonSerializerEI.Entity.CLASS, SUFFIX, Modifier.STATIC | Modifier.PUBLIC},
                {SkeletonSerializerEI.Entity.MEMBER, SUFFIX, Modifier.STATIC | Modifier.PUBLIC},
                {SkeletonSerializerEI.Entity.CONSTRUCTOR, SUFFIX, Modifier.STATIC | Modifier.PUBLIC},
                {SkeletonSerializerEI.Entity.METHOD, SUFFIX, Modifier.STATIC | Modifier.PUBLIC}
        };
    };

    @DisplayName("creation methods")
    @ParameterizedTest
    @MethodSource("getTestData")
    void test(SkeletonSerializerEI.Entity entity, String suffix, int modifiers) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = entity.toString().toLowerCase() + suffix;
        Class<?> clazz = SkeletonSerializerEI.class;
        Method method = clazz.getMethod(methodName);

        assertThat(method.getModifiers()).isEqualTo(modifiers);
        assertThat(method.invoke(null)).isEqualTo(new SkeletonSerializerEI(entity));
    }
}
