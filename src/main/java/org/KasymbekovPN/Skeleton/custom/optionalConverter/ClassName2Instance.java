package org.KasymbekovPN.Skeleton.custom.optionalConverter;

import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class ClassName2Instance implements OptionalConverter<Object, String> {

    private static final Logger log = LoggerFactory.getLogger(ClassName2Instance.class);

    private final Map<String, Class<?>> classes;

    public ClassName2Instance(Map<String, Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Optional<Object> convert(String object) {

        if (classes.containsKey(object)){
            try {
                Constructor<?> constructor = classes.get(object).getConstructor();
                Object instance = constructor.newInstance();

                return Optional.of(instance);
            } catch (NoSuchMethodException |
                     InstantiationException |
                     IllegalAccessException |
                     InvocationTargetException e) {
                log.warn("Failure attempt of instance creation for '{}'", object);
            }
        } else {
            log.warn("Class Name '{}' doesn't exist", object);
        }

        return Optional.empty();
    }
}
