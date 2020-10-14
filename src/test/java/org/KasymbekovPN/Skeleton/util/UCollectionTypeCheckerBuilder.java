package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UCollectionTypeCheckerBuilder {

    private Set<Class<?>> types;
    private Set<Class<?>> argumentTypes;

    public UCollectionTypeCheckerBuilder setTypes(Class<?>... types) {
        this.types = new HashSet<>(Arrays.asList(types));
        return this;
    }

    public UCollectionTypeCheckerBuilder setArgumentTypes(Class<?>... argumentTypes) {
        this.argumentTypes = new HashSet<>(Arrays.asList(argumentTypes));
        return this;
    }

    public CollectionTypeChecker build() throws Exception {
        check();
        return new CollectionTypeChecker(types, argumentTypes);
    }

    private void check() throws Exception {
        StringBuilder message = new StringBuilder();
        if (types == null){
            message.append("UCollectionTypeCheckerBuilder: types is null; ");
        }
        if (argumentTypes == null){
            if (message.length() == 0){
                message.append("UCollectionTypeCheckerBuilder: ");
            }
            message.append("UCollectionTypeCheckerBuilder: argumentTypes is null; ");
        }

        if (message.length() != 0){
            throw new Exception(message.toString());
        }
    }
}
