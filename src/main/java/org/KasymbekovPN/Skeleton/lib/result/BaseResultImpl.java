package org.KasymbekovPN.Skeleton.lib.result;

import java.util.Optional;

//< del !!!
abstract public class BaseResultImpl implements Result {

    protected boolean success;
    protected String status;

    public BaseResultImpl() {
        reset();
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public ResultData getResultData() {
        return null;
    }

    @Override
    public void reset() {
        success = false;
        status = "";
    }

    //< !!! del

    @Override
    public Result createInstance() {
        return null;
    }

    @Override
    public boolean setOptionalData(String dataId, Object optionalData) {
        return false;
    }

    @Override
    public Optional<Object> getOptionalData(String dataId) {
        return Optional.empty();
    }

    //<
}
