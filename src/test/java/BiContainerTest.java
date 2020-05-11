import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

@SkeletonClass(
        includeByName = {"map", "list"},
        includeByModifiers = Modifier.PRIVATE,
        excludeByName = {"list", "map1"}
)
public class BiContainerTest {

    private static Map<String, Integer> map;
    private Map<String, Integer> map1;
    private List<Integer> list;
    private List<Boolean> boolList;
}
