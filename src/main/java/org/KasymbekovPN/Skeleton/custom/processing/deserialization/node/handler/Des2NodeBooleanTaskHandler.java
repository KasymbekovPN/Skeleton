package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder.Finder;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr.Des2NodeCharItr;
import org.KasymbekovPN.Skeleton.lib.converter.Converter;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class Des2NodeBooleanTaskHandler extends BaseContextTaskHandler {

    public Des2NodeBooleanTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {}

    @Override
    protected void doIt(Context context) {
        Des2NodeContext cxt = (Des2NodeContext) context;

        Finder finder = cxt.getFinder();
        Des2NodeCharItr iterator = cxt.iterator();
        Node parent = cxt.getParent();
        Converter<Node, Triple<Node, String, Des2NodeMode>> converter = cxt.getConverter();

        boolean done = false;
        StringBuilder rawValue = new StringBuilder();
        while (iterator.hasNext() && !done){
            Character next = iterator.next();

            if (finder.findValueEnd(next, Des2NodeMode.BOOLEAN)){
                iterator.dec();
                done = true;
            } else {
                rawValue.append(next);
            }
        }

        //<
        System.out.println(rawValue);
        //<


//        BooleanNode booleanNode = new BooleanNode(parent, Boolean.valueOf(rawValue.toString()));
        cxt.setNode(
                converter.convert(new MutableTriple<>(parent, rawValue.toString(), Des2NodeMode.BOOLEAN))
        );
    }
}
