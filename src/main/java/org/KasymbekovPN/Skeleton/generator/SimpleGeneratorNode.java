package org.KasymbekovPN.Skeleton.generator;

import java.util.ArrayList;
import java.util.List;

public class SimpleGeneratorNode implements GeneratorNode {

    private Type type;
    private String property;
    private Object value;
    private List<GeneratorNode> children = new ArrayList<GeneratorNode>();

//    static public GeneratorNode createObjectNode(String property) {
//
//    }
//
//    static public GeneratorNode createArrayNode(String property) {
//
//    }
//
//    static public GeneratorNode createStringNode(String property, String value){
//
//    }
//
//    static public GeneratorNode createCharacterNode(String property, Character value){
//
//    }
//
//    static public GeneratorNode createNumberNode(String property, Number value){
//
//    }
//
//    static public GeneratorNode createBooleanNode(String property, Boolean value){
//
//    }

    private SimpleGeneratorNode(Type type, String property, Object value){
        this.type = type;
        this.property = property;
        this.value = value;
    }

    @Override
    public GeneratorNode getParent() {
        return null;
    }

    //
//    @Override
//    public void write(Writer writer) {
//
//    }

//    @Override
//    public void add(String property, GeneratorNode generatorNode) {
//
//    }
//
//    @Override
//    public void add(GeneratorNode generatorNode) {
//
//    }

    private enum Type {
        OBJECT("object"),
        ARRAY("array"),
        STRING("string"),
        CHARACTER("character"),
        NUMBER("number"),
        BOOLEAN("boolean");

        private String type;

        Type(String type) {
            this.type = type;
        }
    }
}
