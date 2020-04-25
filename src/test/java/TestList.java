import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;

import java.util.List;
import java.util.Set;

@SkeletonClass
public class TestList {

    @SkeletonClass
    protected List<Integer> integerList;

    @SkeletonClass
    private List<Double> doubleList;

    @SkeletonClass
    public  List<Object> objectList;

    @SkeletonClass
    protected Set<Integer> integerSet;

    @SkeletonClass
    private Set<Double> doubleSet;

    @SkeletonClass
    public  Set<Object> objectSet;

    @SkeletonClass
    Set<Boolean> booleanSet;
}
