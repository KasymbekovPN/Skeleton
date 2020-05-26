package org.KasymbekovPN.Skeleton.custom.serialization.handler.method;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.method.classes.TC0;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonToStringMethodSEH. Testing of:")
public class ToStringMethodSEHTest {

    private static final String METHOD_NAME = "toString";

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        TC0.class,
                        new ArrayList<List<String>>(){{
                            add(
                                    new ArrayList<>(){{
                                        add("privateProperty");
                                        add("publicProperty");
                                    }}
                            );
                        }},
                        new String[]{"privateProperty", "publicProperty"},
                        true
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Class<?> clazz, List<List<String>> argumentsByMethods, String[] arguments, boolean result) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorClassPath(collector);
        Utils.fillCollectorMembersPart(collector, arguments);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        TestSerializer serializer = new TestSerializer(collector, ah, cch);
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(collector, argumentsByMethods);
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestSerializer implements Serializer {

        private SerializationElementHandler handler;
        private Collector collector;

        public TestSerializer(Collector collector,
                              AnnotationChecker ah,
                              CollectorCheckingHandler cch) {
            this.collector = collector;
            this.handler = new ToStringMethodSEH(ah, cch);
        }

        @Override
        public void serialize(Class<?> clazz) {
            handler.handle(clazz, collector);
        }
    }

    private static class TestCollectorProcess implements CollectorProcess{

        private final Collector collector;
        private final Set<String> hashes = new HashSet<>();

        private Set<String> nonExistHash = new HashSet<>();
        private boolean valid = false;

        public TestCollectorProcess(Collector collector, List<List<String>> argumentsByMethods) {
            this.collector = collector;
            for (List<String> arguments : argumentsByMethods) {
                hashes.add(String.valueOf(arguments.hashCode()));
            }
        }

        @Override
        public void handle(Node node) {
            Optional<Node> maybeChild = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.methodEI()),
                    ObjectNode.class
            );
            if (maybeChild.isPresent()) {
                ObjectNode methodNode = (ObjectNode) maybeChild.get();

                for (Map.Entry<String, Node> entry : methodNode.getChildren().entrySet()) {
                    String hash = entry.getKey();
                    if (hashes.contains(hash)){
                        hashes.remove(hash);
                    } else {
                        nonExistHash.add(hash);
                    }
                }

                if (hashes.size() == 0 && nonExistHash.size() == 0){
                    valid = true;
                }
            }
        }

        @Override
        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {

        }

        public boolean isValid() {
            return valid;
        }
    }
}
