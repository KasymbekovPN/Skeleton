package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.util.Map;

public class SKSerClassNodeContext implements SerClassNodeContext {

    private final ContextIds contextIds;
    private final SimpleChecker<String> systemTypeChecker;
    private final Map<String, ObjectNode> classNodes;
    private final CollectorPath membersPartCollectorPath;
    private final ClassMembersPartHandler classMembersPartHandler;

    private SimpleChecker<String> customTypeChecker = new AllowedStringChecker();

    public SKSerClassNodeContext(ContextIds contextIds,
                                 SimpleChecker<String> systemTypeChecker,
                                 Map<String, ObjectNode> classNodes,
                                 CollectorPath membersPartCollectorPath,
                                 ClassMembersPartHandler classMembersPartHandler) {
        this.contextIds = contextIds;
        this.systemTypeChecker = systemTypeChecker;
        this.classNodes = classNodes;
        this.membersPartCollectorPath = membersPartCollectorPath;
        this.classMembersPartHandler = classMembersPartHandler;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public void setCustomTypeChecker(SimpleChecker<String> customTypeChecker) {
        this.customTypeChecker = customTypeChecker;
    }

    @Override
    public SimpleChecker<String> getCustomTypeChecker() {
        return customTypeChecker;
    }

    @Override
    public SimpleChecker<String> getSystemTypeChecker() {
        return systemTypeChecker;
    }

    @Override
    public Map<String, ObjectNode> getClassNodes() {
        return classNodes;
    }

    @Override
    public CollectorPath getMembersCollectorPath() {
        return membersPartCollectorPath;
    }

    @Override
    public ClassMembersPartHandler getClassMembersPartHandler() {
        return classMembersPartHandler;
    }
}
