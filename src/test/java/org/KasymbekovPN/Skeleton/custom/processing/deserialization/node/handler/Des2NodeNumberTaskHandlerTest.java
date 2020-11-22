package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.SKDes2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.SKDecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UDes2Node;
import org.KasymbekovPN.Skeleton.util.UHandlerIds;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;

@DisplayName("Des2NodeNumberTaskHandler. Testing of")
public class Des2NodeNumberTaskHandlerTest {
    private static final String NOT_VALID = "not valid";

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        "123.456",
                        123.456,
                        NodeEI.numberEI()
                },
                {
                        "x123y ",
                        0,
                        NodeEI.invalidEI()
                },
                {
                        "12.34.5",
                        0,
                        NodeEI.invalidEI()
                }
        };
    }

    @DisplayName("check method")
    @Test
    void testCheckMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.NUMBER);
        Des2NodeContext context = UDes2Node.createContext(new SKDecrementedCharIterator(""));
        context.getContextStateCareTaker().push(new NotValidMemento());
        tested.check(context);

        SimpleResult result = tested.getResult();
        Assertions.assertThat(result.isSuccess()).isFalse();
        Assertions.assertThat(result.getStatus()).isEqualTo(NOT_VALID);
    }

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getTestData")
    void testDoItMethod(String line, Number value, EntityItem ei) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        Tested tested = new Tested(UHandlerIds.NUMBER);
        Des2NodeContext context = UDes2Node.createContext(new SKDecrementedCharIterator(line));
        context.getContextStateCareTaker().push(new SKDes2NodeContextStateMemento(null));
        tested.check(context);

        Assertions.assertThat(tested.getResult().isSuccess()).isTrue();

        tested.doIt(context);
        Node node = context.getContextStateCareTaker().peek().getNode();
        Assertions.assertThat(node.getEI()).isEqualTo(ei);
        if (node.is(NodeEI.numberEI())){
            Assertions.assertThat(((NumberNode) node).getValue()).isEqualTo(value);
        }
    }

    private static class Tested extends Des2NodeNumberTaskHandler{

        public Tested(String id) {
            super(id);
            simpleResult = new SKSimpleResult();
        }

        public Tested(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
        }

        @Override
        public void doIt(Des2NodeContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
            super.doIt(context);
        }

        @Override
        public void check(Des2NodeContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }
    }

    private static class NotValidMemento implements Des2NodeContextStateMemento {

        @Override
        public void setKey(Object key) {

        }

        @Override
        public Object getKey() {
            return null;
        }

        @Override
        public void setNode(Node node) {

        }

        @Override
        public Node getNode() {
            return null;
        }

        @Override
        public Node getParentNode() {
            return null;
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        }

        @Override
        public SimpleResult getValidationResult() {
            SKSimpleResult result = new SKSimpleResult();
            result.setFailStatus(NOT_VALID);
            return result;
        }
    }
}
