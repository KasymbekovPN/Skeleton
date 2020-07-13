package org.KasymbekovPN.Skeleton.lib.deserialization.clazz.deserializer;

import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.clazz.handler.ClazzDeserializerHandler;
import org.objectweb.asm.ClassWriter;

import java.util.Optional;

//<
public interface ClazzDeserializer /*extends Deserializer*/ {
    Optional<ClassWriter> deserialize(ObjectNode clazz);
    //< !! there is will need to use handlerId with other type (may be impl. of EntityItem)
    void addHandler(String handlerId, ClazzDeserializerHandler handler);
}
