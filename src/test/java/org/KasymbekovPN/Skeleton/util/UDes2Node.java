package org.KasymbekovPN.Skeleton.util;

import org.KasymbekovPN.Skeleton.custom.checker.NumberCharacterChecker;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeContext;
import org.KasymbekovPN.Skeleton.lib.checker.MultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SKMultiChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.MultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKMultiContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.context.ids.SKSimpleContextIds;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.ContextProcessor;

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
        return new ContextProcessor<>();
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

    public static SimpleChecker<Character> createPropertyNameBeginChecker() {
        return new SKSimpleChecker<>('"');
    }

    public static SimpleChecker<Character> createPropertyNameEndChecker() {
        return new SKSimpleChecker<>('"');
    }

    public static SimpleChecker<Character> createValueNameSeparator() {
        return new SKSimpleChecker<>(':');
    }
}
