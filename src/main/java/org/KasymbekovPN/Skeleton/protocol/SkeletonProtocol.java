package org.KasymbekovPN.Skeleton.protocol;

public class SkeletonProtocol implements Protocol {

    private static volatile Protocol instance;

    public static Protocol getInstance(){
        Protocol localInstance = instance;
        if (localInstance == null){
            synchronized (SkeletonProtocol.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new SkeletonProtocol();
                }
            }
        }

        return localInstance;
    }

    @Override
    public Long getVersion() {
        return getLibVersion();
    }
}
