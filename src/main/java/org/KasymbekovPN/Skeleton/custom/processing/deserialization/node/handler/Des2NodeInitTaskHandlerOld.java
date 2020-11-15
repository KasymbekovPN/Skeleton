package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContextOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeModeOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.FinderOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItrOld;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeInitTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContextOld> {

    public Des2NodeInitTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeInitTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContextOld context) {}

    @Override
    protected void doIt(Des2NodeContextOld context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        FinderOld finderOld = context.getFinderOld();
        Des2NodeCharItrOld iterator = context.iterator();
        Des2NodeModeOld mode = Des2NodeModeOld.UNKNOWN;

        while (iterator.hasNext() && mode.equals(Des2NodeModeOld.UNKNOWN)){
            mode = finderOld.findEntityBegin(iterator.next());
        }

        if (!mode.equals(Des2NodeModeOld.UNKNOWN)){
            iterator.dec();
            context.setMode(mode);
            context.runProcessor();
        }
    }
}
