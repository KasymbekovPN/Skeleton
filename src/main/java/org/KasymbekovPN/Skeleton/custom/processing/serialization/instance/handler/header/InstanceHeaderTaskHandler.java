package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public class InstanceHeaderTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    public InstanceHeaderTaskHandler(String id) {
        super(id);
    }

    public InstanceHeaderTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContext context) throws ContextStateCareTakerIsEmpty {
        SimpleResult validationResult = context.getContextStateCareTaker().peek().getValidationResult();
        if (!validationResult.isSuccess()){
            simpleResult.setFailStatus(validationResult.getStatus());
        }
    }

    @Override
    protected void doIt(InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        CollectorPath classCollectorPath = context.getClassCollectorPath();
        ClassHeaderPartHandler classHeaderPartHandler = context.getClassHeaderPartHandler();
        Collector collector = context.getCollector();
        InstanceContextStateMemento memento = context.getContextStateCareTaker().peek();

        ObjectNode target = (ObjectNode) collector.setTarget(classCollectorPath.getPath());
        classHeaderPartHandler.setName(target, memento.getClassName());
        classHeaderPartHandler.setModifiers(target, (int) memento.getClassModifiers());
        collector.reset();
    }
}
