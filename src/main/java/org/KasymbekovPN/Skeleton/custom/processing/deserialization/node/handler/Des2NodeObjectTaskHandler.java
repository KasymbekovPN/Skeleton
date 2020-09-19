package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class Des2NodeObjectTaskHandler extends BaseContextTaskHandler {

    public Des2NodeObjectTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {}

    @Override
    protected void doIt(Context context) {
        Des2NodeContext ctx = (Des2NodeContext) context;

        boolean done = false;
        StringBuilder name = new StringBuilder();
        State state = State.NAME_BEGIN_FINDING;
        Des2NodeCharItr iterator = ctx.iterator();
        Finder finder = ctx.getFinder();
        Node parent = ctx.getParent();

        ObjectNode objectNode = new ObjectNode(parent);
        ctx.setParent(objectNode);

        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case NAME_BEGIN_FINDING:
                    if (finder.findPropertyNameBegin(next)){
                        state = State.NAME_END_FINDING;
                        name.setLength(0);
                    } else if (finder.findValueEnd(next, Des2NodeMode.OBJECT)){
                        done = true;
                    }
                    break;
                case NAME_END_FINDING:
                    if (finder.findPropertyNameEnd(next)){
                        state = State.SEPARATOR_FINDING;
                    } else {
                        name.append(next);
                    }
                    break;
                case SEPARATOR_FINDING:
                    if (finder.findNameValueSeparator(next)){
                        state = State.NAME_BEGIN_FINDING;
                        ctx.setMode(Des2NodeMode.INIT);
                        ctx.runProcessor();

                        Node node = ctx.getNode();
                        objectNode.addChild(name.toString(), node);
                    }
                    break;
            }
        }

        ctx.setParent(parent);
        ctx.setNode(objectNode);
    }

    private enum State{
        NAME_BEGIN_FINDING,
        NAME_END_FINDING,
        SEPARATOR_FINDING
    }
}
