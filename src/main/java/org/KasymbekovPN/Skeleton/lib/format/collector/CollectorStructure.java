package org.KasymbekovPN.Skeleton.lib.format.collector;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

import java.util.List;

public interface CollectorStructure {
    List<String> getPath(EntityItem entityItem);
}
