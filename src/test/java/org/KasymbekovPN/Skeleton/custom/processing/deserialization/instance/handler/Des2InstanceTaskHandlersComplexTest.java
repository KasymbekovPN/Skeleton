package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.extractor.annotation.ClassNameExtractor;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.SKInstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ClassName2Instance;
import org.KasymbekovPN.Skeleton.custom.optionalConverter.ToInstanceOC;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.SKDes2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContextOld;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Complex test of all Des2InstanceTaskHandlers")
public class Des2InstanceTaskHandlersComplexTest {

    @DisplayName("test")
    @Test
    void test() throws Exception {

        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(
                        Integer.class, Float.class, Double.class, Boolean.class, Character.class,
                        String.class, Des2InstanceInnerTC0.class
                )
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class, String.class)
                .setValueArgumentsTypes(String.class, Des2InstanceInnerTC0.class)
                .build();

        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );

        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(
                        int.class, float.class, double.class, boolean.class, char.class,
                        String.class, Boolean.class, Integer.class, Float.class, Double.class,
                        Character.class
                ),
                new SKSimpleChecker<String>("Des2InstanceInnerTC0"),
                new AnnotationExtractor(),
                collectionTypeChecker,
                mapTypeChecker
        );

        HashMap<String, ObjectNode> classNodes = new HashMap<>();

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(
                        Des2InstanceInnerTC0.class,
                        new AnnotationExtractor()
                )
        );
        classProcessor.handle(classContext);
        classNodes.put("Des2InstanceInnerTC0", (ObjectNode) classContext.getCollector().detachNode());

        classContext.getContextStateCareTaker().push(
                new SKClassContextStateMemento(
                        Des2InstanceTC0.class,
                        new AnnotationExtractor()
                )
        );
        classProcessor.handle(classContext);
        classNodes.put("Des2InstanceTC0", (ObjectNode) classContext.getCollector().detachNode());

        OldContextProcessor<InstanceContextOld> instanceProcessor = UInstanceSerializationOld.createInstanceProcessor();
        InstanceContextOld instanceContextOld = UInstanceSerializationOld.createInstanceContext(
                classNodes,
                instanceProcessor,
                null,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                new SKInstanceMembersPartHandler()
        );

        Des2InstanceTC0 original = new Des2InstanceTC0();
        original.setIntValue(123);
        original.setFloatValue(0.25f);
        original.setDoubleValue(1.65);
        original.setBooleanValue(true);
        original.setCharValue('x');
        original.setStringObject("hello");
        original.setBooleanObject(true);
        original.setIntegerObject(654);
        original.setFloatObject(1.258f);
        original.setDoubleObject(5566.558);
        original.setCharacterObject('z');
        original.setIntSet(new HashSet<>(Arrays.asList(1,2,3)));
        original.setIntList(Arrays.asList(4, 5, 6));
        original.setFloatSet(new HashSet<>(Arrays.asList(1.1f, 1.2f)));
        original.setFloatList(Arrays.asList(1.3f, 1.4f));
        original.setDoubleSet(new HashSet<>(Arrays.asList(100.1, 100.2)));
        original.setDoubleList(Arrays.asList(100.3, 100.4));
        original.setBooleanSet(new HashSet<>(Arrays.asList(false, true)));
        original.setBooleanList(Arrays.asList(true, false, true));
        original.setCharacterSet(new HashSet<>(Arrays.asList('a', 'b')));
        original.setCharacterList(Arrays.asList('c', 'd'));
        original.setStringSet(new HashSet<>(Arrays.asList("hello", "world")));
        original.setStringList(Arrays.asList("aaa", "bbb"));

        Des2InstanceInnerTC0 custom = new Des2InstanceInnerTC0();
        custom.setIntValue(456);
        original.setCustom(custom);

        Des2InstanceInnerTC0 custom2 = new Des2InstanceInnerTC0();
        custom2.setIntValue(789);
        original.setCustom2(custom2);

        original.setIntValue2(963);

        Des2InstanceInnerTC0 setCustom1 = new Des2InstanceInnerTC0();
        setCustom1.setIntValue(101);
        Des2InstanceInnerTC0 setCustom2 = new Des2InstanceInnerTC0();
        setCustom2.setIntValue(102);
        Des2InstanceInnerTC0 setCustom3 = new Des2InstanceInnerTC0();
        setCustom3.setIntValue(103);
        original.setCustomSet(new HashSet<>(Arrays.asList(
                setCustom1,
                setCustom2,
                setCustom3
        )));

        Des2InstanceInnerTC0 listCustom1 = new Des2InstanceInnerTC0();
        listCustom1.setIntValue(1001);
        Des2InstanceInnerTC0 listCustom2 = new Des2InstanceInnerTC0();
        listCustom2.setIntValue(1002);
        original.setCustomList(Arrays.asList(listCustom1, listCustom2));

        HashMap<Integer, String> strByIntMap = new HashMap<>() {{
            put(12, "Hello");
            put(13, "world");
        }};
        original.setStringByIntegerMap(strByIntMap);

        Des2InstanceInnerTC0 mapCustom1 = new Des2InstanceInnerTC0();
        mapCustom1.setIntValue(777);
        Des2InstanceInnerTC0 mapCustom2 = new Des2InstanceInnerTC0();
        mapCustom2.setIntValue(888);
        HashMap<String, Des2InstanceInnerTC0> customMap = new HashMap<>() {{
            put("first", mapCustom1);
            put("second", mapCustom2);
        }};
        original.setCustomByStringMap(customMap);

        instanceContextOld.attachInstance(original);
        instanceProcessor.handle(instanceContextOld);

        ObjectNode serData = (ObjectNode) instanceContextOld.getCollector().detachNode();

        ContextProcessor<Des2InstanceCxt> des2InstanceProcessor = USKDes2Instance.createProcessor();

        HashMap<String, Class<?>> map = new HashMap<>(){{
            put("Des2InstanceInnerTC0", Des2InstanceInnerTC0.class);
        }};
        Des2InstanceCxt des2InstanceContext = USKDes2Instance.createContext(
                USKDes2Instance.createContextIds(),
                classNodes,
                new ClassName2Instance(map),
                new ToInstanceOC(map, USKClassHeaderPartHandler.DEFAULT, USKCollectorPath.DEFAULT_CLASS_PART_PATH),
                des2InstanceProcessor,
                new SKContextStateCareTaker<>()
        );

        Des2InstanceTC0 restored = new Des2InstanceTC0();
        SKDes2InstanceContextStateMemento mem = new SKDes2InstanceContextStateMemento(
                restored,
                serData,
                classNodes,
                new ClassNameExtractor(),
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                new AnnotationExtractor()
        );
        des2InstanceContext.getContextStateCareTaker().push(mem);

        des2InstanceProcessor.handle(des2InstanceContext);

        assertThat(original).isEqualTo(restored);
    }

    @SkeletonClass(name = "Des2InstanceInnerTC0")
    public static class Des2InstanceInnerTC0 {

        @SkeletonMember
        private int intValue;

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        @Override
        public String toString() {
            return "Des2InstanceInnerTC0{" +
                    "intValue=" + intValue +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Des2InstanceInnerTC0 that = (Des2InstanceInnerTC0) o;
            return intValue == that.intValue;
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue);
        }
    }

    @SkeletonClass(name = "Des2InstanceTC0")
    public static class Des2InstanceTC0 {

        @SkeletonMember
        private int intValue;

        @SkeletonMember
        private float floatValue;

        @SkeletonMember
        private double doubleValue;

        @SkeletonMember
        private boolean booleanValue;

        @SkeletonMember
        private char charValue;

        @SkeletonMember
        private String stringObject;

        @SkeletonMember
        private Boolean booleanObject;

        @SkeletonMember
        private Integer integerObject;

        @SkeletonMember
        private Float floatObject;

        @SkeletonMember
        private Double doubleObject;

        @SkeletonMember
        private Character characterObject;

        @SkeletonMember
        private Set<Integer> intSet;

        @SkeletonMember
        private List<Integer> intList;

        @SkeletonMember
        private Set<Float> floatSet;

        @SkeletonMember
        private List<Float> floatList;

        @SkeletonMember
        private Set<Double> doubleSet;

        @SkeletonMember
        private List<Double> doubleList;

        @SkeletonMember
        private Set<Boolean> booleanSet;

        @SkeletonMember
        private List<Boolean> booleanList;

        @SkeletonMember
        private Set<Character> characterSet;

        @SkeletonMember
        private List<Character> characterList;

        @SkeletonMember
        private Set<String> stringSet;

        @SkeletonMember
        private List<String> stringList;

        @SkeletonMember(name = "Des2InstanceInnerTC0")
        private Des2InstanceInnerTC0 custom;

        @SkeletonMember(name = "Des2InstanceInnerTC0")
        private Des2InstanceInnerTC0 custom2;

        @SkeletonMember
        private int intValue2;

        @SkeletonMember
        private Set<Des2InstanceInnerTC0> customSet;

        @SkeletonMember
        private List<Des2InstanceInnerTC0> customList;

        @SkeletonMember
        private Map<Integer, String> stringByIntegerMap;

        @SkeletonMember
        private Map<String, Des2InstanceInnerTC0> customByStringMap;

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public void setFloatValue(float floatValue) {
            this.floatValue = floatValue;
        }

        public void setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public void setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public void setCharValue(char charValue) {
            this.charValue = charValue;
        }

        public void setStringObject(String stringObject) {
            this.stringObject = stringObject;
        }

        public void setBooleanObject(Boolean booleanObject) {
            this.booleanObject = booleanObject;
        }

        public void setIntegerObject(Integer integerObject) {
            this.integerObject = integerObject;
        }

        public void setFloatObject(Float floatObject) {
            this.floatObject = floatObject;
        }

        public void setDoubleObject(Double doubleObject) {
            this.doubleObject = doubleObject;
        }

        public void setCharacterObject(Character characterObject) {
            this.characterObject = characterObject;
        }

        public void setIntSet(Set<Integer> intSet) {
            this.intSet = intSet;
        }

        public void setIntList(List<Integer> intList) {
            this.intList = intList;
        }

        public void setFloatSet(Set<Float> floatSet) {
            this.floatSet = floatSet;
        }

        public void setFloatList(List<Float> floatList) {
            this.floatList = floatList;
        }

        public void setDoubleSet(Set<Double> doubleSet) {
            this.doubleSet = doubleSet;
        }

        public void setDoubleList(List<Double> doubleList) {
            this.doubleList = doubleList;
        }

        public void setBooleanSet(Set<Boolean> booleanSet) {
            this.booleanSet = booleanSet;
        }

        public void setBooleanList(List<Boolean> booleanList) {
            this.booleanList = booleanList;
        }

        public void setCharacterSet(Set<Character> characterSet) {
            this.characterSet = characterSet;
        }

        public void setCharacterList(List<Character> characterList) {
            this.characterList = characterList;
        }

        public void setStringSet(Set<String> stringSet) {
            this.stringSet = stringSet;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        public void setCustom(Des2InstanceInnerTC0 custom) {
            this.custom = custom;
        }

        public void setCustom2(Des2InstanceInnerTC0 custom2) {
            this.custom2 = custom2;
        }

        public void setIntValue2(int intValue2) {
            this.intValue2 = intValue2;
        }

        public void setCustomSet(Set<Des2InstanceInnerTC0> customSet) {
            this.customSet = customSet;
        }

        public void setCustomList(List<Des2InstanceInnerTC0> customList) {
            this.customList = customList;
        }

        public void setStringByIntegerMap(Map<Integer, String> stringByIntegerMap) {
            this.stringByIntegerMap = stringByIntegerMap;
        }

        public void setCustomByStringMap(Map<String, Des2InstanceInnerTC0> customByStringMap) {
            this.customByStringMap = customByStringMap;
        }

        @Override
        public String toString() {
            return "Des2InstanceTC0{" +
                    "intValue=" + intValue +
                    ", floatValue=" + floatValue +
                    ", doubleValue=" + doubleValue +
                    ", booleanValue=" + booleanValue +
                    ", charValue=" + charValue +
                    ", stringObject='" + stringObject + '\'' +
                    ", booleanObject=" + booleanObject +
                    ", integerObject=" + integerObject +
                    ", floatObject=" + floatObject +
                    ", doubleObject=" + doubleObject +
                    ", characterObject=" + characterObject +
                    ", intSet=" + intSet +
                    ", intList=" + intList +
                    ", floatSet=" + floatSet +
                    ", floatList=" + floatList +
                    ", doubleSet=" + doubleSet +
                    ", doubleList=" + doubleList +
                    ", booleanSet=" + booleanSet +
                    ", booleanList=" + booleanList +
                    ", characterSet=" + characterSet +
                    ", characterList=" + characterList +
                    ", stringSet=" + stringSet +
                    ", stringList=" + stringList +
                    ", custom=" + custom +
                    ", custom2=" + custom2 +
                    ", intValue2=" + intValue2 +
                    ", customSet=" + customSet +
                    ", customList=" + customList +
                    ", stringByIntegerMap=" + stringByIntegerMap +
                    ", customByStringMap=" + customByStringMap +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Des2InstanceTC0 that = (Des2InstanceTC0) o;
            return intValue == that.intValue &&
                    Float.compare(that.floatValue, floatValue) == 0 &&
                    Double.compare(that.doubleValue, doubleValue) == 0 &&
                    booleanValue == that.booleanValue &&
                    charValue == that.charValue &&
                    intValue2 == that.intValue2 &&
                    Objects.equals(stringObject, that.stringObject) &&
                    Objects.equals(booleanObject, that.booleanObject) &&
                    Objects.equals(integerObject, that.integerObject) &&
                    Objects.equals(floatObject, that.floatObject) &&
                    Objects.equals(doubleObject, that.doubleObject) &&
                    Objects.equals(characterObject, that.characterObject) &&
                    Objects.equals(intSet, that.intSet) &&
                    Objects.equals(intList, that.intList) &&
                    Objects.equals(floatSet, that.floatSet) &&
                    Objects.equals(floatList, that.floatList) &&
                    Objects.equals(doubleSet, that.doubleSet) &&
                    Objects.equals(doubleList, that.doubleList) &&
                    Objects.equals(booleanSet, that.booleanSet) &&
                    Objects.equals(booleanList, that.booleanList) &&
                    Objects.equals(characterSet, that.characterSet) &&
                    Objects.equals(characterList, that.characterList) &&
                    Objects.equals(stringSet, that.stringSet) &&
                    Objects.equals(stringList, that.stringList) &&
                    Objects.equals(custom, that.custom) &&
                    Objects.equals(custom2, that.custom2) &&
                    Objects.equals(customSet, that.customSet) &&
                    Objects.equals(customList, that.customList) &&
                    Objects.equals(stringByIntegerMap, that.stringByIntegerMap) &&
                    Objects.equals(customByStringMap, that.customByStringMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue, floatValue, doubleValue, booleanValue, charValue, stringObject, booleanObject, integerObject, floatObject, doubleObject, characterObject, intSet, intList, floatSet, floatList, doubleSet, doubleList, booleanSet, booleanList, characterSet, characterList, stringSet, stringList, custom, custom2, intValue2, customSet, customList, stringByIntegerMap, customByStringMap);
        }
    }
}
