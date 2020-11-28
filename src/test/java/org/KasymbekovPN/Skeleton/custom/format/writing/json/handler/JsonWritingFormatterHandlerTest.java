package org.KasymbekovPN.Skeleton.custom.format.writing.json.handler;

import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.util.UNodeWriting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@DisplayName("JsonWritingFormatterHandler. Testing of:")
public class JsonWritingFormatterHandlerTest {

    private static Object[][] getTestDataGetDelimiters(){
        return new Object[][]{
                {2, new ArrayNode(null), new ArrayList<>(Arrays.asList("", ",\n"))},
                {2, new BooleanNode(null, true), new ArrayList<>()},
                {2, new CharacterNode(null, 'x'), new ArrayList<>()},
                {2, new InvalidNode(null, "", ""), new ArrayList<>()},
                {2, new NumberNode(null, 123.0), new ArrayList<>()},
                {2, new ObjectNode(null), new ArrayList<>(Arrays.asList("", ",\n"))},
                {2, new StringNode(null, "123"), new ArrayList<>()}
        };
    }

    private static Object[][] getAddDelimitersResetMethod(){
        return new Object[][]{
                {new ArrayList<>(Arrays.asList("a", "b", "c"))},
                {new ArrayList<>(Arrays.asList("x", "y", "z"))}
        };
    }

    private static Object[][] getTestDataAddBeginBorder(){
        return new Object[][]{
                {new ArrayNode(null), "[\n"},
                {new BooleanNode(null, true), ""},
                {new CharacterNode(null, 'x'), ""},
                {new InvalidNode(null, "", ""), ""},
                {new NumberNode(null, 123.0), ""},
                {new ObjectNode(null), "{\n"},
                {new StringNode(null, "hello"), ""}
        };
    }

    private static Object[][] getTestDataAddEndBorder(){
        return new Object[][]{
                {new ArrayNode(null), "\n]"},
                {new BooleanNode(null, true), ""},
                {new CharacterNode(null, 'x'), ""},
                {new InvalidNode(null, "", ""), ""},
                {new NumberNode(null, 123.0), ""},
                {new ObjectNode(null), "\n}"},
                {new StringNode(null, "hello"), ""}
        };
    }

    private static Object[][] getTestAddValue(){
        return new Object[][]{
                {new ArrayNode(null), ""},
                {new BooleanNode(null, true), "true"},
                {new CharacterNode(null, 'x'), "'x'"},
                {new InvalidNode(null, "", ""), ""},
                {new NumberNode(null, 123.0), "123.0"},
                {new ObjectNode(null), ""},
                {new StringNode(null, "hello"), "\"hello\""}
        };
    }

    private static Object[][] getTestDataAddPropertyName(){
        return new Object[][]{
                {new ArrayNode(null), "someName",""},
                {new BooleanNode(null, true), "someName", ""},
                {new CharacterNode(null, 'x'), "someName", ""},
                {new InvalidNode(null, "", ""), "someName", ""},
                {new NumberNode(null, 123.0), "someName", ""},
                {new ObjectNode(null), "someName", "\"someName\":"},
                {new StringNode(null, "hello"), "someName", ""}
        };
    }

    @DisplayName("getDelimiters method")
    @ParameterizedTest
    @MethodSource("getTestDataGetDelimiters")
    void testGetDelimitersMethod(int size, Node node, List<String> check) throws Exception {
        WritingFormatterHandler wfh = UNodeWriting.createWritingFormatterHandler();
        Assertions.assertThat(wfh.getDelimiters(size, node)).isEqualTo(check);
    }

    @DisplayName("addDelimiter/reset method")
    @ParameterizedTest
    @MethodSource("getAddDelimitersResetMethod")
    void testAddDelimitersResetMethods(List<String> arr) throws Exception {
        WritingFormatterHandler wfh = UNodeWriting.createWritingFormatterHandler();
        Iterator<String> iterator = arr.iterator();
        StringBuilder line = new StringBuilder();
        for (String s : arr) {
            line.append(s);
            wfh.addDelimiter(iterator);
        }

        Assertions.assertThat(wfh.getDecoder().getString()).isEqualTo(line.toString());

        wfh.reset();
        Assertions.assertThat(wfh.getDecoder().getString()).isEmpty();
    }

    @DisplayName("addBeginBorder method")
    @ParameterizedTest
    @MethodSource("getTestDataAddBeginBorder")
    void testAddBeginBorderMethod(Node node, String check) throws Exception {
        WritingFormatterHandler wfh = UNodeWriting.createWritingFormatterHandler();
        wfh.addBeginBorder(node);
        Assertions.assertThat(wfh.getDecoder().getString()).isEqualTo(check);
    }

    @DisplayName("addEndBorder method")
    @ParameterizedTest
    @MethodSource("getTestDataAddEndBorder")
    void testAddEndBorderMethod(Node node, String check) throws Exception {
        WritingFormatterHandler wfh = UNodeWriting.createWritingFormatterHandler();
        wfh.addEndBorder(node);
        Assertions.assertThat(wfh.getDecoder().getString()).isEqualTo(check);
    }

    @DisplayName("addValue method")
    @ParameterizedTest
    @MethodSource("getTestAddValue")
    void testAddValueMethod(Node node, String check) throws Exception {
        WritingFormatterHandler wfh = UNodeWriting.createWritingFormatterHandler();
        wfh.addValue(node);
        Assertions.assertThat(wfh.getDecoder().getString()).isEqualTo(check);
    }

    @DisplayName("addPropertyName method")
    @ParameterizedTest
    @MethodSource("getTestDataAddPropertyName")
    void testAddPropertyNameMethod(Node node, String name, String check) throws Exception {
        WritingFormatterHandler wfh = UNodeWriting.createWritingFormatterHandler();
        wfh.addPropertyName(node, name);
        Assertions.assertThat(wfh.getDecoder().getString()).isEqualTo(check);
    }
}
