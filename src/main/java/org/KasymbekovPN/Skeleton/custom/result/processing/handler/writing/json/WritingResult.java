package org.KasymbekovPN.Skeleton.custom.result.processing.handler.writing.json;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class WritingResult extends BaseResultImpl {

    public WritingResult() {
        super();
    }

    @Override
    public Result createNew() {
        return new WritingResult();
    }
}
