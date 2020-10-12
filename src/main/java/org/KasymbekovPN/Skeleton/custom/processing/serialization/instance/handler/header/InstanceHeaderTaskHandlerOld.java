package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class InstanceHeaderTaskHandlerOld extends OldBaseContextTaskHandler<InstanceContext> {

    private final static Logger log = LoggerFactory.getLogger(InstanceHeaderTaskHandlerOld.class);

    private String name;
    private int modifiers;

    public InstanceHeaderTaskHandlerOld(String id) {
        super(id);
    }

    public InstanceHeaderTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContext context) {
        if (context.isValid()){
            ObjectNode classNode = context.getClassNode();
            CollectorPath classPartPath = context.getClassPartPath();
            ClassHeaderPartHandler classHeaderPartHandler = context.getClassHeaderPartHandler();

            Optional<Node> maybeClassPart = classNode.getChild(classPartPath);
            if (maybeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) maybeClassPart.get();

                Optional<String> maybeName = classHeaderPartHandler.getName(classPart);
                Optional<Number> maybeModifiers = classHeaderPartHandler.getModifiers(classPart);
                if (maybeName.isPresent() && maybeModifiers.isPresent()){
                    name = maybeName.get();
                    modifiers = (int) maybeModifiers.get();
                } else {
                    log.error("The class part doesn't contain 'name' and/or 'modifiers'");
                    simpleResult.setSuccess(false);
                }
            } else {
                log.error("The class part isn't exist");
                simpleResult.setSuccess(false);
            }
        } else {
            log.error("The context isn't valid");
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(InstanceContext context) {
        CollectorPath classPartPath = context.getClassPartPath();
        ClassHeaderPartHandler classHeaderPartHandler = context.getClassHeaderPartHandler();
        Collector collector = context.getCollector();
        ObjectNode target = (ObjectNode) collector.setTarget(classPartPath.getPath());
        classHeaderPartHandler.setName(target, name);
        classHeaderPartHandler.setModifiers(target, modifiers);
        collector.reset();
    }
}
