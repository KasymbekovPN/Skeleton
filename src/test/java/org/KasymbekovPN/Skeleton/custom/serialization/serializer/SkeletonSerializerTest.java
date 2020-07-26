package org.KasymbekovPN.Skeleton.custom.serialization.serializer;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer.SerializerEI;
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
    @DisplayName("Builder (without class part)")
    void testBuilderWithoutClassPart() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addMemberHandler(new DummySEH())
                .addConstructorHandler(new DummySEH())
                .addMethodHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without members part)")
    void testBuilderWithoutMembersPart() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(new DummySEH())
                .addConstructorHandler(new DummySEH())
                .addMethodHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without constructor part)")
    void testBuilderWithoutConstructorPart() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(new DummySEH())
                .addMemberHandler(new DummySEH())
                .addMethodHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without method part)")
    void testBuilderWithoutMethodPart() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(new DummySEH())
                .addMemberHandler(new DummySEH())
                .addConstructorHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (class part is null)")
    void testBuilderClassPartIsNull() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(null)
                .addMemberHandler(new DummySEH())
                .addConstructorHandler(new DummySEH())
                .addMethodHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (members part is null)")
    void testBuilderMembersPartIsNull() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(new DummySEH())
                .addMemberHandler(null)
                .addConstructorHandler(new DummySEH())
                .addMethodHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (constructor part is null)")
    void testBuilderConstructorPartIsNull() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(new DummySEH())
                .addMemberHandler(new DummySEH())
                .addConstructorHandler(null)
                .addMethodHandler(new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (method part is null)")
    void testBuilderMethodPartIsNull() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addClassHandler(new DummySEH())
                .addMemberHandler(new DummySEH())
                .addConstructorHandler(new DummySEH())
                .addMethodHandler(null)
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without class part, raw)")
    void testBuilderWithoutClassPartRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.memberEI(), new DummySEH())
                .addHandler(SerializerEI.constructorEI(), new DummySEH())
                .addHandler(SerializerEI.methodEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without members part, raw)")
    void testBuilderWithoutMembersPartRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), new DummySEH())
                .addHandler(SerializerEI.constructorEI(), new DummySEH())
                .addHandler(SerializerEI.methodEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without constructor part, raw)")
    void testBuilderWithoutConstructorPartRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), new DummySEH())
                .addHandler(SerializerEI.memberEI(), new DummySEH())
                .addHandler(SerializerEI.methodEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (without method part, raw)")
    void testBuilderWithoutMethodPartRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), new DummySEH())
                .addHandler(SerializerEI.memberEI(), new DummySEH())
                .addHandler(SerializerEI.constructorEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (class part is null, raw)")
    void testBuilderClassPartIsNullRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), null)
                .addHandler(SerializerEI.memberEI(), new DummySEH())
                .addHandler(SerializerEI.constructorEI(), new DummySEH())
                .addHandler(SerializerEI.methodEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (members part is null, raw)")
    void testBuilderMembersPartIsNullRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), new DummySEH())
                .addHandler(SerializerEI.memberEI(), null)
                .addHandler(SerializerEI.constructorEI(), new DummySEH())
                .addHandler(SerializerEI.methodEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (constructor part is null, raw)")
    void testBuilderConstructorPartIsNullRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), new DummySEH())
                .addHandler(SerializerEI.memberEI(), new DummySEH())
                .addHandler(SerializerEI.constructorEI(), null)
                .addHandler(SerializerEI.methodEI(), new DummySEH())
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Builder (method part is null, raw)")
    void testBuilderMethodPartIsNullRaw() throws Exception {
        Collector collector = Utils.createCollector();
        Throwable throwable = catchThrowable(() -> new SkeletonSerializer.Builder(collector)
                .addHandler(SerializerEI.classEI(), new DummySEH())
                .addHandler(SerializerEI.memberEI(), new DummySEH())
                .addHandler(SerializerEI.constructorEI(), new DummySEH())
                .addHandler(SerializerEI.methodEI(), null)
                .build());
        assertThat(throwable).isInstanceOf(Exception.class);
    }

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        new String[]{"intProperty", "booleanProperty"},
                        true
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC01",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        new String[]{"intProperty", "booleanProperty"},
                        false
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC01",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        new String[]{"intProperty", "booleanProperty"},
                        false
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC01",
                        new String[]{"intProperty", "booleanProperty"},
                        false
                },
                {
                        TC0.class,
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
                        "org.KasymbekovPN.Skeleton.custom.serialization.serializer.classes.TC0",
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
            String constructorValue,
            String methodValue,
            String[] memberNames,
            boolean result
    ) throws Exception {
        Collector collector = Utils.createCollector();
        Serializer serializer = new SkeletonSerializer.Builder(collector)
                .addClassHandler(new ClassConstructorMethodHandler(CollectorStructureEI.classEI()))
                .addMemberHandler(new MembersHandler())
                .addConstructorHandler(new ClassConstructorMethodHandler(CollectorStructureEI.constructorEI()))
                .addMethodHandler(new ClassConstructorMethodHandler(CollectorStructureEI.methodEI()))
                .build();

        serializer.serialize(clazz);

        TestCollectorProcess process = new TestCollectorProcess(
                collector,
                classValue,
                constructorValue,
                methodValue,
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
        private final String constructorValue;
        private final String methodValue;
        private final String[] memberNames;

        private AtomicBoolean valid = new AtomicBoolean(true);

        public TestCollectorProcess(Collector collector, String classValue, String constructorValue, String methodValue, String[] memberNames) {
            this.collector = collector;
            this.classValue = classValue;
            this.constructorValue = constructorValue;
            this.methodValue = methodValue;
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

            Optional<Node> maybeConstructorNode = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.constructorEI()), ObjectNode.class
            );
            if (maybeConstructorNode.isPresent()){
                ObjectNode constructorNode = (ObjectNode) maybeConstructorNode.get();
                constructorNode.get(PROPERTY, StringNode.class).ifPresent(value -> {
                    if (!((StringNode)value).getValue().equals(constructorValue)){
                        valid.set(false);
                    }
                });
            }

            Optional<Node> maybeMethodNode = node.getChild(
                    collector.getCollectorStructure().getPath(CollectorStructureEI.methodEI()), ObjectNode.class
            );
            if (maybeMethodNode.isPresent()){
                ObjectNode methodNode = (ObjectNode) maybeMethodNode.get();
                methodNode.get(PROPERTY, StringNode.class).ifPresent(value -> {
                    if (!((StringNode)value).getValue().equals(methodValue)){
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
