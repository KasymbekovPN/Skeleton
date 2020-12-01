package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.InstanceGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.util.USKDes2Instance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SKDes2InstanceCxt: Testing of:")
public class SKDes2InstanceCxtTest {

    private static Des2InstanceCxt context;
    private static SKContextStateCareTaker<Des2InstanceContextStateMemento> careTaker;

    @BeforeAll
    private static void beforeAll() throws InstanceGeneratorBuildNoOneGenerator, InstanceGeneratorBuildSomeGeneratorsReturnNull {
        Map<String, Class<?>> map = createMap();
        careTaker = new SKContextStateCareTaker<>();
        context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                createClassNodes(),
                USKDes2Instance.createDummyObjectInstanceGenerator(),
                USKDes2Instance.createProcessor(),
                careTaker
        );
    }

    private static Map<String, ObjectNode> createClassNodes(){
        return new HashMap<>() {{
            put("test", new ObjectNode(null));
        }};
    }

    private static Map<String, Class<?>> createMap(){
        return new HashMap<>(){{
            put("Integer", Integer.class);
            put("String", String.class);
        }};
    }

    @DisplayName("getContextIds method")
    @Test
    void testGetContextIdsMethod() throws ContextStateCareTakerIsEmpty {
        assertThat(context.getContextIds()).isEqualTo(USKDes2Instance.createContextIds());
    }

    @DisplayName("getCollectionGenerator method")
    @Test
    void testGetCollectionGeneratorMethod() throws InstanceGeneratorBuildNoOneGenerator, InstanceGeneratorBuildSomeGeneratorsReturnNull {
        assertThat(context.getCollectionGenerator())
                .isEqualTo(USKDes2Instance.createCollectionGenerator());
    }

    @DisplayName("getMapGenerator method")
    @Test
    void testGetMapGeneratorMethod() throws InstanceGeneratorBuildSomeGeneratorsReturnNull, InstanceGeneratorBuildNoOneGenerator {
        assertThat(context.getMapGenerator())
                .isEqualTo(USKDes2Instance.createMapGenerator());
    }

    @DisplayName("getInstanceGenerator method")
    @Test
    void testGetInstanceGeneratorMethod() throws InstanceGeneratorBuildSomeGeneratorsReturnNull, InstanceGeneratorBuildNoOneGenerator {
        assertThat(context.getInstanceGenerator())
                .isEqualTo(USKDes2Instance.createDummyObjectInstanceGenerator());
    }

    @DisplayName("getClassMembersPartHandler method")
    @Test
    void testGetClassMembersPartHandler(){
        assertThat(context.getClassMembersPartHandler())
                .isEqualTo(USKClassMembersPartHandler.DEFAULT);
    }

    @DisplayName("getContextIds method")
    @Test
    void testGetContextIds() throws ContextStateCareTakerIsEmpty {
        assertThat(context.getContextIds()).isEqualTo(USKDes2Instance.createContextIds());
    }

    @DisplayName("getContextStateCareTaker method")
    @Test
    void testGetContextStateCareTaker(){
        assertThat(context.getContextStateCareTaker()).isEqualTo(careTaker);
    }
}
