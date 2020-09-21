package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Des2NodeStringTaskHandler extends BaseContextTaskHandler {

    private static final Character SHIELD = '\\';

    public Des2NodeStringTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(Context context, Task<Context> task) {}

    @Override
    protected void doIt(Context context) {
        Des2NodeContext cxt = (Des2NodeContext) context;

        Finder finder = cxt.getFinder();
        Des2NodeCharItr iterator = cxt.iterator();
        Node parent = cxt.getParent();
        Converter<Node, Triple<Node, String, Des2NodeMode>> converter = cxt.getConverter();

        boolean done = false;
        State state = State.BEGIN;
        StringBuilder raw = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch(state){
                case BEGIN:
                    if (finder.findValueBegin(next, Des2NodeMode.STRING)){
                        state = State.ADD;
                        raw.append(next);
                    }
                    break;
                case ADD:
                    if (finder.findValueEnd(next, Des2NodeMode.STRING)){
                        done = true;
                    } else if (next.equals(SHIELD)){
                        state = State.SHIELD;
                    }
                    raw.append(next);
                    break;
                case SHIELD:
                    state = State.ADD;
                    raw.append(next);
                    break;
            }
        }
        
        cxt.setNode(
                converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeMode.STRING))
        );
    }

    private enum State{
        BEGIN,
        ADD,
        SHIELD
    }
}
