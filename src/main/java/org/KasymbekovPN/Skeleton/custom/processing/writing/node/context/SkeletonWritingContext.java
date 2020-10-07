package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.processor.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.lang.reflect.InvocationTargetException;

public class SkeletonWritingContext implements WritingContext {

    private final ContextIds arrayNodeContextIds;
    private final ContextIds objectNodeContextIds;
    private final ContextIds primitiveNodeContextIds;
    private final WritingFormatterHandler writingFormatterHandler;
    private final ContextProcessor<WritingContext> contextProcessor;

    private Node node;

    public SkeletonWritingContext(ContextIds arrayNodeContextIds,
                                  ContextIds objectNodeContextIds,
                                  ContextIds primitiveNodeContextIds,
                                  WritingFormatterHandler writingFormatterHandler,
                                  ContextProcessor<WritingContext> contextProcessor,
                                  Node node) {
        this.arrayNodeContextIds = arrayNodeContextIds;
        this.objectNodeContextIds = objectNodeContextIds;
        this.primitiveNodeContextIds = primitiveNodeContextIds;
        this.writingFormatterHandler = writingFormatterHandler;
        this.contextProcessor = contextProcessor;
        this.node = node;
    }

    @Override
    public ContextIds getContextIds() {
        if (node.is(ArrayNode.ei())){
            return arrayNodeContextIds;
        } else if (node.is(ObjectNode.ei())){
            return objectNodeContextIds;
        }

        return primitiveNodeContextIds;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public Node attachNode(Node node) {
        Node oldNode = this.node;
        this.node = node;
        return oldNode;
    }

    @Override
    public WritingFormatterHandler getWritingFormatterHandler() {
        return writingFormatterHandler;
    }

    @Override
    public void runProcessor() throws NoSuchMethodException,
                                      InstantiationException,
                                      IllegalAccessException,
                                      InvocationTargetException {
        contextProcessor.handle(this);
    }
}
