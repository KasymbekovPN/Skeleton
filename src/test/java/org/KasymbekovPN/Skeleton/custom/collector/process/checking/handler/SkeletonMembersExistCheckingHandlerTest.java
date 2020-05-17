package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SkeletonMembersExistCheckingHandlerTest {

    private static Object[][] getTestData(){
        return new Object[][]{
                {
                    new ArrayList<>(){{
                        add("members");
                    }},
                    new ArrayList<>(){{
                        add("set");
                        add("map");
                    }},
                    new ArrayList<>(){{
                        add("members");
                    }},
                    new ArrayList<>(){{
                        add("set");
                        add("map");
                    }},
                    CollectorCheckingResult.INCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("members");
                        }},
                        new ArrayList<>(){{
                            add("set");
                            add("map");
                        }},
                        new ArrayList<>(){{
                            add("members");
                            add("sub");
                        }},
                        new ArrayList<>(){{
                            add("set");
                            add("map");
                        }},
                        CollectorCheckingResult.EXCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("members");
                            add("sub");
                        }},
                        new ArrayList<>(){{
                            add("set");
                            add("map");
                        }},
                        new ArrayList<>(){{
                            add("members");
                        }},
                        new ArrayList<>(){{
                            add("set");
                            add("map");
                        }},
                        CollectorCheckingResult.EXCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("members");
                        }},
                        new ArrayList<>(){{
                            add("set");
                            add("map");
                        }},
                        new ArrayList<>(){{
                            add("members");
                        }},
                        new ArrayList<>(){{
                            add("set");
                        }},
                        CollectorCheckingResult.INCLUDE
                },
                {
                        new ArrayList<>(){{
                            add("members");
                        }},
                        new ArrayList<>(){{
                            add("map");
                        }},
                        new ArrayList<>(){{
                            add("members");
                        }},
                        new ArrayList<>(){{
                            add("set");
                            add("map");
                        }},
                        CollectorCheckingResult.EXCLUDE
                },
        };
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void test(List<String> initPath,
              List<String> initMembers,
              List<String> checkPath,
              List<String> checkMembers,
              CollectorCheckingResult result){

        TestCollectorCheckingProcess process = new TestCollectorCheckingProcess();
        SkeletonMembersExistCheckingHandler handler = new SkeletonMembersExistCheckingHandler(
                process, SkeletonObjectNode.class, checkMembers, checkPath);
        handler.handle(create(initPath, initMembers));

        assertThat(process.getResult()).isEqualTo(result);

    }

    private SkeletonObjectNode create(List<String> path, List<String> members){

        SkeletonObjectNode result = new SkeletonObjectNode(null);
        SkeletonObjectNode buffer = SkeletonNodeObjectCreator.create(path, result);

        for (String member : members) {
            buffer.getChildren().put(member, new SkeletonObjectNode(buffer));
        }

        return result;
    }
}
