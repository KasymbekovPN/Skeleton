package org.KasymbekovPN.Skeleton.lib.collector.process.checking;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

/**
 * !!! Its implementation must contain constructor without parameters
 */
public interface CollectorCheckingProcess extends CollectorProcess {
    void setResult(EntityItem ei, CollectorCheckingResult result);
    CollectorCheckingResult getResult();
}
