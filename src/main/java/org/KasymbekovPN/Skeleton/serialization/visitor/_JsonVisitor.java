package org.KasymbekovPN.Skeleton.serialization.visitor;

import org.KasymbekovPN.Skeleton.serialization.visitor.handler._VisitorHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement._VisitorElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class _JsonVisitor implements _Visitor {

    private static final Logger log = LoggerFactory.getLogger(_JsonVisitor.class);

    private Map<Class<? extends _VisitorElement>, _VisitorHandler> handlers = new HashMap<Class<? extends _VisitorElement>, _VisitorHandler>();

    public void visit(_VisitorElement visitorElement) {
        Class<? extends _VisitorElement> clazz = visitorElement.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(visitorElement);
        } else {
            log.error("Handler for ket '{}' doesn't exist", clazz);
        }
    }

    public _Visitor addHandler(Class<? extends _VisitorElement> clazz, _VisitorHandler visitorHandler) {
        handlers.put(clazz, visitorHandler);
        return this;
    }
}
