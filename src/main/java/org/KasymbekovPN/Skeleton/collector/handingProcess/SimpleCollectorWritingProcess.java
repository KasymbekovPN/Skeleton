package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SimpleCollectorWritingProcess implements CollectorWritingProcess {

    private static final Logger log = LoggerFactory.getLogger(SimpleCollectorWritingProcess.class);

//    private Map<Class<? extends Node>, WritingHandler> handlers = new HashMap<>();
    //<
    private Map<Class<? extends Node>, CollectorHandlingProcessHandler> handlers = new HashMap<>();
    private StringBuilder buffer = new StringBuilder();

    private final Formatter formatter;

    public SimpleCollectorWritingProcess(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public StringBuilder getBuffer() {
        return buffer;
    }

    @Override
    public Formatter getFormatter() {
        return formatter;
    }

//    @Override
//    public void write(Node node) {
//        Class<? extends Node> clazz = node.getClass();
//        if (handlers.containsKey(clazz)){
//            handlers.get(clazz).handle(node);
//        } else {
//            log.error("The handler for {} doesn't exist", clazz.getCanonicalName());
//        }
//    }
    //<


    @Override
    public void doIt(Node node) {
        Class<? extends Node> clazz = node.getClass();
        if (handlers.containsKey(clazz)){
            handlers.get(clazz).handle(node);
        } else {
            log.error("The handler for {} doesn't exist", clazz.getCanonicalName());
        }
    }

    @Override
    public void addHandler(Class<? extends Node> clazz, CollectorHandlingProcessHandler collectorHandlingProcessHandler) {
        handlers.put(clazz, collectorHandlingProcessHandler);
    }
    //<
//    @Override
//    public void addHandler(Class<? extends Node> clazz, WritingHandler handler) {
//        handlers.put(clazz, handler);
//    }
}
