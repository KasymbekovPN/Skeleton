package org.KasymbekovPN.Skeleton.serialization.visitor.handler;

import org.KasymbekovPN.Skeleton.serialization.visitorElement._VisitorElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _JsonHeadVisitorHandler implements _VisitorHandler {

    private static final Logger log = LoggerFactory.getLogger(_JsonHeadVisitorHandler.class);

    @Override
    public void handle(_VisitorElement visitorElement) {
        log.info("{}", visitorElement);
    }
}
