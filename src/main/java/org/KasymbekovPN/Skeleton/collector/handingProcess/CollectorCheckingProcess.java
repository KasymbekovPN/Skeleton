package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;

public interface CollectorCheckingProcess extends CollectorHandingProcess {
    void setResult(SkeletonCheckResult result);
    SkeletonCheckResult getResult();
}
