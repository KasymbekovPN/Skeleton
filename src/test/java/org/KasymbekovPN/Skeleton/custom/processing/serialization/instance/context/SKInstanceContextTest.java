package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.util.UInstanceSerialization;
import org.KasymbekovPN.Skeleton.util.USKClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.util.USKClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.util.USKCollectorPath;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@DisplayName("SKInstanceContext. Testing of:")
public class SKInstanceContextTest {

    private static ContextIds contextIds;
    private static ContextProcessor<InstanceContext> processor;
    private static ContextStateCareTaker<InstanceContextStateMemento> careTaker;
    private static InstanceContext instanceContext;
    private static Map<String, ObjectNode> classNodes;
    private static Collector collector;
    private static OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor;

    @BeforeAll
    private static void beforeAll(){
        classNodes = createClassNodes();
        collector = new SKCollector();
        contextIds = UInstanceSerialization.createContextIds();
        processor = UInstanceSerialization.createProcessor();
        careTaker = new SKContextStateCareTaker<>();
        annotationExtractor = new AnnotationExtractor();
        instanceContext = UInstanceSerialization.createContext(
                careTaker,
                classNodes,
                annotationExtractor,
                collector
        );
    }

    private static Map<String, ObjectNode> createClassNodes(){
        return new HashMap<>() {{
            put("test", new ObjectNode(null));
        }};
    }

    @DisplayName("getContextIds method")
    @Test
    void testGetContextMethod() throws ContextStateCareTakerIsEmpty {
        Assertions.assertThat(instanceContext.getContextIds()).isEqualTo(contextIds);
    }

    @DisplayName("getContextStateCareTaker method")
    @Test
    void testGetContextStateCareTaker(){
        Assertions.assertThat(instanceContext.getContextStateCareTaker()).isEqualTo(careTaker);
    }

    @DisplayName("getClassNodes method")
    @Test
    void testGetClassNodes(){
        Assertions.assertThat(instanceContext.getClassNodes()).isEqualTo(classNodes);
    }

    @DisplayName("getCollector method")
    @Test
    void testGetCollector(){
        Assertions.assertThat(instanceContext.getCollector()).isEqualTo(collector);
    }

    @DisplayName("getClassCollectorPath method")
    @Test
    void testGetClassCollectorPath(){
        Assertions.assertThat(instanceContext.getClassCollectorPath()).isEqualTo(USKCollectorPath.DEFAULT_CLASS_PART_PATH);
    }

    @DisplayName("getMembersCollectorPath method")
    @Test
    void testGetMembersCollectorPath(){
        Assertions.assertThat(instanceContext.getMembersCollectorPath()).isEqualTo(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH);
    }

    @DisplayName("getClassHeaderPartHandler method")
    @Test
    void testGetClassHeaderPartHandler(){
        Assertions.assertThat(instanceContext.getClassHeaderPartHandler()).isEqualTo(USKClassHeaderPartHandler.DEFAULT);
    }

    @DisplayName("getClassMembersPartHandler method")
    @Test
    void testGetClassMembersPartHandler(){
        Assertions.assertThat(instanceContext.getClassMembersPartHandler()).isEqualTo(USKClassMembersPartHandler.DEFAULT);
    }

    @DisplayName("getAnnotationExtractor method")
    @Test
    void testGetAnnotationExtractorMethod(){
        Assertions.assertThat(instanceContext.getAnnotationExtractor()).isEqualTo(annotationExtractor);
    }
}
