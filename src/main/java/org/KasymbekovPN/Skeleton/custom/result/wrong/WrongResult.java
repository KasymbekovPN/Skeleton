package org.KasymbekovPN.Skeleton.custom.result.wrong;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class WrongResult extends BaseResultImpl {

    public WrongResult() {
        super();
        success = false;
    }

    public WrongResult(String status){
        super();
        this.success = false;
        this.status = status;
    }

    @Override
    public void setSuccess(boolean success) {
    }

    @Override
    public Result createNew() {
        return new WrongResult();
    }
}
