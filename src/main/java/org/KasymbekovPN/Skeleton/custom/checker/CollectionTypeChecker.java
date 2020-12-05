package org.KasymbekovPN.Skeleton.custom.checker;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class CollectionTypeChecker implements Function<Field, Boolean> {

    private final Set<Class<?>> types;
    private final Set<Class<?>> arguments;

    private CollectionTypeChecker(Set<Class<?>> types,
                                 Set<Class<?>> arguments) {
        this.types = types;
        this.arguments = arguments;
    }

    @Override
    public Boolean apply(Field field) {
        if (checkType(field)){
            return checkArgumentType(field);
        }
        return false;
    }

    private boolean checkType(Field field){
        Class<?> type = field.getType();
        return types.contains(type);
    }

    private boolean checkArgumentType(Field field){
        Type[] argumentTypes = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        return argumentTypes.length == 1 && arguments.contains((Class<?>) argumentTypes[0]);
    }

    public static class Builder{
        private final Set<Class<?>> types = new HashSet<>();
        private final Set<Class<?>> arguments = new HashSet<>();

        public Builder addTypes(Class<?>... types){
            return addTypes(new HashSet<>(Arrays.asList(types)));
        }

        public Builder addTypes(Set<Class<?>> types){
            this.types.addAll(types);
            return this;
        }

        public Builder addArguments(Class<?>... arguments){
            return addArguments(new HashSet<>(Arrays.asList(arguments)));
        }

        public Builder addArguments(Set<Class<?>> arguments){
            this.arguments.addAll(arguments);
            return this;
        }

        public Function<Field, Boolean> build(){
            return new CollectionTypeChecker(types, arguments);
        }
    }
}
