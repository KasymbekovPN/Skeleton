package org.KasymbekovPN.Skeleton.serialization.visitor.handler.member;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.NumberSMVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NumberSVEH implements SerializationVisitorElementHandler {

    private static final Logger log = LoggerFactory.getLogger(NumberSVEH.class);
    private static final Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final Class<?> EXTENDED_TYPE = Number.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};

    @Override
    public boolean handle(SerializationVE serializationVE, Generator generator) {
        Field field = ((NumberSMVE) serializationVE).getData();

        Class<?> type = field.getType();
        if (EXTENDED_TYPE.isAssignableFrom(type) && field.isAnnotationPresent(ANNOTATION)){
            String name = field.getName();
            int modifiers = field.getModifiers();

            generator.setTarget(PATH);
            generator.beginObject(name);
            generator.addProperty("type", type.getCanonicalName());
            generator.addProperty("modifiers", modifiers);
            generator.reset();

            return true;
        }

        return false;
    }
}
