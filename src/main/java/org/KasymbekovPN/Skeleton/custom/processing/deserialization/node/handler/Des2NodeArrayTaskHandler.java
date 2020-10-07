package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeArrayTaskHandler extends BaseContextTaskHandler<Des2NodeContext> {

    public Des2NodeArrayTaskHandler() {
    }

    public Des2NodeArrayTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(Des2NodeContext context, Task<Des2NodeContext> task) {}

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        boolean done = false;
        State state = State.BEGIN_END;
        Des2NodeCharItr iterator = context.iterator();
        Finder finder = context.getFinder();
        Node parent = context.getParent();

        ArrayNode arrayNode = new ArrayNode(parent);
        context.setParent(arrayNode);

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
                    context.setMode(Des2NodeMode.INIT);
                    context.runProcessor();

                    Node node = context.getNode();
                    arrayNode.addChild( node);
                    break;
            }
        }

        context.setParent(parent);
        context.setNode(arrayNode);
    }

    private enum State{
        BEGIN_END,
        MEMBER
    }
}
