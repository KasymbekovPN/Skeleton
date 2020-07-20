package org.KasymbekovPN.Skeleton.custom.filter.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

//< !!! assert
@DisplayName("Testing of different classes: ")
public class StringFilterTest {

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

        PriorityStringFilter filter0 = new PriorityStringFilter();
        filter0.setPriorityString("!!!", 0)
                .setPriorityString("world", 1)
                .setPriorityString("hello", 2);

        IgnoreStringFilter filter1 = new IgnoreStringFilter(ignoredStrings);
        filter1.addFilter(filter0);



        System.out.println(deque);
        Deque<String> filteredDeque = filter1.filter(deque);
        System.out.println(filteredDeque);
    }
}
