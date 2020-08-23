package org.KasymbekovPN.Skeleton.lib.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.Field;
import java.util.Map;

public interface InstanceSerializationHandler {
    InstanceSerializationHandler setNext(InstanceSerializationHandler next);
    void setPrevious(InstanceSerializationHandler previous);
    void setResult(Result result);
    Result handleHeader(Object object, Collector collector, String className, Map<String, ObjectNode> classNodes);
    Result handleMember(Object object, Field field, Collector collector, String className, Map<String, ObjectNode> classNodes);
}
