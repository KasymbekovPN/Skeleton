package org.KasymbekovPN.Skeleton.lib.context.ids;

import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SKSimpleContextIds: Testing of:")
public class SKSimpleContextIdsTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        "task_common",
                        new String[]{"handler_1", "handler_2"},
                        new ArrayList<String>(){{add("task_common");}},
                        new ArrayList<String>(){{
                            add("handler_1");
                            add("handler_2");
                        }},
                        true
                },
                {
                        "task_common",
                        new String[]{"handler_1", "handler_2"},
                        new ArrayList<String>(){{add("task_common1");}},
                        new ArrayList<String>(){{
                            add("handler_1");
                            add("handler_2");
                        }},
                        false
                },
                {
                        "task_common",
                        new String[]{"handler_1", "handler_2", "handler_3"},
                        new ArrayList<String>(){{add("task_common");}},
                        new ArrayList<String>(){{
                            add("handler_1");
                            add("handler_2");
                        }},
                        false
                },
                {
                        "task_common",
                        new String[]{"handler_1", "handler_2"},
                        new ArrayList<String>(){{add("task_common");}},
                        new ArrayList<String>(){{
                            add("handler_1");
                            add("handler_2");
                            add("handler_3");
                        }},
                        false
                },
        };
    }

    @DisplayName("iteration by task and handler IDs")
    @ParameterizedTest
    @MethodSource("getTestData")
    void test(String originalTaskId,
              String[] originalHandlerIds,
              List<String> verificationTaskId,
              List<String> verificationHandlerIds,
              boolean result){

        SKSimpleContextIds contextIds = new SKSimpleContextIds(originalTaskId, originalHandlerIds);

        Iterator<String> originalTaskIterator = contextIds.taskIterator();
        ArrayList<String> taskIds = new ArrayList<>();
        while (originalTaskIterator.hasNext()){
            taskIds.add(originalTaskIterator.next());
        }

        Iterator<String> originalHandlerIterator = contextIds.handlerIterator();
        ArrayList<String> handlerIds = new ArrayList<>();
        while (originalHandlerIterator.hasNext()){
            handlerIds.add(originalHandlerIterator.next());
        }

        boolean res = taskIds.equals(verificationTaskId);
        res &= handlerIds.equals(verificationHandlerIds);

        assertThat(res).isEqualTo(result);
    }
}
