import org.KasymbekovPN.Skeleton.annotation.Skeleton;

import java.util.Map;

@Skeleton(
        includeByName = {"map", "list"}
)
public class BiContainerTest {

    @Skeleton
    private Map<String, Integer> map;
}
