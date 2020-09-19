package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class Des2NodeStringTaskHandler extends BaseContextTaskHandler {

    private static final Character SHIELD = '\\';

    public Des2NodeStringTaskHandler(Result result) {
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

        boolean done = false;
        State state = State.BEGIN;
        StringBuilder rawValue = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch(state){
                case BEGIN:
                    if (finder.findValueBegin(next, Des2NodeMode.STRING)){
                        state = State.ADD;
                    }
                    break;
                case ADD:
                    if (finder.findValueEnd(next, Des2NodeMode.STRING)){
                        done = true;
                    } else {
                        if (next.equals(SHIELD)){
                            state = State.SHIELD;
                        }
                        rawValue.append(next);
                    }
                    break;
                case SHIELD:
                    state = State.ADD;
                    rawValue.append(next);
                    break;
            }
        }

        StringNode stringNode = new StringNode(parent, rawValue.toString());
        cxt.setNode(stringNode);
    }

    private enum State{
        BEGIN,
        ADD,
        SHIELD
    }
}
