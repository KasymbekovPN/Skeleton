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
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class Des2NodeNumberTaskHandler extends Des2NodeBaseTaskHandler {

    private static final String WRONG_LINE = "wrong line";
    private static final String HAS_INVALID_CHARACTER = "has invalid character; ";
    private static final String HAS_MORE_THAN_ONE_SEPARATOR = "has more than one separator; ";

    private static final char NUMBER_SEPARATOR = '.';

    public Des2NodeNumberTaskHandler(String id) {
        super(id);
    }

    public Des2NodeNumberTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        DecrementedCharIterator iterator = context.iterator();
        MultiChecker<EntityItem, Character> valueEndChecker = context.getValueEndChecker(NodeEI.numberEI());

        boolean done = false;
        StringBuilder line = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            if (valueEndChecker.check(NodeEI.numberEI(), next)){
                iterator.dec();
                done = true;
            } else {
                line.append(next);
            }
        }

        memento.setNode(convert(line.toString(), memento.getParentNode()));
    }

    private Node convert(String line, Node parent){
        int length = line.length();
        boolean hasInvalidCharacter = false;
        int separatorCounter = 0;
        for (int i = 0; i < length; i++) {
            char ch = line.charAt(i);
            if (ch == NUMBER_SEPARATOR){
                separatorCounter++;
            } else if (!Character.isDigit(ch)){
                hasInvalidCharacter = true;
            }
        }

        StringBuilder status = new StringBuilder();
        if (hasInvalidCharacter){
            status.append(HAS_INVALID_CHARACTER);
        }
        if (separatorCounter > 1){
            status.append(HAS_MORE_THAN_ONE_SEPARATOR);
        }

        return status.length() == 0
                ? new NumberNode(parent, Double.valueOf(line))
                : new InvalidNode(parent, WRONG_LINE, line);
    }
}
