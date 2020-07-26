package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

public class CharacterWritingHandlerTest {
    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new HashMap<String, Character>(){{
                            put("object1", 'a');
                            put("object2", 'b');
                            put("object3", 'c');
                            put("object4", 'd');
                            put("object5", 'e');
                        }}
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void testLevel0(Map<String, Character> objects) throws Exception {
        Collector collector = Utils.createCollector();
        Utils.fillCollectorWithCharacter(collector, objects);

        CollectorProcess process = Utils.createCollectorWritingProcess();
        //< !!! remake
//        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.ei());
//        new CharacterWritingHandler(process, CharacterNode.ei());
//        collector.apply(process);
//
//        assertThat(new SkeletonCharacterWritingHandlerTestData(objects).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
