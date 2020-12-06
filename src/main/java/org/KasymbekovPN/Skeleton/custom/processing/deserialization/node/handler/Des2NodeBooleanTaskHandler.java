package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.functional.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.node.InvalidNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class Des2NodeBooleanTaskHandler extends Des2NodeBaseTaskHandler {

    private static final String WRONG_LINE = "wrong line";
    private static final Set<String> ALLOWED_BOOLEAN_VALUE = new HashSet<>(){{
        add("true");
        add("false");
    }};

    public Des2NodeBooleanTaskHandler(String id) {
        super(id);
    }

    public Des2NodeBooleanTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {

        DecrementedCharIterator iterator = context.iterator();
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();
        MultiChecker<EntityItem, Character> valueEndChecker = context.getValueEndChecker(BooleanNode.ei());

        boolean done = false;
        StringBuilder line = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            if (valueEndChecker.apply(NodeEI.booleanEI(), next)){
                iterator.dec();
                done = true;
            } else {
                line.append(next);
            }
        }

        memento.setNode(convert(line.toString(), memento.getParentNode()));
    }

    private Node convert(String line, Node parent){
        line = line.trim().toLowerCase();
        return ALLOWED_BOOLEAN_VALUE.contains(line)
                ? new BooleanNode(parent, Boolean.valueOf(line))
                : new InvalidNode(parent, WRONG_LINE, line);
    }
}
