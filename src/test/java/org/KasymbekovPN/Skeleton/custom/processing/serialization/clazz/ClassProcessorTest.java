package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz;

import org.KasymbekovPN.Skeleton.custom.checker.CollectionTypeChecker;
import org.KasymbekovPN.Skeleton.custom.checker.MapTypeChecker;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.classes.ClassProcessorTC0;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.OldClassContext;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.processing.processor.context.OldContextProcessor;
import org.KasymbekovPN.Skeleton.util.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassProcessorTest {

    @Test
    void test() throws Exception {

        CollectionTypeChecker collectionTypeChecker = new UCollectionTypeCheckerBuilder()
                .setTypes(Set.class, List.class)
                .setArgumentTypes(String.class, Integer.class, Float.class)
                .build();

        MapTypeChecker mapTypeChecker = new UMapTypeCheckerBuilder()
                .setTypes(Map.class)
                .setKeyArgumentsTypes(Integer.class)
                .setValueArgumentsTypes(Integer.class)
                .build();


        OldClassContext oldClassContext = UClassSerializationOld.createClassContext(
                USKCollectorPath.DEFAULT_CLASS_PART_PATH,
                USKCollectorPath.DEFAULT_MEMBERS_PATH_PATH,
                ClassProcessorTC0.class,
                USKClassHeaderPartHandler.DEFAULT,
                USKClassMembersPartHandler.DEFAULT
        );

        OldContextProcessor<OldClassContext> classProcessor = UClassSerializationOld.createClassProcessor(
                USKClassHeaderPartHandler.DEFAULT,
                new SKSimpleChecker<Class<?>>(int.class, float.class),
                new SKSimpleChecker<String>("InnerClassProcessorTC0"),
                collectionTypeChecker,
                mapTypeChecker
        );

        classProcessor.handle(oldClassContext);

        //<
        System.out.println(oldClassContext.getCollector().getNode());
        //<
    }
}
