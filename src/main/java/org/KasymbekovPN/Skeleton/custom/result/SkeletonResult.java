package org.KasymbekovPN.Skeleton.custom.result;

import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.result.ResultData;

import java.util.Optional;

public class SkeletonResult implements Result {

    private final ResultData resultData;

    private boolean success;
    private String status;

    public SkeletonResult(ResultData resultData) {
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
    public Result createInstance() {
        return new SkeletonResult(resultData.createInstance());
    }

    //< del

    @Override
    public boolean setOptionalData(String dataId, Object optionalData) {
        return false;
    }

    @Override
    public Optional<Object> getOptionalData(String dataId) {
        return Optional.empty();
    }

    @Override
    public Result createNew() {
        return null;
    }

    //<
}
