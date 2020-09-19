package org.KasymbekovPN.Skeleton.custom.processing.node.task;

//public class NodeTask implements Task<Node> {
//
//    private final Map<String, TaskWrapper<Node>> wrappers = new HashMap<>();
//    private final AggregateResult taskResult;
//
//    private Result wrongResult;
//
//    public NodeTask(AggregateResult taskResult,
//                    Result wrongResult) {
//        this.taskResult = taskResult;
//        this.wrongResult = wrongResult;
//    }
//
//    @Override
//    public AggregateResult handle(Node object) {
//        String wrapperId = object.getEI().toString();
//        Result handlerResult = wrappers.containsKey(wrapperId)
//                ? wrappers.get(wrapperId).handle(object)
//                : getWrongResult("wrapper '" + wrapperId + "' doesn't exist");
//        taskResult.put(wrapperId, handlerResult);
//
//        return taskResult;
//    }
//
//    @Override
//    public Task<Node> add(String wrapperId, TaskWrapper<Node> taskWrapper) {
//        wrappers.put(wrapperId, taskWrapper);
//        return this;
//    }
//
//    @Override
//    public Result getResult(String wrapperId) {
//        return taskResult.get(wrapperId);
//    }
//
//    private Result getWrongResult(String status){
//        Result newWrongResult = wrongResult.createNew();
//        newWrongResult.setStatus(status);
//
//        return newWrongResult;
//    }
//}
