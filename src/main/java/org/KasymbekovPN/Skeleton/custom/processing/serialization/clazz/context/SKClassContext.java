package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;

public class SKClassContext implements ClassContext {

    private final ContextIds contextIds;
    private final ContextStateCareTaker<ClassContextStateMemento> contextStateCareTaker;
    private final CollectorPath classPartPath;
    private final CollectorPath membersPartPath;
    private final Collector collector;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;

    public SKClassContext(ContextIds contextIds,
                          ContextStateCareTaker<ClassContextStateMemento> contextStateCareTaker,
                          CollectorPath classPartPath,
                          CollectorPath membersPartPath,
                          Collector collector,
                          ClassHeaderPartHandler classHeaderPartHandler,
                          ClassMembersPartHandler classMembersPartHandler) {
        this.contextIds = contextIds;
        this.contextStateCareTaker = contextStateCareTaker;
        this.classPartPath = classPartPath;
        this.membersPartPath = membersPartPath;
        this.collector = collector;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classMembersPartHandler = classMembersPartHandler;
    }

    @Override
    public CollectorPath getClassPartPath() {
        return classPartPath;
    }

    @Override
    public CollectorPath getMembersPartPath() {
        return membersPartPath;
    }

    @Override
    public Collector getCollector() {
        return collector;
    }

    @Override
    public ClassHeaderPartHandler getClassHeaderPartHandler() {
        return classHeaderPartHandler;
    }

    @Override
    public ClassMembersPartHandler getClassMembersPartHandler() {
        return classMembersPartHandler;
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public ContextStateCareTaker<ClassContextStateMemento> getContextStateCareTaker() {
        return contextStateCareTaker;
    }
}
