package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.SKDes2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.SKWritingContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.iterator.SKDecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.util.UDes2Node;
import org.KasymbekovPN.Skeleton.util.UNodeWriting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Des2Node Task Handler complex test")
public class Des2NodeTaskHandlerComplexTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {createObjectNode()},
                {createArrayNode()},
                {new BooleanNode(null, true)},
                {new CharacterNode(null, 'x')},
                {new StringNode(null, "qwerty")},
                {new NumberNode(null, 12345.0)}
        };
    }

    private static Node createObjectNode(){
        Node objectNode = new ObjectNode(null);
        objectNode.addChild("stringValue", new StringNode(objectNode, "world"));
        objectNode.addChild("intValue", new NumberNode(objectNode, 123.0));
        objectNode.addChild("charValue", new CharacterNode(objectNode, 'x'));
        objectNode.addChild("boolValue", new BooleanNode(objectNode, true));

        ArrayNode innerArray = new ArrayNode(objectNode);
        objectNode.addChild("innerArray", innerArray);
        innerArray.addChild(new StringNode(innerArray, "111"));
        innerArray.addChild(new StringNode(innerArray, "222"));

        ObjectNode innerObject = new ObjectNode(objectNode);
        innerObject.addChild("innerInt", new NumberNode(innerObject, 456.0));

        return objectNode;
    }

    private static Node createArrayNode(){
        ArrayNode arrayNode = new ArrayNode(null);
        arrayNode.addChild(new NumberNode(arrayNode, 777.0));
        arrayNode.addChild(new StringNode(arrayNode, "hello, world!!!"));
        arrayNode.addChild(new CharacterNode(arrayNode, 'c'));
        arrayNode.addChild(new BooleanNode(arrayNode, true));

        ArrayNode innerArrayNode = new ArrayNode(arrayNode);
        arrayNode.addChild(innerArrayNode);
        innerArrayNode.addChild(new StringNode(innerArrayNode, "xyz"));

        ObjectNode innerObjectNode = new ObjectNode(arrayNode);
        arrayNode.addChild(innerObjectNode);
        innerObjectNode.addChild("str", new StringNode(innerObjectNode, "int"));

        return arrayNode;
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Node node) throws Exception {
        String line = convertToString(node);
        ContextProcessor<Des2NodeContext> processor = UDes2Node.createProcessor();
        Des2NodeContext context = UDes2Node.createContext(new SKDecrementedCharIterator(line));
        context.getContextStateCareTaker().push(new SKDes2NodeContextStateMemento(null));

        processor.handle(context);
        Des2NodeContextStateMemento memento = context.getContextStateCareTaker().peek();

        Node restoredNode = memento.getNode();

        Assertions.assertThat(node).isEqualTo(restoredNode);
    }

    private String convertToString(Node node) throws Exception {

        ContextProcessor<WritingContext> processor = UNodeWriting.createProcessor();
        WritingContext context = UNodeWriting.createContext();

        context.getContextStateCareTaker().push(new SKWritingContextStateMemento(
                node
        ));
        processor.handle(context);

        return context.getWritingFormatterHandler().getDecoder().getString();
    }
}
