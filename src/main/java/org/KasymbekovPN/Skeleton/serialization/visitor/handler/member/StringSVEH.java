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
//    private static final String TYPE = TARGET_TYPE.getCanonicalName();
//    private static final String ENTITY = "member";
    private static final List<String> TARGET_PATH = new ArrayList<>(){{add("members");}};

    @Override
    public boolean handle(SerializationVE serializationVE, Generator generator) {

        StringSMVE ve = (StringSMVE) serializationVE;
        Field field = ve.getData();

        if (field.getType().equals(TYPE) && field.isAnnotationPresent(ANNOTATION)){
            String name = field.getName();
            int modifiers = field.getModifiers();

//            generator.setTarget(TARGET_PATH);

//            String key = String.valueOf((ENTITY + String.valueOf(modifiers) + TYPE + name).hashCode());

//            generator.beginObject(key);
//            generator.addProperty("entity", ENTITY);
//            generator.addProperty("type", TYPE);
//            generator.addProperty("modifiers", modifiers);
//            generator.addProperty("name", name);
//            generator.end();

            return true;
        }

        return false;
    }
}
