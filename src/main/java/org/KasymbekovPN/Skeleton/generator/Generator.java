package org.KasymbekovPN.Skeleton.generator;

public interface Generator {
    void beginObject();
    void beginObject(String property);
    void addProperty(String property, String value);
    void addProperty(String property, Number value);
    void addProperty(String property, Boolean value);
    void addProperty(String property, Character value);
    void endObject();

    void beginArray(String property);
    void addProperty(String value);
    void addProperty(Number value);
    void addProperty(Boolean value);
    void addProperty(Character value);
    void endArray();

    void write(Writer writer);
}