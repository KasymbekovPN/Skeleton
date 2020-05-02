package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SimpleSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializer.class);

    private final SerializationElementHandler classSEH;
    private final SerializationElementHandler memberSEH;
    private final SerializationElementHandler constructorSEH;
    private final Collector collector;
    private final AnnotationHandler classAH;
    private final AnnotationHandler memberAH;
    private final AnnotationHandler constructorAH;

    public SimpleSerializer(SerializationElementHandler classSEH,
                            SerializationElementHandler memberSEH,
                            SerializationElementHandler constructorSEH,
                            Collector collector,
                            AnnotationHandler classAH,
                            AnnotationHandler memberAH,
                            AnnotationHandler constructorAH) {
        this.classSEH = classSEH;
        this.memberSEH = memberSEH;
        this.constructorSEH = constructorSEH;
        this.collector = collector;
        this.classAH = classAH;
        this.memberAH = memberAH;
        this.constructorAH = constructorAH;
    }

    @Override
    public void serialize(Class<?> clazz) {
        classSEH.handle(clazz, collector, classAH);
        for (Field field : clazz.getDeclaredFields()) {
            memberSEH.handle(field, collector, memberAH);
        }
        constructorSEH.handle(clazz, collector, constructorAH);
    }
}
