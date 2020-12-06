package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.functional.extractor.annotation.AnnotationExtractor;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.SKClassContextStateMemento;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.SKInstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.SKCollector;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.util.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

@DisplayName("Testing of Instance Task Handlers")
public class InstanceTaskHandlersComplexTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new MutablePair<>(10, 10),
                        new MutablePair<>(111, 111),
                        new MutablePair<>(true, true),
                        new MutablePair<>(false, false),
                        new MutablePair<>('a', 'a'),
                        new MutablePair<>('b', 'b'),
                        new MutablePair<>("hello", "hello"),
                        new MutablePair<>(123, 123),
                        new MutablePair<>(
                                new HashSet<>(Arrays.asList(1,2,3)),
                                new HashSet<>(Arrays.asList(1,2,3))
                        ),
                        new MutablePair<>(
                                new ArrayList<>(Arrays.asList(4,5,6)),
                                new ArrayList<>(Arrays.asList(4,5,6))
                        ),
                        new MutablePair<>(
                                new ArrayList<>(Arrays.asList(true, false, true)),
                                new ArrayList<>(Arrays.asList(true, false, true))
                        ),
                        new MutablePair<>(
                                new HashSet<>(Arrays.asList('a', 'b', 'c')),
                                new HashSet<>(Arrays.asList('a', 'b', 'c'))
                        ),
                        new MutablePair<>(
                                new ArrayList<>(Arrays.asList('x', 'y', 'x')),
                                new ArrayList<>(Arrays.asList('x', 'y', 'x'))
                        ),
                        new MutablePair<>(
                                new HashSet<>(Arrays.asList("hello", "world", "!!!")),
                                new HashSet<>(Arrays.asList("hello", "world", "!!!"))
                        ),
                        new MutablePair<>(
                                new ArrayList<>(Arrays.asList("aaa", "bbb")),
                                new ArrayList<>(Arrays.asList("aaa", "bbb"))
                        ),
                        new MutablePair<>(
                                new HashSet<>(Arrays.asList(200, 201, 202)),
                                new HashSet<>(Arrays.asList(200, 201, 202))
                        ),
                        new MutablePair<>(
                                new ArrayList<>(Arrays.asList(300, 301, 302)),
                                new ArrayList<>(Arrays.asList(300, 301, 302))
                        ),
                        new MutablePair<>(
                                new HashMap<Integer, String>(){{
                                    put(1, "one");
                                    put(2, "two");
                                }},
                                new HashMap<Integer, String>(){{
                                    put(1, "one");
                                    put(2, "two");
                                }}
                        ),
                        new MutablePair<>(
                                new HashMap<Integer, Integer>(){{
                                    put(0, 456);
                                    put(1, 567);
                                }},
                                new HashMap<Integer, Integer>(){{
                                    put(0, 456);
                                    put(1, 567);
                                }}
                        ),
                        new MutablePair<>(
                                new HashMap<Integer, Integer>(){{
                                    put(1001, 1);
                                    put(1002, 2);
                                }},
                                new HashMap<Integer, Integer>(){{
                                    put(1001, 1);
                                    put(1002, 2);
                                }}
                        )
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Pair<Integer, Integer> intValuePair,
              Pair<Integer, Integer> intObjectPair,
              Pair<Boolean, Boolean> booleanValuePair,
              Pair<Boolean, Boolean> booleanObjectPair,
              Pair<Character, Character> charValuePair,
              Pair<Character, Character> characterObjectPair,
              Pair<String, String> stringObjectPair,
              Pair<Integer, Integer> customPair,
              Pair<Set<Integer>, Set<Integer>> intSetPair,
              Pair<List<Integer>, List<Integer>> intListPair,
              Pair<List<Boolean>, List<Boolean>> booleanListPair,
              Pair<Set<Character>, Set<Character>> characterSetPair,
              Pair<List<Character>, List<Character>> characterListPair,
              Pair<Set<String>, Set<String>> stringSetPair,
              Pair<List<String>, List<String>> stringListPair,
              Pair<Set<Integer>, Set<Integer>> customSetPair,
              Pair<List<Integer>, List<Integer>> customListPair,
              Pair<Map<Integer, String>, Map<Integer, String>> stringByIntegerPair,
              Pair<Map<Integer, Integer>, Map<Integer, Integer>> customNyInteger,
              Pair<Map<Integer, Integer>, Map<Integer, Integer>> integerByCustomPair) throws Exception {

        TestClass testClass = new TestClass();
        testClass.setIntValue(intValuePair.getLeft());
        testClass.setIntObject(intObjectPair.getLeft());
        testClass.setBooleanValue(booleanValuePair.getLeft());
        testClass.setBooleanObject(booleanObjectPair.getLeft());
        testClass.setCharValue(charValuePair.getLeft());
        testClass.setCharacterObject(characterObjectPair.getLeft());
        testClass.setStringObject(stringObjectPair.getLeft());
        testClass.setCustom(new InnerTestClass(customPair.getLeft()));
        testClass.setIntSet(intSetPair.getLeft());
        testClass.setIntList(intListPair.getLeft());
        testClass.setBooleanList(booleanListPair.getLeft());
        testClass.setCharacterSet(characterSetPair.getLeft());
        testClass.setCharacterList(characterListPair.getLeft());
        testClass.setStringSet(stringSetPair.getLeft());
        testClass.setStringList(stringListPair.getLeft());

        HashSet<InnerTestClass> innerTestClassSet = new HashSet<>();
        for (Integer integer : customSetPair.getLeft()) {
            innerTestClassSet.add(new InnerTestClass(integer));
        }
        testClass.setCustomSet(innerTestClassSet);

        ArrayList<InnerTestClass> innerTestClassList = new ArrayList<>();
        for (Integer integer : customListPair.getLeft()) {
            innerTestClassList.add(new InnerTestClass(integer));
        }
        testClass.setCustomList(innerTestClassList);

        testClass.setStringByInteger(stringByIntegerPair.getLeft());

        HashMap<Integer, InnerTestClass> map1 = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : customNyInteger.getLeft().entrySet()) {
            map1.put(entry.getKey(), new InnerTestClass(entry.getValue()));
        }
        testClass.setCustomByInteger(map1);

        HashMap<InnerTestClass, Integer> map2 = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : integerByCustomPair.getLeft().entrySet()) {
            map2.put(new InnerTestClass(entry.getKey()), entry.getValue());
        }
        testClass.setIntegerByCustom(map2);

        ObjectNode innerClassData = getClassSerializedData(InnerTestClass.class);
        ObjectNode classData = getClassSerializedData(TestClass.class);
        HashMap<String, ObjectNode> classNodes = new HashMap<>() {{
            put("InnerTestClass", innerClassData);
            put("TestClass", classData);
        }};
        InstanceContext instanceContext = UInstanceSerialization.createContext(
                new SKContextStateCareTaker<>(),
                classNodes,
                new SKCollector()
        );
        ContextProcessor<InstanceContext> processor = UInstanceSerialization.createProcessor();

        instanceContext.getContextStateCareTaker().push(new SKInstanceContextStateMemento(
                testClass,
                classNodes.get("TestClass"),
                instanceContext
        ));
        processor.handle(instanceContext);
        ObjectNode node = (ObjectNode) instanceContext.getCollector().detachNode();

        Assertions.assertThat(checkClassPart(node));
        Optional<Node> maybeMembersPart = node.getChild(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH);
        Assertions.assertThat(maybeMembersPart).isPresent();
        Map<String, Node> children = ((ObjectNode) maybeMembersPart.get()).getChildren();
        Assertions.assertThat(checkInteger(children, "intValue", intValuePair.getRight())).isTrue();
        Assertions.assertThat(checkInteger(children, "intObject", intObjectPair.getRight())).isTrue();
        Assertions.assertThat(checkBoolean(children, "booleanValue", booleanValuePair.getRight())).isTrue();
        Assertions.assertThat(checkBoolean(children, "booleanObject", booleanObjectPair.getRight())).isTrue();
        Assertions.assertThat(checkCharacter(children, "charValue", charValuePair.getRight())).isTrue();
        Assertions.assertThat(checkCharacter(children, "characterObject", characterObjectPair.getRight())).isTrue();
        Assertions.assertThat(checkString(children, "stringObject", stringObjectPair.getRight())).isTrue();
        Assertions.assertThat(checkCustom(children, "custom", customPair.getRight())).isTrue();
        Assertions.assertThat(checkIntSet(children, "intSet", intSetPair.getRight())).isTrue();
        Assertions.assertThat(checkIntList(children, "intList", intListPair.getRight())).isTrue();
        Assertions.assertThat(checkBooleanList(children, "booleanList", booleanListPair.getRight())).isTrue();
        Assertions.assertThat(checkCharacterSet(children, "characterSet", characterSetPair.getRight())).isTrue();
        Assertions.assertThat(checkCharacterList(children, "characterList", characterListPair.getRight())).isTrue();
        Assertions.assertThat(checkStringSet(children, "stringSet", stringSetPair.getRight())).isTrue();
        Assertions.assertThat(checkStringList(children, "stringList", stringListPair.getRight())).isTrue();
        Assertions.assertThat(checkCustomSet(children, "customSet", customSetPair.getRight())).isTrue();
        Assertions.assertThat(checkCustomList(children, "customList", customListPair.getRight())).isTrue();
        Assertions.assertThat(checkStringByInteger(children, "stringByInteger", stringByIntegerPair.getRight())).isTrue();
        Assertions.assertThat(checkCustomByInteger(children, "customByInteger", customNyInteger.getRight())).isTrue();
        Assertions.assertThat(checkIntegerByCustom(children, "integerByCustom", integerByCustomPair.getRight())).isTrue();
    }

    private ObjectNode getClassSerializedData(Class<?> clazz) throws Exception {
        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(Integer.class, Boolean.class, Character.class, InnerTestClass.class, String.class)
                .build();
        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class, InnerTestClass.class)
                .setValueArgumentsTypes(Integer.class, String.class, InnerTestClass.class)
                .build();
        Processor<ClassContext> classProcessor = UClassSerialization.createProcessor(
                new SKSimpleChecker<Class<?>>(int.class, Integer.class, boolean.class, Boolean.class, char.class,
                                              Character.class, String.class),
                new SKSimpleChecker<String>("InnerTestClass"),
                new AnnotationExtractor(),
                collectionTypeChecker,
                mapTypeChecker
        );
        ClassContext classContext = UClassSerialization.createClassContext(
                new SKCollector(),
                new SKContextStateCareTaker<>()
        );
        classContext.getContextStateCareTaker().push(new SKClassContextStateMemento(
                clazz,
                new AnnotationExtractor()
        ));
        classProcessor.handle(classContext);

        return (ObjectNode) classContext.getCollector().detachNode();
    }

    private boolean checkClassPart(ObjectNode node){
        Optional<Node> maybeClassPart = node.getChild(USKCollectorPath.DEFAULT_CLASS_PART_PATH);
        if (maybeClassPart.isPresent()){
            ObjectNode classPart = (ObjectNode) maybeClassPart.get();
            Map<String, Node> children = classPart.getChildren();
            boolean hasName = false;
            boolean hasModifiers = false;
            if (children.containsKey("name") && children.get("name").is(StringNode.ei())){
                StringNode nameNode = (StringNode) children.get("name");
                hasName = nameNode.getValue().equals("TestClass");
            }
            if (children.containsKey("modifiers") && children.get("modifiers").is(NumberNode.ei())){
                NumberNode modifiersNode = (NumberNode) children.get("modifiers");
                hasModifiers = modifiersNode.getValue().equals(10);
            }

            return hasName && hasModifiers;
        }

        return false;
    }

    private boolean checkInteger(Map<String, Node> children, String name, Integer value){
        if (children.containsKey(name) && children.get(name).is(NumberNode.ei())){
            NumberNode numberNode = (NumberNode) children.get(name);
            return numberNode.getValue().equals(value);
        }
        return false;
    }

    private boolean checkBoolean(Map<String, Node> children, String name, Boolean value){
        if (children.containsKey(name) && children.get(name).is(BooleanNode.ei())){
            BooleanNode booleanNode = (BooleanNode) children.get(name);
            return booleanNode.getValue().equals(value);
        }
        return false;
    }

    private boolean checkCharacter(Map<String, Node> children, String name, Character value){
        if (children.containsKey(name) && children.get(name).is(CharacterNode.ei())){
            CharacterNode characterNode = (CharacterNode) children.get(name);
            return characterNode.getValue().equals(value);
        }
        return false;
    }

    private boolean checkString(Map<String, Node> children, String name, String value){
        if (children.containsKey(name) && children.get(name).is(StringNode.ei())){
            StringNode stringNode = (StringNode) children.get(name);
            return stringNode.getValue().equals(value);
        }
        return false;
    }

    private boolean checkCustom(Map<String, Node> children, String name, Integer intValue){
        if (children.containsKey(name) && children.get(name).is(ObjectNode.ei())){
            Optional<Number> maybeIntValue = extractFromInnerTestNode((ObjectNode) children.get(name));
            if (maybeIntValue.isPresent()){
                return maybeIntValue.get().equals(intValue);
            }
        }

        return false;
    }

    private boolean checkIntSet(Map<String, Node> children, String name, Set<Integer> intSet){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashSet<Integer> buffer = new HashSet<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(NumberNode.ei())){
                    buffer.add((Integer) ((NumberNode) node).getValue());
                } else {
                    return false;
                }
            }

            return intSet.equals(buffer);
        }

        return false;
    }

    private boolean checkIntList(Map<String, Node> children, String name, List<Integer> intList){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            List<Integer> buffer = new ArrayList<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(NumberNode.ei())){
                    buffer.add((Integer) ((NumberNode) node).getValue());
                } else {
                    return false;
                }
            }

            return intList.equals(buffer);
        }

        return false;
    }

    private boolean checkBooleanList(Map<String, Node> children, String name, List<Boolean> booleanList){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            List<Boolean> buffer = new ArrayList<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(BooleanNode.ei())){
                    buffer.add(((BooleanNode) node).getValue());
                } else {
                    return false;
                }
            }

            return booleanList.equals(buffer);
        }

        return false;
    }

    private boolean checkCharacterSet(Map<String, Node> children, String name, Set<Character> characterSet){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashSet<Character> buffer = new HashSet<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(CharacterNode.ei())){
                    buffer.add(((CharacterNode) node).getValue());
                } else {
                    return false;
                }
            }

            return characterSet.equals(buffer);
        }

        return false;
    }

    private boolean checkCharacterList(Map<String, Node> children, String name, List<Character> characterList){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            List<Character> buffer = new ArrayList<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(CharacterNode.ei())){
                    buffer.add(((CharacterNode) node).getValue());
                } else {
                    return false;
                }
            }

            return characterList.equals(buffer);
        }

        return false;
    }

    private boolean checkStringSet(Map<String, Node> children, String name, Set<String> stringSet){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashSet<String> buffer = new HashSet<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(StringNode.ei())){
                    buffer.add(((StringNode) node).getValue());
                } else {
                    return false;
                }
            }

            return stringSet.equals(buffer);
        }

        return false;
    }

    private boolean checkStringList(Map<String, Node> children, String name, List<String> stringList){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            List<String> buffer = new ArrayList<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(StringNode.ei())){
                    buffer.add(((StringNode) node).getValue());
                } else {
                    return false;
                }
            }

            return stringList.equals(buffer);
        }

        return false;
    }

    private boolean checkCustomSet(Map<String, Node> children, String name, Set<Integer> customSet){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashSet<Integer> buffer = new HashSet<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(ObjectNode.ei())){
                    Optional<Number> maybeIntValue = extractFromInnerTestNode((ObjectNode) node);
                    if (maybeIntValue.isPresent()){
                        buffer.add((Integer) maybeIntValue.get());
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            return customSet.equals(buffer);
        }

        return false;
    }

    private boolean checkCustomList(Map<String, Node> children, String name, List<Integer> customList){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            List<Integer> buffer = new ArrayList<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(ObjectNode.ei())){
                    Optional<Number> maybeIntValue = extractFromInnerTestNode((ObjectNode) node);
                    if (maybeIntValue.isPresent()){
                        buffer.add((Integer) maybeIntValue.get());
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            return customList.equals(buffer);
        }

        return false;
    }

    private boolean checkStringByInteger(Map<String, Node> children, String name, Map<Integer, String> stringByInteger){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashMap<Integer, String> buffer = new HashMap<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(ObjectNode.ei())){
                    Map<String, Node> chs = ((ObjectNode) node).getChildren();
                    if (chs.containsKey("key") &&
                        chs.get("key").is(NumberNode.ei()) &&
                        chs.containsKey("value") &&
                        chs.get("value").is(StringNode.ei())){
                        buffer.put((Integer) ((NumberNode) chs.get("key")).getValue(), ((StringNode) chs.get("value")).getValue());
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            return stringByInteger.equals(buffer);
        }

        return false;
    }

    private boolean checkCustomByInteger(Map<String, Node> children, String name, Map<Integer, Integer> customByInteger){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashMap<Integer, Integer> buffer = new HashMap<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(ObjectNode.ei())){
                    Map<String, Node> chs = ((ObjectNode) node).getChildren();
                    if (chs.containsKey("key") &&
                            chs.get("key").is(NumberNode.ei()) &&
                            chs.containsKey("value") &&
                            chs.get("value").is(ObjectNode.ei())){

                        Integer key = (Integer) ((NumberNode) chs.get("key")).getValue();
                        Optional<Number> maybeIntValue = extractFromInnerTestNode((ObjectNode) chs.get("value"));
                        if (maybeIntValue.isPresent()){
                            buffer.put(key, (Integer) maybeIntValue.get());
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            return customByInteger.equals(buffer);
        }

        return false;
    }

    private boolean checkIntegerByCustom(Map<String, Node> children, String name, Map<Integer, Integer> integerByCustom){
        if (children.containsKey(name) && children.get(name).is(ArrayNode.ei())){
            HashMap<Integer, Integer> buffer = new HashMap<>();
            List<Node> collection = ((ArrayNode) children.get(name)).getChildren();
            for (Node node : collection) {
                if (node.is(ObjectNode.ei())){
                    Map<String, Node> chs = ((ObjectNode) node).getChildren();
                    if (chs.containsKey("key") &&
                            chs.get("key").is(ObjectNode.ei()) &&
                            chs.containsKey("value") &&
                            chs.get("value").is(NumberNode.ei())){

                        Integer value = (Integer) ((NumberNode) chs.get("value")).getValue();
                        Optional<Number> maybeIntValue = extractFromInnerTestNode((ObjectNode) chs.get("key"));
                        if (maybeIntValue.isPresent()){
                            buffer.put((Integer) maybeIntValue.get(), value);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            return integerByCustom.equals(buffer);
        }

        return false;
    }

    private Optional<Number> extractFromInnerTestNode(ObjectNode node){
        Optional<Node> maybeMembers = node.getChild(USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH);
        if (maybeMembers.isPresent()){
            Map<String, Node> children = ((ObjectNode) maybeMembers.get()).getChildren();
            if (children.containsKey("intValue") && children.get("intValue").is(NumberNode.ei())){
                return Optional.of(((NumberNode) children.get("intValue")).getValue());
            }
        }

        return Optional.empty();
    }

    @SkeletonClass(name = "TestClass")
    private static class TestClass{

        @SkeletonMember
        private int intValue;

        @SkeletonMember
        private Integer intObject;

        @SkeletonMember
        private boolean booleanValue;

        @SkeletonMember
        private Boolean booleanObject;

        @SkeletonMember
        private char charValue;

        @SkeletonMember
        private Character characterObject;

        @SkeletonMember
        private String stringObject;

        @SkeletonMember(name = "InnerTestClass")
        private InnerTestClass custom;

        @SkeletonMember
        private Set<Integer> intSet;

        @SkeletonMember
        private List<Integer> intList;

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

        @SkeletonMember
        private Set<InnerTestClass> customSet;

        @SkeletonMember
        private List<InnerTestClass> customList;

        @SkeletonMember
        private Map<Integer, String> stringByInteger;

        @SkeletonMember
        private Map<Integer, InnerTestClass> customByInteger;

        @SkeletonMember
        private Map<InnerTestClass, Integer> integerByCustom;

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public void setIntObject(Integer intObject) {
            this.intObject = intObject;
        }

        public void setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public void setBooleanObject(Boolean booleanObject) {
            this.booleanObject = booleanObject;
        }

        public void setCharValue(char charValue) {
            this.charValue = charValue;
        }

        public void setCharacterObject(Character characterObject) {
            this.characterObject = characterObject;
        }

        public void setStringObject(String stringObject) {
            this.stringObject = stringObject;
        }

        public void setCustom(InnerTestClass custom) {
            this.custom = custom;
        }

        public void setIntSet(Set<Integer> intSet) {
            this.intSet = intSet;
        }

        public void setIntList(List<Integer> intList) {
            this.intList = intList;
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

        public void setCustomSet(Set<InnerTestClass> customSet) {
            this.customSet = customSet;
        }

        public void setCustomList(List<InnerTestClass> customList) {
            this.customList = customList;
        }

        public void setStringByInteger(Map<Integer, String> stringByInteger) {
            this.stringByInteger = stringByInteger;
        }

        public void setCustomByInteger(Map<Integer, InnerTestClass> customByInteger) {
            this.customByInteger = customByInteger;
        }

        public void setIntegerByCustom(Map<InnerTestClass, Integer> integerByCustom) {
            this.integerByCustom = integerByCustom;
        }
    }

    @SkeletonClass(name = "InnerTestClass")
    private static class InnerTestClass{

        @SkeletonMember
        private int intValue;

        public InnerTestClass(int intValue) {
            this.intValue = intValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerTestClass that = (InnerTestClass) o;
            return intValue == that.intValue;
        }

        @Override
        public int hashCode() {
            return Objects.hash(intValue);
        }
    }
}
