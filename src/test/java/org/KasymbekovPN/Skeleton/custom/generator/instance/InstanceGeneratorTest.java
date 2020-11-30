package org.KasymbekovPN.Skeleton.custom.generator.instance;

import org.KasymbekovPN.Skeleton.custom.functional.generator.InstanceGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildSomeGeneratorsReturnNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

@DisplayName("InstanceGenerator. Testing of:")
public class InstanceGeneratorTest {

    private static Object[][] getCollectionTestData(){
        return new Object[][]{
                {"java.util.Set", new HashSet<Object>(), true},
                {"java.util.List", new ArrayList<Object>(), true},
                {"wrong", null, false}
        };
    }

    private static Object[][] getMapTestData(){
        return new Object[][]{
                {"java.util.Map", new HashMap<Object, Object>(), true},
                {"wrong", null, false}
        };
    }

    private static Object[][] getCustomTestData(){
        return new Object[][]{
                {"TestClass0", new TestClass0(), true},
                {"TestClass1", new TestClass1(), true},
                {"wrong", null, false}
        };
    }

    @DisplayName("for collection")
    @ParameterizedTest
    @MethodSource("getCollectionTestData")
    void testForCollection(String generatorId, Collection<Object> check, boolean isPresent) throws InstanceGeneratorBuildSomeGeneratorsReturnNull, InstanceGeneratorBuildNoOneGenerator {
        InstanceGenerator<Collection<Object>> generator = new InstanceGenerator.Builder<Collection<Object>>()
                .add("java.util.Set", HashSet::new)
                .add("java.util.List", ArrayList::new)
                .build();

        Optional<Collection<Object>> maybeCollection = generator.apply(generatorId);
        boolean present = maybeCollection.isPresent();
        Assertions.assertThat(isPresent).isEqualTo(present);
        if (present){
            Assertions.assertThat(check).isEqualTo(maybeCollection.get());
        }
    }

    @DisplayName("for map")
    @ParameterizedTest
    @MethodSource("getMapTestData")
    void testForMap(String generatorId, Map<Object, Object> check, boolean isPresent) throws InstanceGeneratorBuildSomeGeneratorsReturnNull, InstanceGeneratorBuildNoOneGenerator {
        InstanceGenerator<Map<Object,Object>> generator = new InstanceGenerator.Builder<Map<Object, Object>>()
                .add("java.util.Map", HashMap::new)
                .build();

        Optional<Map<Object,Object>> maybeMap = generator.apply(generatorId);
        boolean present = maybeMap.isPresent();
        Assertions.assertThat(isPresent).isEqualTo(present);
        if (present){
            Assertions.assertThat(check).isEqualTo(maybeMap.get());
        }
    }

    @DisplayName("for instance")
    @ParameterizedTest
    @MethodSource("getCustomTestData")
    void testForMap(String generatorId, Object check, boolean isPresent) throws InstanceGeneratorBuildSomeGeneratorsReturnNull, InstanceGeneratorBuildNoOneGenerator {
        InstanceGenerator<Object> generator = new InstanceGenerator.Builder<Object>()
                .add("TestClass0", TestClass0::new)
                .add("TestClass1", TestClass1::new)
                .build();

        Optional<Object> maybeInstance = generator.apply(generatorId);
        boolean present = maybeInstance.isPresent();
        Assertions.assertThat(isPresent).isEqualTo(present);
        if (present){
            Assertions.assertThat(check).isEqualTo(maybeInstance.get());
        }
    }

    @DisplayName("build - no one generator")
    @Test
    void testBuild_noOneGenerator(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            new InstanceGenerator.Builder<Object>().build();
        });
        Assertions.assertThat(throwable).isInstanceOf(InstanceGeneratorBuildNoOneGenerator.class);
    }

    @DisplayName("build - some generator return null")
    @Test
    void testBuild_someGeneratorReturnNull(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            new InstanceGenerator.Builder<Object>()
                    .add("TestClass0", TestClass0::new)
                    .add("TestClass1", ()->{return null;})
                    .build();
        });
        Assertions.assertThat(throwable).isInstanceOf(InstanceGeneratorBuildSomeGeneratorsReturnNull.class);
    }


    private static class TestClass0{
        private int i = 100;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass0 that = (TestClass0) o;
            return i == that.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i);
        }
    }

    private static class TestClass1{
        private int i = 200;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestClass1 that = (TestClass1) o;
            return i == that.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i);
        }
    }
}
