package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Des2NodeStringTaskHandler extends BaseContextTaskHandler<Des2NodeContext> {

    private static final Character SHIELD = '\\';

    public Des2NodeStringTaskHandler(String id) {
        super(id);
    }

    public Des2NodeStringTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContext context) {}

    @Override
    protected void doIt(Des2NodeContext context) {

        Finder finder = context.getFinder();
        Des2NodeCharItr iterator = context.iterator();
        Node parent = context.getParent();
        Converter<Node, Triple<Node, String, Des2NodeMode>> converter = context.getConverter();

        boolean done = false;
        State state = State.BEGIN;
        StringBuilder raw = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch(state){
                case BEGIN:
                    if (finder.findValueBegin(next, Des2NodeMode.STRING)){
                        state = State.ADD;
                        raw.append(next);
                    }
                    break;
                case ADD:
                    if (finder.findValueEnd(next, Des2NodeMode.STRING)){
                        done = true;
                    } else if (next.equals(SHIELD)){
                        state = State.SHIELD;
                    }
                    raw.append(next);
                    break;
                case SHIELD:
                    state = State.ADD;
                    raw.append(next);
                    break;
            }
        }

        context.setNode(
                converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeMode.STRING))
        );
    }

    private enum State{
        BEGIN,
        ADD,
        SHIELD
    }
}
