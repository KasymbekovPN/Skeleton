package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state.Des2NodeContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.iterator.SKDecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.util.UDes2Node;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SKDes2NodeContext. Testing of:")
public class SKDes2NodeContextTest {

    static private MultiContextIds<EntityItem> contextIds;
    static private ContextStateCareTaker<Des2NodeContextStateMemento> careTaker;
    static private MultiChecker<EntityItem, Character> entityBeginChecker;
    static private MultiChecker<EntityItem, Character> valueBeginChecker;
    static private MultiChecker<EntityItem, Character> valueEndChecker;
    static private SimpleChecker<Character> propertyNameBeginChecker;
    static private SimpleChecker<Character> propertyNameEndChecker;
    static private SimpleChecker<Character> valueNameSeparator;
    static private ContextProcessor<Des2NodeContext> processor;
    static private DecrementedCharIterator iterator;
    static private Des2NodeContext context;

    @BeforeAll
    static void init(){
        contextIds = UDes2Node.createContextIds();
        careTaker = new SKContextStateCareTaker<>();
        entityBeginChecker = UDes2Node.createEntityBeginChecker();
        valueBeginChecker = UDes2Node.createValueBeginChecker();
        valueEndChecker = UDes2Node.createValueEndChecker();
        propertyNameBeginChecker = UDes2Node.createPropertyNameBeginChecker();
        propertyNameEndChecker = UDes2Node.createPropertyNameEndChecker();
        valueNameSeparator = UDes2Node.createValueNameSeparator();
        processor = UDes2Node.createProcessor();
        iterator = new SKDecrementedCharIterator("hello");
        context = new SKDes2NodeContext(
                contextIds,
                careTaker,
                iterator,
                processor,
                entityBeginChecker,
                valueBeginChecker,
                valueEndChecker,
                propertyNameBeginChecker,
                propertyNameEndChecker,
                valueNameSeparator
        );
    }

    @DisplayName("iterator method")
    @Test
    void testIterator(){
        Assertions.assertThat(iterator).isEqualTo(context.iterator());
    }

    @DisplayName("getEntityBeginChecker method")
    @Test
    void testGetEntityBeginChecker(){
        Assertions.assertThat(entityBeginChecker).isEqualTo(context.getEntityBeginChecker(null));
    }

    @DisplayName("getValueBeginChecker method")
    @Test
    void testGetValueBeginChecker(){
        Assertions.assertThat(valueBeginChecker).isEqualTo(context.getValueBeginChecker(null));
    }

    @DisplayName("getValueEndChecker method")
    @Test
    void testGetValueEndChecker(){
        Assertions.assertThat(valueEndChecker).isEqualTo(context.getValueEndChecker(null));
    }

    @DisplayName("getPropertyNameBeginChecker method")
    @Test
    void testGetPropertyNameBeginChecker(){
        Assertions.assertThat(propertyNameBeginChecker).isEqualTo(context.getPropertyNameBeginChecker());
    }

    @DisplayName("getPropertyNameEndChecker method")
    @Test
    void testGetPropertyNameEndChecker(){
        Assertions.assertThat(propertyNameEndChecker).isEqualTo(context.getPropertyNameEndChecker());
    }

    @DisplayName("getValueNameSeparator method")
    @Test
    void testGetValueNameSeparator(){
        Assertions.assertThat(valueNameSeparator).isEqualTo(context.getValueNameSeparatorChecker());
    }

    @DisplayName("getContextIds method")
    @Test
    void testGetContextIds() throws ContextStateCareTakerIsEmpty {
        Assertions.assertThat(contextIds).isEqualTo(context.getContextIds());
    }

    @DisplayName("getContextStateCareTaker method")
    @Test
    void testGetContextStateCareTaker(){
        Assertions.assertThat(careTaker).isEqualTo(context.getContextStateCareTaker());
    }
}
