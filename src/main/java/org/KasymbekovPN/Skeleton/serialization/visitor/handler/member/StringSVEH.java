package org.KasymbekovPN.Skeleton.serialization.visitor.handler.member;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.StringSMVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StringSVEH implements SerializationVisitorElementHandler {

    private static final Logger log = LoggerFactory.getLogger(StringSVEH.class);
    private static final Class<? extends Annotation> ANNOTATION = Skeleton.class;
    private static final Class<?> TYPE = String.class;
    private static final List<String> PATH = new ArrayList<>(){{add("members");}};

    @Override
    public boolean handle(SerializationVE serializationVE, Generator generator) {

        StringSMVE ve = (StringSMVE) serializationVE;
        Field field = ve.getData();

        if (field.getType().equals(TYPE) && field.isAnnotationPresent(ANNOTATION)){
            String name = field.getName();
            int modifiers = field.getModifiers();

            generator.setTarget(PATH);
            generator.beginObject(name);
            generator.addProperty("type", TYPE.getCanonicalName());
            generator.addProperty("modifiers", modifiers);
            generator.reset();

            return true;
        }

        return false;
    }
}
