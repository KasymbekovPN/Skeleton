package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SKClassMembersPartHandler;

public class USKClassMembersPartHandler {

    public static final String KIND = "kind";
    public static final String TYPE = "type";
    public static final String CLASS_NAME = "className";
    public static final String MODIFIERS = "modifiers";
    public static final String ARGUMENTS = "arguments";

    public static final ClassMembersPartHandler DEFAULT = new SKClassMembersPartHandler(
            KIND,
            TYPE,
            CLASS_NAME,
            MODIFIERS,
            ARGUMENTS
    );
}
