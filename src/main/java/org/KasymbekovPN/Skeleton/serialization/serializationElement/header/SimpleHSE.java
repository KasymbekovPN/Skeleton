package org.KasymbekovPN.Skeleton.serialization.serializationElement.header;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;

/**
 * HSE - Header Serialization Element
 */
public class SimpleHSE implements HeaderSE {

    private final SerializationElementHandler handler;

    private Class<?> clazz;

    public SimpleHSE(SerializationElementHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handle(Generator generator) {
        return handler.handle(this, generator);
    }

    @Override
    public void setData(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<?> getData() {
        return clazz;
    }
}
