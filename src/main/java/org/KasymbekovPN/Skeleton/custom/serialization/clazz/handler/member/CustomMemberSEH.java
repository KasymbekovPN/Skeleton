package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member;

//<
//public class CustomMemberSEH extends BaseSEH {
//
//    private final SimpleChecker<String> classNameChecker;
//    private final Filter<Annotation> annotationFilter;
//    private final Processor<Node> nodeProcessor;
//    private final String taskName;
//    private final CollectorPath collectorServicePath;
//    private final ClassMembersPartHandler classMembersPartHandler;
//    private final String kind;
//
//    private String name;
//    private String type;
//    private String className;
//    private int modifiers;
//    private List<String> membersPath;
//
//    public CustomMemberSEH(SimpleChecker<String> classNameChecker,
//                           Filter<Annotation> annotationFilter,
//                           Processor<Node> nodeProcessor,
//                           String taskName,
//                           CollectorPath collectorServicePath,
//                           ClassMembersPartHandler classMembersPartHandler,
//                           String kind) {
//        this.classNameChecker = classNameChecker;
//        this.annotationFilter = annotationFilter;
//        this.nodeProcessor = nodeProcessor;
//        this.taskName = taskName;
//        this.collectorServicePath = collectorServicePath;
//        this.classMembersPartHandler = classMembersPartHandler;
//        this.kind = kind;
//    }
//
//    @Override
//    protected boolean checkData(Field field, Collector collector) {
//
//        boolean success = false;
//        String className = "";
//
//        Deque<Annotation> filteredAnnotation
//                = annotationFilter.filter(new ArrayDeque<>(Arrays.asList(field.getDeclaredAnnotations())));
//        if (filteredAnnotation.size() > 0){
//            SkeletonMember annotation = (SkeletonMember) filteredAnnotation.pollFirst();
//            className = annotation.name();
//
//            success = classNameChecker.check(className) && checkCollectorContent(collector);
//        }
//
//        Optional<List<String>> mayBeMembersPath = extractMembersPath(collector.getNode());
//        success &= mayBeMembersPath.isPresent();
//
//        if (success){
//            this.name = field.getName();
//            this.type = field.getType().getCanonicalName();
//            this.modifiers = field.getModifiers();
//            this.className = className;
//            this.membersPath = mayBeMembersPath.get();
//        }
//
//        return success;
//    }
//
//    @Override
//    protected boolean fillCollector(Collector collector) {
//        List<String> path = new ArrayList<>(membersPath);
//        path.add(name);
//        ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
//        classMembersPartHandler.setKind(targetNode, kind);
//        classMembersPartHandler.setType(targetNode, type);
//        classMembersPartHandler.setClassName(targetNode, className);
//        classMembersPartHandler.setModifiers(targetNode, modifiers);
//        collector.reset();
//
//        return true;
//    }
//
//    private boolean checkCollectorContent(Collector collector){
//
//        boolean result = false;
//        Optional<Task<Node>> mayBeTask = nodeProcessor.get(taskName);
//        if (mayBeTask.isPresent()){
//            Task<Node> nodeTask = mayBeTask.get();
//            Node rootNode = collector.getNode();
//            rootNode.apply(nodeTask);
//
//            result = nodeTask.getResult(ObjectNode.ei().toString()).isSuccess();
//        }
//
//        return result;
//    }
//
//    private Optional<List<String>> extractMembersPath(Node node){
//        if (collectorServicePath.getEi().equals(ArrayNode.ei())){
//            Optional<Node> mayBeServicePathNode = node.getChild(collectorServicePath);
//            if (mayBeServicePathNode.isPresent()){
//                List<String> classPath = new ArrayList<>();
//                ArrayNode servicePathNode = (ArrayNode) mayBeServicePathNode.get();
//                for (Node pathPart : servicePathNode.getChildren()) {
//                    classPath.add(((StringNode)pathPart).getValue());
//                }
//
//                return Optional.of(classPath);
//            }
//        }
//
//        return Optional.empty();
//    }
//}
