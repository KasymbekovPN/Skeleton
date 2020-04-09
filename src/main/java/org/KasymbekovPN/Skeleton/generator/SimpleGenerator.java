package org.KasymbekovPN.Skeleton.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGenerator implements Generator {

    private static final Logger log = LoggerFactory.getLogger(SimpleGenerator.class);

    private GeneratorNode root;
    private GeneratorNode target;

    /* false - target is array */
    private boolean targetIsObject;

    public SimpleGenerator() {
        reset();
    }

    @Override
    public void reset() {
        target = root = new GeneratorObjectNode(null);
        targetIsObject = true;
    }

    @Override
    public void beginObject(String property) {
//        if (targetIsObject) {
//            GeneratorObjectNode generatorObjectNode = new GeneratorObjectNode(target);
//            ((GeneratorObjectNode) target).addChild(property, generatorObjectNode);
//            target = generatorObjectNode;
//        }
        //<
        GeneratorNode node = new GeneratorObjectNode(target);
        if (target.addChild(property, node)){
            target = node;
        }
    }

    //< may be only end() ???
    @Override
    public void endObject() {
        if (null != target.getParent()){
            target = target.getParent();
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

    private void addProperty(String property, Object value){
        GeneratorNode node = new GeneratorElementNode(target, value);
        target.addChild(property, node);
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

    @Override
    public String toString() {
        return "SimpleGenerator{" +
                "root=" + root +
                '}';
    }
}
