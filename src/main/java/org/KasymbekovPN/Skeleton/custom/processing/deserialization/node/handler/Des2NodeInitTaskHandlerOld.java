package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeInitTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContext> {

    public Des2NodeInitTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeInitTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContext context) {}

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

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
