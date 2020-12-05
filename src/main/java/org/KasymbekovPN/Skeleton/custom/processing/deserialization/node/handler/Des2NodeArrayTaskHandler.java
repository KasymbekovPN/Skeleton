package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.SKDes2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeArrayTaskHandler extends Des2NodeBaseTaskHandler {

    public Des2NodeArrayTaskHandler(String id) {
        super(id);
    }

    public Des2NodeArrayTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        DecrementedCharIterator iterator = context.iterator();
        MultiChecker<EntityItem, Character> valueBeginChecker = context.getValueBeginChecker(NodeEI.arrayEI());
        MultiChecker<EntityItem, Character> valueEndChecker = context.getValueEndChecker(NodeEI.arrayEI());

        boolean done = false;
        State state = State.BEGIN_END;
        ArrayNode arrayNode = new ArrayNode(memento.getParentNode());

        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case BEGIN_END:
                    if (valueBeginChecker.apply(NodeEI.arrayEI(), next)){
                        state = State.MEMBER;
                    } else if (valueEndChecker.apply(NodeEI.arrayEI(), next)) {
                        done = true;
                    }
                    break;
                case MEMBER:
                    iterator.dec();

                    context.getContextStateCareTaker().push(new SKDes2NodeContextStateMemento(arrayNode));
                    context.runProcessor();

                    Des2NodeContextStateMemento childMemento = context.getContextStateCareTaker().pop();
                    arrayNode.addChild(childMemento.getNode());

                    state = State.BEGIN_END;

                    break;
            }
        }

        if (done){
            memento.setNode(arrayNode);
        }
    }

    private enum State{
        BEGIN_END,
        MEMBER
    }
}
