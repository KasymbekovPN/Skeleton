package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

abstract public class BaseContextTaskHandler implements TaskHandler<Context> {

    private Result result;

    protected boolean success;
    protected String status;

    public BaseContextTaskHandler(Result result) {
        this.result = result;
    }

    @Override
    public Result handle(Context object, Task<Context> task) {
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

    protected void check(Context context, Task<Context> task){
    }

    protected void fillCollector(Context context){
    }

    protected void resetResult(){
        result = result.createNew();
        result.setSuccess(success);
        result.setStatus(status);
    }

}
