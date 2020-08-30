package org.KasymbekovPN.Skeleton.custom.processing.node.handler.writing;

import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class JsonPrimitiveTaskHandler implements TaskHandler<Node> {

    private final WritingFormatterHandler writingFormatterHandler;

    private Result result;

    public JsonPrimitiveTaskHandler(WritingFormatterHandler writingFormatterHandler,
                                    Result result) {
        this.writingFormatterHandler = writingFormatterHandler;
        this.result = result;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {
        writingFormatterHandler.addValue(object);
        return result;
    }

    @Override
    public Result getResult() {
        return result;
    }
}
