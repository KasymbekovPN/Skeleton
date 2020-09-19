package org.KasymbekovPN.Skeleton.custom.serialization.group.serializer;

//<
//public class SkeletonSerializerGroup implements SerializerGroup {
//
//    private static final Logger log = LoggerFactory.getLogger(SkeletonSerializerGroup.class);
//
//    public final static String EXTRACT_CLASS_NAME = "extractClassName";
//
//    private final static String SERIALIZER_DOES_NOT_EXIST= "Serializer with ID '%s' doesn't exist";
//    private final static String DEFAULT_STATUS = "";
//    private final static String DEFAULT_CLASS_NAME = "";
//    private final static String CLASS_NAME = "className";
//    private final static String RESULT_DOES_NOT_CONTAIN = "Task result doesn't contain '%s'";
//    private final static String TASK_DOES_NOT_EXIST = "Task '%s' doesn't exist";
//
//    //<
////    private final static String NODE = "node";
//
//    private final Map<String, Serializer> serializers = new HashMap<>();
//    private final Processor<Node> nodeProcessor;
//
//    private Result result;
//    private ObjectNode groupNodeRoot = new ObjectNode(null);
//
//    public SkeletonSerializerGroup(Processor<Node> nodeProcessor,
//                                   Result result) {
//        this.nodeProcessor = nodeProcessor;
//        this.result = result;
//    }
//
//    @Override
//    public Result serialize(String serializerId, Class<?> clazz) {
//        if (checkSerializer(serializerId)){
//            Serializer serializer = serializeAndGet(serializerId, clazz);
//            Node rootNode = serializer.getCollector().detachNode();
//            Triple<Boolean, String, String> extractingResult = extractClassName(rootNode);
//
//            modifyGroupNodeRoot(extractingResult, rootNode);
//            return getResult(extractingResult, rootNode);
//        }
//
//        return getWrongResult(String.format(SERIALIZER_DOES_NOT_EXIST, serializerId));
//    }
//
//    @Override
//    public void attach(String serializerId, Serializer serializer) {
//        serializers.put(serializerId, serializer);
//    }
//
//    @Override
//    public Optional<Serializer> detach(String serializerId) {
//        return serializers.containsKey(serializerId)
//                ? Optional.of(serializers.remove(serializerId))
//                : Optional.empty();
//    }
//
//    @Override
//    public void reset() {
//        groupNodeRoot = new ObjectNode(null);
//    }
//
//    @Override
//    public ObjectNode getGroupRootNode() {
//        return groupNodeRoot;
//    }
//
//    private boolean checkSerializer(String serializerId){
//        return serializers.containsKey(serializerId);
//    }
//
//    private Serializer serializeAndGet(String serializerId, Class<?> clazz){
//        Serializer serializer = serializers.get(serializerId);
//        serializer.serialize(clazz);
//
//        return serializer;
//    }
//
//    private Triple<Boolean, String, String> extractClassName(Node node){
//
//        boolean success = false;
//        String status = DEFAULT_STATUS;
//        String className = DEFAULT_CLASS_NAME;
//
//        Optional<Task<Node>> mayBeTask = nodeProcessor.get(EXTRACT_CLASS_NAME);
//        if (mayBeTask.isPresent()){
//            Task<Node> nodeTask = mayBeTask.get();
//            node.apply(nodeTask);
//
//            Result taskResult = nodeTask.getResult(ObjectNode.ei().toString());
//
//            success = taskResult.isSuccess();
//            if (success){
//                Optional<Object> mayBeClassName = taskResult.getOptionalData(CLASS_NAME);
//                if (mayBeClassName.isPresent()){
//                    className = (String) mayBeClassName.get();
//                } else {
//                    success = false;
//                    status = String.format(RESULT_DOES_NOT_CONTAIN, CLASS_NAME);
//                }
//            } else {
//                status = taskResult.getStatus();
//            }
//        } else {
//            status = String.format(TASK_DOES_NOT_EXIST, EXTRACT_CLASS_NAME);
//        }
//
//        return new MutableTriple<>(success, className, status);
//    }
//
//    private void modifyGroupNodeRoot(Triple<Boolean, String, String> extractClassNameData, Node rootNode){
//        Boolean success = extractClassNameData.getLeft();
//        String className = extractClassNameData.getMiddle();
//        if (success){
//            groupNodeRoot.addChild(className, rootNode);
//        }
//    }
//
//    private Result getResult(Triple<Boolean, String, String> extractClassNameData, Node rootNode){
//        result = result.createNew();
//        Boolean success = extractClassNameData.getLeft();
//        String status = extractClassNameData.getRight();
//        result.setSuccess(success);
//        result.setStatus(status);
//        result.setOptionalData(SerializationGroupResult.OBJECT_NODE, rootNode);
//
//        return result;
//    }
//
//    private Result getWrongResult(String status){
//        result = result.createNew();
//        result.setSuccess(false);
//        result.setStatus(status);
//
//        return result;
//    }
//}
