package org.KasymbekovPN.Skeleton.custom.filter.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

//< !!! assert
@DisplayName("IgnoreStringFilter. Testing of:")
public class IgnoreStringFilterTest {

    @Test
    void test(){

        HashSet<String> ignoredStrings = new HashSet<>() {{
            add("HELLO");
            add("WORLD");
        }};

        ArrayDeque<String> deque = new ArrayDeque<>() {{
            add("hello");
            add("HELLO");
            add("world");
            add("WORLD");
            add("!!!");
        }};

        IgnoreStringFilter filter = new IgnoreStringFilter(ignoredStrings);

        System.out.println(deque);
        Deque<String> filteredDeque = filter.filter(deque);
        System.out.println(filteredDeque);
    }
}
