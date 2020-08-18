package org.KasymbekovPN.Skeleton.custom.serialization.handler.member;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.custom.*;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonCustomMemberSEH. Testing of:")
public class CustomMemberSEHTest {

    private static Object[][] getTestDataMemberAnnotationTesting(){
        return new Object[][]{
                {
                        CustomTC.class,
                        new HashMap<String, Triple<Class<?>, String, Number>>(){{
                            put(
                                    "customInnerTC0",
                                    new MutableTriple<>(CustomInnerTC0.class, "CustomInnerTC0", Modifier.PUBLIC)
                            );
                            put(
                                    "customInnerTC1",
                                    new MutableTriple<>(CustomInnerTC1.class, "CustomInnerTC1", Modifier.PROTECTED)
                            );
                            put(
                                    "customInnerTC2",
                                    new MutableTriple<>(CustomInnerTC2.class, "CustomInnerTC2", Modifier.PRIVATE)
                            );
                            put(
                                    "customInnerTC3",
                                    new MutableTriple<>(CustomInnerTC3.class, "CustomInnerTC3", Modifier.STATIC)
                            );
                        }},
                        true
                },
                {
                        CustomTC.class,
                        new HashMap<String, Triple<Class<?>, String, Number>>(){{
                            put(
                                    "customInnerTC0",
                                    new MutableTriple<>(CustomInnerTC0.class, "CustomInnerTC0", Modifier.PUBLIC)
                            );
                            put(
                                    "customInnerTC1",
                                    new MutableTriple<>(CustomInnerTC1.class, "CustomInnerTC1", Modifier.PROTECTED)
                            );
                            put(
                                    "customInnerTC2",
                                    new MutableTriple<>(CustomInnerTC2.class, "CustomInnerTC2", Modifier.PRIVATE)
                            );
                            put(
                                    "customInnerTC3",
                                    new MutableTriple<>(CustomInnerTC3.class, "CustomInnerTC3", Modifier.STATIC)
                            );
                            put(
                                    "customInnerTC4",
                                    new MutableTriple<>(CustomInnerTC4.class, "CustomInnerTC4", 0)
                            );
                        }},
                        false
                },
                {
                        CustomTC.class,
                        new HashMap<String, Triple<Class<?>, String, Number>>(){{
                            put(
                                    "customInnerTC0",
                                    new MutableTriple<>(CustomInnerTC0.class, "CustomInnerTC0", Modifier.PUBLIC)
                            );
                            put(
                                    "customInnerTC1",
                                    new MutableTriple<>(CustomInnerTC1.class, "CustomInnerTC1", Modifier.PROTECTED)
                            );
                            put(
                                    "customInnerTC2",
                                    new MutableTriple<>(CustomInnerTC2.class, "CustomInnerTC2", Modifier.PRIVATE)
                            );
                            put(
                                    "customInnerTC3",
                                    new MutableTriple<>(CustomInnerTC3.class, "CustomInnerTC3", Modifier.STATIC)
                            );
                            put(
                                    "customInnerTC5",
                                    new MutableTriple<>(CustomInnerTC5.class, "CustomInnerTC5", 0)
                            );
                        }},
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestDataMemberAnnotationTesting")
    @DisplayName("member annotation handling")
    void testMemberAnnotation(Class<?> clazz,
                              Map<String, Triple<Class<?>, String, Number>> testData,
                              boolean result) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorClassPath(collector);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        AllowedStringChecker classNameChecker
                = new AllowedStringChecker("CustomInnerTC0", "CustomInnerTC1", "CustomInnerTC2", "CustomInnerTC3");

        CustomMemberSEH customMemberSEH = new CustomMemberSEH(classNameChecker, ah, cch);

        TestSerializer serializer = new TestSerializer(collector, customMemberSEH);
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(testData, collector);
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestSerializer implements Serializer {

        private SerializationElementHandler handler;
        private Collector collector;

        public TestSerializer(Collector collector,
                              SerializationElementHandler serializationElementHandler) {
            this.collector = collector;
            this.handler = serializationElementHandler;
        }

        @Override
        public void serialize(Class<?> clazz) {
            for (Field field : clazz.getDeclaredFields()) {
                handler.handle(field, collector);
            }
        }

        @Override
        public void apply(CollectorProcess collectorProcess) {}

        @Override
        public Collector getCollector() {
            return null;
        }

        @Override
        public Collector attachCollector(Collector collector) {
            return null;
        }

        @Override
        public String getId() {
            return null;
        }
    }

    private static class TestCollectorProcess implements CollectorProcess {

        private final Map<String, Triple<Class<?>, String, Number>> members;
        private final Collector collector;

        private Set<String> notExistMembers = new HashSet<>();
        private boolean valid = false;

        public TestCollectorProcess(Map<String, Triple<Class<?>, String, Number>> members, Collector collector) {
            this.members = members;
            this.collector = collector;
        }

        @Override
        public void handle(Node node) {
            Optional<ObjectNode> mayBeMembersNode = extractMembersNode(node);
            Set<Pair<String, ObjectNode>> memberNodes = new HashSet<>();
            if (mayBeMembersNode.isPresent()){
                memberNodes = extractMemberNodes(mayBeMembersNode.get());
            }
            if (memberNodes.size() > 0){
                for (Pair<String, ObjectNode> memberNodePair : memberNodes) {

                    ObjectNode memberNode = memberNodePair.getRight();
                    Optional<Triple<String, String, Number>> mayBeNodeData = extractMemberNodeData(memberNode);
                    if (mayBeNodeData.isPresent()){
                        String member = memberNodePair.getLeft();
                        Triple<String, String, Number> nodeData = mayBeNodeData.get();

                        if (members.containsKey(member)){
                            if (checkMember(members.get(member), nodeData)){
                                members.remove(member);
                            }
                        } else {
                            notExistMembers.add(member);
                        }
                    }
                }

                if (members.size() == 0 && notExistMembers.size() == 0){
                    valid = true;
                }
            } else if (members.size() == 0){
                valid = true;
            }
        }

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {}

        public boolean isValid() {
            return valid;
        }

        private Optional<ObjectNode> extractMembersNode(Node node){
            Optional<Node> mayBeMembersNode = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()),
                    ObjectNode.class
            );
            return mayBeMembersNode.isPresent() && mayBeMembersNode.get().isObject()
                    ? Optional.of((ObjectNode)mayBeMembersNode.get())
                    : Optional.empty();
        }

        private Set<Pair<String, ObjectNode>> extractMemberNodes(ObjectNode node){
            Set<Pair<String, ObjectNode>> result = new HashSet<>();
            for (Map.Entry<String, Node> entry : node.getChildren().entrySet()) {
                Node child = entry.getValue();
                if (child.isObject()){
                    String name = entry.getKey();
                    result.add(new MutablePair<>(name, (ObjectNode)child));
                }
            }

            return result;
        }

        private Optional<Triple<String, String, Number>> extractMemberNodeData(ObjectNode memberNode){

            if (memberNode.containsKey("type") && memberNode.getChildren().get("type").isString() &&
                    memberNode.containsKey("modifiers") && memberNode.getChildren().get("modifiers").isNumber() &&
                    memberNode.containsKey("className") && memberNode.getChildren().get("className").isString()) {

                String type = ((StringNode) memberNode.getChildren().get("type")).getValue();
                String className = ((StringNode) memberNode.getChildren().get("className")).getValue();
                Number modifiers = ((NumberNode) memberNode.getChildren().get("modifiers")).getValue();

                return Optional.of(new MutableTriple<>(type, className, modifiers));
            }

            return Optional.empty();
        }

        private boolean checkMember(Triple<Class<?>, String, Number> testData,
                                    Triple<String, String, Number> data){

            return testData.getLeft().getTypeName().equals(data.getLeft()) &&
                    testData.getMiddle().equals(data.getMiddle()) &&
                    testData.getRight().equals(data.getRight());
        }
    }
}
