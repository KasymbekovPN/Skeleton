package org.KasymbekovPN.Skeleton.custom.optionalConverter;

import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

public class CollectionGenerator implements OptionalConverter<Collection<Object>, String> {

    private static final Logger log = LoggerFactory.getLogger(CollectionGenerator.class);

    private final Map<String, Supplier<Collection<Object>>> generators;

    private CollectionGenerator(Map<String, Supplier<Collection<Object>>> generators) {
        this.generators = generators;
    }

    @Override
    public Optional<Collection<Object>> convert(String object) {
        if (generators.containsKey(object)){
            return Optional.of(generators.get(object).get());
        }

        log.warn("Generator with ID {} doesn't exist", object);
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionGenerator that = (CollectionGenerator) o;
        return Objects.equals(generators, that.generators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generators);
    }

    public static class Builder{

        private Map<String, Supplier<Collection<Object>>> generators = new HashMap<>();

        public Builder add(String generatorId, Supplier<Collection<Object>> generator){
            generators.put(generatorId, generator);
            return this;
        }

        public OptionalConverter<Collection<Object>, String> build() throws CollectionGeneratorBuildNoOneGenerator, CollectionGeneratorBuildSomeGeneratorsReturnNull {
            checkGeneratorsNumber();
            checkReturned();
            return new CollectionGenerator(generators);
        }

        void checkGeneratorsNumber() throws CollectionGeneratorBuildNoOneGenerator {
            if (generators.size() == 0){
                throw new CollectionGeneratorBuildNoOneGenerator("");
            }
        }

        void checkReturned() throws CollectionGeneratorBuildSomeGeneratorsReturnNull {
            StringBuilder ids = new StringBuilder();
            for (Map.Entry<String, Supplier<Collection<Object>>> entry : generators.entrySet()) {
                String generatorId = entry.getKey();
                Supplier<Collection<Object>> generator = entry.getValue();
                if (generator.get() == null){
                    if (ids.length() != 0){
                        ids.append(",");
                    }
                    ids.append(generatorId);
                }
            }

            if (ids.length() != 0){
                throw new CollectionGeneratorBuildSomeGeneratorsReturnNull(ids.toString());
            }
        }
    }
}
