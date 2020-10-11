package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.util.Map;

public interface SerClassNodeContext extends Context {
    void setCustomTypeChecker(SimpleChecker<String> customTypeChecker);
    SimpleChecker<String> getCustomTypeChecker();
    SimpleChecker<String> getSystemTypeChecker();
    Map<String, ObjectNode> getClassNodes();
    CollectorPath getMembersCollectorPath();
    ClassMembersPartHandler getClassMembersPartHandler();
}
