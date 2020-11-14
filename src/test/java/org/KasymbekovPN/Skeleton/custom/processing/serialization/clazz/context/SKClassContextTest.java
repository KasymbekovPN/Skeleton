package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.util.UClassSerialization;
import org.KasymbekovPN.Skeleton.util.USKClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import  static org.assertj.core.api.Assertions.*;

@DisplayName("SKClassContext. Testing of")
public class SKClassContextTest {

    private static Collector collector;
    private static ContextStateCareTaker<ClassContextStateMemento> careTaker;
    private static ClassContext classContext;

    @BeforeAll
    private static void beforeAll(){
        collector = new SKCollector();
        careTaker = UClassSerialization.createCateTaker();
        classContext = UClassSerialization.createClassContext(collector, careTaker);
    }

    @DisplayName("getClassPartPath method")
    @Test
    void testGetClassPartPath(){
        assertThat(classContext.getClassPartPath()).isEqualTo(USKCollectorPath.DEFAULT_CLASS_PART_PATH);
    }

    @DisplayName("getMembersPartPath method")
    @Test
    void testGetMembersPartPath(){
        assertThat(classContext.getMembersPartPath()).isEqualTo(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH);
    }

    @DisplayName("getCollector method")
    @Test
    void testGetCollector(){
        assertThat(classContext.getCollector()).isEqualTo(collector);
    }

    @DisplayName("getClassHeaderPartHandler method")
    @Test
    void testGetClassHeaderPartHandler(){
        assertThat(classContext.getClassHeaderPartHandler()).isEqualTo(USKClassHeaderPartHandler.DEFAULT);
    }

    @DisplayName("getClassMembersPartHandler method")
    @Test
    void testGetClassMembersPartHandler(){
        assertThat(classContext.getClassMembersPartHandler()).isEqualTo(USKClassMembersPartHandler.DEFAULT);
    }

    @DisplayName("getContextIds method")
    @Test
    void testGetContextIds() throws ContextStateCareTakerIsEmpty {
        assertThat(classContext.getContextIds()).isEqualTo(UClassSerialization.createContextIds());
    }

    @DisplayName("getContextStateCareTaker method")
    @Test
    void testGetContextStateCareTaker(){
        assertThat(classContext.getContextStateCareTaker()).isEqualTo(careTaker);
    }
}
