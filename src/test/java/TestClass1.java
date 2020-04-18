import org.KasymbekovPN.Skeleton.annotation.Skeleton;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.*;

@Skeleton
public class TestClass1 {

    @Skeleton
    private String stringObject;

    @Skeleton
    private Byte byteObject;

    @Skeleton
    private Short shortObject;

    @Skeleton
    private Integer integerObject;

    @Skeleton
    private AtomicInteger atomicInteger;

    @Skeleton
    private Double doubleObject;

    @Skeleton
    private DoubleAccumulator doubleAccumulatorObject;

    @Skeleton
    private DoubleAdder doubleAdderObject;

    @Skeleton
    private Float floatObject;

    @Skeleton
    private Long longObject;

    @Skeleton
    private LongAccumulator longAccumulatorObject;

    @Skeleton
    private LongAdder longAdderObject;

    @Skeleton
    private AtomicLong atomicLongObject;

    @Skeleton
    private BigDecimal bigDecimalObject;

    @Skeleton
    private BigInteger bigIntegerObject;

    @Skeleton
    private int intPrimitive;
}
