package org.KasymbekovPN.Skeleton.serialization.serializer;

import org.KasymbekovPN.Skeleton.serialization.visitor._Visitor;
import org.KasymbekovPN.Skeleton.serialization.visitorElement._HeadVisitorElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _JsonSerializer implements _Serializer {

    private static final Logger log = LoggerFactory.getLogger(_JsonSerializer.class);

    private _Visitor visitor;
    private _HeadVisitorElement headVE;

    public _JsonSerializer(_Visitor visitor, _HeadVisitorElement headVE) {
        this.visitor = visitor;
        this.headVE = headVE;
    }

    public String serialize(Class clazz) {
        headVE.set(clazz);
        headVE.accept(visitor);

        return null;
    }
}
