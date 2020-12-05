package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.checker.NumberCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.SKDes2NodeContext;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.handler.*;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SKMultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.iterator.DecrementedCharIterator;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKMultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.SKContextStateCareTaker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;
import org.KasymbekovPN.Skeleton.lib.processing.task.context.ContextTask;

import java.util.function.Function;

public class UDes2Node {

    public static MultiContextIds<EntityItem> createContextIds(){
        return new SKMultiContextIds.Builder<EntityItem>(new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.INIT))
                .add(NodeEI.objectEI(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.OBJECT))
                .add(NodeEI.arrayEI(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.ARRAY))
                .add(NodeEI.booleanEI(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.BOOLEAN))
                .add(NodeEI.characterEI(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.CHARACTER))
                .add(NodeEI.numberEI(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.NUMBER))
                .add(NodeEI.stringEI(), new SKSimpleContextIds(UTaskIds.COMMON, UHandlerIds.STRING))
                .build();
    };

    public static ContextProcessor<Des2NodeContext> createProcessor(){

        ContextTask<Des2NodeContext> task = new ContextTask<>(UTaskIds.COMMON);

        task.add(new Des2NodeInitTaskHandler(UHandlerIds.INIT))
                .add(new Des2NodeObjectTaskHandler(UHandlerIds.OBJECT))
                .add(new Des2NodeArrayTaskHandler(UHandlerIds.ARRAY))
                .add(new Des2NodeBooleanTaskHandler(UHandlerIds.BOOLEAN))
                .add(new Des2NodeCharacterTaskHandler(UHandlerIds.CHARACTER))
                .add(new Des2NodeNumberTaskHandler(UHandlerIds.NUMBER))
                .add(new Des2NodeStringTaskHandler(UHandlerIds.STRING));;

        return new ContextProcessor<Des2NodeContext>().add(task);
    }

    public static Des2NodeContext createContext(DecrementedCharIterator iterator){
        return new SKDes2NodeContext(
                createContextIds(),
                new SKContextStateCareTaker<>(),
                iterator,
                createProcessor(),
                createEntityBeginChecker(),
                createValueBeginChecker(),
                createValueEndChecker(),
                createPropertyNameBeginChecker(),
                createPropertyNameEndChecker(),
                createValueNameSeparator()
        );
    }

    public static MultiChecker<EntityItem, Character> createEntityBeginChecker(){
        return new SKMultiChecker.Builder<EntityItem, Character>(new SKSimpleChecker<Character>())
                .add(NodeEI.arrayEI(), new SKSimpleChecker<>('['))
                .add(NodeEI.booleanEI(), new SKSimpleChecker<>('T', 't', 'F', 'f'))
                .add(NodeEI.characterEI(), new SKSimpleChecker<>('\''))
                .add(NodeEI.objectEI(), new SKSimpleChecker<>('{'))
                .add(NodeEI.stringEI(), new SKSimpleChecker<>('"'))
                .add(NodeEI.numberEI(), new NumberCharacterChecker())
                .build();
    }

    public static MultiChecker<EntityItem, Character> createValueBeginChecker() {
        return new SKMultiChecker.Builder<EntityItem, Character>(new SKSimpleChecker<>())
                .add(NodeEI.arrayEI(), new SKSimpleChecker<>('[', ','))
                .add(NodeEI.characterEI(), new SKSimpleChecker<>('\''))
                .add(NodeEI.stringEI(), new SKSimpleChecker<>('"'))
                .build();
    }

    public static MultiChecker<EntityItem, Character> createValueEndChecker(){
        return new SKMultiChecker.Builder<EntityItem, Character>(new SKSimpleChecker<>())
                .add(NodeEI.numberEI(), new SKSimpleChecker<>(',', ']', '}'))
                .add(NodeEI.booleanEI(), new SKSimpleChecker<>(',', ']', '}'))
                .add(NodeEI.characterEI(), new SKSimpleChecker<>('\''))
                .add(NodeEI.stringEI(), new SKSimpleChecker<>('"'))
                .add(NodeEI.arrayEI(), new SKSimpleChecker<>(']'))
                .add(NodeEI.objectEI(), new SKSimpleChecker<>('}'))
                .build();
    }

    public static Function<Character, Boolean> createPropertyNameBeginChecker() {
        return new SKSimpleChecker<>('"');
    }

    public static Function<Character, Boolean> createPropertyNameEndChecker() {
        return new SKSimpleChecker<>('"');
    }

    public static Function<Character, Boolean> createValueNameSeparator() {
        return new SKSimpleChecker<>(':');
    }
}
