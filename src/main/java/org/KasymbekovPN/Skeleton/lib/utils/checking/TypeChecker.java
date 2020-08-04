package org.KasymbekovPN.Skeleton.lib.utils.checking;

//<
//import java.util.Set;
//
//public class TypeChecker implements Checker<Class<?>> {
//
//    private final Set<Class<?>> extendableTypes;
//    private final Set<Class<?>> specificTypes;
//
//    public TypeChecker(Set<Class<?>> extendableTypes, Set<Class<?>> specificTypes) {
//        this.extendableTypes = extendableTypes;
//        this.specificTypes = specificTypes;
//    }
//
//    @Override
//    public boolean check(Class<?> object) {
//
//        for (Class<?> extendableType : extendableTypes) {
//            if (extendableType.isAssignableFrom(object)){
//                return true;
//            }
//        }
//
//        return specificTypes.contains(object);
//    }
//}
