package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public class Des2NodeInitTaskHandler extends BaseContextTaskHandler<Des2NodeContext> {

    public Des2NodeInitTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(Des2NodeContext context, Task<Des2NodeContext> task) {}

    @Override
    protected void doIt(Des2NodeContext context) {

        Finder finder = context.getFinder();
        Des2NodeCharItr iterator = context.iterator();
        Des2NodeMode mode = Des2NodeMode.UNKNOWN;

        while (iterator.hasNext() && mode.equals(Des2NodeMode.UNKNOWN)){
            mode = finder.findEntityBegin(iterator.next());
        }

        if (!mode.equals(Des2NodeMode.UNKNOWN)){
            iterator.dec();
            context.setMode(mode);
            context.runProcessor();
        }
    }
}
