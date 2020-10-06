package org.KasymbekovPN.Skeleton.custom.optionalConverter;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class ToInstanceOC implements OptionalConverter<Object, ObjectNode> {

    private static final Logger log = LoggerFactory.getLogger(ToInstanceOC.class);

    private final Map<String, Class<?>> classes;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final CollectorPath classPath;

    public ToInstanceOC(Map<String, Class<?>> classes,
                        ClassHeaderPartHandler classHeaderPartHandler,
                        CollectorPath classPath) {
        this.classes = classes;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classPath = classPath;
    }

    @Override
    public Optional<Object> convert(ObjectNode object) {
        Optional<Node> maybeClassPart = object.getChild(classPath);
        if (maybeClassPart.isPresent()){
            ObjectNode classPart = (ObjectNode) maybeClassPart.get();
            Optional<String> maybeName = classHeaderPartHandler.getName(classPart);
            if (maybeName.isPresent()){
                String name = maybeName.get();
                if (classes.containsKey(name)){

                    try {
                        Constructor<?> constructor = classes.get(name).getConstructor();
                        Object instance = constructor.newInstance();

                        return Optional.of(instance);
                    } catch (NoSuchMethodException |
                             InstantiationException  |
                            IllegalAccessException |
                            InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.warn("Class for name '{}' doesn't exist", name);
                }
            } else {
                log.warn("Class name doesn't exist; {} >< {}", classPath, object);
            }
        } else {
            log.warn("Class part doesn't exist; {}  >< {}", classPath, object);
        }

        return Optional.empty();
    }
}