package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.handler.header;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class ClassSignatureTaskHandler extends BaseContextTaskHandler<ClassContext> {

    public ClassSignatureTaskHandler(String id) {
        super(id);
    }

    public ClassSignatureTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(ClassContext context) throws ContextStateCareTakerIsEmpty {
        ClassContextStateMemento memento = context.getContextStateCareTaker().peek();
        SimpleResult validationResult = memento.getValidationResult();
        if (!validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    @Override
    protected void doIt(ClassContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        ClassHeaderPartHandler classHeaderPartHandler = context.getClassHeaderPartHandler();
        ClassContextStateMemento memento = context.getContextStateCareTaker().peek();
        Class<?> clazz = memento.getClazz();
        String className = memento.getClassName();

        Collector collector = context.getCollector();

        ObjectNode targetNode = (ObjectNode) collector.setTarget(context.getClassPartPath().getPath());
        classHeaderPartHandler.setType(targetNode, clazz.getTypeName());
        classHeaderPartHandler.setName(targetNode, className);
        classHeaderPartHandler.setModifiers(targetNode, clazz.getModifiers());
        collector.reset();
    }
}
