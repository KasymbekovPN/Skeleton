package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.condition.Condition;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SimpleSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializer.class);

    private final SerializationElementHandler classSEH;
    private final SerializationElementHandler memberSEH;
    private final Generator generator;
    private final Condition condition;

    public SimpleSerializer(SerializationElementHandler classSEH,
                            SerializationElementHandler memberSEH,
                            Generator generator,
                            Condition condition) {
        this.classSEH = classSEH;
        this.memberSEH = memberSEH;
        this.generator = generator;
        this.condition = condition;
    }

    @Override
    public void serialize(Class<?> clazz) {
        classSEH.handle(clazz, generator, condition);
        for (Field field : clazz.getDeclaredFields()) {
            memberSEH.handle(field, generator, condition);
        }
    }
}
