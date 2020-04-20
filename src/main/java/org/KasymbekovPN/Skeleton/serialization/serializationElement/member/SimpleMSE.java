package org.KasymbekovPN.Skeleton.serialization.serializationElement.member;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * MSE - Member Serialization Element
 */
public class SimpleMSE implements MemberSE {

    private final SerializationElementHandler handler;

    private MemberSE next;
    private Field field;

    public SimpleMSE(SerializationElementHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handle(Generator generator) {
        if (handler.handle(this, generator)){
            return true;
        } else if (next != null){
            return next.handle(generator);
        } else {
            return false;
        }
    }

    @Override
    public void setData(Field field) {
        this.field = field;
        if (this.next != null){
            this.next.setData(field);
        }
    }

    @Override
    public Field getData() {
        return field;
    }

    @Override
    public MemberSE setNext(MemberSE next) {
        if (this.next == null){
            this.next = next;
        } else {
            this.next.setNext(next);
        }
        return this;
    }

    @Override
    public MemberSE setNativeNext(SerializationElementHandler seh) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (next == null){
            Constructor<? extends MemberSE> constructor = getClass().getConstructor(SerializationElementHandler.class);
            next = constructor.newInstance(seh);
        } else {
            next.setNativeNext(seh);
        }

        return this;
    }
}
