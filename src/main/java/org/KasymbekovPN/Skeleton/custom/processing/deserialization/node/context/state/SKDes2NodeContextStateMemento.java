package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.state;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.SKDes2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SKDes2NodeContextStateMemento implements Des2NodeContextStateMemento {

    private static final Logger log = LoggerFactory.getLogger(SKDes2InstanceContextStateMemento.class);

    private static final Class<? extends SimpleResult> RESULT_CLASS = SKSimpleResult.class;
    private static final String PARENT_NODE_HAS_WRONG_TYPE = "The parent node has wrong type";

    private final Node parentNode;

    private Node node;
    private SimpleResult result;

    public SKDes2NodeContextStateMemento(Node parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        result = createResultInstance();

        checkParentNode();

        if (!result.isSuccess()){
            log.warn("{}", result.getStatus());
        }
    }

    @Override
    public SimpleResult getValidationResult() {
        return result;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public Node getParentNode() {
        return parentNode;
    }

    private SimpleResult createResultInstance() throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<? extends SimpleResult> constructor = RESULT_CLASS.getConstructor();
        return constructor.newInstance();
    }

    void checkParentNode(){
        if (result.isSuccess() && parentNode != null && parentNode.is(NodeEI.invalidEI())){
            result.setFailStatus(PARENT_NODE_HAS_WRONG_TYPE);
        }
    }
}
