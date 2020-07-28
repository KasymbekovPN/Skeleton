package org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz;

//import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
//import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
//import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassAnnotationDataSEH;
//import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.classes.forAnnotationData.*;
//import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.lib.collector.node.*;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
//import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
//import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@DisplayName("SkeletonClassAnnotationDataSEH. Testing of:")
//public class ClassAnnotationDataSEHTest {
//
//    private static Object[][] getTestData(Class<?> clazz,
//                                          int includeModifiers,
//                                          int wrongIncludeModifiers,
//                                          int excludeModifiers,
//                                          int wrongExcludeModifiers,
//                                          String[] includeByName,
//                                          String[] wrongIncludeByName,
//                                          String[] excludeByName,
//                                          String[] wrongExcludeByNames){
//
//        Object[][] objects = new Object[16][6];
//
//        for (int i = 0; i < 16; i++) {
//            objects[i][0] = clazz;
//            objects[i][1] = 0 == (i % 2) ? includeModifiers : wrongIncludeModifiers;
//            objects[i][2] = 0 == (i % 4) ? excludeModifiers : wrongExcludeModifiers;
//            objects[i][3] = 0 == (i % 8) ? includeByName : wrongIncludeByName;
//            objects[i][4] = 0 == (i % 16) ? excludeByName : wrongExcludeByNames;
//            objects[i][5] = 0 == i;
//        }
//
//        return objects;
//    }
//
//    private static Object[][] getTestDataDefault(){
//        return getTestData(
//                TC0.class,
//                -1,
//                10,
//                -1,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM(){
//        return getTestData(
//                TC_1im.class,
//                4,
//                10,
//                -1,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_EM(){
//        return getTestData(
//                TC_2em.class,
//                -1,
//                10,
//                5,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_EM(){
//        return getTestData(
//                TC_3_im_em.class,
//                6,
//                10,
//                5,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IN(){
//        return getTestData(
//                TC_4_in.class,
//                -1,
//                10,
//                -1,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_IN(){
//        return getTestData(
//                TC_5_im_in.class,
//                6,
//                10,
//                -1,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_EM_IN(){
//        return getTestData(
//                TC_6_em_in.class,
//                -1,
//                10,
//                5,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_EM_IN(){
//        return getTestData(
//                TC_7_im_em_in.class,
//                6,
//                10,
//                5,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_EN(){
//        return getTestData(
//                TC_8_en.class,
//                -1,
//                10,
//                -1,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_EN(){
//        return getTestData(
//                TC_9_im_en.class,
//                4,
//                10,
//                -1,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_EM_EN(){
//        return getTestData(
//                TC_10_em_en.class,
//                -1,
//                10,
//                5,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_EM_EN(){
//        return getTestData(
//                TC_11_im_em_en.class,
//                6,
//                10,
//                5,
//                10,
//                new String[]{},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IN_EN(){
//        return getTestData(
//                TC_12_in_en.class,
//                -1,
//                10,
//                -1,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_IN_EN(){
//        return getTestData(
//                TC_13_im_in_en.class,
//                6,
//                10,
//                -1,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_EM_IN_EN(){
//        return getTestData(
//                TC_14_em_in_en.class,
//                -1,
//                10,
//                5,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    private static Object[][] getTestData_IM_EM_IN_EN(){
//        return getTestData(
//                TC_15_im_em_in_en.class,
//                6,
//                10,
//                5,
//                10,
//                new String[]{"value1"},
//                new String[]{"map"},
//                new String[]{"value2"},
//                new String[]{"list"});
//    }
//
//    @ParameterizedTest
//    @MethodSource({
//            "getTestDataDefault",
//            "getTestData_IM",
//            "getTestData_EM",
//            "getTestData_IM_EM",
//            "getTestData_IN",
//            "getTestData_IM_IN",
//            "getTestData_EM_IN",
//            "getTestData_IM_EM_IN",
//            "getTestData_EN",
//            "getTestData_IM_EN",
//            "getTestData_EM_EN",
//            "getTestData_IM_EM_EN",
//            "getTestData_IN_EN",
//            "getTestData_IM_IN_EN",
//            "getTestData_EM_IN_EN",
//            "getTestData_IM_EM_IN_EN"
//    })
//    void test(Class<?> clazz,
//              int includeByModifiers,
//              int excludeByModifiers,
//              String[] includeByName,
//              String[] excludeByName,
//              boolean result) throws Exception {
//
//        Collector collector = Utils.createCollector();
//        ClassAnnotationDataSEH seh = new ClassAnnotationDataSEH(new SkeletonAnnotationChecker());
//        seh.handle(clazz, collector);
//
//        TestCollectorProcess process = new TestCollectorProcess(
//                collector.getCollectorStructure().getPath(CollectorStructureEI.annotationEI()),
//                includeByModifiers,
//                excludeByModifiers,
//                includeByName,
//                excludeByName
//        );
//        collector.apply(process);
//
//        assertThat(process.isValid()).isEqualTo(result);
//    }
//
//    private static class TestCollectorProcess implements CollectorProcess{
//
//        private static final String INCLUDE_BY_MODIFIERS ="includeByModifiers";
//        private static final String EXCLUDE_BY_MODIFIERS = "excludeByModifiers";
//        private static final String INCLUDE_BY_NAME = "includeByName";
//        private static final String EXCLUDE_BY_NAME = "excludeByName";
//
//        private final List<String> path;
//        private final int includeByModifiers;
//        private final int excludeByModifiers;
//        private final String[] includeByName;
//        private final String[] excludeByName;
//
//        private boolean valid = false;
//
//        public TestCollectorProcess(List<String> path,
//                                    int includeByModifiers,
//                                    int excludeByModifiers,
//                                    String[] includeByName,
//                                    String[] excludeByName) {
//            this.path = path;
//            this.includeByModifiers = includeByModifiers;
//            this.excludeByModifiers = excludeByModifiers;
//            this.includeByName = includeByName;
//            this.excludeByName = excludeByName;
//        }
//
//        @Override
//        public void handle(Node node) {
//            Optional<Node> maybeChild = node.getChild(path, ObjectNode.class);
//            if (maybeChild.isPresent()){
//                ObjectNode annotationNode = (ObjectNode) maybeChild.get();
//
//                valid = checkModifiers(annotationNode, INCLUDE_BY_MODIFIERS, includeByModifiers);
//                valid &= checkModifiers(annotationNode, EXCLUDE_BY_MODIFIERS, excludeByModifiers);
//                valid &= checkNames(annotationNode, INCLUDE_BY_NAME, includeByName);
//                valid &= checkNames(annotationNode, EXCLUDE_BY_NAME, excludeByName);
//            }
//        }
//
//        @Override
//        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {
//
//        }
//
//        public boolean isValid(){
//            return valid;
//        }
//
//        private boolean checkModifiers(ObjectNode node, String property, int modifiers){
//            if (node.containsKey(property)){
//                Node modifiersNode = node.getChildren().get(property);
//                if (modifiersNode.isNumber()){
//                    return ((NumberNode)modifiersNode).getValue().equals(modifiers);
//                }
//            }
//
//            return false;
//        }
//
//        private boolean checkNames(ObjectNode node, String property, String[] names){
//            if (node.containsKey(property)){
//                Node namesNode = node.getChildren().get(property);
//                if (namesNode.isArray()){
//                    List<String> nodeNames = new ArrayList<>();
//                    for (Node child : ((ArrayNode) namesNode).getChildren()) {
//                        if (child.isString()){
//                            nodeNames.add(((StringNode)child).getValue());
//                        } else {
//                            return false;
//                        }
//                    }
//
//                    return nodeNames.equals(Arrays.asList(names));
//                }
//            }
//
//            return false;
//        }
//    }
//}
