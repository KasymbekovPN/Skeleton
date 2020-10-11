package org.KasymbekovPN.Skeleton.lib.processing.handler.context;

import org.KasymbekovPN.Skeleton.lib.processing.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.ResultData;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

abstract public class BaseContextTaskHandler<T extends Context> implements TaskHandler<T> {

    private static final Class<? extends SimpleResult> SIMPLE_RESULT_CLASS = SKSimpleResult.class;

    protected final String id;
    protected SimpleResult simpleResult;

    public BaseContextTaskHandler(String id) {
        this.id = id;
    }

    public BaseContextTaskHandler(String id,
                                  SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
        this.id = id;
    }

    @Override
    public SimpleResult handle(T object) throws NoSuchMethodException,
                                                              InstantiationException,
                                                              IllegalAccessException,
                                                              InvocationTargetException {
        simpleResult = createSimpleResult();
        check(object);
        if (simpleResult.isSuccess()){
            doIt(object);
        }

        return getResult();
    }

    @Override
    public SimpleResult getResult() {
        return simpleResult;
    }

    @Override
    public String getId() {
        return id;
    }

    protected void check(T context){
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
