package org.KasymbekovPN.Skeleton.custom.format.writing.json.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;

import java.util.ArrayList;
import java.util.List;

public class JsonObjectWritingFormatter extends JsonBaseWritingFormatter {

    private static final String BEGIN_BORDER = "{\n";
    private static final String END_BORDER = "\n%offset%}";
    private static final String BEGIN_NAME_BORDER = "%offset%\"";
    private static final String END_NAME_BORDER = "\":";
    private static final String FIRST_DELIMITER = "";
    private static final String DELIMITER = ",\n";

    private final String beginBorder;
    private final String endBorder;
    private final String beginNameBorder;
    private final String endNameBorder;
    private final String firstDelimiter;
    private final String delimiter;

    public JsonObjectWritingFormatter(Offset offset) {
        super(offset);
        this.beginBorder = BEGIN_BORDER;
        this.endBorder = END_BORDER;
        this.beginNameBorder = BEGIN_NAME_BORDER;
        this.endNameBorder = END_NAME_BORDER;
        this.firstDelimiter = FIRST_DELIMITER;
        this.delimiter = DELIMITER;
    }

    public JsonObjectWritingFormatter(Offset offset,
                                      String beginBorder,
                                      String endBorder,
                                      String beginNameBorder,
                                      String endNameBorder,
                                      String firstDelimiter,
                                      String delimiter) {
        super(offset);
        this.beginBorder = beginBorder;
        this.endBorder = endBorder;
        this.beginNameBorder = beginNameBorder;
        this.endNameBorder = endNameBorder;
        this.firstDelimiter = firstDelimiter;
        this.delimiter = delimiter;
    }

    @Override
    public StringDecoder getBeginBorder() {
        StringStringDecoder decoder = new StringStringDecoder(beginBorder);
        offset.inc();
        return decoder;
    }

    @Override
    public StringDecoder getEndBorder() {
        offset.dec();
        return new StringStringDecoder(offset.prepareTemplate(endBorder));
    }

    @Override
    public StringDecoder getPropertyName(String propertyName) {
        return new StringStringDecoder(
                offset.prepareTemplate(beginNameBorder) + propertyName + endNameBorder
        );
    }

    @Override
    public List<StringDecoder> getDelimiters(int size) {
        List<StringDecoder> delimiters = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            delimiters.add(new StringStringDecoder(0 == i ? firstDelimiter : delimiter));
        }
        return delimiters;
    }
}
