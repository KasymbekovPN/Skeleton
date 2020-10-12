package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

public class WritingPrimitiveTaskHandlerOld extends OldBaseContextTaskHandler<WritingContext> {

    public WritingPrimitiveTaskHandlerOld(String id) {
        super(id);
    }

    public WritingPrimitiveTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(WritingContext context) {
        Node node = context.getNode();
        if (!node.is(BooleanNode.ei()) &&
            !node.is(CharacterNode.ei()) &&
            !node.is(NumberNode.ei()) &&
            !node.is(StringNode.ei())) {

            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(WritingContext context) {
        WritingFormatterHandler writingFormatterHandler = context.getWritingFormatterHandler();
        Node node = context.getNode();
        writingFormatterHandler.addValue(node);
    }
}
