package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//< rename -> ??Context
public interface InstanceContext {
    List<String> getTaskIds();
    List<String> getWrapperIds();
    Object getInstance();
    Optional<String> getClassName();
    Optional<ObjectNode> getClassNode(String className);
    Map<String, Field> getFields(String kind);
    Collector getCollector();
    Optional<ObjectNode> getClassPart(String className);
    Optional<ObjectNode> getMembersPart(String className);
    Optional<List<String>> getClassPath(String className);
    Optional<List<String>> getMembersPath(String className);
    InstanceContext createNew(Object instance);
    Processor<InstanceContext> getProcessor();
}
