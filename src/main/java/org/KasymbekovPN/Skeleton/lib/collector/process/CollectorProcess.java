package org.KasymbekovPN.Skeleton.lib.collector.process;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

public interface CollectorProcess {
    void handle(Node node);
    void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler);
}
