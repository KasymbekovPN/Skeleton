import org.KasymbekovPN.Skeleton.annotation.Skeleton;

import java.util.List;
import java.util.Set;

@Skeleton
public class TestList {

    @Skeleton
    protected List<Integer> integerList;

    @Skeleton
    private List<Double> doubleList;

    @Skeleton
    public  List<Object> objectList;

    @Skeleton
    protected Set<Integer> integerSet;

    @Skeleton
    private Set<Double> doubleSet;

    @Skeleton
    public  Set<Object> objectSet;

    @Skeleton
    Set<Boolean> booleanSet;
}
