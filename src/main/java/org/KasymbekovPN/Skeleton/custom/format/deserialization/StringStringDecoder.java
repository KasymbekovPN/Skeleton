package org.KasymbekovPN.Skeleton.custom.format.deserialization;

import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

// TODO: 22.11.2020 test
public class StringStringDecoder implements StringDecoder {

    private String str;

    public StringStringDecoder() {
        this.str = "";
    }

    public StringStringDecoder(String str) {
        this.str = str;
    }

    @Override
    public String getString() {
        return str;
    }
}
