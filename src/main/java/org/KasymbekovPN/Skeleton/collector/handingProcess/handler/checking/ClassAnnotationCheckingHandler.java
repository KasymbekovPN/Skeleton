package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking;

import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;

public class ClassAnnotationCheckingHandler implements CollectorHandlingProcessHandler {

    //< skel-30
    private static final String ANNOTATION_OBJECT_PROPERTY = "annotation";

    private final String name;
    private final int modifiers;
    private final CollectorCheckingProcess collectorCheckingProcess;

    public ClassAnnotationCheckingHandler(int modifiers,
                                          String name,
                                          CollectorCheckingProcess collectorCheckingProcess,
                                          Class<? extends Node> clazz) {

        this.name = name;
        this.modifiers = modifiers;

        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
    }

    @Override
    public void handle(Node node) {
        ObjectNode objectNode = (ObjectNode) node;
        if (objectNode.containsKey(ANNOTATION_OBJECT_PROPERTY)){
            ObjectNode annotationNode = (ObjectNode) objectNode.getChildren().get(ANNOTATION_OBJECT_PROPERTY);

            int includeByModifiers = -1;
            int excludeByModifiers = -1;
            String[] includeByName = new String[0];
            String[] excludeByName = new String[0];

            //<
//            Map<String, Node> children = annotationNode.getChildren();
//            if (children.containsKey("includeByModifiers")){
//                includeByModifiers = children.get("includeByModifiers");
//            }

//            "includeByModifiers":-1,
//                    "excludeByModifiers":-1,
//                    "includeByName":[
//            "map"
//		],
//            "":[
//
//		]
        }
    }

    //<
//    private static final Logger log = LoggerFactory.getLogger(ClassExistCheckingHandler.class);
//
//    //< SKEL-30
//    private static final String CLASS_OBJECT_PROPERTY = "class";
//
//    //    private SkeletonCheckResult checkResult = SkeletonCheckResult.NONE;
//    //<
////    private final Processor processor;
//    //<
//    private final CollectorCheckingProcess collectorCheckingProcess;
//
//    public ClassExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess, Class<? extends Node> clazz) {
////        this.processor = processor;
//        //<

//    }
//
//    @Override
//    public void handle(Node node) {
//        log.info("{}", node);
//        //<
//
//        SkeletonCheckResult checkResult = ((ObjectNode) node).getChildren().containsKey(CLASS_OBJECT_PROPERTY)
//                ? SkeletonCheckResult.INCLUDE
//                : SkeletonCheckResult.EXCLUDE;
//        collectorCheckingProcess.setResult(checkResult);
//    }
}
