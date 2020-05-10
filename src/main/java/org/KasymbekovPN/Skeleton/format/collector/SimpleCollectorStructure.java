package org.KasymbekovPN.Skeleton.format.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SimpleCollectorStructure implements CollectorStructure {

    private final static Logger log = LoggerFactory.getLogger(SimpleCollectorStructure.class);

    private final Map<CollectorStructureItem, List<String>> paths;

    private SimpleCollectorStructure(Map<CollectorStructureItem, List<String>> paths) {
        this.paths = paths;
    }

    @Override
    public List<String> getPath(CollectorStructureItem item) {
        return paths.get(item);
    }

    @Override
    public String toString() {
        return "SimpleCollectorStructure{" +
                "paths=" + paths +
                '}';
    }

    public static class Builder{

        private final Map<CollectorStructureItem, List<String>> paths = new HashMap<>();

        public Builder setClassPath(String... path){
            return setPath(CollectorStructureItem.CLASS, Arrays.asList(path));
        }

        public Builder setMembersPath(String... path){
            return setPath(CollectorStructureItem.MEMBERS, Arrays.asList(path));
        }

        public Builder setAnnotationPath(String... path){
            return setPath(CollectorStructureItem.ANNOTATION, Arrays.asList(path));
        }

        public Builder setConstructorPath(String... path){
            return setPath(CollectorStructureItem.CONSTRUCTOR, Arrays.asList(path));
        }

        public Builder setMethodPath(String... path){
            return setPath(CollectorStructureItem.METHOD, Arrays.asList(path));
        }

        public Builder setProtocolPath(String... path){
            return setPath(CollectorStructureItem.PROTOCOL, Arrays.asList(path));
        }

        public Builder setPath(CollectorStructureItem item, List<String> path){
            paths.put(item, path);
            return this;
        }

        public CollectorStructure build() throws Exception {
            Set<Integer> checkingSet = new HashSet<>();
            for (Map.Entry<CollectorStructureItem, List<String>> entry : paths.entrySet()) {
                if (entry.getValue() == null || entry.getValue().size() == 0){
                    throw new Exception("Argument is null or its size is 1");
                }
                checkingSet.add(entry.getValue().hashCode());
            }

            if (checkingSet.size() != CollectorStructureItem.values().length){
                throw new Exception("There were set no all item or two and more item have non-unique path");
            }

            return new SimpleCollectorStructure(paths);
        }
    }
}
