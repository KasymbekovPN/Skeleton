package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SimpleSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializer.class);

    private final SerializationElementHandler classSEH;
    private final SerializationElementHandler memberSEH;
    private final SerializationElementHandler constructorSEH;
    //<
    private final Collector collector;
//    private final AnnotationHandler classAH;
//    private final AnnotationHandler memberAH;
//    private final AnnotationHandler constructorAH;
//    private final CollectorCheckingHandler collectorCheckingHandler;

    public SimpleSerializer(SerializationElementHandler classSEH,
                            SerializationElementHandler memberSEH,
                            SerializationElementHandler constructorSEH,
                            Collector collector) {
        this.classSEH = classSEH;
        this.memberSEH = memberSEH;
        this.constructorSEH = constructorSEH;
        this.collector = collector;
    }


    //<
//    public SimpleSerializer(SerializationElementHandler classSEH,
//                            SerializationElementHandler memberSEH,
//                            SerializationElementHandler constructorSEH,
//                            Collector collector,
//                            AnnotationHandler classAH,
//                            AnnotationHandler memberAH,
//                            AnnotationHandler constructorAH,
//                            CollectorCheckingHandler collectorCheckingHandler) {
//        this.classSEH = classSEH;
//        this.memberSEH = memberSEH;
//        this.constructorSEH = constructorSEH;
//        this.collector = collector;
//        this.classAH = classAH;
//        this.memberAH = memberAH;
//        this.constructorAH = constructorAH;
//        this.collectorCheckingHandler = collectorCheckingHandler;
//    }

    @Override
    public void serialize(Class<?> clazz) {
        classSEH.handle(clazz, collector);
        for (Field field : clazz.getDeclaredFields()) {
            memberSEH.handle(field, collector);
        }
        constructorSEH.handle(clazz, collector);
    }
}
