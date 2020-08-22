package org.KasymbekovPN.Skeleton.custom.result.serialization.instance;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class InstanceSerializationResult extends BaseResultImpl {

    @Override
    public Result createNew() {
        return new InstanceSerializationResult();
    }
}
