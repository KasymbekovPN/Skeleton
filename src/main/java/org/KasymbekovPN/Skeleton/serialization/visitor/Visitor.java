package org.KasymbekovPN.Skeleton.serialization.visitor;

import org.KasymbekovPN.Skeleton.serialization.visitor.handler.VisitorHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.VisitorElement;

public interface Visitor {
    void visit(VisitorElement visitorElement);
    Visitor addHandler(Class<? extends VisitorElement> clazz, VisitorHandler visitorHandler);
}
