package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationHeaderVE;

public class SimpleSerializer implements Serializer {

    private final SerializationVisitor visitor;
    private final SerializationHeaderVE headerVE;

    public SimpleSerializer(SerializationVisitor visitor, SerializationHeaderVE headerVE) {
        this.visitor = visitor;
        this.headerVE = headerVE;
    }

    @Override
    public void serialize(Class clazz) {
        headerVE.setData(clazz);
        headerVE.accept(visitor);
    }
}
