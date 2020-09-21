package org.KasymbekovPN.Skeleton.lib.result;

public class SkeletonSimpleResult implements SimpleResult {

    private final ResultData resultData;

    private boolean success;
    private String status;

    public SkeletonSimpleResult(ResultData resultData) {
        this.resultData = resultData;
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
        return resultData;
    }

    @Override
    public void reset() {
        success = true;
        status = "";
        resultData.clear();
    }

    @Override
    public SimpleResult createInstance() {
        return new SkeletonSimpleResult(resultData.createInstance());
    }
}
