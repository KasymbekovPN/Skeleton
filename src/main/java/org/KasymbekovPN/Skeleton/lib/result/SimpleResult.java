package org.KasymbekovPN.Skeleton.lib.result;

public interface SimpleResult extends Result {
    boolean isSuccess();
    String getStatus();
    void setSuccess(boolean success);
    void setStatus(String status);
    ResultData getResultData();
    SimpleResult createInstance();
}
