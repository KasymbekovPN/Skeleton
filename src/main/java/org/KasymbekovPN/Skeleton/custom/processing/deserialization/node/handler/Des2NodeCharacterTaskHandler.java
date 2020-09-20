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
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Des2NodeCharacterTaskHandler extends BaseContextTaskHandler {

    public Des2NodeCharacterTaskHandler(Result result) {
        super(result);
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

        StringBuilder raw = new StringBuilder();
        boolean done = false;
        State state = State.BEGIN;
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case BEGIN:
                    if (finder.findValueBegin(next, Des2NodeMode.CHARACTER)){
                        state = State.FILL;
                        raw.append(next);
                    }
                    break;
                case FILL:
                    raw.append(next);
                    state = State.END;
                    break;
                case END:
                    raw.append(next);
                    if (finder.findValueEnd(next, Des2NodeMode.CHARACTER)){
                        done = true;
                    }
                    break;
            }
        }

        cxt.setNode(
            converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeMode.CHARACTER))
        );
    }

    private enum State{
        BEGIN,
        FILL,
        END
    }
}
