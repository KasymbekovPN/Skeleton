package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InstanceContext {
    List<String> getTaskIds();
    List<String> getWrapperIds();
    Object getInstance();
    Optional<String> getClassName();
    Optional<ObjectNode> getClassNode(String className);
    Map<String, Field> getFields(String kind);
    Collector getCollector();
    Triple<Boolean, String, List<String>> getClassPath();
    Triple<Boolean, String, ObjectNode> getClassPart();
    Triple<Boolean, String, List<String>> getMembersPath();
    Triple<Boolean, String, ObjectNode> getMembersPart();
    Object attachInstance(Object instance);
    Processor<InstanceContext> getProcessor();
}
