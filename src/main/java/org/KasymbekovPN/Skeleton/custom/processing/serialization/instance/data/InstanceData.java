package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//< rename -> ??Context
public interface InstanceData {
    List<String> getTaskIds();
    List<String> getWrapperIds();
    Object getInstance();
    Optional<String> getClassName();
    Optional<ObjectNode> getClassNode(String className);
    Map<String, Field> getFields(String kind);
    Collector getCollector();
}
