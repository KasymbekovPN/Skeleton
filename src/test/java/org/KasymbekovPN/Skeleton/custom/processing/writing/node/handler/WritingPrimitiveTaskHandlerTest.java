package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.SKWritingContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.WritingContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UHandlerIds;
import org.KasymbekovPN.Skeleton.util.UNodeWriting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;

@DisplayName("WritingPrimitiveTaskHandler. Testing of:")
public class WritingPrimitiveTaskHandlerTest {

    private static final String NOT_VALID = "node valid";
    private static final String WRONG_NODE_TYPE = "Node has wrong type - %s";

    private static Object[][] getCheckMethodTestData(){
        return new Object[][]{
                {new BooleanNode(null, true), "", true},
                {new CharacterNode(null, 'x'), "", true},
                {new NumberNode(null, 123), "", true},
                {new StringNode(null, "hello"), "", true},
                {new ArrayNode(null), WRONG_NODE_TYPE, false},
                {new ObjectNode(null), WRONG_NODE_TYPE, false},
                {new InvalidNode(null, "", ""), WRONG_NODE_TYPE, false}
        };
    }

    private static Object[][] getDoItMethodCheckData() {
        return new Object[][]{
                {new BooleanNode(null, true), "true"},
                {new CharacterNode(null, 'x'), "'x'"},
                {new NumberNode(null, 456), "456"},
                {new StringNode(null, "world"), "\"world\""}
        };
    }

    @DisplayName("check method - not valid memento")
    @Test
    void testCheckMethod_notValidMemento() throws Exception {
        Tested tested = new Tested(UHandlerIds.PRIMITIVE);

        WritingContext context = UNodeWriting.createContext();
        context.getContextStateCareTaker().push(
                new NotValidMemento()
        );
        tested.check(context);

        SimpleResult result = tested.getResult();
        Assertions.assertThat(result.isSuccess()).isFalse();
        Assertions.assertThat(result.getStatus()).isEqualTo(NOT_VALID);
    }

    @DisplayName("check method - all node types")
    @ParameterizedTest
    @MethodSource("getCheckMethodTestData")
    void testCheckMethod_allNodeTypes(Node node, String status, boolean success) throws Exception {

        Tested tested = new Tested(UHandlerIds.PRIMITIVE);
        WritingContext context = UNodeWriting.createContext();
        context.getContextStateCareTaker().push(
                new SKWritingContextStateMemento(node)
        );
        tested.check(context);

        SimpleResult result = tested.getResult();
        Assertions.assertThat(result.isSuccess()).isEqualTo(success);
        Assertions.assertThat(result.getStatus()).isEqualTo(String.format(status, node.getEI()));
    }

    @DisplayName("doIt method")
    @ParameterizedTest
    @MethodSource("getDoItMethodCheckData")
    void testDoItMethod(Node node, String line) throws Exception {
        Tested tested = new Tested(UHandlerIds.PRIMITIVE);
        WritingContext context = UNodeWriting.createContext();
        context.getContextStateCareTaker().push(
                new SKWritingContextStateMemento(node)
        );

        tested.check(context);
        Assertions.assertThat(tested.getResult().isSuccess()).isTrue();
        Assertions.assertThat(tested.getResult().getStatus()).isEmpty();

        tested.doIt(context);
        Assertions.assertThat(context.getWritingFormatterHandler().getDecoder().getString()).isEqualTo(line);
    }

    private static class Tested extends WritingPrimitiveTaskHandler{

        public Tested(String id) {
            super(id);
            simpleResult =new SKSimpleResult();
        }

        public Tested(String id, SimpleResult simpleResult) {
            super(id, simpleResult);
        }

        @Override
        public void check(WritingContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void doIt(WritingContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
            super.doIt(context);
        }
    }

    private static class NotValidMemento implements WritingContextStateMemento{

        @Override
        public Node getNode() {
            return new ObjectNode(null);
        }

        @Override
        public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        }

        @Override
        public SimpleResult getValidationResult() {
            SKSimpleResult skSimpleResult = new SKSimpleResult();
            skSimpleResult.setFailStatus(NOT_VALID);
            return skSimpleResult;
        }
    }
}
