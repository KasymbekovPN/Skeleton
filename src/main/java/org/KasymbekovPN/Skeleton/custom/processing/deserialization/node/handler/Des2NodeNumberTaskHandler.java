package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class Des2NodeNumberTaskHandler extends BaseContextTaskHandler {

    public Des2NodeNumberTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {}

    @Override
    protected void doIt(Context context) {
        //<
        System.out.println("number b");
        //<

        Des2NodeContext cxt = (Des2NodeContext) context;

        Finder finder = cxt.getFinder();
        Des2NodeCharItr iterator = cxt.iterator();
        Node parent = cxt.getParent();

        boolean done = false;
        StringBuilder sValue = new StringBuilder();
        State state = State.VALUE_BEGIN_FINDING;
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case VALUE_BEGIN_FINDING:
                    if (finder.findValueBegin(next, Des2NodeMode.NUMBER)){
                        state = State.VALUE_END_FINDING;
                        sValue.append(next);
                    }
                    break;
                case VALUE_END_FINDING:
                    if (finder.findValueEnd(next, Des2NodeMode.NUMBER)){
                        iterator.dec();
                        done = true;
                    } else {
                        sValue.append(next);
                    }
                    break;
            }
        }

        NumberNode numberNode = new NumberNode(parent, Double.valueOf(sValue.toString()));
        cxt.setNode(numberNode);

        //<
        System.out.println("number e");
        //<
    }

    private enum State{
        VALUE_BEGIN_FINDING,
        VALUE_END_FINDING
    }
}
