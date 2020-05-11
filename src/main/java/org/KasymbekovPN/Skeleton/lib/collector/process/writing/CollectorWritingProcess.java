package org.KasymbekovPN.Skeleton.lib.collector.process.writing;

import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.writing.Formatter;

public interface CollectorWritingProcess extends CollectorProcess {
    StringBuilder getBuffer();
    void clearBuffer();
    Formatter getFormatter();
}
