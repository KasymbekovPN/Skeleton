package org.KasymbekovPN.Skeleton.custom.serialization.handler.member;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container.TC0;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container.TC1;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.utils.checking.TypeChecker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.ContainerArgumentChecker;
import org.KasymbekovPN.Skeleton.lib.utils.checking.containerArgumentChecker.SkeletonCAC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonContainerMemberSEH. Testing of:")
public class SkeletonContainerMemberSEHTest {

    private static Object[][] getDataForMemberAnnotationTesting(){
        return new Object[][]{
                {
                        TC0.class,
                        new HashMap<String, Trio>(){{
                            put(
                                    "publicProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(String.class);}}, Modifier.PUBLIC)
                            );
                            put(
                                    "protectedProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED)
                            );
                            put(
                                    "privateProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE)
                            );
                            put(
                                    "staticProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Double.class);}}, Modifier.STATIC)
                            );
                        }},
                        true
                },
                {
                        TC0.class,
                        new HashMap<String, Trio>(){{
                            put(
                                    "publicProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(String.class);}}, Modifier.PUBLIC)
                            );
                            put(
                                    "protectedProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED)
                            );
                            put(
                                    "privateProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE)
                            );
                        }},
                        false
                },
                {
                        TC0.class,
                        new HashMap<String, Trio>(){{
                            put(
                                    "publicProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(String.class);}}, Modifier.PUBLIC)
                            );
                            put(
                                    "protectedProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Integer.class);}}, Modifier.PROTECTED)
                            );
                            put(
                                    "privateProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Float.class);}}, Modifier.PRIVATE)
                            );
                            put(
                                    "staticProperty",
                                    new Trio(Set.class, new ArrayList<>(){{add(Double.class);}}, Modifier.STATIC)
                            );
                            put(
                                    "staticProperty1",
                                    new Trio(Set.class, new ArrayList<>(){{add(Double.class);}}, Modifier.STATIC)
                            );
                        }},
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getDataForMemberAnnotationTesting")
    @DisplayName("member annotation handling")
    void testMemberAnnotation(Class<?> clazz, Map<String, Trio> members, boolean result) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorClassPath(collector);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);
        SkeletonCAC skeletonCAC = new SkeletonCAC(
                new TypeChecker(
                        new HashSet<>(),
                        new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class, Double.class))
                )
        );

        TestSerializer serializer = new TestSerializer(collector, skeletonCAC, ah, cch);
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(members, collector);
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static final List<Class<?>> ARGS = new ArrayList<>(Arrays.asList(String.class, Integer.class, Float.class, Double.class));
    private static Object[][] getDataForClassAnnotationTesting(){
        return new Object[][]{
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(int.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(boolean.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(float.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(double.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },

                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty", "protectedProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.STATIC | Modifier.PROTECTED,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.STATIC | Modifier.PROTECTED,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new HashMap<String, Trio>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty"},
                        new HashMap<String, Trio>(){{
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{"publicProperty", "staticProperty"},
                        new HashMap<String, Trio>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE,
                        new String[]{},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty", "protectedProperty"},
                        new String[]{"publicProperty", "privateProperty"},
                        new HashMap<String, Trio>(){{
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty", "protectedProperty"},
                        new String[]{"publicProperty", "privateProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        Modifier.PRIVATE,
                        new String[]{"publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        Modifier.PRIVATE,
                        new String[]{"publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Trio>(){{
                            put("privateProperty", new Trio(Set.class, ARGS, Modifier.PRIVATE));
                            put("protectedProperty", new Trio(Set.class, ARGS, Modifier.PROTECTED));
                            put("publicProperty", new Trio(Set.class, ARGS, Modifier.PUBLIC));
                            put("staticProperty", new Trio(Set.class, ARGS, Modifier.STATIC));
                        }},
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getDataForClassAnnotationTesting")
    @DisplayName("class annotation handling")
    void testClassAnnotation(Class<?> clazz,
                             int annotationIncludeByModifiers,
                             int annotationExcludeByModifiers,
                             String[] annotationIncludeByName,
                             String[] annotationExcludeByName,
                             Map<String, Trio> members,
                             boolean result) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorClassPath(collector);
        Utils.fillCollectorAnnotationPart(
                collector,
                annotationIncludeByModifiers,
                annotationExcludeByModifiers,
                annotationIncludeByName,
                annotationExcludeByName);

        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);
        SkeletonCAC skeletonCAC = new SkeletonCAC(
                new TypeChecker(
                        new HashSet<>(),
                        new HashSet<>(Arrays.asList(String.class, Integer.class, Float.class, Double.class))
                )
        );

        TestSerializer serializer = new TestSerializer(collector, skeletonCAC, ah, cch);
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(members, collector);
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestSerializer implements Serializer {

        private SerializationElementHandler handler;
        private Collector collector;

        public TestSerializer(Collector collector,
                              ContainerArgumentChecker cac,
                              AnnotationChecker ah,
                              CollectorCheckingHandler cch) {
            this.collector = collector;
            this.handler = new SkeletonContainerMemberSEH(Set.class, cac, ah, cch);
        }

        @Override
        public void serialize(Class<?> clazz) {
            for (Field field : clazz.getDeclaredFields()) {
                handler.handle(field, collector);
            }
        }
    }

    private static class TestCollectorProcess implements CollectorProcess {

        private final Map<String, Trio> members;
        private final Collector collector;

        private Set<String> notExistMembers = new HashSet<>();
        private boolean valid = false;

        public TestCollectorProcess(Map<String, Trio> members, Collector collector) {
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
                                memberNode.containsKey("modifiers") && memberNode.getChildren().get("modifiers").isNumber() &&
                                memberNode.containsKey("arguments") && memberNode.getChildren().get("arguments").isArray()){

                            String member = entry.getKey();
                            String type = ((SkeletonStringNode) memberNode.getChildren().get("type")).getValue();
                            Number modifiers = ((SkeletonNumberNode) memberNode.getChildren().get("modifiers")).getValue();
                            SkeletonArrayNode argumentsNode = (SkeletonArrayNode) memberNode.getChildren().get("arguments");
                            List<String> arguments = new ArrayList<>();
                            for (Node arrayItem : argumentsNode.getChildren()) {
                                if (arrayItem.isString()){
                                    arguments.add(((SkeletonStringNode)arrayItem).getValue());
                                } else {
                                    break;
                                }
                            }

                            if (members.containsKey(member)){
                                Trio trio = members.get(member);
                                if (trio.check(type, modifiers, arguments)){
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
            } else if (members.size() == 0){
                valid = true;
            }
        }

        @Override
        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) { }

        public boolean isValid() {
            return valid;
        }
    }

    private static class Trio{
        public Class<?> clazz;
        public List<String> args;
        public int modifiers;

        public Trio(Class<?> clazz, List<Class<?>> args, int modifiers) {
            this.clazz = clazz;
            this.modifiers = modifiers;
            this.args = new ArrayList<>();
            for (Class<?> arg : args) {
                this.args.add(arg.getTypeName());
            }
        }

        boolean check(String type, Number modifiers, List<String> args){
            ArrayList<String> list1 = new ArrayList<>(this.args);
            return type.equals(clazz.getTypeName()) && modifiers.equals(this.modifiers) && list1.retainAll(args);
        }
    }
}
