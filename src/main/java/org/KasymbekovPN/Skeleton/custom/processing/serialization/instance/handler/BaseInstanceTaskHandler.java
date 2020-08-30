package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

abstract public class BaseInstanceTaskHandler implements TaskHandler<InstanceContext> {

    private Result result;

    protected boolean success;
    protected String status;

    public BaseInstanceTaskHandler(Result result) {
        this.result = result;
    }

    @Override
    public Result handle(InstanceContext object, Task<InstanceContext> task) {

        success = true;
        status = "";

        check(object, task);
        if (success){
            fillCollector(object);
        }
        resetResult();

        return getResult();
    }

    @Override
    public Result getResult() {
        return result;
    }

    protected void check(InstanceContext instanceContext, Task<InstanceContext> task){
    }

    protected void fillCollector(InstanceContext instanceContext){
    }

    protected void resetResult(){
        result = result.createNew();
        result.setSuccess(success);
        result.setStatus(status);
    }
}
