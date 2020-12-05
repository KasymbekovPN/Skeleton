package org.KasymbekovPN.Skeleton.lib.processing.processor.context;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class ContextProcessorTest {
    private static final String TASK_ID = "task_id";
    private static final String HANDLER_ID = "handler_id";

    private ContextTask<TestContext> task;
    private ContextProcessor<TestContext> processor;

    @BeforeEach
    void init(){
        task = new ContextTask<>(TASK_ID);
        processor = new ContextProcessor<>();
    }

    @DisplayName("add/get/remove methods")
    @Test
    void testAddGetRemoveMethods(){
        Assertions.assertThat(processor.get(TASK_ID)).isEmpty();
        processor.add(task);
        Assertions.assertThat(processor.get(TASK_ID)).isPresent();
        Assertions.assertThat(processor.remove(task.getId())).isPresent();
        Assertions.assertThat(processor.remove(task.getId())).isEmpty();
    }

    @DisplayName("handle/getProcessorResult methods")
    @Test
    void testHandleGetProcessorResultMethods() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        TestContextImpl testContext = new TestContextImpl();
        testContext.getContextStateCareTaker().push(new TestContextStateMementoImpl());

        AggregateResult result = (AggregateResult) processor.handle(testContext);
        Assertions.assertThat(result.has(TASK_ID)).isFalse();

        processor.add(task);
        processor.handle(testContext);
        Assertions.assertThat(result.has(TASK_ID)).isTrue();
    }

    private interface TestContextStateMemento extends ContextStateMemento {}
    private static class TestContextStateMementoImpl implements TestContextStateMemento {

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        }

        @Override
        public SimpleResult getValidationResult() {
            return new SKSimpleResult();
        }
    }

    interface TestContext extends Context<TestContextStateMemento> {}
    private static class TestContextImpl implements TestContext {

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
}
