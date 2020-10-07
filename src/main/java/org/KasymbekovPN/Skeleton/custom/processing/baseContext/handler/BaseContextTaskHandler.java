package org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.ResultData;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SkeletonSimpleResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

abstract public class BaseContextTaskHandler<T extends Context> implements TaskHandler<T> {

    private static final Class<? extends SimpleResult> SIMPLE_RESULT_CLASS = SkeletonSimpleResult.class;

    protected SimpleResult simpleResult;

    public BaseContextTaskHandler() {
    }

    public BaseContextTaskHandler(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    @Override
    public SimpleResult handle(T object, Task<T> task) throws NoSuchMethodException,
                                                              InstantiationException,
                                                              IllegalAccessException,
                                                              InvocationTargetException {
        simpleResult = createSimpleResult();
        check(object, task);
        if (simpleResult.isSuccess()){
            doIt(object);
        }

        return getResult();
    }

    @Override
    public SimpleResult getResult() {
        return simpleResult;
    }

    protected void check(T context, Task<T> task){
    }

    protected void doIt(T context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    private SimpleResult createSimpleResult() throws InvocationTargetException,
                                                     NoSuchMethodException,
                                                     InstantiationException,
                                                     IllegalAccessException {
        return simpleResult == null
                ? createDefaultSimpleResult()
                : createSimpleResultByPrevious();
    }

    private SimpleResult createDefaultSimpleResult() throws IllegalAccessException,
                                                            InvocationTargetException,
                                                            InstantiationException,
                                                            NoSuchMethodException {
        Constructor<? extends SimpleResult> constructor = SIMPLE_RESULT_CLASS.getConstructor();
        return constructor.newInstance();
    }

    private SimpleResult createSimpleResultByPrevious() throws NoSuchMethodException,
                                                               InstantiationException,
                                                               IllegalAccessException,
                                                               InvocationTargetException {
        Constructor<? extends SimpleResult> simpleResultConstructor
                = simpleResult.getClass().getConstructor(ResultData.class);
        Constructor<? extends ResultData> resultDataConstructor
                = simpleResult.getResultData().getClass().getConstructor();

        ResultData resultData = resultDataConstructor.newInstance();
        return simpleResultConstructor.newInstance(resultData);
    }
}
