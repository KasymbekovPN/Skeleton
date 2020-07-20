package org.KasymbekovPN.Skeleton.custom.serialization.handler.member;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member.CustomMemberSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.custom.*;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.handler.SkeletonCollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.SkeletonCollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SkeletonCustomMemberSEH. Testing of:")
public class CustomMemberSEHTest {

    private static Object[][] getDataForMemberAnnotationTesting(){
        return new Object[][]{
                {
                        TC0.class,
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC0.class,
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                        }},
                        false
                },
                {
                        TC0.class,
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                            put("staticPropertyNE", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getDataForMemberAnnotationTesting")
    @DisplayName("member annotation handling")
    void testMemberAnnotation(Class<?> clazz, Map<String, Pair> members, boolean result) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorClassPath(collector);
        SkeletonAnnotationChecker ah = new SkeletonAnnotationChecker();
        SkeletonCollectorCheckingHandler cch = new SkeletonCollectorCheckingHandler(SkeletonCollectorCheckingProcess.class);

        TestSerializer serializer = new TestSerializer(collector, ah, cch);
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(members, collector);
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static Object[][] getDataForClassAnnotationTesting(){
        return new Object[][]{
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty", "protectedProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.STATIC | Modifier.PROTECTED,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.STATIC | Modifier.PROTECTED,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{"protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new HashMap<String, Pair>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{},
                        new String[]{"privateProperty", "protectedProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{"publicProperty", "staticProperty"},
                        new HashMap<String, Pair>(),
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        new String[]{},
                        new String[]{},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE,
                        new String[]{},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC | Modifier.STATIC,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        new String[]{},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        -1,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty", "protectedProperty"},
                        new String[]{"publicProperty", "privateProperty"},
                        new HashMap<String, Pair>(){{
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PUBLIC | Modifier.STATIC,
                        -1,
                        new String[]{"privateProperty", "protectedProperty"},
                        new String[]{"publicProperty", "privateProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        -1,
                        Modifier.PRIVATE,
                        new String[]{"privateProperty", "protectedProperty", "publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        false
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        Modifier.PRIVATE,
                        new String[]{"publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
                        }},
                        true
                },
                {
                        TC1.class,
                        Modifier.PRIVATE | Modifier.PROTECTED,
                        Modifier.PRIVATE,
                        new String[]{"publicProperty", "staticProperty"},
                        new String[]{"publicProperty"},
                        new HashMap<String, Pair>(){{
                            put("publicProperty", new Pair(CustomClass1.class, Modifier.PUBLIC));
                            put("protectedProperty", new Pair(CustomClass2.class, Modifier.PROTECTED));
                            put("privateProperty", new Pair(CustomClass3.class, Modifier.PRIVATE));
                            put("staticProperty", new Pair(CustomClass4.class, Modifier.STATIC));
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
                             Map<String, Pair> members,
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

        TestSerializer serializer = new TestSerializer(collector, ah, cch);
        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(members, collector);
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
            this.handler = new CustomMemberSEH(ah, cch);
        }

        @Override
        public void serialize(Class<?> clazz) {
            for (Field field : clazz.getDeclaredFields()) {
                handler.handle(field, collector);
            }
        }

        @Override
        public void apply(CollectorProcess collectorProcess) {

        }

        @Override
        public void clear() {
            
        }

        @Override
        public void setCollector(Collector collector) {

        }

        @Override
        public Collector getCollector() {
            return null;
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
                    ObjectNode.class
            );
            if (maybeChild.isPresent()){
                ObjectNode membersNode = (ObjectNode) maybeChild.get();

                for (Map.Entry<String, Node> entry : membersNode.getChildren().entrySet()) {
                    if (node.isObject()){

                        ObjectNode memberNode = (ObjectNode) entry.getValue();

                        if (memberNode.containsKey("type") && memberNode.getChildren().get("type").isString() &&
                                memberNode.containsKey("modifiers") && memberNode.getChildren().get("modifiers").isNumber()){

                            String member = entry.getKey();
                            String type = ((StringNode) memberNode.getChildren().get("type")).getValue();
                            Number modifiers = ((NumberNode) memberNode.getChildren().get("modifiers")).getValue();

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
            } else if (members.size() == 0){
                valid = true;
            }
        }

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {

        }

        //<
//        @Override
//        public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) { }

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
