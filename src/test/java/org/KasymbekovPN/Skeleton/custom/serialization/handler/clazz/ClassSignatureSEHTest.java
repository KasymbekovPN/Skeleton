package org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz.ClassSignatureSEH;
import org.KasymbekovPN.Skeleton.custom.serialization.handler.clazz.classes.forSignature.TCForSignaturePublic;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.SkeletonAnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SkeletonSignatureSEH. Testing of:")
public class ClassSignatureSEHTest {

    private static Object[][] getTestData(){
        Class<?>[] classes = {
                TCForSignaturePublic.class,
                TCDefault.class,
                TCStaticDefault.class,
                TCProtected.class,
                TCProtectedStatic.class
        };

        Object[][] objects = new Object[classes.length * 4][4];
        for (int i = 0; i < classes.length; i++) {

            int index = 4 * i;
            objects[index][0] = classes[i];
            objects[index][1] = classes[i].getTypeName();
            objects[index][2] = classes[i].getModifiers();
            objects[index][3] = true;

            index++;
            objects[index][0] = classes[i];
            objects[index][1] = classes[i].getTypeName() + "wrong";
            objects[index][2] = classes[i].getModifiers();
            objects[index][3] = false;

            index++;
            objects[index][0] = classes[i];
            objects[index][1] = classes[i].getTypeName();
            objects[index][2] = classes[i].getModifiers() + 1;
            objects[index][3] = false;

            index++;
            objects[index][0] = classes[i];
            objects[index][1] = classes[i].getTypeName() + "wrong";
            objects[index][2] = classes[i].getModifiers() + 1;
            objects[index][3] = false;
        }

        return objects;
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Class<?> clazz, String name, int modifiers, boolean result) throws Exception {

        Collector collector = Utils.createCollector();
        ClassSignatureSEH seh = new ClassSignatureSEH(new SkeletonAnnotationChecker());
        seh.handle(clazz, collector);

        TestCollectorProcess process = new TestCollectorProcess(
                name,
                modifiers,
                collector.getCollectorStructure().getPath(CollectorStructureEI.classEI())
        );
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class TestCollectorProcess implements CollectorProcess {

        private static String MODIFIERS = "modifiers";
        private static int CLASS_SECTION_SIZE = 1;

        private final String name;
        private final int modifiers;
        private final List<String> path;

        private boolean valid = false;

        public TestCollectorProcess(String name, int modifiers, List<String> path) {
            this.name = name;
            this.modifiers = modifiers;
            this.path = path;
        }

        @Override
        public void handle(Node node) {
            Optional<Node> maybeChild = node.getChild(path, ObjectNode.class);
            if (maybeChild.isPresent()){
                ObjectNode classNode = (ObjectNode) maybeChild.get();
                if (classNode.getChildren().size() == CLASS_SECTION_SIZE && classNode.containsKey(name)){
                    Node concreteNode = classNode.getChildren().get(name);
                    if (concreteNode.isObject()){
                        ObjectNode concreteObjectNode = (ObjectNode) concreteNode;
                        if (concreteObjectNode.containsKey(MODIFIERS)){
                            Node modifiersNode = concreteObjectNode.getChildren().get(MODIFIERS);
                            if (modifiersNode.isNumber()){
                                valid = ((NumberNode)modifiersNode).getValue().equals(modifiers);
                            }
                        }
                    }
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

    @SkeletonClass
    class TCDefault { }

    @SkeletonClass
    static class TCStaticDefault { }

    @SkeletonClass
    protected class TCProtected{ }

    @SkeletonClass
    protected static class TCProtectedStatic{}
}
