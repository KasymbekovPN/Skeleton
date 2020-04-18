package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.header.HeaderSE;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.member.MemberSE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SimpleSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SimpleSerializer.class);

    private final HeaderSE headerVE;
    private final MemberSE memberVE;
    private final Generator generator;

    public SimpleSerializer(HeaderSE headerVE, MemberSE memberVE, Generator generator) {
        this.headerVE = headerVE;
        this.memberVE = memberVE;
        this.generator = generator;
    }

    @Override
    public void serialize(Class clazz) {
        headerVE.setData(clazz);
        headerVE.handle(generator);

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            memberVE.setData(field);
            memberVE.handle(generator);
        }
    }
}
