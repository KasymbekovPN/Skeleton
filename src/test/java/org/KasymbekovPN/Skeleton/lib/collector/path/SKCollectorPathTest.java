package org.KasymbekovPN.Skeleton.lib.collector.path;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

@DisplayName("SKCollectorPath. Testing of:")
public class SKCollectorPathTest {

    private static Object[][] setGetPathTestData(){
        return new Object[][]{
                {
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        true,
                        new ArrayList<>(Arrays.asList("a", "b", "c")),
                        new ArrayList<>(Arrays.asList("a", "b", "c")),
                        true
                },
                {
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        new ArrayList<>(Arrays.asList("1", "2", "3", "4")),
                        false,
                        new ArrayList<>(Arrays.asList("a", "b", "c")),
                        new ArrayList<>(Arrays.asList("a", "b", "c")),
                        true
                },
                {
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        true,
                        new ArrayList<>(Arrays.asList("a", "b", "c")),
                        new ArrayList<>(Arrays.asList("a", "b", "c", "d")),
                        false
                },
                {
                        new ArrayList<>(Arrays.asList("1", "2", "3")),
                        new ArrayList<>(Arrays.asList("1", "2", "3", "4")),
                        false,
                        new ArrayList<>(Arrays.asList("a", "b", "c")),
                        new ArrayList<>(Arrays.asList("a", "b", "c", "d")),
                        false
                }
        };
    }

    private static Object[][] setGetEiTestData(){
        return new Object[][]{
                {
                        NodeEI.objectEI(),
                        NodeEI.objectEI(),
                        true,
                        NodeEI.arrayEI(),
                        NodeEI.arrayEI(),
                        true
                },
                {
                        NodeEI.objectEI(),
                        NodeEI.arrayEI(),
                        false,
                        NodeEI.arrayEI(),
                        NodeEI.arrayEI(),
                        true
                },
                {
                        NodeEI.objectEI(),
                        NodeEI.objectEI(),
                        true,
                        NodeEI.arrayEI(),
                        NodeEI.objectEI(),
                        false
                },
                {
                        NodeEI.objectEI(),
                        NodeEI.arrayEI(),
                        false,
                        NodeEI.arrayEI(),
                        NodeEI.objectEI(),
                        false
                }
        };
    }

    private static Object[][] getIteratorTestData(){
        return new Object[][]{
                {NodeEI.arrayEI(), new ArrayList<>(Collections.singletonList("1"))},
                {NodeEI.booleanEI(), new ArrayList<>(Arrays.asList("1", "2"))},
                {NodeEI.characterEI(), new ArrayList<>(Arrays.asList("1","2","3"))},
                {NodeEI.numberEI(), new ArrayList<>(Arrays.asList("1","2","3","4"))},
                {NodeEI.objectEI(), new ArrayList<>(Arrays.asList("1","2","3","4","5"))},
                {NodeEI.numberEI(), new ArrayList<>(Arrays.asList("1","2","3","4","5","6"))}
        };
    }

    @DisplayName("set/getPath method")
    @ParameterizedTest
    @MethodSource("setGetPathTestData")
    void testSetGetPathMethod(List<String> init0,
                              List<String> check0,
                              boolean result0,
                              List<String> init1,
                              List<String> check1,
                              boolean result1){
        SKCollectorPath collectorPath = new SKCollectorPath(init0, NodeEI.objectEI());
        Assertions.assertThat(collectorPath.getPath().equals(check0)).isEqualTo(result0);

        collectorPath.setPath(init1);
        Assertions.assertThat(collectorPath.getPath().equals(check1)).isEqualTo(result1);
    }

    @DisplayName("set/getEi method")
    @ParameterizedTest
    @MethodSource("setGetEiTestData")
    void testSetGetEiMethod(EntityItem init0,
                            EntityItem check0,
                            boolean result0,
                            EntityItem init1,
                            EntityItem check1,
                            boolean result1
                            ){
        SKCollectorPath collectorPath = new SKCollectorPath(new ArrayList<>(), init0);
        Assertions.assertThat(collectorPath.getEi().equals(check0)).isEqualTo(result0);

        collectorPath.setEi(init1);
        Assertions.assertThat(collectorPath.getEi().equals(check1)).isEqualTo(result1);
    }

    @DisplayName("iterator method")
    @ParameterizedTest
    @MethodSource("getIteratorTestData")
    void testIteratorMethod(EntityItem ei, List<String> path){
        SKCollectorPath collectorPath = new SKCollectorPath(path, ei);
        Iterator<Pair<String, EntityItem>> iterator = collectorPath.iterator();

        ArrayList<String> strings = new ArrayList<>();
        ArrayList<EntityItem> entityItems = new ArrayList<>();
        while (iterator.hasNext()){
            Pair<String, EntityItem> next = iterator.next();
            strings.add(next.getLeft());
            entityItems.add(next.getRight());
        }

        Assertions.assertThat(strings).isEqualTo(path);

        for (int i = 0; i < entityItems.size(); i++) {
            EntityItem entityItem = entityItems.size() - 1 != i ? NodeEI.objectEI() : ei;
            Assertions.assertThat(entityItems.get(i)).isEqualTo(entityItem);
        }
    }
}
