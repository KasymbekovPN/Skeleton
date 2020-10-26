package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.ClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.SKClassContext;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.ContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;

public class UClassSerialization {

    public static ContextIds createContextIds(){
        return new SKSimpleContextIds(
                UTaskIds.COMMON,
                UHandlerIds.SIGNATURE,
                UHandlerIds.SPECIFIC,
                UHandlerIds.CUSTOM,
                UHandlerIds.COLLECTION,
                UHandlerIds.MAP
        );
    }

    public static ContextStateCareTaker<ClassContextStateMemento> createCateTaker(){
        return new SKContextStateCareTaker<>();
    }

    public static ClassContext createClassContext(Collector collector,
                                                  ContextStateCareTaker<ClassContextStateMemento> cateTaker){
        return new SKClassContext(
                createContextIds(),
                cateTaker,
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                collector,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );
    }
}
