package org.KasymbekovPN.Skeleton.custom.serialization.handler.member;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.specific.TC0;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonNumberNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonStringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SkeletonSpecificTypeMemberSEH. Testing of:")
public class SkeletonSpecificTypeMemberSEHTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                    TC0.class,
                    new HashMap<String, Pair>(){{
                        put("stringObjectProperty", new Pair(String.class, Modifier.STATIC));
                        put("byteProperty", new Pair(byte.class, 0));
                        put("shortProperty", new Pair(short.class, Modifier.PUBLIC));
                        put("intProperty", new Pair(int.class, Modifier.PROTECTED));
                        put("longProperty", new Pair(long.class, Modifier.PRIVATE));
                        put("floatProperty", new Pair(float.class, Modifier.STATIC | Modifier.PUBLIC));
                        put("doubleProperty", new Pair(double.class, Modifier.STATIC | Modifier.PROTECTED));
                        put("charProperty", new Pair(char.class, Modifier.STATIC | Modifier.PROTECTED));
                        put("booleanProperty", new Pair(boolean.class, Modifier.STATIC | Modifier.PRIVATE));
                    }},
                    true
                },
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Class<?> clazz, Map<String, Pair> members, boolean result) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorClassPath(collector);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        TestSerializer serializer = new TestSerializer(collector, ah, cch,
                String.class, byte.class, short.class, int.class, long.class, float.class, double.class,
                char.class, boolean.class, Boolean.class, Character.class
        );
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(members, collector);
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestSerializer implements Serializer{

        private SerializationElementHandler handler;
        private Collector collector;

        public TestSerializer(Collector collector,
                              AnnotationChecker ah,
                              CollectorCheckingHandler cch,
                              Class<?>... classes
                              ) {
            this.collector = collector;
            for (Class<?> clazz : classes) {
                if (handler == null){
                    handler = new SkeletonSpecificTypeMemberSEH(clazz, ah, cch);
                } else {
                    handler.setNext(new SkeletonSpecificTypeMemberSEH(clazz, ah, cch));
                }
            }
        }

        @Override
        public void serialize(Class<?> clazz) {
            for (Field field : clazz.getDeclaredFields()) {
                handler.handle(field, collector);
            }
        }
    }

    private static class TestCollectorProcess implements CollectorProcess {

        private final Map<String, Pair> members;
        private final Collector collector;

        private Set<String> notExistMembers = new HashSet<>();
        private boolean valid = false;

        public TestCollectorProcess(Map<String, Pair> members, Collector collector) {
            this.members = members;
            this.collector = collector;
        }

        @Override
        public void handle(Node node) {

            Optional<Node> maybeChild = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()),
                    SkeletonObjectNode.class
            );
            if (maybeChild.isPresent()){
                SkeletonObjectNode membersNode = (SkeletonObjectNode) maybeChild.get();

                for (Map.Entry<String, Node> entry : membersNode.getChildren().entrySet()) {
                    if (node.isObject()){

                        SkeletonObjectNode memberNode = (SkeletonObjectNode) entry.getValue();

                        if (memberNode.containsKey("type") && memberNode.getChildren().get("type").isString() &&
                            memberNode.containsKey("modifiers") && memberNode.getChildren().get("modifiers").isNumber()){

                            String member = entry.getKey();
                            String type = ((SkeletonStringNode) memberNode.getChildren().get("type")).getValue();
                            Number modifiers = ((SkeletonNumberNode) memberNode.getChildren().get("modifiers")).getValue();

                            if (members.containsKey(member)){
                                Pair pair = members.get(member);
                                if (pair.check(type, modifiers)){
                                    members.remove(member);
                                }
                            } else {
                                notExistMembers.add(member);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }

                if (members.size() == 0 && notExistMembers.size() == 0){
                    valid = true;
                }
            }
        }

        @Override
        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) { }

        public boolean isValid() {
            return valid;
        }
    }

    private static class Pair{
        public Class<?> clazz;
        public int modifiers;

        public Pair(Class<?> clazz, int modifiers) {
            this.clazz = clazz;
            this.modifiers = modifiers;
        }

        boolean check(String type, Number modifiers){
            return type.equals(clazz.getTypeName()) && modifiers.equals(this.modifiers);
        }
    }
}
