package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

abstract public class BaseContextTaskHandler implements TaskHandler<Context> {

    protected SimpleResult simpleResult;

    public BaseContextTaskHandler(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    @Override
    public SimpleResult handle(Context object, Task<Context> task) {
        simpleResult = simpleResult.createInstance();
        check(object, task);
        if (simpleResult.isSuccess()){
            doIt(object);
        }

        return getResult();
    }

    @Override
    public SimpleResult getResult() {
        return simpleResult;
    }

    protected void check(Context context, Task<Context> task){
    }

    protected void doIt(Context context){
    }
}
