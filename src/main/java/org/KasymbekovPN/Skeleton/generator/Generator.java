package org.KasymbekovPN.Skeleton.generator;

import org.KasymbekovPN.Skeleton.generator.writer.Writer;

import java.util.List;

public interface Generator {
    void clear();
    void reset();

    void beginObject(String property);
    void beginObject();

    void addProperty(String property, String value);
    void addProperty(String property, Number value);
    void addProperty(String property, Boolean value);
    void addProperty(String property, Character value);

    void beginArray(String property);
    void beginArray();

    void addProperty(String value);
    void addProperty(Number value);
    void addProperty(Boolean value);
    void addProperty(Character value);

    void end();

    void write(Writer writer);

    void setTarget(List<String> path);
}
