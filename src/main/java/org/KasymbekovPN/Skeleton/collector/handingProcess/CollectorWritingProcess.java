package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;

//<  !! rename interface
public interface CollectorWritingProcess extends CollectorHandingProcess {
//    void write(Node node);
//    void addHandler(Class<? extends Node> clazz, WritingHandler handler);
    //<
    StringBuilder getBuffer();
    Formatter getFormatter();
}
