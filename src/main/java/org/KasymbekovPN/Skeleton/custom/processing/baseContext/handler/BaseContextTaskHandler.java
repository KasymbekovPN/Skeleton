package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

abstract public class BaseContextTaskHandler<T extends Context> implements TaskHandler<T> {

    protected SimpleResult simpleResult;

    public BaseContextTaskHandler(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    @Override
    public SimpleResult handle(T object, Task<T> task) {
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

    protected void check(T context, Task<T> task){
    }

    protected void doIt(T context){
    }
}
