package org.KasymbekovPN.Skeleton.generator;

import org.KasymbekovPN.Skeleton.generator.node.*;
import org.KasymbekovPN.Skeleton.generator.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimpleGenerator implements Generator {

    private static final Logger log = LoggerFactory.getLogger(SimpleGenerator.class);

    private Node root;
    private Node target;

    public SimpleGenerator() {
        clear();
    }

    @Override
    public void clear() {
        target = root = new ObjectNode(null);
    }

    @Override
    public void reset() {
        while (target.getParent() != null){
            target = target.getParent();
            end();
        }
    }

    @Override
    public void beginObject(String property) {
        target.addChild(property, new ObjectNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void beginObject() {
        target.addChild(new ObjectNode(target)).ifPresent(value -> target = value);
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
        target.addChild(property, new ArrayNode(target)).ifPresent(value -> target = value);
    }

    @Override
    public void beginArray() {
        target.addChild(new ArrayNode(target)).ifPresent(value -> target = value);
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
    public void setTarget(List<String> path) {
        reset();
        setEachTarget(path);
    }

    private void setEachTarget(List<String> path){
        String pathItem = path.remove(0);
        beginObject(pathItem);
        if (path.size() > 0){
            setEachTarget(path);
        }
    }

    @Override
    public String toString() {
        return "SimpleGenerator{" +
                "root=" + root +
                '}';
    }
}
