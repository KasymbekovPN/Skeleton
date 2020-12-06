package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.custom.functional.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header.ClassSignatureTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassContainerTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassCustomTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.member.ClassSpecificTaskHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.functional.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.KasymbekovPN.Skeleton.util.UClassSerialization;
import org.KasymbekovPN.Skeleton.util.UCollectionTypeCheckerBuilder;
import org.KasymbekovPN.Skeleton.util.UHandlerIds;
import org.KasymbekovPN.Skeleton.util.UMapTypeCheckerBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Complex test of all ClassTaskHandlers")
public class ClassTaskHandlerComplexTest {

    @Test
    void test() throws Exception {
        SKSimpleChecker<Class<?>> specificChecker = new SKSimpleChecker<>(int.class);
        SKSimpleChecker<String> customChecker = new SKSimpleChecker<>("InnerTestClass");
        CollectionTypeChecker collectionChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class)
                .setArgumentTypes(Integer.class)
                .build();
        MapTypeChecker mapChecker = new UMapTypeCheckerBuilder()
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class)
                .setValueArgumentsTypes(Integer.class)
                .build();
        SKCollector collector = new SKCollector();
        AnnotationExtractor annotationExtractor = new AnnotationExtractor();

        handleComplex(collector, specificChecker, customChecker, annotationExtractor, collectionChecker, mapChecker);

        Node complexObjectNode = collector.detachNode();

        ClassSignatureTaskHandlerWrapper signatureWrapper = new ClassSignatureTaskHandlerWrapper(UHandlerIds.SIGNATURE);
        handleWrapper(collector, signatureWrapper, annotationExtractor);

        ClassSpecificTaskHandlerWrapper specificWrapper = new ClassSpecificTaskHandlerWrapper(specificChecker, UHandlerIds.SPECIFIC);
        handleWrapper(collector, specificWrapper, annotationExtractor);

        ClassCustomTaskHandlerWrapper customWrapper = new ClassCustomTaskHandlerWrapper(customChecker, annotationExtractor, UHandlerIds.CUSTOM);
        handleWrapper(collector, customWrapper, annotationExtractor);

        ClassContainerTaskHandlerWrapper collectionWrapper = new ClassContainerTaskHandlerWrapper(collectionChecker, UHandlerIds.COLLECTION);
        handleWrapper(collector, collectionWrapper, annotationExtractor);

        ClassContainerTaskHandlerWrapper mapWrapper = new ClassContainerTaskHandlerWrapper(mapChecker, UHandlerIds.MAP);
        handleWrapper(collector, mapWrapper, annotationExtractor);

        Node objectNode = collector.detachNode();

        assertThat(complexObjectNode).isEqualTo(objectNode);
    }

    private void handleComplex(Collector collector,
                               Function<Class<?>, Boolean> specificChecker,
                               Function<String, Boolean> customChecker,
                               OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor,
                               Function<Field, Boolean> collectionChecker,
                               Function<Field, Boolean> mapChecker) throws Exception {
        ClassContext classContext = UClassSerialization.createClassContext(
                collector,
                new SKContextStateCareTaker<>()
        );

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(
                        TestClass.class,
                        new AnnotationExtractor()
                )
        );

        Processor<ClassContext> processor = UClassSerialization.createProcessor(
                specificChecker,
                customChecker,
                annotationExtractor,
                collectionChecker,
                mapChecker
        );

        processor.handle(classContext);
    }

    private void handleWrapper(Collector collector,
                               Wrapper wrapper,
                               OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor) throws InvocationTargetException, NoSuchMethodException, InstantiationException, ContextStateCareTakerIsEmpty, IllegalAccessException {
        ClassContext classContext = UClassSerialization.createClassContext(
                collector,
                new SKContextStateCareTaker<>()
        );

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(
                        TestClass.class,
                        annotationExtractor
                )
        );
        wrapper.callCheck(classContext);

        SimpleResult result = wrapper.getResult();
        if (result.isSuccess()){
            wrapper.callDoId(classContext);
        }
    }

    @SkeletonClass(name = "TestClass")
    public static class TestClass {

        @SkeletonMember
        private int intValue;

        @SkeletonMember(name = "InnerTestClass")
        private InnerTestClass innerInstanceProcessorTC0;

        @SkeletonMember
        private Set<Integer> intSet;

        @SkeletonMember
        private Map<Integer, Integer> intMap;
    }

    @SkeletonClass(name = "InnerTestClass")
    public static class InnerTestClass {
    }

    private interface Wrapper{
        void callCheck(ClassContext context) throws ContextStateCareTakerIsEmpty;
        void callDoId(ClassContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException;
        SimpleResult getResult();
    }

    private static class ClassSignatureTaskHandlerWrapper extends ClassSignatureTaskHandler implements Wrapper{

        public ClassSignatureTaskHandlerWrapper(String id) {
            super(id);
            simpleResult = new SKSimpleResult();
        }

        @Override
        public void callCheck(ClassContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void callDoId(ClassContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            super.doIt(context);
        }

        @Override
        public SimpleResult getResult(){
            return simpleResult;
        }
    }

    private static class ClassSpecificTaskHandlerWrapper extends ClassSpecificTaskHandler implements Wrapper {

        public ClassSpecificTaskHandlerWrapper(Function<Class<?>, Boolean> fieldChecker, String id) {
            super(fieldChecker, id);
            simpleResult = new SKSimpleResult();
        }

        @Override
        public void callCheck(ClassContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void callDoId(ClassContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            super.doIt(context);
        }

        @Override
        public SimpleResult getResult(){
            return simpleResult;
        }
    }

    private static class ClassContainerTaskHandlerWrapper extends ClassContainerTaskHandler implements Wrapper{

        public ClassContainerTaskHandlerWrapper(Function<Field, Boolean> fieldChecker, String id) {
            super(fieldChecker, id);
            simpleResult = new SKSimpleResult();
        }

        @Override
        public void callCheck(ClassContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void callDoId(ClassContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            super.doIt(context);
        }

        @Override
        public SimpleResult getResult(){
            return simpleResult;
        }
    }

    private static class ClassCustomTaskHandlerWrapper extends ClassCustomTaskHandler implements Wrapper{

        public ClassCustomTaskHandlerWrapper(Function<String, Boolean> classNameChecker,
                                             OptFunction<Pair<Class<? extends Annotation>, Annotation[]>, Annotation> annotationExtractor,
                                             String id) {
            super(classNameChecker, annotationExtractor, id);
            simpleResult = new SKSimpleResult();
        }

        @Override
        public void callCheck(ClassContext context) throws ContextStateCareTakerIsEmpty {
            super.check(context);
        }

        @Override
        public void callDoId(ClassContext context) throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException {
            super.doIt(context);
        }

        @Override
        public SimpleResult getResult(){
            return simpleResult;
        }
    }
}
