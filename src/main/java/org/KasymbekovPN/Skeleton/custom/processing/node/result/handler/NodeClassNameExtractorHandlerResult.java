package org.KasymbekovPN.Skeleton.custom.processing.node.result.handler;

import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;

import java.util.Optional;

public class NodeClassNameExtractorHandlerResult implements HandlerResult {

    private static final String CLASS_NAME = "className";

    private boolean success;
    private String status;
    private String className;

    public NodeClassNameExtractorHandlerResult() {
        reset();
    }

    public NodeClassNameExtractorHandlerResult(boolean success, String status, String className) {
        this.success = success;
        this.status = status;
        this.className = className;
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
        className = "";
    }

    @Override
    public Optional<Object> getOptionalData(String dataId) {
        return dataId.equals(CLASS_NAME)
                ? Optional.of(className)
                : Optional.empty();
    }

    @Override
    public boolean setOptionalData(String dataId, Object optionalData) {
        if (dataId.equals(CLASS_NAME)){
            className = (String) optionalData;
            return true;
        }
        return false;
    }
}
