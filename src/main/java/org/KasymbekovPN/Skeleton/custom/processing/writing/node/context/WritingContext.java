package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.Node;

import java.lang.reflect.InvocationTargetException;

public interface WritingContext extends Context {
    Node getNode();
    Node attachNode(Node node);
    WritingFormatterHandler getWritingFormatterHandler();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
