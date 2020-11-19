package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

import java.lang.reflect.InvocationTargetException;

public interface Des2NodeContext extends Context<Des2NodeContextStateMemento> {
    DecrementedCharIterator iterator();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException;
    MultiChecker<EntityItem, Character> getEntityBeginChecker();
    MultiChecker<EntityItem, Character> getValueBeginChecker();
    MultiChecker<EntityItem, Character> getValueEndChecker();
    SimpleChecker<Character> getPropertyNameBeginChecker();
    SimpleChecker<Character> getPropertyNameEndChecker();
    SimpleChecker<Character> getValueNameSeparatorChecker();
    void setKey(EntityItem key);
}
