package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.serialization.visitor.Visitor;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.HeadVisitorElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(JsonSerializer.class);

    private Visitor visitor;
    private HeadVisitorElement headVE;

    public JsonSerializer(Visitor visitor, HeadVisitorElement headVE) {
        this.visitor = visitor;
        this.headVE = headVE;
    }

    public String serialize(Class clazz) {

        //<
        log.info("clazz : {}", clazz);
        log.info("name : {}", clazz.getCanonicalName());
        //<

        headVE.set(clazz);
        headVE.accept(visitor);

        return null;
    }
}
