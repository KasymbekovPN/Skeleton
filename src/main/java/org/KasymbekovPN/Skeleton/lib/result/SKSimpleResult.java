package org.KasymbekovPN.Skeleton.lib.result;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class SKSimpleResult implements SimpleResult {

    private static final Class<? extends ResultData> RESULT_DATA_CLASS = SKResultData.class;

    private ResultData resultData;
    private boolean success = true;
    private String status = "";

    public SKSimpleResult() {
        reset();
    }

    public SKSimpleResult(ResultData resultData) {
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
    public void setFailStatus(String status) {
        this.success = false;
        this.status = status;
    }

    @Override
    public ResultData getResultData() throws InvocationTargetException,
                                             NoSuchMethodException,
                                             InstantiationException,
                                             IllegalAccessException {
        if (resultData == null){
            resultData = createResultData();
        }

        return resultData;
    }

    @Override
    public void reset() {
        success = true;
        status = "";
        if (resultData != null){
            resultData.clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKSimpleResult that = (SKSimpleResult) o;
        return success == that.success &&
                Objects.equals(resultData, that.resultData) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultData, success, status);
    }

    private ResultData createResultData() throws NoSuchMethodException,
                                                 IllegalAccessException,
                                                 InvocationTargetException,
                                                 InstantiationException {
        Constructor<? extends ResultData> constructor = RESULT_DATA_CLASS.getConstructor();
        return constructor.newInstance();
    }
}
