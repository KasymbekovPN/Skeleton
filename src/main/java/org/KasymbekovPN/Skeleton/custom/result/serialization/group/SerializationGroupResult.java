package org.KasymbekovPN.Skeleton.custom.result.serialization.group;

import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.Optional;

public class SerializationGroupResult extends BaseResultImpl {

    public static final String OBJECT_NODE = "objectNode";

    private ObjectNode objectNode;

    public SerializationGroupResult() {
        super();
        reset();
    }


    @Override
    public void reset() {
        super.reset();
        objectNode = new ObjectNode(null);
    }

    @Override
    public boolean setOptionalData(String dataId, Object optionalData) {
        if (dataId.equals(OBJECT_NODE) && optionalData.getClass().equals(ObjectNode.class)){
            objectNode = (ObjectNode) optionalData;
        }
        return false;
    }

    @Override
    public Optional<Object> getOptionalData(String dataId) {
        return dataId.equals(OBJECT_NODE)
                ? Optional.of(objectNode)
                : Optional.empty();
    }

    @Override
    public Result createNew() {
        return new SerializationGroupResult();
    }
}
