package org.KasymbekovPN.Skeleton.serialization.visitor.handler.header;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.header.SimpleSHVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class HeaderSVEH implements SerializationVisitorElementHandler {

    private static final Logger log = LoggerFactory.getLogger(HeaderSVEH.class);
    private static Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final List<String> PATH = new ArrayList<>(){{add("class");}};

    @Override
    public boolean handle(SerializationVE serializationVE, Generator generator) {
        SimpleSHVE ve = (SimpleSHVE) serializationVE;
        Class<?> clazz = ve.getData();

        if (clazz.isAnnotationPresent(ANNOTATION)){
            String name = clazz.getCanonicalName();
            int modifiers = clazz.getModifiers();

            generator.setTarget(PATH);
            generator.beginObject(name);
            generator.addProperty("modifiers", modifiers);
            generator.reset();

            return true;
        }
        log.error("{} doesn't annotated of {}", clazz, ANNOTATION);
        return false;
    }
}
