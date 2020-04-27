package org.KasymbekovPN.Skeleton.utils.containerArgumentChecker;

import org.KasymbekovPN.Skeleton.utils.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleCAC implements ContainerArgumentChecker {

    private final static Logger log = LoggerFactory.getLogger(SimpleCAC.class);

    private final Checker<Class<?>>[] checkers;

    @SafeVarargs
    public SimpleCAC(Checker<Class<?>>... checkers) {
        this.checkers = checkers;
    }

    @Override
    public Optional<List<Class<?>>> check(Type[] arguments) {

        List<Class<?>> argTypes = new ArrayList<>();
        if (arguments.length == checkers.length && arguments.length > 0){
            for (int i = 0; i < arguments.length; i++) {
                Class<?> argument = (Class<?>) arguments[i];
                if (checkers[i].check(argument)){
                    argTypes.add(argument);
                } else {
                    return Optional.empty();
                }
            }
        }

        return Optional.of(argTypes);
    }
}
