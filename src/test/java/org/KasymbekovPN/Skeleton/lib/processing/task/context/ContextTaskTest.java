package org.KasymbekovPN.Skeleton.lib.processing.task.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

@DisplayName("ContextTask. Testing of:")
public class ContextTaskTest {

    private static final String TASK_ID = "task_id";
    private static final String HANDLER_ID = "handler_id";

    private ContextTask<TestContext> task;

    @BeforeEach
    void init(){
        task = new ContextTask<>(TASK_ID);
    }

    @DisplayName("getId method")
    @Test
    void testGetIdMethod(){
        Assertions.assertThat(task.getId()).isEqualTo(TASK_ID);
    }

    @DisplayName("handle/add/getTaskResult methods")
    @Test
    void testHandleAddGetTaskResultMethods() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        TestContextImpl testContext = new TestContextImpl();
        testContext.getContextStateCareTaker().push(new TestContextStateMementoImpl());

        AggregateResult aggResult = (AggregateResult) task.handle(testContext);
        Assertions.assertThat(aggResult.has(HANDLER_ID)).isFalse();

        task.add(new TestHandler(HANDLER_ID));

        aggResult = (AggregateResult) task.handle(testContext);
        Assertions.assertThat(aggResult.has(HANDLER_ID)).isTrue();
    }

    private interface TestContextStateMemento extends ContextStateMemento{}
    private static class TestContextStateMementoImpl implements TestContextStateMemento{

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        }

        @Override
        public SimpleResult getValidationResult() {
            return new SKSimpleResult();
        }
    }

    interface TestContext extends Context<TestContextStateMemento>{}
    private static class TestContextImpl implements TestContext{

        private final ContextIds contextIds;
        private final ContextStateCareTaker<TestContextStateMemento> careTaker;

        public TestContextImpl() {
            contextIds = new SKSimpleContextIds(TASK_ID, HANDLER_ID);
            careTaker = new SKContextStateCareTaker<>();
        }

        @Override
        public ContextIds getContextIds() throws ContextStateCareTakerIsEmpty {
            return contextIds;
        }

        @Override
        public ContextStateCareTaker<TestContextStateMemento> getContextStateCareTaker() {
            return careTaker;
        }
    }

    private static class TestHandler extends BaseContextTaskHandler<TestContext>{

        public TestHandler(String id) {
            super(id);
        }

        public TestHandler(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
        }
    }
}
