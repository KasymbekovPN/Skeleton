package org.KasymbekovPN.Skeleton.generator;

public interface Writer {
    void write(GeneratorNode generatorNode);
    void addHandler(Class<? extends GeneratorNode> clazz, GeneratorNodeWrHand handler);
    StringBuilder getBuffer();
}
