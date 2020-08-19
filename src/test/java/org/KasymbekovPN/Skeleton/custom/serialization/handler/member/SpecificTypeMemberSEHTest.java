package org.KasymbekovPN.Skeleton.custom.serialization.handler.member;

//< !!! restore
//import org.KasymbekovPN.Skeleton.custom.checker.AllowedClassChecker;
//import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
//import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.SpecificTypeMemberSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.specific.TC0;
//import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
//import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
//import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
//import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
//import org.KasymbekovPN.Skeleton.lib.node.StringNode;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.SerializationElementHandler;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
//import org.apache.commons.lang3.tuple.MutablePair;
//import org.apache.commons.lang3.tuple.Pair;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("SkeletonSpecificTypeMemberSEH. Testing of:")
//public class SpecificTypeMemberSEHTest {
//
//    private static Object[][] getTestDataForMemberAnnotation(){
//        return new Object[][]{
//                {
//                    TC0.class,
//                    new HashMap<String, Pair<Class<?>, Integer>>(){{
//                        put("stringObjectProperty", new MutablePair<>(String.class, Modifier.STATIC));
//                        put("byteProperty", new MutablePair<>(byte.class, 0));
//                        put("shortProperty", new MutablePair<>(short.class, Modifier.PUBLIC));
//                        put("intProperty", new MutablePair<>(int.class, Modifier.PROTECTED));
//                        put("longProperty", new MutablePair<>(long.class, Modifier.PRIVATE));
//                        put("floatProperty", new MutablePair<>(float.class, Modifier.STATIC | Modifier.PUBLIC));
//                        put("doubleProperty", new MutablePair<>(double.class, Modifier.STATIC | Modifier.PROTECTED));
//                        put("charProperty", new MutablePair<>(char.class, Modifier.STATIC | Modifier.PROTECTED));
//                        put("booleanProperty", new MutablePair<>(boolean.class, Modifier.STATIC | Modifier.PRIVATE));
//                    }},
//                    true
//                },
//                {
//                    TC0.class,
//                    new HashMap<String, Pair<Class<?>, Integer>>(){{
//                        put("stringObjectProperty", new MutablePair<>(String.class, Modifier.STATIC));
//                        put("byteProperty", new MutablePair<>(byte.class, 0));
//                        put("shortProperty", new MutablePair<>(short.class, Modifier.PUBLIC));
//                        put("intProperty", new MutablePair<>(int.class, Modifier.PROTECTED));
//                        put("longProperty", new MutablePair<>(long.class, Modifier.PRIVATE));
//                        put("floatProperty", new MutablePair<>(float.class, Modifier.STATIC | Modifier.PUBLIC));
//                        put("doubleProperty", new MutablePair<>(double.class, Modifier.STATIC | Modifier.PROTECTED));
//                        put("charProperty", new MutablePair<>(char.class, Modifier.STATIC | Modifier.PROTECTED));
//                        put("booleanProperty", new MutablePair<>(boolean.class, Modifier.STATIC | Modifier.PRIVATE));
//
//                        put("stringObjectPropertyNA", new MutablePair<>(String.class, Modifier.STATIC));
//                    }},
//                    false
//                }
//        };
//    }
//
//    @ParameterizedTest
//    @MethodSource("getTestDataForMemberAnnotation")
//    @DisplayName("member annotation handling")
//    void testMemberAnnotation(Class<?> clazz, Map<String, Pair<Class<?>, Integer>> members, boolean result) throws Exception {
//        Collector collector = Utils.createCollector();
//        Utils.fillCollectorClassPath(collector);
//        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
//        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);
//
//        AllowedClassChecker allowedClassChecker = new AllowedClassChecker(
//                String.class, byte.class, short.class, int.class, long.class, float.class,
//                double.class, char.class, boolean.class, Boolean.class, Character.class
//        );
//        TestSerializer testSerializer = new TestSerializer(collector, ah, cch, allowedClassChecker);
//        testSerializer.serialize(clazz);
//
//        TestCollectorProcess process = new TestCollectorProcess(members, collector);
//        collector.apply(process);
//
//        assertThat(process.isValid()).isEqualTo(result);
//    }
//
//    private static class TestSerializer implements Serializer {
//
//        private SerializationElementHandler handler;
//        private Collector collector;
//
//        public TestSerializer(Collector collector,
//                              AnnotationChecker ah,
//                              CollectorCheckingHandler cch,
//                              SimpleChecker<Class<?>> classChecker
//                              ) {
//            this.collector = collector;
//            this.handler = new SpecificTypeMemberSEH(classChecker, ah, cch);
//        }
//
//        @Override
//        public void serialize(Class<?> clazz) {
//            for (Field field : clazz.getDeclaredFields()) {
//                handler.handle(field, collector);
//            }
//        }
//
//        @Override
//        public void apply(CollectorProcess collectorProcess) {}
//
//        @Override
//        public Collector getCollector() {
//            return null;
//        }
//
//        @Override
//        public Collector attachCollector(Collector collector) {
//            return null;
//        }
//
//        @Override
//        public String getId() {
//            return null;
//        }
//    }
//
//    private static class TestCollectorProcess implements CollectorProcess {
//
//        private final Map<String, Pair<Class<?>, Integer>> members;
//        private final Collector collector;
//
//        private Set<String> notExistMembers = new HashSet<>();
//        private boolean valid = false;
//
//        public TestCollectorProcess(Map<String, Pair<Class<?>, Integer>> members, Collector collector) {
//            this.members = members;
//            this.collector = collector;
//        }
//
//        @Override
//        public void handle(Node node) {
//
//            Optional<ObjectNode> mayBeMembersNode = extractMembersNode(node);
//            if (mayBeMembersNode.isPresent()){
//                ObjectNode membersNode = mayBeMembersNode.get();
//                Optional<Set<MutablePair<String, ObjectNode>>> mayBeMemberNodes = extractMemberNodes(membersNode);
//                if (mayBeMemberNodes.isPresent()){
//                    Set<MutablePair<String, ObjectNode>> memberNodes = mayBeMemberNodes.get();
//                    for (MutablePair<String, ObjectNode> memberNode : memberNodes) {
//                        Optional<MutablePair<String, Number>> mayBeMemberNodeData = extractMemberNodeData(memberNode.getRight());
//                        if (mayBeMemberNodeData.isPresent()){
//                            MutablePair<String, Number> memberNodeData = mayBeMemberNodeData.get();
//
//                            String member = memberNode.getLeft();
//                            String type = memberNodeData.getLeft();
//                            Number modifiers = memberNodeData.getRight();
//
//                            if (members.containsKey(member)){
//                                Pair<Class<?>, Integer> testData = members.get(member);
//                                if (testData.getLeft().getTypeName().equals(type) &&
//                                    modifiers.equals(testData.getRight())){
//
//                                    members.remove(member);
//                                }
//                            } else {
//                                notExistMembers.add(member);
//                                break;
//                            }
//                        }
//                    }
//
//                    if (members.size() == 0 && notExistMembers.size() == 0){
//                        valid = true;
//                    }
//                }
//            } else if (members.size() == 0){
//                valid = true;
//            }
//        }
//
//        private Optional<ObjectNode> extractMembersNode(Node node){
//            Optional<Node> maybeChild = node.getChild(
//                    collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()),
//                    ObjectNode.class
//            );
//            return maybeChild.isPresent() && maybeChild.get().isObject()
//                    ? Optional.of((ObjectNode)maybeChild.get())
//                    : Optional.empty();
//        }
//
//        private Optional<Set<MutablePair<String, ObjectNode>>> extractMemberNodes(ObjectNode membersNode){
//            Set<MutablePair<String, ObjectNode>> memberNodes = new HashSet<>();
//            for (Map.Entry<String, Node> entry : membersNode.getChildren().entrySet()) {
//                Node node = entry.getValue();
//                if (node.isObject()){
//                    memberNodes.add(new MutablePair<>(entry.getKey(), (ObjectNode) node));
//                } else {
//                    return Optional.empty();
//                }
//            }
//
//            return Optional.of(memberNodes);
//        }
//
//        private Optional<MutablePair<String, Number>> extractMemberNodeData(ObjectNode memberNode){
//            if (memberNode.containsKey("type") && memberNode.getChildren().get("type").isString() &&
//                memberNode.containsKey("modifiers") && memberNode.getChildren().get("modifiers").isNumber()) {
//
//                String type = ((StringNode) memberNode.getChildren().get("type")).getValue();
//                Number modifiers = ((NumberNode) memberNode.getChildren().get("modifiers")).getValue();
//
//                return Optional.of(new MutablePair<>(type, modifiers));
//            }
//
//            return Optional.empty();
//        }
//
//        @Override
//        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {}
//
//        public boolean isValid() {
//            return valid;
//        }
//    }
//}
