package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.functional.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class SKDes2NodeContext implements Des2NodeContext {

    private final MultiContextIds<EntityItem> contextIds;
    private final ContextStateCareTaker<Des2NodeContextStateMemento> careTaker;
    private final DecrementedCharIterator iterator;
    private final ContextProcessor<Des2NodeContext> processor;
    private final MultiChecker<EntityItem, Character> entityBeginChecker;
    private final MultiChecker<EntityItem, Character> valueBeginChecker;
    private final MultiChecker<EntityItem, Character> valueEndChecker;
    private final Function<Character, Boolean> propertyNameBeginChecker;
    private final Function<Character, Boolean> propertyNameEndChecker;
    private final Function<Character, Boolean> valueNameSeparator;

    public SKDes2NodeContext(MultiContextIds<EntityItem> contextIds,
                             ContextStateCareTaker<Des2NodeContextStateMemento> careTaker,
                             DecrementedCharIterator iterator,
                             ContextProcessor<Des2NodeContext> processor,
                             MultiChecker<EntityItem, Character> entityBeginChecker,
                             MultiChecker<EntityItem, Character> valueBeginChecker,
                             MultiChecker<EntityItem, Character> valueEndChecker,
                             Function<Character, Boolean> propertyNameBeginChecker,
                             Function<Character, Boolean> propertyNameEndChecker,
                             Function<Character, Boolean> valueNameSeparator) {
        this.contextIds = contextIds;
        this.careTaker = careTaker;
        this.iterator = iterator;
        this.processor = processor;
        this.entityBeginChecker = entityBeginChecker;
        this.valueBeginChecker = valueBeginChecker;
        this.valueEndChecker = valueEndChecker;
        this.propertyNameBeginChecker = propertyNameBeginChecker;
        this.propertyNameEndChecker = propertyNameEndChecker;
        this.valueNameSeparator = valueNameSeparator;
    }

    @Override
    public DecrementedCharIterator iterator() {
        return iterator;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
        contextIds.setKey((EntityItem) careTaker.peek().getKey());
        processor.handle(this);
    }

    @Override
    public MultiChecker<EntityItem, Character> getEntityBeginChecker(EntityItem ei) {
        return entityBeginChecker;
    }

    @Override
    public MultiChecker<EntityItem, Character> getValueBeginChecker(EntityItem ei) {
        return valueBeginChecker;
    }

    @Override
    public MultiChecker<EntityItem, Character> getValueEndChecker(EntityItem ei) {
        return valueEndChecker;
    }

    @Override
    public Function<Character, Boolean> getPropertyNameBeginChecker() {
        return propertyNameBeginChecker;
    }

    @Override
    public Function<Character, Boolean> getPropertyNameEndChecker() {
        return propertyNameEndChecker;
    }

    @Override
    public Function<Character, Boolean> getValueNameSeparatorChecker() {
        return valueNameSeparator;
    }

    @Override
    public ContextIds getContextIds() throws ContextStateCareTakerIsEmpty {
        return contextIds;
    }

    @Override
    public ContextStateCareTaker<Des2NodeContextStateMemento> getContextStateCareTaker() {
        return careTaker;
    }
}
