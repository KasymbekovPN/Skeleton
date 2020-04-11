package org.KasymbekovPN.Skeleton.generator;

import java.util.Map;

public class JsonObjectGenNodeWrHand implements GeneratorNodeWrHand {

    static private final String FIRST_DELIMITER = "";
    static private final String DELIMITER = ",";

    private StringBuilder buffer;
    private Writer writer;

    public JsonObjectGenNodeWrHand(Writer writer, Class clazz) {
        this.writer = writer;
        this.writer.addHandler(clazz, this);
        this.buffer = (StringBuilder) writer.getBuffer();
    }

    @Override
    public void handle(GeneratorNode generatorNode) {
        GeneratorObjectNode node = (GeneratorObjectNode) generatorNode;
        Map<String, GeneratorNode> children = node.getChildren();

        buffer.append("{");
        String delimiter = FIRST_DELIMITER;
        for (Map.Entry<String, GeneratorNode> entry: children.entrySet()) {
            buffer.append(delimiter).append("\"").append(entry.getKey()).append("\":");
            entry.getValue().write(writer);
            delimiter = DELIMITER;
        }
        buffer.append("}");
    }
}
