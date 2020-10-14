package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.SKClassMembersPartHandler;

public class USKClassMembersPartHandler {

    public static final ClassMembersPartHandler DEFAULT = new SKClassMembersPartHandler(
            "kind",
            "type",
            "className",
            "modifiers",
            "arguments"
    );
}
