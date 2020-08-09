package org.KasymbekovPN.Skeleton.custom.processing.node.result.handler;

import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;

import java.util.Optional;

public class CommonNodeHandlerResult implements HandlerResult {

    private boolean success;
    private String status;

    public CommonNodeHandlerResult(boolean success, String status) {
        this.success = success;
        this.status = status;
    }

    public CommonNodeHandlerResult(String status) {
        this.status = status;
        this.success = false;
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
        status = "";
        success = false;
    }

    @Override
    public Optional<Object> getOptionalData(String dataId) {
        return Optional.empty();
    }

    @Override
    public boolean setOptionalData(String dataId, Object optionalData) {
        return false;
    }
}
