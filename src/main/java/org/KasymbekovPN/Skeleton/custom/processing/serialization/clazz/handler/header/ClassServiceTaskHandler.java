package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class ClassServiceTaskHandler extends BaseContextTaskHandler {

    public ClassServiceTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {

        System.out.println(1);

        super.check(context, task);
    }

    @Override
    protected void fillCollector(Context context) {
        super.fillCollector(context);
    }
}
