package org.KasymbekovPN.Skeleton.lib.result;

import java.util.Optional;

public interface Result{
    boolean isSuccess();
    String getStatus();
    void setSuccess(boolean success);
    void setStatus(String status);
    void reset();
    default boolean setOptionalData(String dataId, Object optionalData){return false;}
    default Optional<Object> getOptionalData(String dataId){return Optional.empty();}
    Result createNew();
}
