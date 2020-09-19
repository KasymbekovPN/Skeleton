package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.CharacterNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

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

        char ch = 0;
        boolean done = false;
        State state = State.BEGIN;
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case BEGIN:
                    if (finder.findValueBegin(next, Des2NodeMode.CHARACTER)){
                        state = State.FILL;
                    }
                    break;
                case FILL:
                    ch = next;
                    state = State.END;
                    break;
                case END:
                    if (finder.findValueEnd(next, Des2NodeMode.CHARACTER)){
                        done = true;
                    }
                    break;
            }
        }

        CharacterNode characterNode = new CharacterNode(parent, ch);
        cxt.setNode(characterNode);
    }

    private enum State{
        BEGIN,
        FILL,
        END
    }
}
