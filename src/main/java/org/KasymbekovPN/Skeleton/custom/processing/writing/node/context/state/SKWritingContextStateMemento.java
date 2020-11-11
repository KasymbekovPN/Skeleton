package org.KasymbekovPN.Skeleton.custom.processing.writing.node.context.state;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.result.SKSimpleResult;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SKWritingContextStateMemento implements WritingContextStateMemento {

    private static final Logger log = LoggerFactory.getLogger(SKWritingContextStateMemento.class);

    private static final Class<? extends SimpleResult> RESULT_CLASS = SKSimpleResult.class;
    private static final String NODE_IS_NULL = "Node is null";

    private final Node node;

    private SimpleResult result;

    public SKWritingContextStateMemento(Node node) {
        this.node = node;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        result = createResultInstance();

        checkNode();

        if (!result.isSuccess()){
            log.warn("{}", result.getStatus());
        }
    }

    @Override
    public SimpleResult getValidationResult() {
        return result;
    }

    private SimpleResult createResultInstance() throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<? extends SimpleResult> constructor = RESULT_CLASS.getConstructor();
        return constructor.newInstance();
    }

    private void checkNode(){
        if (node == null){
            result.setFailStatus(NODE_IS_NULL);
        }
    }
}
