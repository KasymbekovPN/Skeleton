package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContextOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeModeOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.FinderOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItrOld;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeArrayTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContextOld> {

    public Des2NodeArrayTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeArrayTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContextOld context) {}

    @Override
    protected void doIt(Des2NodeContextOld context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        boolean done = false;
        State state = State.BEGIN_END;
        Des2NodeCharItrOld iterator = context.iterator();
        FinderOld finderOld = context.getFinderOld();
        Node parent = context.getParent();

        ArrayNode arrayNode = new ArrayNode(parent);
        context.setParent(arrayNode);

        while (iterator.hasNext() && !done) {
            Character next = iterator.next();

            switch (state){
                case BEGIN_END:
                    if (finderOld.findValueBegin(next, Des2NodeModeOld.ARRAY)){
                        state = State.MEMBER;
                    } else if (finderOld.findValueEnd(next, Des2NodeModeOld.ARRAY)){
                        done = true;
                    }
                    break;
                case MEMBER:
                    iterator.dec();

                    state = State.BEGIN_END;
                    context.setMode(Des2NodeModeOld.INIT);
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
