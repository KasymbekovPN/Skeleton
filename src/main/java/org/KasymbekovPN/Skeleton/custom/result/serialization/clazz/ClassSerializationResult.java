package org.KasymbekovPN.Skeleton.custom.result.serialization.clazz;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class ClassSerializationResult extends BaseResultImpl {

    @Override
    public Result createNew() {
        return new ClassSerializationResult();
    }
}
