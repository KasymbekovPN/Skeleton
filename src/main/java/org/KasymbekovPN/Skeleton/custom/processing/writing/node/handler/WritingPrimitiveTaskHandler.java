package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class WritingPrimitiveTaskHandler extends BaseContextTaskHandler {

    public WritingPrimitiveTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        WritingContext cxt = (WritingContext) context;

        Node node = cxt.getNode();
        if (!node.is(BooleanNode.ei()) &&
            !node.is(CharacterNode.ei()) &&
            !node.is(NumberNode.ei()) &&
            !node.is(StringNode.ei())) {

            success = false;
        }
    }

    @Override
    protected void doIt(Context context) {
        WritingContext cxt = (WritingContext) context;

        WritingFormatterHandler writingFormatterHandler = cxt.getWritingFormatterHandler();
        Node node = cxt.getNode();
        writingFormatterHandler.addValue(node);
    }
}
