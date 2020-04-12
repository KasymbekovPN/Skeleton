package org.KasymbekovPN.Skeleton.generator;

import org.KasymbekovPN.Skeleton.generator.node.*;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleGenerator implements Generator {

    private static final Logger log = LoggerFactory.getLogger(SimpleGenerator.class);

    private Node root;
    private Node target;

    public SimpleGenerator() {
        reset();
    }

    @Override
    public void reset() {
        target = root = new ObjectNode(null);
    }

    @Override
    public void beginObject(String property) {
        Node node = new ObjectNode(target);
        if (target.addChild(property, node)){
            target = node;
        }
    }

    @Override
    public void beginObject() {
        Node node = new ObjectNode(target);
        if (target.addChild(node)){
            target = node;
        }
    }

    @Override
    public void addProperty(String property, String value) {
        target.addChild(property, new StringNode(target, value));
    }

    @Override
    public void addProperty(String property, Number value) {
        target.addChild(property, new NumberNode(target, value));
    }

    @Override
    public void addProperty(String property, Boolean value) {
        target.addChild(property, new BooleanNode(target, value));
    }

    @Override
    public void addProperty(String property, Character value) {
        target.addChild(property, new CharacterNode(target, value));
    }

    @Override
    public void beginArray(String property) {
        Node node = new ArrayNode(target);
        if (target.addChild(property, node)){
            target = node;
        }
    }

    @Override
    public void beginArray() {
        Node node = new ArrayNode(target);
        if (target.addChild(node)){
            target = node;
        }
    }

    @Override
    public void addProperty(String value) {
        target.addChild(new StringNode(target,value));
    }

    @Override
    public void addProperty(Number value) {
        target.addChild(new NumberNode(target, value));
    }

    @Override
    public void addProperty(Boolean value) {
        target.addChild(new BooleanNode(target, value));
    }

    @Override
    public void addProperty(Character value) {
        target.addChild(new CharacterNode(target, value));
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
    }

    @Override
    public String toString() {
        return "SimpleGenerator{" +
                "root=" + root +
                '}';
    }
}
