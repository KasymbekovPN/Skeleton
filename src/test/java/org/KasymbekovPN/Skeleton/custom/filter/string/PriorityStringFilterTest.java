package org.KasymbekovPN.Skeleton.custom.filter.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

//< !!! assert
@DisplayName("PriorityStringFilter. Testing of:")
public class PriorityStringFilterTest {

    @Test
    void test() {

        ArrayDeque<String> deque = new ArrayDeque<>() {{
            add("hello");
            add("HELLO");
            add("world");
            add("WORLD");
            add("!!!");
        }};

        PriorityStringFilter filter = new PriorityStringFilter();
        filter.setPriorityString("!!!", 0)
                .setPriorityString("world", 1)
                .setPriorityString("hello", 2);

        System.out.println(deque);
        Deque<String> filteredDeque = filter.filter(deque);
        System.out.println(filteredDeque);
    }
}
