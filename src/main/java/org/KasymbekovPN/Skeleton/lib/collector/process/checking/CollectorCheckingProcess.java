package org.KasymbekovPN.Skeleton.lib.collector.process.checking;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;

/**
 * !!! Its implementation must contain constructor without parameters
 */
public interface CollectorCheckingProcess extends CollectorProcess {
    void setResult(Class<? extends Node> clazz, CollectorCheckingResult result);
    CollectorCheckingResult getResult();
}
