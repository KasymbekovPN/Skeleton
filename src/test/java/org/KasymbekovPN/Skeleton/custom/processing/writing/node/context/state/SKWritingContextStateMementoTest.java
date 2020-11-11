package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state;

import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

@DisplayName("SKWritingContextStateMemento. Testing of:")
public class SKWritingContextStateMementoTest {

    private static final String NODE_IS_NULL = "Node is null";

    @DisplayName("validate method - node is null")
    @Test
    void testValidateMethod_nodeIsNull() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        SKWritingContextStateMemento mem = new SKWritingContextStateMemento(null);
        mem.validate();
        SimpleResult validationResult = mem.getValidationResult();

        Assertions.assertThat(validationResult.isSuccess()).isFalse();
        Assertions.assertThat(validationResult.getStatus()).isEqualTo(NODE_IS_NULL);
    }

    @DisplayName("getNode method")
    @Test
    void testGetNode(){

        SKCollector collector = new SKCollector();
        collector.beginObject("object");
        collector.addProperty("string", "hello");
        collector.reset();

        Node node = collector.getNode();
        SKWritingContextStateMemento mem = new SKWritingContextStateMemento(node);
        Assertions.assertThat(node).isEqualTo(mem.getNode());
    }
}
