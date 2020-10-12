package org.KasymbekovPN.Skeleton.lib.context.state;

import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("SKContextStateCareTakerTest. Testing of:")
public class SKContextStateCareTakerTest {

    @DisplayName("isEmpty")
    @Test
    void testIsEmpty() throws ContextStateCareTakerIsEmpty {
        ContextStateCareTaker<ContextStateMementoImpl> ct = createContextStateCareTaker();
        assertThat(ct.isEmpty()).isTrue();

        ct.push(new ContextStateMementoImpl());
        assertThat(ct.isEmpty()).isFalse();

        ct.pop();
        assertThat(ct.isEmpty()).isTrue();
    }

    @DisplayName("test pop")
    @Test
    void testPop() throws ContextStateCareTakerIsEmpty {
        ContextStateCareTaker<ContextStateMementoImpl> ct = createContextStateCareTaker();
        ContextStateMementoImpl mem = new ContextStateMementoImpl();
        ct.push(mem);

        assertThat(ct.isEmpty()).isFalse();

        ContextStateMementoImpl popMem = ct.pop();

        assertThat(ct.isEmpty()).isTrue();
        assertThat(mem).isEqualTo(popMem);
    }

    @DisplayName("pop with throwable")
    @Test
    void testPopWithThrowable(){
        ContextStateCareTaker<ContextStateMementoImpl> ct = createContextStateCareTaker();
        Throwable throwable = catchThrowable(ct::pop);
        assertThat(throwable).isInstanceOf(ContextStateCareTakerIsEmpty.class);
    }

    @DisplayName("peek with throwable")
    @Test
    void testPeek() throws ContextStateCareTakerIsEmpty {
        ContextStateCareTaker<ContextStateMementoImpl> ct = createContextStateCareTaker();
        ContextStateMementoImpl mem = new ContextStateMementoImpl();
        ct.push(mem);

        assertThat(ct.isEmpty()).isFalse();

        ContextStateMementoImpl popMem = ct.peek();

        assertThat(ct.isEmpty()).isFalse();
        assertThat(mem).isEqualTo(popMem);
    }

    @DisplayName("peek with throwable")
    @Test
    void testPeekWithThrowable(){
        ContextStateCareTaker<ContextStateMementoImpl> ct = createContextStateCareTaker();
        Throwable throwable = catchThrowable(ct::peek);
        assertThat(throwable).isInstanceOf(ContextStateCareTakerIsEmpty.class);
    }

    private ContextStateCareTaker<ContextStateMementoImpl> createContextStateCareTaker(){
        return new SKContextStateCareTaker<>();
    }

    private static class ContextStateMementoImpl implements ContextStateMemento{
        @Override
        public boolean isValid() {
            return false;
        }
    }
}
