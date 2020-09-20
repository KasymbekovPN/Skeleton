package org.KasymbekovPN.Skeleton.custom.result.processing.handler.common;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

//< del
public class CommonNodeHandlerResultOLd extends BaseResultImpl {

    public CommonNodeHandlerResultOLd() {
        super();
    }

    @Override
    public Result createInstance() {
        return null;
    }

    //<
    @Override
    public Result createNew() {
        return new CommonNodeHandlerResultOLd();
    }
}
