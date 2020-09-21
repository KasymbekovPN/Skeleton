package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public class Des2NodeArrayTaskHandler extends BaseContextTaskHandler {

    public Des2NodeArrayTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(Context context, Task<Context> task) {}

    @Override
    protected void doIt(Context context) {
        Des2NodeContext ctx = (Des2NodeContext) context;

        boolean done = false;
        State state = State.BEGIN_END;
        Des2NodeCharItr iterator = ctx.iterator();
        Finder finder = ctx.getFinder();
        Node parent = ctx.getParent();

        ArrayNode arrayNode = new ArrayNode(parent);
        ctx.setParent(arrayNode);

        while (iterator.hasNext() && !done) {
            Character next = iterator.next();

            switch (state){
                case BEGIN_END:
                    if (finder.findValueBegin(next, Des2NodeMode.ARRAY)){
                        state = State.MEMBER;
                    } else if (finder.findValueEnd(next, Des2NodeMode.ARRAY)){
                        done = true;
                    }
                    break;
                case MEMBER:
                    iterator.dec();

                    state = State.BEGIN_END;
                    ctx.setMode(Des2NodeMode.INIT);
                    ctx.runProcessor();

                    Node node = ctx.getNode();
                    arrayNode.addChild( node);
                    break;
            }
        }

        ctx.setParent(parent);
        ctx.setNode(arrayNode);
    }

    private enum State{
        BEGIN_END,
        MEMBER
    }
}
