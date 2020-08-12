package org.KasymbekovPN.Skeleton.lib.format.collector;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

import java.util.List;
import java.util.Map;

public interface CollectorStructure {
    List<String> getPath(EntityItem entityItem);
    Map<EntityItem, List<String>> getPaths();
}
