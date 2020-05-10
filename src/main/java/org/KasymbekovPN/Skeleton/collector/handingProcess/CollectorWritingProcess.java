package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.format.writing.Formatter;

public interface CollectorWritingProcess extends CollectorHandingProcess {
    StringBuilder getBuffer();
    void clearBuffer();
    Formatter getFormatter();
}
