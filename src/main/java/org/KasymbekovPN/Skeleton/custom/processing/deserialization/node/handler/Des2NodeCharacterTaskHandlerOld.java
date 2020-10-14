package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Des2NodeCharacterTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContext> {

    public Des2NodeCharacterTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeCharacterTaskHandlerOld(String id, SimpleResult simpleResult) {
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

        StringBuilder raw = new StringBuilder();
        boolean done = false;
        State state = State.BEGIN;
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch (state){
                case BEGIN:
                    if (finder.findValueBegin(next, Des2NodeMode.CHARACTER)){
                        state = State.FILL;
                        raw.append(next);
                    }
                    break;
                case FILL:
                    raw.append(next);
                    state = State.END;
                    break;
                case END:
                    raw.append(next);
                    if (finder.findValueEnd(next, Des2NodeMode.CHARACTER)){
                        done = true;
                    }
                    break;
            }
        }

        context.setNode(
            converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeMode.CHARACTER))
        );
    }

    private enum State{
        BEGIN,
        FILL,
        END
    }
}