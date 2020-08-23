package org.KasymbekovPN.Skeleton.custom.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.serialization.instance.handler.BaseISH;

import java.lang.reflect.Field;
import java.util.Map;

public class SpecificISH extends BaseISH {

    private Object value;

    @Override
    protected boolean checkMemberData(Object object, Field field, String className, Map<String, ObjectNode> classNodes) {

        System.out.println(object);
        System.out.println(field);

        return super.checkMemberData(object, field, className, classNodes);
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        return super.fillCollector(collector);
    }
}
