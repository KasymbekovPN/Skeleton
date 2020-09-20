package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

abstract public class BaseContextTaskHandler implements TaskHandler<Context> {

    protected Result result;

    public BaseContextTaskHandler(Result result) {
        this.result = result;
    }

    @Override
    public Result handle(Context object, Task<Context> task) {

        //<
        Result r1 = result.createNew();
        Result r2 = result.createInstance();
        result = r2 != null ? r2 : r1;
        //<

        check(object, task);
        if (result.isSuccess()){
            doIt(object);
        }

        return getResult();
    }

    @Override
    public Result getResult() {
        return result;
    }

    protected void check(Context context, Task<Context> task){
    }

    protected void doIt(Context context){
    }

    //<
//    protected void resetResult(){
//        result = result.createNew();
//        result.setSuccess(success);
//        result.setStatus(status);
//    }

}
