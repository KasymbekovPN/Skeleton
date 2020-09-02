package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class ClassSpecificTaskHandler extends BaseContextTaskHandler {

    public ClassSpecificTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        super.check(context, task);
    }

    @Override
    protected void fillCollector(Context context) {
        super.fillCollector(context);
    }
}
