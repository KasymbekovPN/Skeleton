package org.KasymbekovPN.Skeleton.custom.optionalConverter;

import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@DisplayName("CollectionGenerator. Testing of:")
public class CollectionGeneratorTest {

    private static Object[][] getTestDataOfConvertMethod(){
        return new Object[][]{
                {"java.util.Set", new HashSet<Object>(), true},
                {"java.util.List", new ArrayList<Object>(), true},
                {"wrong", null, false}
        };
    }

    @DisplayName("convert method")
    @ParameterizedTest
    @MethodSource("getTestDataOfConvertMethod")
    void testConvertMethod(String generatorId, Collection<Object> collection, boolean isPresent) throws CollectionGeneratorBuildNoOneGenerator, CollectionGeneratorBuildSomeGeneratorsReturnNull {
        OptionalConverter<Collection<Object>, String> collectionGenerator
                = new CollectionGenerator.Builder()
                .add("java.util.Set", HashSet::new)
                .add("java.util.List", ArrayList::new)
                .build();

        Optional<Collection<Object>> maybeCollection = collectionGenerator.convert(generatorId);
        boolean present = maybeCollection.isPresent();
        Assertions.assertThat(present).isEqualTo(isPresent);
        if (present){
            Assertions.assertThat(maybeCollection.get()).isEqualTo(collection);
        }
    }

    @DisplayName("build - no one generator")
    @Test
    void testBuild_noOneGenerator(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            new CollectionGenerator.Builder().build();
        });
        Assertions.assertThat(throwable).isInstanceOf(CollectionGeneratorBuildNoOneGenerator.class);
    }

    @DisplayName("build - some generator return null")
    @Test
    void testBuild_someGeneratorReturnNull(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            new CollectionGenerator.Builder()
                    .add("java.util.Set", () -> {return null;})
                    .add("java.util.List", ArrayList::new)
                    .build();
        });
        Assertions.assertThat(throwable).isInstanceOf(CollectionGeneratorBuildSomeGeneratorsReturnNull.class);
    }
}
