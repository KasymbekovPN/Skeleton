import org.KasymbekovPN.Skeleton.annotation.*;

import java.util.Map;
import java.util.Set;

//@SkeletonClass(
//        includeByName = {"map"}
//)
@SkeletonClass
@SkeletonConstructor(
        arguments = {
                @SkeletonArguments(arguments = {"map"}),
                @SkeletonArguments(arguments = {"map", "set"})
        }
)
@SkeletonMethodToString(
        arguments = {
                @SkeletonArguments(arguments = {"map", "set"}),
                @SkeletonArguments(arguments = {"set"})
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
