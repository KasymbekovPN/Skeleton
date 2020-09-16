package org.KasymbekovPN.Skeleton.custom.result.deserialization.node;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class Des2NodeArrayTaskHandlerResult extends BaseResultImpl {

    @Override
    public Result createNew() {
        return new Des2NodeArrayTaskHandlerResult();
    }
}
