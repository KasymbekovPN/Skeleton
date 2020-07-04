package org.KasymbekovPN.Skeleton.lib.format.entity;

import java.util.Collection;

/**
 * All instances of this interface must implement constructor without params.
 */
public interface EntityItem {
    default boolean checkInstance(EntityItem instance) {return false;}
    default boolean checkInstances(Collection<EntityItem> instances) {return false;}
    default boolean checkInstancesStrict(Collection<EntityItem> instances) {return false;}
}
