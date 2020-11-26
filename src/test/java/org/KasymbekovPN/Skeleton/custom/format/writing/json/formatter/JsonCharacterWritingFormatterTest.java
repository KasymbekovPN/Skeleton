package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.custom.format.offset.SKOffset;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("JsonCharacterWritingFormatter. Testing of :")
public class JsonCharacterWritingFormatterTest {

    private static final String OFFSET = "    ";

    private static Object[][] getTestData(){
        return new Object[][]{
                {new BooleanNode(null, true), ""},
                {new CharacterNode(null, 'x'), "'x'"},
                {new InvalidNode(null, "", ""), ""},
                {new NumberNode(null, 123.45), ""},
                {new ObjectNode(null), ""},
                {new StringNode(null, "hello"), ""}
        };
    }

    @DisplayName("getValue method")
    @ParameterizedTest
    @MethodSource("getTestData")
    void testGetValue(Node node, String check){
        JsonCharacterWritingFormatter formatter = new JsonCharacterWritingFormatter(new SKOffset(OFFSET));
        Assertions.assertThat(formatter.getValue(node).getString()).isEqualTo(check);
    }

    @DisplayName("getBeginBorder method")
    @Test
    void testGetBeginBorder(){
        JsonCharacterWritingFormatter formatter = new JsonCharacterWritingFormatter(new SKOffset(OFFSET));
        Assertions.assertThat(formatter.getBeginBorder()).isEqualTo(new StringStringDecoder());
    }

    @DisplayName("getEndBorder method")
    @Test
    void testGetEndBorder(){
        JsonCharacterWritingFormatter formatter = new JsonCharacterWritingFormatter(new SKOffset(OFFSET));
        Assertions.assertThat(formatter.getEndBorder()).isEqualTo(new StringStringDecoder());
    }

    @DisplayName("getPropertyName method")
    @Test
    void testGetPropertyName(){
        JsonCharacterWritingFormatter formatter = new JsonCharacterWritingFormatter(new SKOffset(OFFSET));
        Assertions.assertThat(formatter.getPropertyName("")).isEqualTo(new StringStringDecoder());
    }

    @DisplayName("getDelimiters method")
    @Test
    void testGetDelimiters(){
        JsonCharacterWritingFormatter formatter = new JsonCharacterWritingFormatter(new SKOffset(OFFSET));
        Assertions.assertThat(formatter.getDelimiters(10)).isEmpty();
    }
}
