package org.KasymbekovPN.Skeleton.serialization.serializationElement.header;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.utils.GeneralCondition;

/**
 * HSE - Header Serialization Element
 */
public class SimpleHSE implements HeaderSE {

    private final SerializationElementHandler handler;
    private final GeneralCondition generalCondition;

    private Class<?> clazz;

    public SimpleHSE(SerializationElementHandler handler, GeneralCondition generalCondition) {
        this.handler = handler;
        this.generalCondition = generalCondition;
    }

    @Override
    public boolean handle(Generator generator) {
        return handler.handle(this, generator, generalCondition);
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
