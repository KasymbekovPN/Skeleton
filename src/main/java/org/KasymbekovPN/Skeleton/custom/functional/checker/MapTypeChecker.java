package org.KasymbekovPN.Skeleton.custom.functional.checker;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class MapTypeChecker implements Function<Field, Boolean> {

    private final Set<Class<?>> types;
    private final Set<Class<?>> keyArgumentTypes;
    private final Set<Class<?>> valueArgumentTypes;

    private MapTypeChecker(Set<Class<?>> types,
                          Set<Class<?>> keyArgumentTypes,
                          Set<Class<?>> valueArgumentTypes) {
        this.types = types;
        this.keyArgumentTypes = keyArgumentTypes;
        this.valueArgumentTypes = valueArgumentTypes;
    }

    @Override
    public Boolean apply(Field field) {
        boolean result = checkType(field);
        if (result){
            result = checkArgumentType(field, keyArgumentTypes, 0);
            result &= checkArgumentType(field, valueArgumentTypes, 1);
        }

        return result;
    }

    private boolean checkType(Field field){
        return types.contains(field.getType());
    }

    private boolean checkArgumentType(Field field, Set<Class<?>> argumentTypes, int index){
        if (index > 1)
            return false;
        Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        return types.length == 2 && argumentTypes.contains((Class<?>) types[index]);
    }

    public static class Builder{

        private final Set<Class<?>> types = new HashSet<>();
        private final Set<Class<?>> keyArgumentTypes = new HashSet<>();
        private final Set<Class<?>> valueArgumentTypes = new HashSet<>();

        public Builder addTypes(Class<?>... types){
            return addTypes(new HashSet<>(Arrays.asList(types)));
        }

        public Builder addTypes(Set<Class<?>> types){
            this.types.addAll(types);
            return this;
        }

        public Builder addKeyArgumentTypes(Class<?>... keyArgumentTypes){
            return addKeyArgumentTypes(new HashSet<>(Arrays.asList(keyArgumentTypes)));
        }

        public Builder addKeyArgumentTypes(Set<Class<?>> keyArgumentTypes){
            this.keyArgumentTypes.addAll(keyArgumentTypes);
            return this;
        }

        public Builder addValueArgumentTypes(Class<?>... valueArgumentTypes){
            return addValueArgumentTypes(new HashSet<>(Arrays.asList(valueArgumentTypes)));
        }

        public Builder addValueArgumentTypes(Set<Class<?>> valueArgumentTypes){
            this.valueArgumentTypes.addAll(valueArgumentTypes);
            return this;
        }

        public Function<Field, Boolean> build(){
            return new MapTypeChecker(types, keyArgumentTypes, valueArgumentTypes);
        }
    }
}
