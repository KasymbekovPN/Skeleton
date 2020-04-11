package org.KasymbekovPN.Skeleton.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JsonWriter implements Writer {

    private static final Logger log = LoggerFactory.getLogger(JsonWriter.class);

    private Map<Class<? extends GeneratorNode>, GeneratorNodeWrHand> handlers = new HashMap<>();
    private StringBuilder buffer = new StringBuilder();

    @Override
    public StringBuilder getBuffer() {
        return buffer;
    }

    @Override
    public void write(GeneratorNode generatorNode) {
        Class<? extends GeneratorNode> clazz = generatorNode.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(generatorNode);
        } else {
            log.error("The handler for {} doesn't exist", clazz.getCanonicalName());
        }
    }

    @Override
    public void addHandler(Class<? extends GeneratorNode> clazz, GeneratorNodeWrHand handler) {
        handlers.put(clazz, handler);
    }
}
