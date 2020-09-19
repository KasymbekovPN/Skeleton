package org.KasymbekovPN.Skeleton.custom.processing.node.processor;

//<
//public class NodeProcessor implements Processor<Node> {
//
//    private final Map<String, Task<Node>> tasks = new HashMap<>();
//    private final AggregateResult processorResult;
//
//    public NodeProcessor(AggregateResult processorResult) {
//        this.processorResult = processorResult;
//    }
//
//    @Override
//    public Task<Node> add(String taskId, Task<Node> task) {
//        return tasks.put(taskId, task);
//    }
//
//    @Override
//    public Optional<Task<Node>> get(String taskId) {
//        return tasks.containsKey(taskId)
//                ? Optional.of(tasks.get(taskId))
//                : Optional.empty();
//    }
//
//    @Override
//    public Optional<Task<Node>> remove(String taskId) {
//        return tasks.containsKey(taskId)
//                ? Optional.of(tasks.remove(taskId))
//                : Optional.empty();
//    }
//
//    //<
////    @Override
////    public AggregateResult handle(Node object, Filter<String> taskIdFilter) {
////        Deque<String> filterKeys = taskIdFilter.filter(new ArrayDeque<>(tasks.keySet()));
////        for (String filterKey : filterKeys) {
////            Result taskResult = (Result) tasks.get(filterKey).handle(object);
////            processorResult.put(filterKey, taskResult);
////        }
////
////        return processorResult;
////    }
//
//    @Override
//    public AggregateResult handle(Node object) {
//        for (Map.Entry<String, Task<Node>> entry : tasks.entrySet()) {
//            String taskId = entry.getKey();
//            Result taskResult = (Result) tasks.get(taskId).handle(object);
//            processorResult.put(taskId, taskResult);
//        }
//
//        return processorResult;
//    }
//
//    @Override
//    public AggregateResult getResult() {
//        return processorResult;
//    }
//}
