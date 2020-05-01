package org.KasymbekovPN.Skeleton.condition;

public class SimpleAHC implements AnnotationHandlerContainer {

    private AnnotationHandler classAH;
    private AnnotationHandler memberAH;

    @Override
    public void setClassAnnotationHandler(AnnotationHandler classAH) {
        this.classAH = classAH;
    }

    @Override
    public void setMemberAnnotationHandler(AnnotationHandler memberAH) {
        this.memberAH = memberAH;
    }

    @Override
    public AnnotationHandler getClassAnnotationHandler() {
        return classAH;
    }
}
