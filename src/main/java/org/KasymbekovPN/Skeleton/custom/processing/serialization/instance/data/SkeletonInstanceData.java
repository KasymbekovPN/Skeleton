package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data;

import java.util.List;

public class SkeletonInstanceData implements InstanceData {

    private final List<String> taskIds;
    private final List<String> wrapperIds;

    public SkeletonInstanceData(List<String> taskIds, List<String> wrapperIds) {
        this.taskIds = taskIds;
        this.wrapperIds = wrapperIds;
    }

    @Override
    public List<String> getTaskIds() {
        return null;
    }

    @Override
    public List<String> getWrapperIds() {
        return wrapperIds;
    }
}
