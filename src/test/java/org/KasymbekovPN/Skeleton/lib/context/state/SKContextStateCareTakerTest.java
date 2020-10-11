package org.KasymbekovPN.Skeleton.lib.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("SKContextStateCareTakerTest. Testing of:")
public class SKContextStateCareTakerTest {

    private static Object[][] getTestDataForPush(){
        return new Object[][]{
                {new ContextStateMementoImpl(), true},
                {new WrongContextStateMementoImpl(), false}
        };
    }

    @DisplayName("isEmpty")
    @Test
    void testIsEmpty() throws ContextStateCareTakerIsEmpty {
        ContextStateCareTaker ct = createContextStateCareTaker();
        assertThat(ct.isEmpty()).isTrue();

        ct.push(new ContextStateMementoImpl());
        assertThat(ct.isEmpty()).isFalse();

        ct.pop();
        assertThat(ct.isEmpty()).isTrue();
    }

    @DisplayName("push")
    @ParameterizedTest
    @MethodSource("getTestDataForPush")
    void testPush(ContextStateMemento value, boolean result){
        ContextStateCareTaker ct = createContextStateCareTaker();
        assertThat(ct.push(value)).isEqualTo(result);
    }

    @DisplayName("pop with throwable")
    @Test
    void testPopWithThrowable(){
        ContextStateCareTaker ct = createContextStateCareTaker();
        Throwable throwable = catchThrowable(ct::pop);
        assertThat(throwable).isInstanceOf(ContextStateCareTakerIsEmpty.class);
    }

    private ContextStateCareTaker createContextStateCareTaker(){
        SKSimpleChecker<Class<? extends ContextStateMemento>> simpleChecker
                = new SKSimpleChecker<>(ContextStateMementoImpl.class);
        return new SKContextStateCareTaker(simpleChecker);
    }

    private static class ContextStateMementoImpl implements ContextStateMemento{
        @Override
        public boolean isValid() {
            return false;
        }
    }

    private static class WrongContextStateMementoImpl implements ContextStateMemento{
        @Override
        public boolean isValid() {
            return false;
        }
    }
}
