package org.KasymbekovPN.Skeleton.lib.result;

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
    public void reset() {
        success = false;
        status = "";
    }
}
