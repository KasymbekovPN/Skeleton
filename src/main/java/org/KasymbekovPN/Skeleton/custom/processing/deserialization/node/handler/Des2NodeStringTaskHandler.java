package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.InvalidNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeStringTaskHandler extends Des2NodeBaseTaskHandler {

    private static final Character SHIELD = '\\';
    private static final String WRONG_LINE = "wrong line";
    private static final char STRING_BORDER = '"';

    public Des2NodeStringTaskHandler(String id) {
        super(id);
    }

    public Des2NodeStringTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        DecrementedCharIterator iterator = context.iterator();
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        MultiChecker<EntityItem, Character> valueBeginChecker = context.getValueBeginChecker(NodeEI.stringEI());
        MultiChecker<EntityItem, Character> valueEndChecker = context.getValueEndChecker(NodeEI.stringEI());

        boolean done = false;
        StringBuilder line = new StringBuilder();
        State state = State.BEGIN;

        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch(state){
                case BEGIN:
                    if (valueBeginChecker.apply(NodeEI.stringEI(), next)){
                        line.append(next);
                        state = State.ADD;
                    }
                    break;
                case ADD:
                    if (valueEndChecker.apply(NodeEI.stringEI(), next)){
                        done = true;
                    } else if (next.equals(SHIELD)){
                        state = State.SHIELD;
                    }
                    line.append(next);
                    break;
                case SHIELD:
                    line.append(next);
                    state = State.ADD;
                    break;
            }
        }

        memento.setNode(convert(line.toString(), memento.getParentNode()));
    }

    private Node convert(String line, Node parent){
        int length = line.length();
        return length >= 2 && line.charAt(0) == STRING_BORDER && line.charAt(length - 1) == STRING_BORDER
                ? new StringNode(parent, line.substring(1, length - 1))
                : new InvalidNode(parent, WRONG_LINE, line);
    }

    private enum State{
        BEGIN,
        ADD,
        SHIELD
    }
}
