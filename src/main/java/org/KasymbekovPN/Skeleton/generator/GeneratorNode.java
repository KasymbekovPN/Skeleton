package org.KasymbekovPN.Skeleton.generator;

public interface GeneratorNode {
    GeneratorNode getParent();
    default boolean addChild(String property, GeneratorNode value) {
        return false;
    };
    default boolean addChild(GeneratorNode value){
        return false;
    };
    //<
//    void write(Writer writer);
    //<
//    void add(String property, GeneratorNode generatorNode);
//    void add(GeneratorNode generatorNode);
}
