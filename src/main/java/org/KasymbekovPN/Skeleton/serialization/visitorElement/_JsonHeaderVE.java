package org.KasymbekovPN.Skeleton.serialization.visitorElement;

import org.KasymbekovPN.Skeleton.serialization.visitor._Visitor;

public class _JsonHeaderVE implements _HeadVisitorElement {

    private Class clazz;

    @Override
    public void set(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void accept(_Visitor visitor) {
        visitor.visit(this);
    }

    //    @Override
//    public void accept(Visitor visitor) {
//    }
//
//    @Override
//    public void append(VisitorElement visitorElement) {
//    }
}
