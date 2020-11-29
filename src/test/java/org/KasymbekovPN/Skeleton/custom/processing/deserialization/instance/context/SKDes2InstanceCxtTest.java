package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2MapOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildNoOneGenerator;
import org.KasymbekovPN.Skeleton.exception.optionalConverter.CollectionGenerator.CollectionGeneratorBuildSomeGeneratorsReturnNull;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.util.USKClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
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
    private static void beforeAll() throws CollectionGeneratorBuildNoOneGenerator, CollectionGeneratorBuildSomeGeneratorsReturnNull {
        Map<String, Class<?>> map = createMap();
        careTaker = new SKContextStateCareTaker<>();
        context = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                createClassNodes(),
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
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
    void testGetCollectionGeneratorMethod() throws CollectionGeneratorBuildNoOneGenerator, CollectionGeneratorBuildSomeGeneratorsReturnNull {
        assertThat(context.getCollectionGenerator())
                .isEqualTo(USKDes2Instance.createCollectionGenerator());
    }

    @DisplayName("getStrType2MapConverter method")
    @Test
    void testGetStrType2MapConverter(){
        assertThat(context.getStrType2MapConverter())
                .isEqualTo(new StrType2MapOptConverter(USKClassMembersPartHandler.DEFAULT));
    }

    @DisplayName("getClassName2InstanceConverter method")
    @Test
    void testGetClassName2InstanceConverter(){
        assertThat(context.getClassName2InstanceConverter())
                .isEqualTo(new ClassName2Instance(createMap()));
    }

    @DisplayName("getToInstanceConverter method")
    @Test
    void testGetToInstanceConverter(){
        assertThat(context.getToInstanceConverter())
                .isEqualTo(new ToInstanceOC(createMap(), USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH));
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
