package org.KasymbekovPN.Skeleton.lib.processing.context.ids;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@DisplayName("SKMultiContextIds. Testing of:")
public class SKMultiContextIdsTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                        new MutablePair<ContextIds, ContextIds>(
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0"),
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0")
                        ),
                        new MutablePair<Map<EntityItem, ContextIds>, Map<EntityItem, ContextIds>>(
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }},
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }}
                        ),
                        true
                },
                {
                        new MutablePair<ContextIds, ContextIds>(
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0"),
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId1")
                        ),
                        new MutablePair<Map<EntityItem, ContextIds>, Map<EntityItem, ContextIds>>(
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }},
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }}
                        ),
                        false
                },
                {
                        new MutablePair<ContextIds, ContextIds>(
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0"),
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0")
                        ),
                        new MutablePair<Map<EntityItem, ContextIds>, Map<EntityItem, ContextIds>>(
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0", "objectHandlerId1"));
                                }},
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }}
                        ),
                        false
                },
                {
                        new MutablePair<ContextIds, ContextIds>(
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0"),
                                new SKSimpleContextIds("defaultTaskId", "defaultHandlerId0")
                        ),
                        new MutablePair<Map<EntityItem, ContextIds>, Map<EntityItem, ContextIds>>(
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }},
                                new HashMap<EntityItem, ContextIds>(){{
                                    put(ArrayNode.ei(), new SKSimpleContextIds("arrayTaskId", "arrayHandlerId0", "arrayHandlerId1"));
                                    put(ObjectNode.ei(), new SKSimpleContextIds("objectTaskId", "objectHandlerId0"));
                                }}
                        ),
                        false
                }
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(Pair<ContextIds, ContextIds> defaultPair,
              Pair<Map<EntityItem, ContextIds>, Map<EntityItem, ContextIds>> contextIdsPair,
              boolean result){

        SKMultiContextIds.Builder<NodeEI> builder = new SKMultiContextIds.Builder<>(defaultPair.getLeft());
        for (Map.Entry<EntityItem, ContextIds> entry : contextIdsPair.getLeft().entrySet()) {
            builder.add((NodeEI) entry.getKey(), entry.getValue());
        }

        SKMultiContextIds<NodeEI> multiContextIds = builder.build();

        Iterator<String> defaultTaskIterator = multiContextIds.taskIterator();
        Iterator<String> defaultHandlerIterator = multiContextIds.handlerIterator();
        Iterator<String> checkDefaultTaskIterator = defaultPair.getRight().taskIterator();
        Iterator<String> checkDefaultHandlerIterator = defaultPair.getRight().handlerIterator();

        boolean checkResult = isEquals(defaultTaskIterator, checkDefaultTaskIterator);
        checkResult &= isEquals(defaultHandlerIterator, checkDefaultHandlerIterator);

        for (Map.Entry<EntityItem, ContextIds> entry : contextIdsPair.getRight().entrySet()) {
            Iterator<String> checkTaskItr = entry.getValue().taskIterator();
            Iterator<String> checkHandlerItr = entry.getValue().handlerIterator();
            multiContextIds.setKey((NodeEI) entry.getKey());
            Iterator<String> taskItr = multiContextIds.taskIterator();
            Iterator<String> handlerItr = multiContextIds.handlerIterator();

            checkResult &= isEquals(taskItr, checkTaskItr);
            checkResult &= isEquals(handlerItr, checkHandlerItr);
        }

        Assertions.assertThat(checkResult).isEqualTo(result);
    }

    private boolean isEquals(Iterator<String> it1, Iterator<String> it2){

        boolean result = true;
        while (true){
            if (it1.hasNext() == it2.hasNext()){
                if (it1.hasNext()){
                    if (!it1.next().equals(it2.next())){
                        result = false;
                        break;
                    }
                } else {
                    break;
                }
            } else {
                result = false;
                break;
            }
        }

        return result;
    }
}
