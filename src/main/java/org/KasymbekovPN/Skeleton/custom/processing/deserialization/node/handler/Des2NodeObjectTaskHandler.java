package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.SKDes2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeObjectTaskHandler extends Des2NodeBaseTaskHandler{

    public Des2NodeObjectTaskHandler(String id) {
        super(id);
    }

    public Des2NodeObjectTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        DecrementedCharIterator iterator = context.iterator();
        SimpleChecker<Character> propertyNameBeginChecker = context.getPropertyNameBeginChecker();
        SimpleChecker<Character> propertyNameEndChecker = context.getPropertyNameEndChecker();

        MultiChecker<EntityItem, Character> valueEndChecker = context.getValueEndChecker(NodeEI.objectEI());

        SimpleChecker<Character> valueNameSeparatorChecker = context.getValueNameSeparatorChecker();

        boolean done = false;
        StringBuilder name = new StringBuilder();
        State state = State.NAME_BEGIN_FINDING;

        ObjectNode objectNode = new ObjectNode(memento.getParentNode());

        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case NAME_BEGIN_FINDING:
                    if (propertyNameBeginChecker.check(next)){
                        state = State.NAME_END_FINDING;
                        name.setLength(0);
                    } else if (valueEndChecker.check(NodeEI.objectEI(), next)) {
                        done = true;
                    }
                    break;
                case NAME_END_FINDING:
                    if (propertyNameEndChecker.check(next)){
                        state = State.SEPARATOR_FINDING;
                    } else {
                        name.append(next);
                    }
                    break;
                case SEPARATOR_FINDING:
                    if (valueNameSeparatorChecker.check(next)){
                        context.getContextStateCareTaker().push(new SKDes2NodeContextStateMemento(objectNode));
                        context.runProcessor();

                        Des2NodeContextStateMemento childMemento = context.getContextStateCareTaker().pop();
                        objectNode.addChild(name.toString(), childMemento.getNode());

                        state = State.NAME_BEGIN_FINDING;
                    }
                    break;
            }
        }

        if (done){
            memento.setNode(objectNode);
        }
    }

    private enum State{
        NAME_BEGIN_FINDING,
        NAME_END_FINDING,
        SEPARATOR_FINDING
    }
}
