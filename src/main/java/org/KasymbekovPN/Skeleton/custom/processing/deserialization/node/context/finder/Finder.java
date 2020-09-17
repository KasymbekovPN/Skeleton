package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;

public interface Finder {
    Des2NodeMode findEntityBegin(Character character);
    boolean findPropertyNameBegin(Character character);
    boolean findPropertyNameEnd(Character character);
    boolean findNameValueSeparator(Character character);
    boolean findValueBegin(Character character, Des2NodeMode mode);
    boolean findValueEnd(Character character, Des2NodeMode mode);
}
