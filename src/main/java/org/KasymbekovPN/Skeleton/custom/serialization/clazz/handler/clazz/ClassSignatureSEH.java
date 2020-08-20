package org.KasymbekovPN.Skeleton.custom.serialization.clazz.handler.clazz;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.handler.AnnotationChecker;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.BaseSEH;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassSignatureSEH extends BaseSEH {

    private final AnnotationChecker annotationChecker;
    private final CollectorPath collectorServicePath;

    private String name;
    private int modifiers;
    private List<String> classPath;

    public ClassSignatureSEH(AnnotationChecker annotationChecker,
                             CollectorPath collectorServicePath) {
        this.annotationChecker = annotationChecker;
        this.collectorServicePath = collectorServicePath;
    }

    @Override
    protected boolean checkData(Class<?> clazz, Collector collector) {
        boolean result = false;
        Optional<Annotation> maybeAnnotation = annotationChecker.check(clazz.getDeclaredAnnotations(), SkeletonClass.class);
        Optional<List<String>> mayBeClassPath = extractClassPath(collector.getNode());
        if (maybeAnnotation.isPresent() && mayBeClassPath.isPresent()){
            SkeletonClass annotation = (SkeletonClass) maybeAnnotation.get();
            result = true;
            name = annotation.name();
            modifiers = clazz.getModifiers();
            classPath = mayBeClassPath.get();
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        collector.setTarget(classPath);
        collector.addProperty("name", name);
        collector.addProperty("modifiers", modifiers);
        collector.reset();

        return false;
    }

    private Optional<List<String>> extractClassPath(Node node){
        if (collectorServicePath.getEi().equals(ArrayNode.ei())){
            Optional<Node> mayBeServicePathNode = node.getChild(collectorServicePath);
            if (mayBeServicePathNode.isPresent()){
                List<String> classPath = new ArrayList<>();
                ArrayNode servicePathNode = (ArrayNode) mayBeServicePathNode.get();
                for (Node pathPart : servicePathNode.getChildren()) {
                    classPath.add(((StringNode)pathPart).getValue());
                }

                return Optional.of(classPath);
            }
        }

        return Optional.empty();
    }
}
