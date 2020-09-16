package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class Des2NodeInitTaskHandler extends BaseContextTaskHandler {

    public Des2NodeInitTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        Des2NodeContext cxt = (Des2NodeContext) context;

        //<
//        Des2NodeCharItr iterator = cxt.iterator();
//        while (iterator.hasNext()){
//            Character next = iterator.next();
//            System.out.println(next);
//        }

        super.check(context, task);
    }

    @Override
    protected void doIt(Context context) {
        super.doIt(context);
    }
}
