package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

// TODO: 22.11.2020 test
public class CollectionTypeChecker implements SimpleChecker<Field> {

    private final Set<Class<?>> types;
    private final Set<Class<?>> arguments;

    public CollectionTypeChecker(Set<Class<?>> types,
                                 Set<Class<?>> arguments) {
        this.types = types;
        this.arguments = arguments;
    }

    @Override
    public boolean check(Field checkableValue) {
        if (checkType(checkableValue)){
            return checkArgumentType(checkableValue);
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
}
