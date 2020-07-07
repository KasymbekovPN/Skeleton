package org.KasymbekovPN.Skeleton.custom.format.deserialization;

import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

public class StringStringDecoder implements StringDecoder {

    private final String str;

    public StringStringDecoder(String str) {
        this.str = str;
    }

    @Override
    public String getString() {
        return str;
    }
}
