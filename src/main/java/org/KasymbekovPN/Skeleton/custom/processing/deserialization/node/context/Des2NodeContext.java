package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.functional.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public interface Des2NodeContext extends Context<Des2NodeContextStateMemento> {
    DecrementedCharIterator iterator();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException;
    MultiChecker<EntityItem, Character> getEntityBeginChecker(EntityItem ei);
    MultiChecker<EntityItem, Character> getValueBeginChecker(EntityItem ei);
    MultiChecker<EntityItem, Character> getValueEndChecker(EntityItem ei);
    Function<Character, Boolean> getPropertyNameBeginChecker();
    Function<Character, Boolean> getPropertyNameEndChecker();
    Function<Character, Boolean> getValueNameSeparatorChecker();
}
