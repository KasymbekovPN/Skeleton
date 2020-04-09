package org.KasymbekovPN.Skeleton.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGenerator implements Generator {

    private static final Logger log = LoggerFactory.getLogger(SimpleGenerator.class);

    private GeneratorNode root;
    private GeneratorNode target;

    @Override
    public void beginObject() {
        this.root = new GeneratorObjectNode(null);
        this.target = this.root;
    }

    @Override
    public void beginObject(String property) {
        //< only Object yet
        GeneratorObjectNode generatorObjectNode = new GeneratorObjectNode(target);
        ((GeneratorObjectNode) target).addChild(property, generatorObjectNode);
        target = generatorObjectNode;
    }

    @Override
    public void endObject() {
        target = target.getParent();
    }

    @Override
    public void beginArray(String property) {

    }

    @Override
    public void addProperty(String value) {

    }

    @Override
    public void addProperty(Number value) {

    }

    @Override
    public void addProperty(Boolean value) {

    }

    @Override
    public void addProperty(Character value) {

    }

    @Override
    public void endArray() {

    }

    @Override
    public void addProperty(String property, String value) {

    }

    @Override
    public void addProperty(String property, Number value) {

    }

    @Override
    public void addProperty(String property, Boolean value) {

    }

    @Override
    public void addProperty(String property, Character value) {

    }

    @Override
    public void write(Writer writer) {
        //<
//        if (root != null){
//            root.write(writer);
//        } else {
//            log.error("generatorNode isn't initialized");
//        }
    }
}
