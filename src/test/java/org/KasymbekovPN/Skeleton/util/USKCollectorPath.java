package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.collector.path.SKCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.util.Collections;

public class USKCollectorPath {

    public final static CollectorPath DEFAULT_CLASS_PART_PATH = new SKCollectorPath(
            Collections.singletonList("class"),
            ObjectNode.ei()
    );

    public final static CollectorPath DEFAULT_MEMBERS_PATH_PATH = new SKCollectorPath(
            Collections.singletonList("members"),
            ObjectNode.ei()
    );
}
