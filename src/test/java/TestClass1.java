import org.KasymbekovPN.Skeleton.annotation.SkeletonClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.*;

@SkeletonClass
public class TestClass1 {

    @SkeletonClass
    private String stringObject;

    @SkeletonClass
    private Byte byteObject;

    @SkeletonClass
    private Short shortObject;

    @SkeletonClass
    private Integer integerObject;

    @SkeletonClass
    private AtomicInteger atomicInteger;

    @SkeletonClass
    private Double doubleObject;

    @SkeletonClass
    private DoubleAccumulator doubleAccumulatorObject;

    @SkeletonClass
    private DoubleAdder doubleAdderObject;

    @SkeletonClass
    private Float floatObject;

    @SkeletonClass
    private Long longObject;

    @SkeletonClass
    private LongAccumulator longAccumulatorObject;

    @SkeletonClass
    private LongAdder longAdderObject;

    @SkeletonClass
    private AtomicLong atomicLongObject;

    @SkeletonClass
    private BigDecimal bigDecimalObject;

    @SkeletonClass
    private BigInteger bigIntegerObject;

    @SkeletonClass
    private byte bytePrimitive;

    @SkeletonClass
    private short shortPrimitive;

    @SkeletonClass
    private int intPrimitive;

    @SkeletonClass
    private long longPrimitive;

    @SkeletonClass
    private float floatPrimitive;

    @SkeletonClass
    private double doublePrimitive;

    @SkeletonClass
    private char charPrimitive;

    @SkeletonClass
    boolean booleanPrimitive;
}
