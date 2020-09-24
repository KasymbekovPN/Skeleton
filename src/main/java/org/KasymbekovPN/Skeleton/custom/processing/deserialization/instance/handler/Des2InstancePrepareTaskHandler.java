package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public class Des2InstancePrepareTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

    public Des2InstancePrepareTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(Des2InstanceContext context, Task<Des2InstanceContext> task) {
        System.out.println("1");
    }

    @Override
    protected void doIt(Des2InstanceContext context) {
    }
}
