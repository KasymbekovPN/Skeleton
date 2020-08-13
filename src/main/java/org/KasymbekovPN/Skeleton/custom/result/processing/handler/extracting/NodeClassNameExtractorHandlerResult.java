package org.KasymbekovPN.Skeleton.custom.result.processing.handler.extracting;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.Optional;

public class NodeClassNameExtractorHandlerResult extends BaseResultImpl {

    private static final String CLASS_NAME = "className";

    private String className;

    public NodeClassNameExtractorHandlerResult() {
        super();
    }

    @Override
    public void reset() {
        super.reset();
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

    @Override
    public Result createNew() {
        return new NodeClassNameExtractorHandlerResult();
    }
}
