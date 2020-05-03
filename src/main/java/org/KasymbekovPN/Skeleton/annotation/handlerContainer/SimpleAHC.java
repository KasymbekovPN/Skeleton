package org.KasymbekovPN.Skeleton.annotation.handlerContainer;

//import org.KasymbekovPN.Skeleton.annotation.handler.AnnotationHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.*;
//
//public class SimpleAHC implements AnnotationHandlerContainer {
//
////    private AnnotationHandler classAH;
////    private AnnotationHandler memberAH;
////
////    @Override
////    public void setClassAnnotationHandler(AnnotationHandler classAH) {
////        this.classAH = classAH;
////    }
////
////    @Override
////    public void setMemberAnnotationHandler(AnnotationHandler memberAH) {
////        this.memberAH = memberAH;
////    }
////
////    @Override
////    public AnnotationHandler getClassAnnotationHandler() {
////        return classAH;
////    }
//    //<
//
//    private static final Logger log = LoggerFactory.getLogger(SimpleAHC.class);
//
//    private final Map<Entity, AnnotationHandler> annotationHandlers = new HashMap<>();
//    private final Set<String> memberNames = new HashSet<>();
//
//    @Override
//    public void setAnnotationHandler(Entity entity, AnnotationHandler annotationHandler) {
//        annotationHandlers.put(entity, annotationHandler);
//    }
//
//    @Override
//    public Optional<AnnotationHandler> getAnnotationHandler(Entity entity) {
//        return annotationHandlers.containsKey(entity)
//                ? Optional.of(annotationHandlers.get(entity))
//                : Optional.empty();
//    }
//
//    @Override
//    public void addMemberName(String memberName) {
//        memberNames.add(memberName);
//    }
//
//    @Override
//    public Set<String> getMemberName() {
//        return memberNames;
//    }
//}
