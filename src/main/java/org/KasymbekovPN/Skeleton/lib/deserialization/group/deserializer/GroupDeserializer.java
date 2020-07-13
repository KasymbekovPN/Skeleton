package org.KasymbekovPN.Skeleton.lib.deserialization.group.deserializer;

import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.objectweb.asm.ClassWriter;

import java.util.Map;

public interface GroupDeserializer {
    void deserialize(ObjectNode classes);
    Map<String, ClassWriter> getClasses();
}
