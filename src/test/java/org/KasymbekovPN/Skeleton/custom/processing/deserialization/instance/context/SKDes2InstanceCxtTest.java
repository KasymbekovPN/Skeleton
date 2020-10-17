package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.StrType2CollectionOptConverter;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
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

    @BeforeAll
    private static void beforeAll(){
        Map<String, Class<?>> map = createMap();
        SKContextStateCareTaker<Des2InstanceContextStateMemento> careTaker = new SKContextStateCareTaker<>();
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
    void testGetContextIdsMethod(){
        assertThat(context.getContextIds()).isEqualTo(USKDes2Instance.createContextIds());
    }

    @DisplayName("getStrType2CollectionConverter method")
    @Test
    void testGetStrType2CollectionConverter(){
        assertThat(context.getStrType2CollectionConverter())
                .isEqualTo(new StrType2CollectionOptConverter(USKClassMembersPartHandler.DEFAULT));
    }
}
