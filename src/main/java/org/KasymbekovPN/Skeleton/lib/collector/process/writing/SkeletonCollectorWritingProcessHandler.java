package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

public class SkeletonCollectorWritingProcessHandler implements CollectorProcessHandler {

    private final WritingCollectorProcessHandler writingCollectorProcessHandler;
    private final WritingFormatter writingFormatter;
    private final EntityItem ei;

    public SkeletonCollectorWritingProcessHandler(WritingCollectorProcessHandler writingCollectorProcessHandler,
                                                  WritingFormatter writingFormatter,
                                                  CollectorWritingProcess collectorWritingProcess,
                                                  EntityItem ei) {
        this.writingCollectorProcessHandler = writingCollectorProcessHandler;
        this.writingFormatter = writingFormatter;
        this.ei = ei;

        collectorWritingProcess.addHandler(ei, this);
    }

    @Override
    public void handle(Node node) {
        if (node.getEI().equals(ei)){
            writingCollectorProcessHandler.handle(node, writingFormatter);
        }
    }
}

//<
//public class StringWritingHandler implements CollectorProcessHandler {
//
//    private final WritingFormatter writingFormatter;
//
//    public StringWritingHandler(CollectorWritingProcess collectorWritingProcess,
//                                WritingFormatter writingFormatter) {
//        collectorWritingProcess.addHandler(StringNode.ei(), this);
//
//        this.writingFormatter = writingFormatter;
//    }
//
//    @Override
//    public void handle(Node node) {
//        if (node.isString()){
//            writingFormatter.addValue(node);
//        }
//    }
//}
