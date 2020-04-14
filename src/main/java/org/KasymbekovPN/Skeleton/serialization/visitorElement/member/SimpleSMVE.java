package org.KasymbekovPN.Skeleton.serialization.visitorElement.member;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;

import java.lang.reflect.Field;

public abstract class SimpleSMVE implements SerializationMemberVE {

    private final SerializationMemberVE next;

    private Field field;

    public SimpleSMVE(SerializationMemberVE next) {
        this.next = next;
    }

    @Override
    public void accept(SerializationVisitor visitor) {
        if (!visitor.visit(this)){
            if (next != null){
                next.accept(visitor);
            }
        }
    }

    @Override
    public void setData(Field field) {
        this.field = field;
    }

    @Override
    public Field getData() {
        return field;
    }
}
