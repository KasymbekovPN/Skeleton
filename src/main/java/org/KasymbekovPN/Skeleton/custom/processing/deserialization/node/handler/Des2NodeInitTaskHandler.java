package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class Des2NodeInitTaskHandler extends BaseContextTaskHandler {

    public Des2NodeInitTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {}

    @Override
    protected void doIt(Context context) {
        //<
        System.out.println("init b");
        //<
        Des2NodeContext ctx = (Des2NodeContext) context;

        Finder finder = ctx.getFinder();
        Des2NodeCharItr iterator = ctx.iterator();
        Des2NodeMode mode = Des2NodeMode.UNKNOWN;

        while (iterator.hasNext() && mode.equals(Des2NodeMode.UNKNOWN)){
            mode = finder.findEntityBegin(iterator.next());
        }

        if (!mode.equals(Des2NodeMode.UNKNOWN)){
            iterator.dec();
            ctx.setMode(mode);
            ctx.runProcessor();
        }

        //<
        System.out.println("init e");
        //<
    }
}
