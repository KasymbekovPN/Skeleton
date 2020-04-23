package org.KasymbekovPN.Skeleton.serialization.handler.header;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.header.SimpleHSE;
import org.KasymbekovPN.Skeleton.utils.GeneralCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * SEH - Serialization Element Handler
 */
public class HeaderSEH implements SerializationElementHandler {

    private static final Logger log = LoggerFactory.getLogger(HeaderSEH.class);
    private static Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final List<String> PATH = new ArrayList<>(){{add("class");}};

    @Override
    public boolean handle(SerializationElement serializationElement, Generator generator, GeneralCondition generalCondition) {
        SimpleHSE ve = (SimpleHSE) serializationElement;
        Class<?> clazz = ve.getData();

        if (clazz.isAnnotationPresent(ANNOTATION)){
            generalCondition.setData(clazz.getAnnotation(ANNOTATION));

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
