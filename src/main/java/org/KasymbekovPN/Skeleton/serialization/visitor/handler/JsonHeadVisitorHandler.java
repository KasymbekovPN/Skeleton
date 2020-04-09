package org.KasymbekovPN.Skeleton.serialization.visitor.handler;

import org.KasymbekovPN.Skeleton.serialization.visitorElement.VisitorElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHeadVisitorHandler implements VisitorHandler {

    private static final Logger log = LoggerFactory.getLogger(JsonHeadVisitorHandler.class);

    @Override
    public void handle(VisitorElement visitorElement) {
        log.info("{}", visitorElement);
    }
}
