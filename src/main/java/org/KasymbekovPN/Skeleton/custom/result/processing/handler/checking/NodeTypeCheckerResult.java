package org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking;

import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class NodeTypeCheckerResult extends BaseResultImpl {

    public static final String NOT_CONTAINS_MEMBER_PATH = "notContainsMemberPath";
    public static final String NOT_CONTAINS_MEMBER_NODE = "notContainsMemberNode";
    public static final String DISALLOWED_SYSTEM_TYPES = "disallowedSystemTypes";
    public static final String DISALLOWED_CUSTOM_TYPES = "disallowedCustomTypes";

    private Set<String> notContainsMemberPath = new HashSet<>();
    private Set<String> notContainsMemberNode = new HashSet<>();
    private Set<String> disallowedSystemTypes = new HashSet<>();
    private Set<String> disallowedCustomTypes = new HashSet<>();

    public NodeTypeCheckerResult() {
        super();
    }

    @Override
    public Result createNew() {
        return new NodeTypeCheckerResult();
    }

    @Override
    public boolean setOptionalData(String dataId, Object optionalData) {
        boolean result = true;

        switch (dataId){
            case NOT_CONTAINS_MEMBER_PATH:
                notContainsMemberPath = (Set<String>) optionalData;
                break;
            case NOT_CONTAINS_MEMBER_NODE:
                notContainsMemberNode = (Set<String>) optionalData;
                break;
            case DISALLOWED_SYSTEM_TYPES:
                disallowedSystemTypes = (Set<String>) optionalData;
                break;
            case DISALLOWED_CUSTOM_TYPES:
                disallowedCustomTypes = (Set<String>) optionalData;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    @Override
    public Optional<Object> getOptionalData(String dataId) {
        switch (dataId){
            case NOT_CONTAINS_MEMBER_PATH:
                 return Optional.of(notContainsMemberPath);
            case NOT_CONTAINS_MEMBER_NODE:
                 return Optional.of(notContainsMemberNode);
            case DISALLOWED_SYSTEM_TYPES:
                 return Optional.of(disallowedSystemTypes);
            case DISALLOWED_CUSTOM_TYPES:
                 return Optional.of(disallowedCustomTypes);
            default:
                return Optional.empty();
        }
    }

    //< del ???
    @Override
    public String toString() {
        return "NodeTypeCheckerResult{" +
                "notContainsMemberPath=" + notContainsMemberPath +
                ", notContainsMemberNode=" + notContainsMemberNode +
                ", disallowedSystemTypes=" + disallowedSystemTypes +
                ", disallowedCustomTypes=" + disallowedCustomTypes +
                ", success=" + success +
                ", status='" + status + '\'' +
                '}';
    }
}
