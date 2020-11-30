package org.KasymbekovPN.Skeleton.custom.functional.generator;

import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class InstanceGenerator<R> implements OptFunction<String, R> {

    private static final Logger log = LoggerFactory.getLogger(InstanceGenerator.class);

    private final Map<String, Supplier<R>> generators;

    private InstanceGenerator(Map<String, Supplier<R>> generators) {
        this.generators = generators;
    }

    @Override
    public Optional<R> apply(String var) {
        if (generators.containsKey(var)){
            R result = generators.get(var).get();
            if (result != null){
                return Optional.of(result);
            } else {
                log.warn("The generator {} result is null", var);
            }
        } else {
            log.warn("Generator with ID {} doesn't exist", var);
        }

        return Optional.empty();
    }

    public static class Builder<R>{

        private final Map<String, Supplier<R>> generators = new HashMap<>();

        public Builder<R> add(String generatorId, Supplier<R> generator){
            generators.put(generatorId, generator);
            return this;
        }

        public InstanceGenerator<R> build() throws InstanceGeneratorBuildNoOneGenerator, InstanceGeneratorBuildSomeGeneratorsReturnNull {
            checkGeneratorsNumber();
            checkReturned();
            return new InstanceGenerator<>(generators);
        }

        void checkGeneratorsNumber() throws InstanceGeneratorBuildNoOneGenerator {
            if (generators.size() == 0){
                throw new InstanceGeneratorBuildNoOneGenerator("");
            }
        }

        void checkReturned() throws InstanceGeneratorBuildSomeGeneratorsReturnNull {
            StringBuilder ids = new StringBuilder();
            for (Map.Entry<String, Supplier<R>> entry : generators.entrySet()) {
                String generatorId = entry.getKey();
                Supplier<R> generator = entry.getValue();
                if (generator.get() == null){
                    if (ids.length() != 0){
                        ids.append(",");
                    }
                    ids.append(generatorId);
                }
            }

            if (ids.length() != 0){
                throw new InstanceGeneratorBuildSomeGeneratorsReturnNull(ids.toString());
            }
        }
    }
}
