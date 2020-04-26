package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
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
    private final AnnotationConditionHandler classACH;
    private final AnnotationConditionHandler memberACH;

    public SimpleSerializer(SerializationElementHandler classSEH,
                            SerializationElementHandler memberSEH,
                            Generator generator,
                            AnnotationConditionHandler classACH,
                            AnnotationConditionHandler memberACH) {
        this.classSEH = classSEH;
        this.memberSEH = memberSEH;
        this.generator = generator;
        this.classACH = classACH;
        this.memberACH = memberACH;
    }

    @Override
    public void serialize(Class<?> clazz) {
        classSEH.handle(clazz, generator, classACH);
        for (Field field : clazz.getDeclaredFields()) {
            memberSEH.handle(field, generator, memberACH);
        }
    }
}
