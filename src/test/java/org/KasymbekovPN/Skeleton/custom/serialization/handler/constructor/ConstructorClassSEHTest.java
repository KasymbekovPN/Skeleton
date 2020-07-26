package org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.constructor.ConstructorClassSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor.clalsses.TC0;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.constructor.clalsses.TC1;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonConstructorClassSEH. Testing of:")
public class ConstructorClassSEHTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                    TC0.class,
                    new String[]{"map", "set", "list"},
                    new String[][]{
                            {"map", "set"},
                            {"map"},
                            {}
                    },
                    true
                },
                {
                    TC0.class,
                    new String[]{"set", "list"},
                    new String[][]{
                            {"map", "set"},
                            {"map"},
                            {}
                    },
                    false
                },
                {
                    TC1.class,
                    new String[]{},
                    new String[][]{
                            {}
                    },
                    true
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Class<?> clazz, String[] memberNames, String[][] constructorArgs, boolean result) throws Exception {

        Collector collector = Utils.createCollector();
        Utils.fillCollectorMembersPart(collector, memberNames);

        ConstructorClassSEH seh = new ConstructorClassSEH(
                new SkeletonAnnotationChecker(),
                new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class)
        );
        seh.handle(clazz, collector);

        TestCollectorProcess process = new TestCollectorProcess(
                collector.getCollectorStructure().getPath(CollectorStructureEI.constructorEI()),
                constructorArgs
        );
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestCollectorProcess implements CollectorProcess{

        private final List<String> path;
        private final String[][] constructorArgs;

        private boolean valid = false;

        public TestCollectorProcess(List<String> path, String[][] constructorArgs) {
            this.path = path;
            this.constructorArgs = constructorArgs;
        }

        @Override
        public void handle(Node node) {

            Set<String> hashes = new HashSet<>();

            Optional<Node> maybeChild = node.getChild(path, ObjectNode.class);
            if (maybeChild.isPresent() && maybeChild.get().isObject()){
                ObjectNode constructorsNode = (ObjectNode) maybeChild.get();

                valid = true;
                for (Map.Entry<String, Node> entry : constructorsNode.getChildren().entrySet()) {
                    if (entry.getValue().isArray()){
                        ArrayNode paramsNode = (ArrayNode) entry.getValue();
                        List<String> params = new ArrayList<>();
                        for (Node child : paramsNode.getChildren()) {
                            if (child.isString()){
                                params.add(((StringNode)child).getValue());
                            } else {
                                valid = false;
                                break;
                            }
                        }

                        String hash = String.valueOf(params.hashCode());
                        if (hashes.contains(hash)){
                            valid = true;
                            break;
                        } else {
                            hashes.add(hash);
                        }
                    }
                    else {
                        valid = false;
                        break;
                    }
                }

                if (valid){
                    Set<String> calcHashes = new HashSet<>();
                    for (String[] constructorArgsItem : constructorArgs) {
                        String hash = String.valueOf(Arrays.asList(constructorArgsItem).hashCode());
                        calcHashes.add(hash);
                    }
                    valid = hashes.equals(calcHashes);
                }
            }
        }



        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {
        }

        public boolean isValid() {
            return valid;
        }
    }
}
