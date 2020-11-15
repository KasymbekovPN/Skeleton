package org.KasymbekovPN.Skeleton.lib.iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@DisplayName("SKDecrementedCharIterator. Testing of:")
public class SKDecrementedCharIteratorTest {

    private static Object[][] getHasNextTestData(){
        return new Object[][]{
                {"hello, world!!!", 15, true},
                {"hello, world!!!", 20, false},
                {"hello, world!!!", 2, false}
        };
    }

    private static Object[][] getNextTestData(){
        return new Object[][]{
                {"hello", new Character[]{'h','e','l','l','o'}, true},
                {"hello", new Character[]{'h','e','l','l','o','!'}, false},
                {"hello", new Character[]{'H','e','l','l','o'}, false},
                {"hello", new Character[]{'h','e'}, false}
        };
    }

    private static Object[][] getDecMethodTestData(){
        return new Object[][]{
                {
                        "hello",
                        new HashMap<Integer, Character>(){{
                            put(1, 'o');
                            put(2, 'l');
                            put(3, 'l');
                            put(4, 'e');
                            put(5, 'h');
                        }},
                        true
                },
                {
                        "hello",
                        new HashMap<Integer, Character>(){{
                            put(1, 'o');
                            put(2, 'l');
                            put(3, 'l');
                            put(4, 'e');
                            put(5, 'H');
                        }},
                        false
                }
        };
    }

    @DisplayName("hasNext method - result")
    @ParameterizedTest
    @MethodSource("getHasNextTestData")
    void testHasNext_result(String line, int size, boolean success){

        SKDecrementedCharIterator itr = new SKDecrementedCharIterator(line);
        int counter = 0;
        while (itr.hasNext()){
            counter++;
            itr.next();
        }

        Assertions.assertThat(counter == size).isEqualTo(success);
    }

    @DisplayName("next method - result")
    @ParameterizedTest
    @MethodSource("getNextTestData")
    void testNextMethod_result(String line, Character[] array, boolean success){

        SKDecrementedCharIterator itr = new SKDecrementedCharIterator(line);
        int size = array.length;
        int counter = 0;
        boolean s = true;
        while (itr.hasNext()){
            Character next = itr.next();
            if (size <= counter){
                s = false;
            }
            else {
                if (!next.equals(array[counter])){
                    s = false;
                }
            }
            counter++;
        }

        s &= counter == size;
        Assertions.assertThat(s).isEqualTo(success);
    }

    @DisplayName("next method - throw")
    @Test
    void testNextMethod_throw(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            SKDecrementedCharIterator itr = new SKDecrementedCharIterator("");
            itr.next();
        });
        Assertions.assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("dec method")
    @ParameterizedTest
    @MethodSource("getDecMethodTestData")
    void testDecMethod(String line, Map<Integer, Character> checkMap, boolean success){

        boolean s = true;
        for (Map.Entry<Integer, Character> entry : checkMap.entrySet()) {
            Integer shift = entry.getKey();
            Character ch = entry.getValue();

            SKDecrementedCharIterator itr = new SKDecrementedCharIterator(line);
            while (itr.hasNext()){
                itr.next();
            }

            for (int i = 0; i < shift; i++) {
                itr.dec();
            }
            s &= ch.equals(itr.next());
        }

        Assertions.assertThat(s).isEqualTo(success);
    }
}
