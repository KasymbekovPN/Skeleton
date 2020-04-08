package org.KasymbekovPN.Skeleton.serialization.visitor;

import org.KasymbekovPN.Skeleton.serialization.visitor.handler.VisitorHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.VisitorElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JsonVisitor implements Visitor {

    private static final Logger log = LoggerFactory.getLogger(JsonVisitor.class);

    private Map<Class<? extends VisitorElement>, VisitorHandler> handlers = new HashMap<Class<? extends VisitorElement>, VisitorHandler>();

    public void visit(VisitorElement visitorElement) {
        Class<? extends VisitorElement> clazz = visitorElement.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(visitorElement);
        } else {
            log.error("Handler for ket '{}' doesn't exist", clazz);
        }
    }

    public Visitor addHandler(Class<? extends VisitorElement> clazz, VisitorHandler visitorHandler) {
        handlers.put(clazz, visitorHandler);
        return this;
    }
}
