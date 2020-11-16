package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

public interface Des2NodeContext extends Context<Des2NodeContextStateMemento> {
    MultiChecker<EntityItem, Character> getEntityBeginChecker();
    MultiChecker<EntityItem, Character> getValueBeginChecker();
    MultiChecker<EntityItem, Character> getValueEndChecker();
    SimpleChecker<Character> getPropertyNameBeginChecker();
    SimpleChecker<Character> getPropertyNameEndChecker();
    SimpleChecker<Character> getValueNameSeparator();
    void setKey(EntityItem key);
}
