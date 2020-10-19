package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.OldContext;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

//< del
public interface OldDes2InstanceContext extends OldContext {
    boolean isValid();
    void push(Object instance, ObjectNode serData);
    Object pop();
    Object getInstance();
    Set<Triple<Field, Node, ObjectNode>> getMembers(String kind);
    OptionalConverter<Collection<Object>, ObjectNode> getStrType2CollectionConverter();
    OptionalConverter<Map<Object, Object>, ObjectNode> getStrType2MapConverter();
    OptionalConverter<Object, String> getClassName2InstanceConverter();
    OptionalConverter<Object, ObjectNode> getToInstanceConverter();
    ClassMembersPartHandler getClassMembersPartHandler();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
}
