package org.KasymbekovPN.Skeleton.custom.processing.writing.node.handler;

import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.WritingContext;
import org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state.SKWritingContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.util.UNodeWriting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Writing Task handlers Complex Test")
public class WritingTaskHandlerComplexTest {

    private static Object[][] getTestData(){

        ArrayNode strArray = new ArrayNode(null);
        strArray.addChild(new StringNode(strArray, "aaa"));
        strArray.addChild(new StringNode(strArray, "bbb"));

        ObjectNode objectNode = new ObjectNode(null);
        objectNode.addChild("intValue", new NumberNode(objectNode, 123));
        objectNode.addChild("booleanValue", new BooleanNode(objectNode, true));
        objectNode.addChild("characterValue", new CharacterNode(objectNode, 'a'));
        objectNode.addChild("stringValue", new StringNode(objectNode, "hello"));

        ObjectNode innerObject = new ObjectNode(objectNode);
        innerObject.addChild("innerInt", new NumberNode(innerObject, 999));
        objectNode.addChild("obj", innerObject);

        ArrayNode innerArray = new ArrayNode(objectNode);
        innerArray.addChild(new NumberNode(innerArray, 333));
        objectNode.addChild("arr", innerArray);

        return new Object[][]{
                {
                        objectNode,
                        "{\n" +
                                "    \"arr\":[\n" +
                                "        333\n" +
                                "    ],\n" +
                                "    \"stringValue\":\"hello\",\n" +
                                "    \"intValue\":123,\n" +
                                "    \"obj\":{\n" +
                                "        \"innerInt\":999\n" +
                                "    },\n" +
                                "    \"characterValue\":'a',\n" +
                                "    \"booleanValue\":true\n" +
                                "}",
                        true
                },
                {
                        strArray,
                        "[\n    \"aaa\",\n    \"bbb\"\n]",
                        true
                },
                {
                        new ObjectNode(null),
                        "{\n\n}",
                        true
                },
                {
                        new ObjectNode(null),
                        "{}",
                        false
                },
                {
                        new ArrayNode(null),
                        "[\n\n]",
                        true
                },
                {
                        new ArrayNode(null),
                        "[]",
                        false
                },
                {
                        new BooleanNode(null, true),
                        "true",
                        true
                },
                {
                        new BooleanNode(null, true),
                        "true1",
                        false
                },
                {
                        new BooleanNode(null, false),
                        "false",
                        true
                },
                {
                        new BooleanNode(null, false),
                        "false111",
                        false
                },
                {
                        new CharacterNode(null, 'a'),
                        "'a'",
                        true
                },
                {
                        new CharacterNode(null, 'b'),
                        "b",
                        false
                },
                {
                        new NumberNode(null, 123),
                        "123",
                        true
                },
                {
                        new NumberNode(null, 123),
                        "567",
                        false
                },
                {
                        new StringNode(null, "hello"),
                        "\"hello\"",
                        true
                },
                {
                        new StringNode(null, "world"),
                        "\"hello\"",
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Node node, String checkLine, boolean success) throws Exception {
        WritingContext context = UNodeWriting.createContext();
        ContextProcessor<WritingContext> processor = UNodeWriting.createProcessor();

        context.getContextStateCareTaker().push(new SKWritingContextStateMemento(node));
        processor.handle(context);

        String line = context.getWritingFormatterHandler().getDecoder().getString();
        Assertions.assertThat(line.equals(checkLine)).isEqualTo(success);
    }
}
