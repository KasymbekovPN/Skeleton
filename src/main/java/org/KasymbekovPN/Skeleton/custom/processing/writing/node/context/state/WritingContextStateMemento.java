package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;

public interface WritingContextStateMemento extends ContextStateMemento {
    Node getNode();
}
