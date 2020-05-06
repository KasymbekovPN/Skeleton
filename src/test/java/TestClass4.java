import org.KasymbekovPN.Skeleton.annotation.SkeletonArguments;
import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.annotation.SkeletonConstructor;
import org.KasymbekovPN.Skeleton.annotation.SkeletonMember;

import java.util.Map;
import java.util.Set;

//@SkeletonClass(
//        includeByName = {"map"}
//)
@SkeletonClass
@SkeletonConstructor(
        constructor = {
                @SkeletonArguments(arguments = {"map"}),
                @SkeletonArguments(arguments = {"map", "set"})
        }
)
public class TestClass4 {

    @SkeletonMember
    private int intMember;

    @SkeletonMember
    private Map<Integer, String> map;

    @SkeletonMember
    private static Integer integerObject;

    @SkeletonMember
    public Set<Integer> set;
}
