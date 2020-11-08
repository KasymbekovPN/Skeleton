package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                        )
//        @SkeletonMember
//        private Map<Integer, String> stringByInteger;
//
//        @SkeletonMember
//        private Map<Integer, InnerTestClass> customByInteger;
//
//        @SkeletonMember
//        private Map<InnerTestClass, Integer> integerByCustom;
                }
        };
    }

    @Test

    void test(){
//        @SkeletonMember
//        private int intValue;
//
//        @SkeletonMember
//        private Integer intObject;
//
//        @SkeletonMember
//        private boolean booleanValue;
//
//        @SkeletonMember
//        private Boolean booleanObject;
//
//        @SkeletonMember
//        private char charValue;
//
//        @SkeletonMember
//        private Character characterObject;
//
//        @SkeletonMember
//        private String stringObject;
//
//        @SkeletonMember(name = "InnerTestClass")
//        private InnerTestClass custom;
//
//        @SkeletonMember
//        private Set<Integer> intSet;
//
//        @SkeletonMember
//        private List<Integer> intList;
//
//        @SkeletonMember
//        private List<Boolean> booleanList;
//
//        @SkeletonMember
//        private Set<Character> characterSet;
//
//        @SkeletonMember
//        private List<Character> characterList;
//
//        @SkeletonMember
//        private Set<String> stringSet;
//
//        @SkeletonMember
//        private List<String> stringList;
//
//        @SkeletonMember
//        private Set<InnerTestClass> customSet;
//
//        @SkeletonMember
//        private List<InnerTestClass> customList;
//
//        @SkeletonMember
//        private Map<Integer, String> stringByInteger;
//
//        @SkeletonMember
//        private Map<Integer, InnerTestClass> customByInteger;
//
//        @SkeletonMember
//        private Map<InnerTestClass, Integer> integerByCustom;
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
