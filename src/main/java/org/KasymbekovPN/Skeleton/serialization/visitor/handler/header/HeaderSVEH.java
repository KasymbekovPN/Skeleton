package org.KasymbekovPN.Skeleton.serialization.visitor.handler.header;

import org.KasymbekovPN.Skeleton.annotation.Skeleton;
import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.header.SimpleSHVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderSVEH implements SerializationVisitorElementHandler {

    private static final Logger log = LoggerFactory.getLogger(HeaderSVEH.class);
    private static final Class ANNOTATION = Skeleton.class;

    @Override
    public boolean handle(SerializationVE serializationVE, Generator generator) {

        SimpleSHVE ve = (SimpleSHVE) serializationVE;
        Class clazz = ve.getData();
        if (clazz.isAnnotationPresent(ANNOTATION)){
            generator.beginObject("class");
            generator.addProperty("name", clazz.getCanonicalName());
            generator.end();
            return true;
        }

        log.error("{} doesn't annotated of {}", clazz, ANNOTATION);
        return false;
    }
}
