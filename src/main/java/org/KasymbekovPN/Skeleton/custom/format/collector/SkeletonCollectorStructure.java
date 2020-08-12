package org.KasymbekovPN.Skeleton.custom.format.collector;

import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SkeletonCollectorStructure implements CollectorStructure {

    private final static Logger log = LoggerFactory.getLogger(SkeletonCollectorStructure.class);

    private final Map<EntityItem, List<String>> paths;

    private SkeletonCollectorStructure(Map<EntityItem, List<String>> paths) {
        this.paths = paths;
    }

    @Override
    public List<String> getPath(EntityItem entityItem) {
        return paths.get(entityItem);
    }

    @Override
    public Map<EntityItem, List<String>> getPaths() {
        return paths;
    }

    public static class Builder{

        private final Map<EntityItem, List<String>> paths = new HashMap<>();

        public Builder setClassPath(String... path){
            return setPath(CollectorStructureEI.classEI(), Arrays.asList(path));
        }

        public Builder setMembersPath(String... path){
            return setPath(CollectorStructureEI.membersEI(), Arrays.asList(path));
        }

        public Builder setAnnotationPath(String... path){
            return setPath(CollectorStructureEI.annotationEI(), Arrays.asList(path));
        }

        public Builder setConstructorPath(String... path){
            return setPath(CollectorStructureEI.constructorEI(), Arrays.asList(path));
        }

        public Builder setMethodPath(String... path){
            return setPath(CollectorStructureEI.methodEI(), Arrays.asList(path));
        }

        public Builder setProtocolPath(String... path){
            return setPath(CollectorStructureEI.protocolEI(), Arrays.asList(path));
        }

        private Builder setPath(EntityItem entityItem, List<String> path){
            paths.put(entityItem, path);
            return this;
        }

        public CollectorStructure build() throws Exception {
            Set<Integer> checkingSet = new HashSet<>();
            for (Map.Entry<EntityItem, List<String>> entry : paths.entrySet()) {
                if (entry.getValue() == null || entry.getValue().size() == 0){
                    throw new Exception("Argument is null or empty");
                }
                checkingSet.add(entry.getValue().hashCode());
            }

            if (checkingSet.size() != CollectorStructureEI.Entity.values().length){
                throw new Exception("There were set no all item or two and more item have non-unique path");
            }

            return new SkeletonCollectorStructure(paths);
        }
    }
}
