package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContextOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeModeOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.FinderOld;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItrOld;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Des2NodeStringTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContextOld> {

    private static final Character SHIELD = '\\';

    public Des2NodeStringTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeStringTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(Des2NodeContextOld context) {}

    @Override
    protected void doIt(Des2NodeContextOld context) {

        FinderOld finderOld = context.getFinderOld();
        Des2NodeCharItrOld iterator = context.iterator();
        Node parent = context.getParent();
        Converter<Node, Triple<Node, String, Des2NodeModeOld>> converter = context.getConverter();

        boolean done = false;
        State state = State.BEGIN;
        StringBuilder raw = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            switch(state){
                case BEGIN:
                    if (finderOld.findValueBegin(next, Des2NodeModeOld.STRING)){
                        state = State.ADD;
                        raw.append(next);
                    }
                    break;
                case ADD:
                    if (finderOld.findValueEnd(next, Des2NodeModeOld.STRING)){
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
                converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeModeOld.STRING))
        );
    }

    private enum State{
        BEGIN,
        ADD,
        SHIELD
    }
}
