package org.KasymbekovPN.Skeleton.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGenerator implements Generator {

    private static final Logger log = LoggerFactory.getLogger(SimpleGenerator.class);

    private GeneratorNode root;
    private GeneratorNode target;

    public SimpleGenerator() {
        reset();
    }

    @Override
    public void reset() {
        target = root = new GeneratorObjectNode(null);
    }

    @Override
    public void beginObject(String property) {
        GeneratorNode node = new GeneratorObjectNode(target);
        if (target.addChild(property, node)){
            target = node;
        }
    }

    @Override
    public void beginObject() {
        GeneratorNode node = new GeneratorObjectNode(target);
        if (target.addChild(node)){
            target = node;
        }
    }

    @Override
    public void addProperty(String property, String value) {
        addProperty(property, (Object)value);
    }

    @Override
    public void addProperty(String property, Number value) {
        addProperty(property, (Object)value);
    }

    @Override
    public void addProperty(String property, Boolean value) {
        addProperty(property, (Object)value);
    }

    @Override
    public void addProperty(String property, Character value) {
        addProperty(property, (Object)value);
    }

    @Override
    public void beginArray(String property) {
        GeneratorNode node = new GeneratorArrayNode(target);
        if (target.addChild(property, node)){
            target = node;
        }
    }

    @Override
    public void beginArray() {
        GeneratorNode node = new GeneratorArrayNode(target);
        if (target.addChild(node)){
            target = node;
        }
    }

    @Override
    public void addProperty(String value) {
        addProperty((Object) value);
    }

    @Override
    public void addProperty(Number value) {
        addProperty((Object) value);
    }

    @Override
    public void addProperty(Boolean value) {
        addProperty((Object) value);
    }

    @Override
    public void addProperty(Character value) {
        addProperty((Object) value);
    }

    private void addProperty(String property, Object value){
        GeneratorNode node = new GeneratorElementNode(target, value);
        target.addChild(property, node);
    }

    private void addProperty(Object value){
        GeneratorNode node = new GeneratorElementNode(target, value);
        target.addChild(node);
    }

    @Override
    public void end() {
        if (null != target.getParent()){
            target = target.getParent();
        }
    }

    @Override
    public void write(Writer writer) {
        root.write(writer);
        //<
//        if (root != null){
//            root.write(writer);
//        } else {
//            log.error("generatorNode isn't initialized");
//        }
    }

    @Override
    public String toString() {
        return "SimpleGenerator{" +
                "root=" + root +
                '}';
    }
}
