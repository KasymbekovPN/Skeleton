package org.KasymbekovPN.Skeleton.lib.collector.node;

import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

public class TestCollectorProcess implements CollectorProcess {
    private final Node node;

    private boolean valid = false;

    public TestCollectorProcess(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Node node) {
        valid = this.node.equals(node);
    }

    @Override
    public void addHandler(EntityItem handlerId, CollectorProcessHandler collectorProcessHandler) {

    }


    //<
//    @Override
//    public void addHandler(Class<? extends Node> clazz, CollectorProcessHandler collectorProcessHandler) {}

    public boolean isValid() {
        return valid;
    }
}
