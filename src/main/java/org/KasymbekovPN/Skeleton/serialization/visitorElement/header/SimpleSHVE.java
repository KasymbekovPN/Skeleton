package org.KasymbekovPN.Skeleton.serialization.visitorElement.header;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;

public class SimpleSHVE implements SerializationHeaderVE {

    private Class clazz;

    @Override
    public void accept(SerializationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void setData(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class getData() {
        return clazz;
    }
}
