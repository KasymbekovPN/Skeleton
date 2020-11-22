package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.CharacterNode;
import org.KasymbekovPN.Skeleton.lib.node.InvalidNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeCharacterTaskHandler extends Des2NodeBaseTaskHandler {

    private static final String WRONG_LINE = "wrong line";
    private static final char CHAR_BORDER = '\'';

    public Des2NodeCharacterTaskHandler(String id) {
        super(id);
    }

    public Des2NodeCharacterTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        DecrementedCharIterator iterator = context.iterator();
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        MultiChecker<EntityItem, Character> valueBeginChecker = context.getValueBeginChecker(NodeEI.characterEI());
        MultiChecker<EntityItem, Character> valueEndChecker = context.getValueEndChecker(NodeEI.characterEI());

        State state = State.BEGIN;
        boolean done = false;
        StringBuilder line = new StringBuilder();

        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case BEGIN:
                    if (valueBeginChecker.check(NodeEI.characterEI(), next)){
                        line.append(next);
                        state = State.END;
                    }
                    break;
                case END:
                    line.append(next);
                    if (valueEndChecker.check(NodeEI.characterEI(), next)){
                        done = true;
                    }
                    break;
            }
        }

        memento.setNode(convert(line.toString(), memento.getParentNode()));
    }

    private Node convert(String line, Node parent){
        return line.length() == 3 && line.charAt(0) == CHAR_BORDER && line.charAt(2) == CHAR_BORDER
                ? new CharacterNode(parent, line.charAt(1))
                : new InvalidNode(parent, WRONG_LINE, line);
    }

    private enum State{
        BEGIN,
        END
    }
}
