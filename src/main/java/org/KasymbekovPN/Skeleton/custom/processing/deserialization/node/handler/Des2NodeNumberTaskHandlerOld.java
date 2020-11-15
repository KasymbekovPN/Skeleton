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

public class Des2NodeNumberTaskHandlerOld extends OldBaseContextTaskHandler<Des2NodeContextOld> {

    public Des2NodeNumberTaskHandlerOld(String id) {
        super(id);
    }

    public Des2NodeNumberTaskHandlerOld(String id, SimpleResult simpleResult) {
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
        StringBuilder raw = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            if (finderOld.findValueEnd(next, Des2NodeModeOld.NUMBER)){
                iterator.dec();
                done = true;
            } else {
                raw.append(next);
            }
        }

        context.setNode(
                converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeModeOld.NUMBER))
        );
    }
}
