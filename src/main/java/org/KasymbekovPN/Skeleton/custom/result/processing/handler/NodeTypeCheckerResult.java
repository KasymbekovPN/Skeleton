package org.KasymbekovPN.Skeleton.custom.result.processing.handler;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class NodeTypeCheckerResult extends BaseResultImpl {

    public NodeTypeCheckerResult() {
        super();
    }

    @Override
    public Result createNew() {
        return new NodeTypeCheckerResult();
    }
}
