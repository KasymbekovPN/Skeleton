package org.KasymbekovPN.Skeleton.utils.containerArgumentChecker;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface ContainerArgumentChecker {
    Optional<List<Class<?>>> check(Type[] arguments);
}