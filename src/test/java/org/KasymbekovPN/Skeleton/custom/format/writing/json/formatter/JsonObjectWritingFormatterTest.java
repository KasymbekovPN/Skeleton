package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.custom.format.offset.SKOffset;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("JsonObjectWritingFormatter. Testing of:")
public class JsonObjectWritingFormatterTest {

    private static final String OFFSET = "    ";
    private static final String BEGIN_BORDER = "{\n";
    private static final String END_BORDER = "\n%offset%}";
    private static final String BEGIN_NAME_BORDER = "%offset%\"";
    private static final String END_NAME_BORDER = "\":";
    private static final String FIRST_DELIMITER = "";
    private static final String DELIMITER = ",\n";

    @DisplayName("getBeginBorder method")
    @Test
    void testGetBeginBorder(){
        SKOffset offset = new SKOffset(OFFSET);
        JsonObjectWritingFormatter formatter = new JsonObjectWritingFormatter(
                offset,
                BEGIN_BORDER,
                END_BORDER,
                BEGIN_NAME_BORDER,
                END_NAME_BORDER,
                FIRST_DELIMITER,
                DELIMITER
        );

        StringDecoder beginBorder = formatter.getBeginBorder();
        Assertions.assertThat(beginBorder).isEqualTo(new StringStringDecoder(BEGIN_BORDER));
        Assertions.assertThat(OFFSET).isEqualTo(offset.get());
    }

    @DisplayName("getEndBorder method")
    @Test
    void testGetEndBorder(){
        SKOffset offset = new SKOffset(OFFSET);
        JsonObjectWritingFormatter formatter = new JsonObjectWritingFormatter(
                offset,
                BEGIN_BORDER,
                END_BORDER,
                BEGIN_NAME_BORDER,
                END_NAME_BORDER,
                FIRST_DELIMITER,
                DELIMITER
        );

        offset.inc();
        offset.inc();

        StringDecoder endBorder = formatter.getEndBorder();
        Assertions.assertThat(endBorder).isEqualTo(new StringStringDecoder("\n    }"));
        Assertions.assertThat(offset.get()).isEqualTo(OFFSET);
    }

    @DisplayName("getValue method")
    @Test
    void testGetValue(){
        SKOffset offset = new SKOffset(OFFSET);
        JsonObjectWritingFormatter formatter = new JsonObjectWritingFormatter(
                offset,
                BEGIN_BORDER,
                END_BORDER,
                BEGIN_NAME_BORDER,
                END_NAME_BORDER,
                FIRST_DELIMITER,
                DELIMITER
        );

        Assertions.assertThat(formatter.getValue(new ObjectNode(null))).isEqualTo(new StringStringDecoder());
    }

    @DisplayName("getPropertyName method")
    @Test
    void testGetPropertyName(){
        SKOffset offset = new SKOffset(OFFSET);
        JsonObjectWritingFormatter formatter = new JsonObjectWritingFormatter(
                offset,
                BEGIN_BORDER,
                END_BORDER,
                BEGIN_NAME_BORDER,
                END_NAME_BORDER,
                FIRST_DELIMITER,
                DELIMITER
        );

        Assertions.assertThat(formatter.getPropertyName("someName")).isEqualTo(new StringStringDecoder("\"someName\":"));
    }

    @DisplayName("getDelimiters method")
    @Test
    void testGetDelimiters(){
        SKOffset offset = new SKOffset(OFFSET);
        JsonObjectWritingFormatter formatter = new JsonObjectWritingFormatter(
                offset,
                BEGIN_BORDER,
                END_BORDER,
                BEGIN_NAME_BORDER,
                END_NAME_BORDER,
                FIRST_DELIMITER,
                DELIMITER
        );

        offset.inc();

        List<StringDecoder> delimiters = formatter.getDelimiters(3);
        ArrayList<StringDecoder> stringDecoders = new ArrayList<>(Arrays.asList(
                new StringStringDecoder(""),
                new StringStringDecoder(",\n"),
                new StringStringDecoder(",\n")
        ));
        Assertions.assertThat(delimiters).isEqualTo(stringDecoders);
    }
}
