package org.KasymbekovPN.Skeleton.lib.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

public abstract class BaseISH implements InstanceSerializationHandler {

    private InstanceSerializationHandler next;

    @Override
    public InstanceSerializationHandler setNext(InstanceSerializationHandler next) {
        if (this.next == null){
            this.next = next;
        } else {
            this.next.setNext(next);
        }

        return this;
    }

    @Override
    public void handle(Object object, Collector collector, ObjectNode classNode) {
        if (!runHandlingImplementation(object, collector, classNode) && next != null){
            next.handle(object, collector, classNode);
        }
    }

    protected boolean runHandlingImplementation(Object object, Collector collector, ObjectNode classNode){
        if (checkData(object, classNode)){
            return fillCollector(collector);
        }

        return false;
    }

    protected boolean checkData(Object object, ObjectNode classNode){
        return false;
    }

    protected boolean fillCollector(Collector collector){
        return false;
    }
}
