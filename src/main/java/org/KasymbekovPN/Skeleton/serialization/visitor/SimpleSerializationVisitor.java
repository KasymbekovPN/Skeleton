package org.KasymbekovPN.Skeleton.serialization.visitor;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SimpleSerializationVisitor implements SerializationVisitor {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializationVisitor.class);

    private final Map<Class<? extends SerializationVE>, SerializationVisitorElementHandler> handlers = new HashMap<>();
    private final Generator generator;

    public SimpleSerializationVisitor(Generator generator) {
        this.generator = generator;
    }

    @Override
    public void visit(SerializationVE ve) {
        Class<? extends SerializationVE> clazz = ve.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(ve, generator);
        } else {
            log.error("The handler for {} doesn't exist", clazz);
        }
    }

    @Override
    public SerializationVisitor addHandler(Class<? extends SerializationVE> clazz,
                                           SerializationVisitorElementHandler handler) {
        handlers.put(clazz, handler);
        return this;
    }
}
