package org.KasymbekovPN.Skeleton.lib.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class BaseISH implements InstanceSerializationHandler {

    private InstanceSerializationHandler next;
    private InstanceSerializationHandler previous;
    protected SimpleResult simpleResult;

    public BaseISH(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    public BaseISH() {
    }

    @Override
    public InstanceSerializationHandler setNext(InstanceSerializationHandler next) {
        if (this.next == null){
            this.next = next;
            this.next.setPrevious(this);
            this.next.setSimpleResult(simpleResult);
        } else {
            this.next.setNext(next);
        }

        return this;
    }

    @Override
    public void setPrevious(InstanceSerializationHandler previous) {
        this.previous = previous;
    }

    @Override
    public void setSimpleResult(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    @Override
    public SimpleResult handleHeader(Object object, Collector collector, String className, Map<String, ObjectNode> classNodes) {
        if (!runHeaderDataHandling(object, collector, className, classNodes) && next != null){
            next.handleHeader(object, collector, className, classNodes);
        }

        return simpleResult;
    }

    @Override
    public SimpleResult handleMember(Object object, Field field, Collector collector, String className, Map<String, ObjectNode> classNodes) {
        if (!runMemberDataHandling(object, field, collector, className, classNodes) && next != null){
            next.handleMember(object, field, collector, className, classNodes);
        }

        return simpleResult;
    }

    protected boolean runHeaderDataHandling(Object object, Collector collector, String className, Map<String, ObjectNode> classNodes){
        return checkHeaderData(object, className, classNodes) && fillCollector(collector);
    }

    protected boolean runMemberDataHandling(Object object, Field field, Collector collector, String className, Map<String, ObjectNode> classNodes){
        return checkMemberData(object, field, className, classNodes) && fillCollector(collector);
    }

    protected boolean checkHeaderData(Object object, String className, Map<String, ObjectNode> classNodes){
        return false;
    }

    protected boolean checkMemberData(Object object, Field field, String className, Map<String, ObjectNode> classNodes){
        return false;
    }

    protected boolean fillCollector(Collector collector){
        return false;
    }
}
