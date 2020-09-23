package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceContext;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public class Des2InstanceSpecificTaskHandler extends BaseContextTaskHandler<Des2InstanceContext> {

    public Des2InstanceSpecificTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(Des2InstanceContext context, Task<Des2InstanceContext> task) {
        super.check(context, task);
    }

    @Override
    protected void doIt(Des2InstanceContext context) {
        super.doIt(context);
    }
}
