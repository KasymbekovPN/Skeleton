package org.KasymbekovPN.Skeleton.custom.serialization.serializer;

//< restore

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SkeletonSerializer;
import org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("SkeletonSerializer. Testing of:")
public class SkeletonSerializerTest {

    private static final String PROPERTY = "name";

    @Test
    @DisplayName("builder without class and member handlers")
    void testBuilderWithoutClassAndMemberHandler() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> {
            new SkeletonSerializer.Builder(collector).build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("builder without class handler")
    void testBuilderWithoutClassHandler() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> {
            new SkeletonSerializer.Builder(collector)
                    .addMemberHandler(new DummySEH())
                    .build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("builder without member handler")
    void testBuilderWithoutMemberHandler() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> {
            new SkeletonSerializer.Builder(collector)
                    .addClassHandler(new DummySEH())
                    .build();
        });
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("builder with all handlers")
    void testBuilderWithAllHandlers() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> {
            new SkeletonSerializer.Builder(collector)
                    .addMemberHandler(new DummySEH())
                    .addClassHandler(new DummySEH())
                    .build();
        });
        assertThat(throwable).isEqualTo(null);
    }

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        new String[]{"intProperty", "booleanProperty"},
                        true
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC011",
                        new String[]{"intProperty", "booleanProperty"},
                        false
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        new String[]{"intProperty", "booleanProperty", "xyz"},
                        false
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC011",
                        new String[]{"intProperty", "booleanProperty", "xyz"},
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    @DisplayName("Serialization Handlers")
    void testSerialization(
            Class<?> clazz,
            String classValue,
            String[] memberNames,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        Serializer serializer = new SkeletonSerializer.Builder(collector)
                .addClassHandler(new ClassConstructorMethodHandler(CollectorStructureEI.classEI()))
                .addMemberHandler(new MembersHandler())
                .build();

        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(
                collector,
                classValue,
                memberNames
        );
        collector.apply(process);

        assertThat(process.isValid()).isEqualTo(result);
    }

    private static class DummySEH extends BaseSEH {}

    private static class ClassConstructorMethodHandler extends BaseSEH{

        private final EntityItem entityItem;

        private String name;

        public ClassConstructorMethodHandler(EntityItem entityItem) {
            this.entityItem = entityItem;
        }

        @Override
        protected boolean checkData(Class<?> clazz, Collector collector) {
            name = clazz.getName();
            return true;
        }

        @Override
        protected boolean fillCollector(Collector collector) {
            collector.setTarget(collector.getCollectorStructure().getPath(entityItem));
            collector.addProperty(PROPERTY, name);
            collector.reset();
            return true;
        }
    }

    private static class MembersHandler extends BaseSEH{

        private String name;

        @Override
        protected boolean checkData(Field field, Collector collector) {
            name = field.getName();
            return true;
        }

        @Override
        protected boolean fillCollector(Collector collector) {
            collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()));
            collector.addProperty(name, name);
            collector.reset();
            return true;
        }
    }

    private static class TestCollectorProcess implements CollectorProcess{

        private final Collector collector;
        private final String classValue;
        private final String[] memberNames;

        private AtomicBoolean valid = new AtomicBoolean(true);

        public TestCollectorProcess(Collector collector, String classValue, String[] memberNames) {
            this.collector = collector;
            this.classValue = classValue;
            this.memberNames = memberNames;
        }

        @Override
        public void handle(Node node) {

            Optional<Node> maybeClassNode = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.classEI()), ObjectNode.class
            );
            if (maybeClassNode.isPresent()){
                ObjectNode classNode = (ObjectNode) maybeClassNode.get();
                classNode.get(PROPERTY, StringNode.class).ifPresent(value -> {
                    if (!((StringNode)value).getValue().equals(classValue)){
                        valid.set(false);
                    }
                });
            }

            Optional<Node> maybeMembersNode = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()), ObjectNode.class
            );
            if (maybeMembersNode.isPresent()){
                ObjectNode membersNode = (ObjectNode) maybeMembersNode.get();
                for (String memberName : memberNames) {
                    Optional<Node> memberNode = membersNode.get(memberName, StringNode.class);
                    if (memberNode.isEmpty()){
                        valid.set(false);
                    }
                }
            }
        }

        @Override
        public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {
        }

        public boolean isValid() {
            return valid.get();
        }
    }
}
