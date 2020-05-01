package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.condition.AnnotationHandler;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SimpleSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializer.class);

    private final SerializationElementHandler classSEH;
    private final SerializationElementHandler memberSEH;
    private final Collector collector;
    private final AnnotationHandler classAH;
    private final AnnotationHandler memberAH;

    public SimpleSerializer(SerializationElementHandler classSEH,
                            SerializationElementHandler memberSEH,
                            Collector collector,
                            AnnotationHandler classAH,
                            AnnotationHandler memberAH) {
        this.classSEH = classSEH;
        this.memberSEH = memberSEH;
        this.collector = collector;
        this.classAH = classAH;
        this.memberAH = memberAH;
    }

    @Override
    public void serialize(Class<?> clazz) {
        classSEH.handle(clazz, collector, classAH);
        for (Field field : clazz.getDeclaredFields()) {
            memberSEH.handle(field, collector, memberAH);
        }
    }
}
