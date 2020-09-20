package org.KasymbekovPN.Skeleton.lib.result;

import java.util.Optional;

public interface Result{
    boolean isSuccess();
    String getStatus();
    void setSuccess(boolean success);
    void setStatus(String status);
    ResultData getResultData();
    void reset();
    Result createInstance();

    //< !!! del
    boolean setOptionalData(String dataId, Object optionalData);
    Optional<Object> getOptionalData(String dataId);
    Result createNew();
    //<
}
