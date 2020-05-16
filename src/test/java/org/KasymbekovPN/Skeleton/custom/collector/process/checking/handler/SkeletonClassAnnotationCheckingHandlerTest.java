package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonNumberNodeSkeleton;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonStringNodeSkeleton;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Skeleton Class Annotation Checking Handler. Testing of:")
public class SkeletonClassAnnotationCheckingHandlerTest {

    private static final Logger log = LoggerFactory.getLogger(SkeletonClassAnnotationCheckingHandlerTest.class);
    private static final int SIGN_MASK = 0x80000000;
    private static final int NODE_MODIFIERS = 2;
    private static final int SUCCESS_MASK = NODE_MODIFIERS;
    private static final int FAIL_MASK = SIGN_MASK ^ (~SUCCESS_MASK);

    private static final List<String> INCLUDE_BY_NAME = new ArrayList<>(){{
        add("map");
        add("set");
    }};

    private static final List<String> EXCLUDE_BY_NAME = new ArrayList<>(){{
        add("map");
        add("list");
    }};

    private static final List<String> EMPTY_ARRAY = new ArrayList<>();

    private static final List<String> PATH = new ArrayList<>(){{add("annotation");}};

    private static final int RANGE_BEGIN = 0;
    private static final int RANGE_END = 4095;

    private static Object[][] getTestData(){
        return new Object[][]{
                {true,  SUCCESS_MASK, "map", -1,             -1,             EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true,  SUCCESS_MASK, "map", NODE_MODIFIERS, -1,             EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {false, FAIL_MASK,    "map", NODE_MODIFIERS, -1,             EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true,  SUCCESS_MASK, "map", -1            , NODE_MODIFIERS, EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {false, FAIL_MASK,    "map", -1            , NODE_MODIFIERS, EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true,  SUCCESS_MASK, "map", NODE_MODIFIERS,             NODE_MODIFIERS,             EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {true,  SUCCESS_MASK, "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS,             EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {true,  SUCCESS_MASK, "map", NODE_MODIFIERS,             NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {true,  SUCCESS_MASK, "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true,  SUCCESS_MASK, "map",  -1, -1, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {true,  SUCCESS_MASK, "map1", -1, -1, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true,  SUCCESS_MASK, "map", NODE_MODIFIERS, -1, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {false, FAIL_MASK,    "map", NODE_MODIFIERS, -1, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {true,  SUCCESS_MASK, "mmm", NODE_MODIFIERS, -1, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {false, FAIL_MASK,    "mmm", NODE_MODIFIERS, -1, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true,  SUCCESS_MASK, "map", -1, NODE_MODIFIERS, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {true,  SUCCESS_MASK, "mmm", -1, NODE_MODIFIERS, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {false, FAIL_MASK,    "map", -1, NODE_MODIFIERS, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {false, FAIL_MASK,    "mmm", -1, NODE_MODIFIERS, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.NONE},

                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , NODE_MODIFIERS            , INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS            , INCLUDE_BY_NAME, EMPTY_ARRAY, CollectorCheckingResult.EXCLUDE},

                {true, SUCCESS_MASK,  "map", -1, -1, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "mmm", -1, -1, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},

                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, -1, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , -1, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, -1, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , -1, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},

                {true, SUCCESS_MASK,  "mmm", -1, NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "mmm", -1, NODE_MODIFIERS,             EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", -1, NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", -1, NODE_MODIFIERS            , EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},

                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , NODE_MODIFIERS            , EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS            , EMPTY_ARRAY, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},

                {true, SUCCESS_MASK,  "mmm", -1, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "set", -1, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "list", -1, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", -1, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},

                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "set", NODE_MODIFIERS & FAIL_MASK, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "set", NODE_MODIFIERS            , -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "list", NODE_MODIFIERS & FAIL_MASK, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "list", NODE_MODIFIERS            , -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , -1, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},

                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.NONE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "mmm", NODE_MODIFIERS            , NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "set", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "set", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.INCLUDE},
                {true, SUCCESS_MASK,  "set", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "set", NODE_MODIFIERS            , NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS & FAIL_MASK, INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS & FAIL_MASK, NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE},
                {true, SUCCESS_MASK,  "map", NODE_MODIFIERS            , NODE_MODIFIERS            , INCLUDE_BY_NAME, EXCLUDE_BY_NAME, CollectorCheckingResult.EXCLUDE}
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(boolean isOr,
              int mask,
              String name,
              int includeByModifiers,
              int excludeByModifiers,
              List<String> includeByName,
              List<String> excludeByName,
              CollectorCheckingResult result){

        for (int i = RANGE_BEGIN; i < RANGE_END; i++) {
            int modifiers = isOr
                    ? i | mask
                    : i & mask;
            TestCollectorCheckingProcess process = new TestCollectorCheckingProcess();
            SkeletonClassAnnotationCheckingHandler handler = new SkeletonClassAnnotationCheckingHandler(
                    modifiers,
                    name,
                    process,
                    SkeletonObjectNode.class,
                    PATH);

            handler.handle(
                    create(includeByModifiers, excludeByModifiers, includeByName, excludeByName)
            );

            assertThat(process.getResult()).isEqualTo(result);
        }
    }

    private SkeletonObjectNode create(int includeModifiers,
                                      int excludeModifiers,
                                      List<String> includeByName,
                                      List<String> excludeByName){

        SkeletonObjectNode result = new SkeletonObjectNode(null);
        SkeletonObjectNode buffer = result;
        for (String pathItem : PATH) {
            buffer.getChildren().put(pathItem, new SkeletonObjectNode(buffer));
            buffer = (SkeletonObjectNode) buffer.getChildren().get(pathItem);
        }

        buffer.getChildren().put("includeByModifiers",new SkeletonNumberNodeSkeleton(buffer, includeModifiers));
        buffer.getChildren().put("excludeByModifiers", new SkeletonNumberNodeSkeleton(buffer, excludeModifiers));

        SkeletonArrayNode includeByNameArr = new SkeletonArrayNode(buffer);
        for (String name : includeByName) {
            includeByNameArr.getChildren().add(new SkeletonStringNodeSkeleton(includeByNameArr, name));
        }

        SkeletonArrayNode excludeByNameArr = new SkeletonArrayNode(buffer);
        for (String name : excludeByName) {
            excludeByNameArr.getChildren().add(new SkeletonStringNodeSkeleton(excludeByNameArr, name));
        }

        buffer.getChildren().put("includeByName", includeByNameArr);
        buffer.getChildren().put("excludeByName", excludeByNameArr);

        return result;
    }
}
