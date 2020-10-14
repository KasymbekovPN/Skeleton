package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.SKClassHeaderPartHandler;

public class USKClassHeaderPartHandler {
    public static final ClassHeaderPartHandler DEFAULT = new SKClassHeaderPartHandler(
            "type",
            "name",
            "modifiers"
    );
}
