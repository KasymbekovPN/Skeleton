import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;

import java.util.Map;
import java.util.Set;

@SkeletonClass
public class TestClass4 {

    @SkeletonMember
    private int intMember;

    @SkeletonMember
    private Map<Integer, Integer> map;

    @SkeletonMember
    private static Integer integerObject;

    @SkeletonMember
    public Set<Integer> set;
}
