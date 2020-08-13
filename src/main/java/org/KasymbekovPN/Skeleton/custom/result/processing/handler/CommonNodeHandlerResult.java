package org.KasymbekovPN.Skeleton.custom.result.processing.handler;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class CommonNodeHandlerResult extends BaseResultImpl {

    public CommonNodeHandlerResult() {
        super();
    }

    @Override
    public Result createNew() {
        return new CommonNodeHandlerResult();
    }
}
