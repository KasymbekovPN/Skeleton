package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.writing;

import org.KasymbekovPN.Skeleton.format.writing.Formatter;
import org.KasymbekovPN.Skeleton.collector.node.CharacterNode;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorWritingProcess;

//< SKEL-31
public class CharacterWritingHandler implements CollectorHandlingProcessHandler {

    private final StringBuilder buffer;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Formatter formatter;

    public CharacterWritingHandler(CollectorWritingProcess collectorWritingProcess, Class<? extends Node> clazz) {
        this.collectorWritingProcess = collectorWritingProcess;
        this.collectorWritingProcess.addHandler(clazz, this);
        this.buffer = this.collectorWritingProcess.getBuffer();
        this.formatter = this.collectorWritingProcess.getFormatter();
    }

    @Override
    public void handle(Node node) {
        if (node.isCharacter()){
            Character value = ((CharacterNode) node).getValue();
            Class<CharacterNode> clazz = CharacterNode.class;
            buffer.append(formatter.getBeginBorder(clazz)).append(value).append(formatter.getEndBorder(clazz));
        }
    }
}
