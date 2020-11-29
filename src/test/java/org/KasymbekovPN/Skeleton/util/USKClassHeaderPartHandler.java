package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SKClassHeaderPartHandler;

public class USKClassHeaderPartHandler {

    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String MODIFIERS = "modifiers";

    public static final ClassHeaderPartHandler DEFAULT = new SKClassHeaderPartHandler(
            "type",
            "name",
            "modifiers"
    );
}
