package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.BaseInstanceTaskHandler;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassHeaderHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Optional;

public class InstanceHeaderTaskHandler extends BaseInstanceTaskHandler {

    private static final String NODE_CONTENT_IS_INVALID = "Node content is invalid";

    private final ClassHeaderHandler classHeaderHandler;
    private final CollectorPath collectorPath;

    private String name;
    private int modifiers;

    public InstanceHeaderTaskHandler(ClassHeaderHandler classHeaderHandler,
                                     CollectorPath collectorPath,
                                     Result result) {
        super(result);
        this.classHeaderHandler = classHeaderHandler;
        this.collectorPath = collectorPath;
    }

    @Override
    protected void check(InstanceContext instanceContext, Task<InstanceContext> task) {
        Triple<Boolean, String, ObjectNode> classPartResult = instanceContext.getClassPart1();
        success = classPartResult.getLeft();
        status = classPartResult.getMiddle();
        ObjectNode classNode = classPartResult.getRight();
        if (success){
            Optional<String> maybeName = classHeaderHandler.getName(classNode);
            Optional<Number> maybeModifiers = classHeaderHandler.getModifiers(classNode);
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
        classHeaderHandler.setName(targetNode, name);
        classHeaderHandler.setModifiers(targetNode, modifiers);
        collector.reset();
    }
}
