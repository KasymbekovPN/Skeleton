package org.KasymbekovPN.Skeleton.serialization.visitor;

import org.KasymbekovPN.Skeleton.serialization.visitor.handler._VisitorHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement._VisitorElement;

public interface _Visitor {
    void visit(_VisitorElement visitorElement);
    _Visitor addHandler(Class<? extends _VisitorElement> clazz, _VisitorHandler visitorHandler);
}
