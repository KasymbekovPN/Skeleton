package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

// TODO: 22.11.2020 test
public class MapTypeChecker implements SimpleChecker<Field> {

    private final Set<Class<?>> types;
    private final Set<Class<?>> keyArgumentTypes;
    private final Set<Class<?>> valueArgumentTypes;

    public MapTypeChecker(Set<Class<?>> types,
                          Set<Class<?>> keyArgumentTypes,
                          Set<Class<?>> valueArgumentTypes) {
        this.types = types;
        this.keyArgumentTypes = keyArgumentTypes;
        this.valueArgumentTypes = valueArgumentTypes;
    }

    @Override
    public boolean check(Field checkableValue) {
        boolean result = checkType(checkableValue);
        if (result){
            result = checkArgumentType(checkableValue, keyArgumentTypes, 0);
            result &= checkArgumentType(checkableValue, valueArgumentTypes, 1);
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
}
