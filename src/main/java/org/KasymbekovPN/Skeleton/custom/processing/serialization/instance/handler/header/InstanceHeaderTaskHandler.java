package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceData;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.Optional;

public class InstanceHeaderTaskHandler implements TaskHandler<InstanceData> {

    private final CollectorPath serviceClassPath;
    private final CollectorPath objectPath;

    private Result result;

    public InstanceHeaderTaskHandler(CollectorPath serviceClassPath,
                                     CollectorPath objectPath,
                                     Result result) {
        this.serviceClassPath = serviceClassPath;
        this.objectPath = objectPath;
        this.result = result;
    }

    @Override
    public Result handle(InstanceData object, Task<InstanceData> task) {

        Optional<String> mayBeClassName = object.getClassName();
        if (mayBeClassName.isPresent()){
            String className = mayBeClassName.get();
            Optional<ObjectNode> mayBeClassNode = object.getClassNode(className);
            if (mayBeClassNode.isPresent()){

            } else {
                //< !!!
            }
        } else {
            //< !!!
        }

        return null;
    }

    @Override
    public Result getHandlerResult() {
        return null;
    }
}
