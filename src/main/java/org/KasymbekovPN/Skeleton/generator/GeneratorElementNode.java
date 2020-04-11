package org.KasymbekovPN.Skeleton.generator;

public class GeneratorElementNode implements GeneratorNode {

    private GeneratorNode parent;
    private Object value;

    public GeneratorElementNode(GeneratorNode parent, Object value) {
        this.parent = parent;
        this.value = value;
    }

    @Override
    public GeneratorNode getParent() {
        return parent;
    }

    @Override
    public void write(Writer writer) {
        writer.write(this);
    }

    @Override
    public String toString() {
        return "GeneratorElementNode{" +
                "value=" + value +
                '}';
    }
}
