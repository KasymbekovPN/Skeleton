package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

import java.lang.reflect.InvocationTargetException;

public class SKDes2NodeContext implements Des2NodeContext {

    private final MultiContextIds<EntityItem> contextIds;
    private final ContextStateCareTaker<Des2NodeContextStateMemento> careTaker;
    private final DecrementedCharIterator iterator;
    private final ContextProcessor<Des2NodeContext> processor;
    private final MultiChecker<EntityItem, Character> entityBeginChecker;
    private final MultiChecker<EntityItem, Character> valueBeginChecker;
    private final MultiChecker<EntityItem, Character> valueEndChecker;
    private final SimpleChecker<Character> propertyNameBeginChecker;
    private final SimpleChecker<Character> propertyNameEndChecker;
    private final SimpleChecker<Character> valueNameSeparator;

    private EntityItem key;

    public SKDes2NodeContext(MultiContextIds<EntityItem> contextIds,
                             ContextStateCareTaker<Des2NodeContextStateMemento> careTaker,
                             DecrementedCharIterator iterator,
                             ContextProcessor<Des2NodeContext> processor,
                             MultiChecker<EntityItem, Character> entityBeginChecker,
                             MultiChecker<EntityItem, Character> valueBeginChecker,
                             MultiChecker<EntityItem, Character> valueEndChecker,
                             SimpleChecker<Character> propertyNameBeginChecker,
                             SimpleChecker<Character> propertyNameEndChecker,
                             SimpleChecker<Character> valueNameSeparator) {
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
        processor.handle(this);
    }

    @Override
    public MultiChecker<EntityItem, Character> getEntityBeginChecker() {
        return entityBeginChecker;
    }

    @Override
    public MultiChecker<EntityItem, Character> getValueBeginChecker() {
        return valueBeginChecker;
    }

    @Override
    public MultiChecker<EntityItem, Character> getValueEndChecker() {
        return valueEndChecker;
    }

    @Override
    public SimpleChecker<Character> getPropertyNameBeginChecker() {
        return propertyNameBeginChecker;
    }

    @Override
    public SimpleChecker<Character> getPropertyNameEndChecker() {
        return propertyNameEndChecker;
    }

    @Override
    public SimpleChecker<Character> getValueNameSeparator() {
        return valueNameSeparator;
    }

    @Override
    public void setKey(EntityItem key) {
        this.key = key;
    }

    @Override
    public ContextIds getContextIds() throws ContextStateCareTakerIsEmpty {
        contextIds.setKey(key);
        return contextIds;
    }

    @Override
    public ContextStateCareTaker<Des2NodeContextStateMemento> getContextStateCareTaker() {
        return careTaker;
    }
}
