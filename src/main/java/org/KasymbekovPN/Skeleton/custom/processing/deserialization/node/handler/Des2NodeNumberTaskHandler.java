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

public class Des2NodeNumberTaskHandler extends BaseContextTaskHandler<Des2NodeContext> {

    public Des2NodeNumberTaskHandler(String id) {
        super(id);
    }

    public Des2NodeNumberTaskHandler(String id, SimpleResult simpleResult) {
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
        StringBuilder raw = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            if (finder.findValueEnd(next, Des2NodeMode.NUMBER)){
                iterator.dec();
                done = true;
            } else {
                raw.append(next);
            }
        }

        context.setNode(
                converter.convert(new MutableTriple<>(parent, raw.toString(), Des2NodeMode.NUMBER))
        );
    }
}
