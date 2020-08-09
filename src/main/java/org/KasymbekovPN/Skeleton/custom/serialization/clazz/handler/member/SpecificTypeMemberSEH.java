package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.member;

import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.ClassExistCheckingHandler;
import org.KasymbekovPN.Skeleton.custom.filter.string.AllowedStringFilter;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SpecificTypeMemberSEH extends BaseSEH {

    private static final Logger log = LoggerFactory.getLogger(SpecificTypeMemberSEH.class);

    private String name;
    private String typeName;
    private int modifiers;

    private final SimpleChecker<Class<?>> clazzChecker;
    private final AnnotationChecker annotationChecker;
    private final CollectorCheckingHandler collectorCheckingHandler;

    public SpecificTypeMemberSEH(SimpleChecker<Class<?>> clazzChecker,
                                 AnnotationChecker annotationChecker,
                                 CollectorCheckingHandler collectorCheckingHandler) {
        this.clazzChecker = clazzChecker;
        this.annotationChecker = annotationChecker;
        this.collectorCheckingHandler = collectorCheckingHandler;
    }

    @Override
    protected boolean checkData(Field field, Collector collector) {

        Class<?> type = field.getType();
        boolean success = clazzChecker.check(type);
        success &= annotationChecker.check(field.getDeclaredAnnotations(), SkeletonMember.class).isPresent();
        success &= checkCollectorContent(collector);

        if (success){
            name = field.getName();
            typeName = type.getCanonicalName();
            modifiers = field.getModifiers();
        }

        return success;
    }

    private boolean checkCollectorContent(Collector collector){

        boolean result = false;

        String processId = UUID.randomUUID().toString();
        Optional<CollectorCheckingProcess> mayBeProcess = collectorCheckingHandler.add(processId);
        if (mayBeProcess.isPresent()){

            new ClassExistCheckingHandler(
                    mayBeProcess.get(),
                    ObjectNode.ei(),
                    collector.getCollectorStructure().getPath(CollectorStructureEI.classEI())
            );

            Map<String, CollectorCheckingResult> handlingResult
                    = collectorCheckingHandler.handle(collector, new AllowedStringFilter(processId));

            collectorCheckingHandler.remove(processId);

            result = handlingResult.get(processId).equals(CollectorCheckingResult.INCLUDE);
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(collector.getCollectorStructure().getPath(CollectorStructureEI.membersEI()));
        collector.beginObject(name);
        collector.addProperty("custom", false);
        collector.addProperty("type", typeName);
        collector.addProperty("modifiers", modifiers);
        collector.reset();

        return true;
    }
}
