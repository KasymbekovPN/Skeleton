package org.KasymbekovPN.Skeleton.collector.handingProcess;

import org.KasymbekovPN.Skeleton.collector.formatter.Formatter;

public interface CollectorWritingProcess extends CollectorHandingProcess {
    StringBuilder getBuffer();
    Formatter getFormatter();
}
