package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.header.SerializationHeaderVE;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.member.SerializationMemberVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SimpleSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializer.class);

    private final SerializationVisitor visitor;
    private final SerializationHeaderVE headerVE;
    private final SerializationMemberVE memberVE;

    public SimpleSerializer(SerializationVisitor visitor, SerializationHeaderVE headerVE, SerializationMemberVE memberVE) {
        this.visitor = visitor;
        this.headerVE = headerVE;
        this.memberVE = memberVE;
    }

    @Override
    public void serialize(Class clazz) {
        headerVE.setData(clazz);
        headerVE.accept(visitor);

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            memberVE.setData(field);
            memberVE.accept(visitor);
        }
    }
}
