package org.KasymbekovPN.Skeleton.generator.writer;

import org.KasymbekovPN.Skeleton.generator.formatter.Formatter;
import org.KasymbekovPN.Skeleton.generator.writeHandler.WritingHandler;
import org.KasymbekovPN.Skeleton.generator.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SimpleWriter implements Writer {

    private static final Logger log = LoggerFactory.getLogger(SimpleWriter.class);

    private Map<Class<? extends Node>, WritingHandler> handlers = new HashMap<>();
    private StringBuilder buffer = new StringBuilder();

    private final Formatter formatter;

    public SimpleWriter(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public StringBuilder getBuffer() {
        return buffer;
    }

    @Override
    public Formatter getFormatter() {
        return formatter;
    }

    @Override
    public void write(Node node) {
        Class<? extends Node> clazz = node.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(node);
        } else {
            log.error("The handler for {} doesn't exist", clazz.getCanonicalName());
        }
    }

    @Override
    public void addHandler(Class<? extends Node> clazz, WritingHandler handler) {
        handlers.put(clazz, handler);
    }
}
