package org.KasymbekovPN.Skeleton.custom.result.writing.node;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class WritingObjectTaskHandlerResult extends BaseResultImpl {

    @Override
    public Result createNew() {
        return new WritingObjectTaskHandlerResult();
    }
}
