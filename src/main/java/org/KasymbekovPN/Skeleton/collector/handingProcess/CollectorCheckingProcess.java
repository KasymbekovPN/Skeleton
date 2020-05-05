package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.node.Node;

/**
 * !!! Its implementation must contain constructor without parameters
 */
public interface CollectorCheckingProcess extends CollectorHandingProcess {
    void setResult(Class<? extends Node> clazz, SkeletonCheckResult result);
    SkeletonCheckResult getResult(boolean cleanHandlers);
}
