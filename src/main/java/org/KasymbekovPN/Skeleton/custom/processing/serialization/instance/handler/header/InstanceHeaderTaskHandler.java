package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.BaseInstanceTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Optional;

public class InstanceHeaderTaskHandler extends BaseInstanceTaskHandler {

    private static final String NODE_CONTENT_IS_INVALID = "Node content is invalid";

    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final CollectorPath collectorPath;

    private String name;
    private int modifiers;

    public InstanceHeaderTaskHandler(ClassHeaderPartHandler classHeaderPartHandler,
                                     CollectorPath collectorPath,
                                     Result result) {
        super(result);
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.collectorPath = collectorPath;
    }

    @Override
    protected void check(InstanceContext instanceContext, Task<InstanceContext> task) {
        Triple<Boolean, String, ObjectNode> classPartResult = instanceContext.getClassPart();
        success = classPartResult.getLeft();
        status = classPartResult.getMiddle();
        ObjectNode classNode = classPartResult.getRight();
        if (success){
            Optional<String> maybeName = classHeaderPartHandler.getName(classNode);
            Optional<Number> maybeModifiers = classHeaderPartHandler.getModifiers(classNode);
            if (maybeName.isPresent() && maybeModifiers.isPresent()){
                name = maybeName.get();
                modifiers = (int) maybeModifiers.get();
            } else {
                success = false;
                status = NODE_CONTENT_IS_INVALID;
            }
        }
    }

    @Override
    protected void fillCollector(InstanceContext instanceContext) {
        Collector collector = instanceContext.getCollector();
        ObjectNode targetNode = (ObjectNode) collector.setTarget(collectorPath.getPath());
        classHeaderPartHandler.setName(targetNode, name);
        classHeaderPartHandler.setModifiers(targetNode, modifiers);
        collector.reset();
    }
}
