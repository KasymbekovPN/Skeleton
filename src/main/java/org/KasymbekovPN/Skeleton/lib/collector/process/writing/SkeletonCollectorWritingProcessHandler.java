package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

public class SkeletonCollectorWritingProcessHandler implements CollectorProcessHandler {

    private final WritingCollectorProcessHandler writingCollectorProcessHandler;
    private final WritingFormatterHandler writingFormatterHandler;
    private final CollectorWritingProcess collectorWritingProcess;
    private final EntityItem ei;

    public SkeletonCollectorWritingProcessHandler(WritingCollectorProcessHandler writingCollectorProcessHandler,
                                                  WritingFormatterHandler writingFormatterHandler,
                                                  CollectorWritingProcess collectorWritingProcess,
                                                  EntityItem ei) {
        this.writingCollectorProcessHandler = writingCollectorProcessHandler;
        this.writingFormatterHandler = writingFormatterHandler;
        this.collectorWritingProcess = collectorWritingProcess;
        this.ei = ei;

        collectorWritingProcess.addHandler(ei, this);
    }

    @Override
    public void handle(Node node) {
        if (node.getEI().equals(ei)){
            writingCollectorProcessHandler.handle(node, writingFormatterHandler, collectorWritingProcess);
        }
    }
}