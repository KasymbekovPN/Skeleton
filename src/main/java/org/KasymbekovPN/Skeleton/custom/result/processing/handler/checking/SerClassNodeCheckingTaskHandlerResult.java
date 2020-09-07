package org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class SerClassNodeCheckingTaskHandlerResult extends BaseResultImpl {

    @Override
    public Result createNew() {
        return new SerClassNodeCheckingTaskHandlerResult();
    }
}
