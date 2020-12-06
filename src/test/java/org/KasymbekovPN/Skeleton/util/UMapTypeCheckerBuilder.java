package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.functional.checker.MapTypeChecker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UMapTypeCheckerBuilder {

    private Set<Class<?>> types;
    private Set<Class<?>> keyArgumentsTypes;
    private Set<Class<?>> valueArgumentsTypes;

    public UMapTypeCheckerBuilder setTypes(Class<?>... types) {
        this.types = new HashSet<>(Arrays.asList(types));
        return this;
    }

    public UMapTypeCheckerBuilder setKeyArgumentsTypes(Class<?>... keyArgumentsTypes) {
        this.keyArgumentsTypes = new HashSet<>(Arrays.asList(keyArgumentsTypes));
        return this;
    }

    public UMapTypeCheckerBuilder setValueArgumentsTypes(Class<?>... valueArgumentsTypes) {
        this.valueArgumentsTypes = new HashSet<>(Arrays.asList(valueArgumentsTypes));
        return this;
    }

    public MapTypeChecker build() throws Exception {
        check();
        return (MapTypeChecker) new MapTypeChecker.Builder()
                .addTypes(types)
                .addKeyArgumentTypes(keyArgumentsTypes)
                .addValueArgumentTypes(valueArgumentsTypes)
                .build();
    }

    private void check() throws Exception {
        StringBuilder message = new StringBuilder();
        if (types == null){
            message.append("types is null; ");
        }
        if (keyArgumentsTypes == null){
            message.append("keyArgumentsTypes is null; ");
        }
        if (valueArgumentsTypes == null){
            message.append("valueArgumentsTypes is null");
        }

        if (message.length() != 0){
            throw new Exception("UMapTypeCheckerBuilder : " + message.toString());
        }
    }
}

