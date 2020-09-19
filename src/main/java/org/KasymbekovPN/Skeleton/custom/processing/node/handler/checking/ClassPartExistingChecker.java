package org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking;

//< !!! del
//public class ClassPartExistingChecker implements TaskHandler<Node> {
//
//    public static final String TASK_NAME = "classExist";
//
//    private final CollectorPath serviceClassPath;
//    private final CollectorPath classPath;
//
//    private Result result;
//
//    public ClassPartExistingChecker(Result result,
//                                    CollectorPath serviceClassPath,
//                                    CollectorPath classPath) {
//        this.result = result;
//        this.serviceClassPath = serviceClassPath;
//        this.classPath = classPath;
//    }
//
//    @Override
//    public Result handle(Node object, Task<Node> task) {
//        boolean success = false;
//        String status = "";
//
//        Optional<List<String>> mayBeClassPath = extractClassPath(object);
//        if (mayBeClassPath.isPresent()){
//            List<String> path = mayBeClassPath.get();
//            classPath.setPath(path);
//            classPath.setEi(ObjectNode.ei());
//            Optional<Node> mayBeClassNode = object.getChild(classPath);
//            if (mayBeClassNode.isPresent()){
//                success = true;
//            } else {
//                status = "Doesn't contain class node";
//            }
//        } else {
//            status = "Doesn't contain class path";
//        }
//
//        result = result.createNew();
//        result.setSuccess(success);
//        result.setStatus(status);
//
//        return result;
//    }
//
//    @Override
//    public Result getResult() {
//        return result;
//    }
//
//    private Optional<List<String>> extractClassPath(Node object){
//        Optional<Node> mayBeClassPathNode = object.getChild(serviceClassPath);
//        if (mayBeClassPathNode.isPresent()){
//            ArrayList<String> classPath = new ArrayList<>();
//            ArrayNode classPathNode = (ArrayNode) mayBeClassPathNode.get();
//            for (Node pathPart : classPathNode.getChildren()) {
//                classPath.add(((StringNode)pathPart).getValue());
//            }
//
//            return Optional.of(classPath);
//        }
//
//        return Optional.empty();
//    }
//}
