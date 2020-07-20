package org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.Utils;
import org.KasymbekovPN.Skeleton.custom.collector.process.writing.handler.utils.dataChecker.SkeletonCharacterWritingHandlerTestData;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

        CollectorWritingProcess process = Utils.createCollectorWritingProcess();
        //<
//        new JsonObjectWritingCollectorProcessHandler(process, ObjectNode.class);
//        new CharacterWritingHandler(process, CharacterNode.class);
        collector.apply(process);

        assertThat(new SkeletonCharacterWritingHandlerTestData(objects).check(process.getBuffer().toString())).isEqualTo(true);
    }
}
