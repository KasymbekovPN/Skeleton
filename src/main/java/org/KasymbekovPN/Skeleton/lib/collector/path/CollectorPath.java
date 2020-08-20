package org.KasymbekovPN.Skeleton.lib.collector.path;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;
import java.util.List;

public interface CollectorPath {
    void setPath(List<String> path);
    void setEi(EntityItem ei);
    List<String> getPath();
    EntityItem getEi();
    Iterator<Pair<String, EntityItem>> iterator();
}
