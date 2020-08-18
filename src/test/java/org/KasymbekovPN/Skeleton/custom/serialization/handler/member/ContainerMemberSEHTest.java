package org.KasymbekovPN.Skeleton.custom.serialization.handler.member;

//<
//import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
//import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.ContainerMemberSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container.TC0;
//import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container.TC1;
//import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
//import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.node.*;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.SerializationElementHandler;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
//import org.KasymbekovPN.Skeleton.lib.utils.checking.TypeChecker;
//import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.ContainerArgumentChecker;
//import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.SkeletonCAC;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;

import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container.CollectionTC;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.*;
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
import java.util.stream.Collectors;

@DisplayName("SkeletonContainerMemberSEH. Testing of:")
public class ContainerMemberSEHTest {

    private static Object[][] getTestDataForMemberAnnotation(){
        return new Object[][]{
                {
                        CollectionTC.class,
                        new HashMap<String, Triple<Class<?>, List<Class<?>>, Integer>>(){{
                            put(
                                    "setPublic",
                                    new MutableTriple<>(Set.class, new ArrayList<>(){{add(String.class);}}, Modifier.PUBLIC)
                            );
                            put(
                                    "setProtected",
                                    new MutableTriple<>(Set.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED)
                            );
                            put(
                                    "setPrivate",
                                    new MutableTriple<>(Set.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE)
                            );
                            put(
                                    "listPublic",
                                    new MutableTriple<>(List.class, new ArrayList<>(){{add(String.class);}}, Modifier.PUBLIC | Modifier.STATIC)
                            );
                            put(
                                    "listProtected",
                                    new MutableTriple<>(List.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED | Modifier.STATIC)
                            );
                            put(
                                    "listPrivate",
                                    new MutableTriple<>(List.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE | Modifier.STATIC)
                            );
                        }},
                        true
                },
                {
                        CollectionTC.class,
                        new HashMap<String, Triple<Class<?>, List<Class<?>>, Integer>>(){{
                            put(
                                    "setPublic",
                                    new MutableTriple<>(Set.class, new ArrayList<>(){{add(String.class);}}, Modifier.PROTECTED)
                            );
                            put(
                                    "setProtected",
                                    new MutableTriple<>(Set.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED)
                            );
                            put(
                                    "setPrivate",
                                    new MutableTriple<>(Set.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE)
                            );
                            put(
                                    "listPublic",
                                    new MutableTriple<>(List.class, new ArrayList<>(){{add(String.class);}}, Modifier.PUBLIC | Modifier.STATIC)
                            );
                            put(
                                    "listProtected",
                                    new MutableTriple<>(List.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED | Modifier.STATIC)
                            );
                            put(
                                    "listPrivate",
                                    new MutableTriple<>(List.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE | Modifier.STATIC)
                            );
                        }},
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestDataForMemberAnnotation")
    @DisplayName("member annotation handling")
    void testMemberAnnotation(Class<?> clazz,
                              Map<String, Triple<Class<?>, List<Class<?>>, Number>> members,
                              boolean result) throws Exception {

        //<
//        Collector collector = Utils.createCollector();
//        Utils.fillCollectorClassPath(collector);
//        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
//        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);
//
//        Set<Class<?>> types = new HashSet<>(Arrays.asList(Set.class, List.class));
//        Set<Class<?>> argumentTypes = new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class));
//        CollectionInstanceChecker collectionInstanceChecker = new CollectionInstanceChecker(types, argumentTypes);
//
//        ContainerMemberSEH containerMemberSEH = new ContainerMemberSEH(collectionInstanceChecker, ah, cch);
//        TestSerializer serializer = new TestSerializer(collector, containerMemberSEH);
//        serializer.serialize(clazz);
//
//        TestCollectorProcess testCollectorProcess = new TestCollectorProcess(members, collector);
//        collector.apply(testCollectorProcess);
//
//        assertThat(testCollectorProcess.isValid()).isEqualTo(result);
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
        
        private final Map<String, Triple<Class<?>, List<Class<?>>, Number>> members;
        private final Collector collector;

        private Set<String> notExistMembers = new HashSet<>();
        private boolean valid = false;

        public TestCollectorProcess(Map<String, Triple<Class<?>, List<Class<?>>, Number>> members,
                                    Collector collector) {
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
                    Optional<Triple<String, List<String>, Number>> mayBeNodeData = extractMemberNodeData(memberNode);
                    if (mayBeNodeData.isPresent()){
                        String member = memberNodePair.getLeft();
                        Triple<String, List<String>, Number> nodeData = mayBeNodeData.get();

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

        private Optional<Triple<String, List<String>, Number>> extractMemberNodeData(ObjectNode memberNode){

            if (memberNode.containsKey("type") && memberNode.getChildren().get("type").isString() &&
                memberNode.containsKey("modifiers") && memberNode.getChildren().get("modifiers").isNumber() &&
                memberNode.containsKey("arguments") && memberNode.getChildren().get("arguments").isArray()) {

                String type = ((StringNode) memberNode.getChildren().get("type")).getValue();
                Number modifiers = ((NumberNode) memberNode.getChildren().get("modifiers")).getValue();
                ArrayNode argumentsNode = (ArrayNode) memberNode.getChildren().get("arguments");
                List<String> arguments = new ArrayList<>();
                for (Node arrayItem : argumentsNode.getChildren()) {
                    if (arrayItem.isString()){
                        arguments.add(((StringNode)arrayItem).getValue());
                    } else {
                        break;
                    }
                }

                return Optional.of(new MutableTriple<>(type, arguments, modifiers));
            }

            return Optional.empty();
        }

        private boolean checkMember(Triple<Class<?>, List<Class<?>>, Number> testData,
                                    Triple<String, List<String>, Number> data){

            List<String> strArguments = new ArrayList<>(testData.getMiddle()).stream()
                    .map(Class::getTypeName)
                    .collect(Collectors.toList());

            MutableTriple<String, List<String>, Number> preparedTestData
                    = new MutableTriple<>(testData.getLeft().getTypeName(), strArguments, testData.getRight());

            return data.equals(preparedTestData);
        }

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {}

        public boolean isValid() {
            return valid;
        }
    }
}
