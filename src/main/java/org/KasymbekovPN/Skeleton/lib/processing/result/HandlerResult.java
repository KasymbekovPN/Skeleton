package org.KasymbekovPN.Skeleton.lib.processing.result;

import java.util.Optional;

public interface HandlerResult extends Result {
    boolean isSuccess();
    String getStatus();
    Optional<Object> getOptionalData(String dataId);
    void setSuccess(boolean success);
    void setStatus(String status);
    boolean setOptionalData(String dataId, Object optionalData);
}
