package org.KasymbekovPN.Skeleton.custom.format.deserialization;

import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringStringDecoder that = (StringStringDecoder) o;
        return Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }
}
