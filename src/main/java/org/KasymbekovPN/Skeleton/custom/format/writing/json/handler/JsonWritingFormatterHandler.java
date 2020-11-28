package org.KasymbekovPN.Skeleton.custom.format.writing.json.handler;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.exception.exception.format.writing.formatter.JsonWritingFormatterBuildException;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.entity.node.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.offset.Offset;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

import java.util.*;

public class JsonWritingFormatterHandler implements WritingFormatterHandler {

    private static final int RESET_BUFFER_LEN = 0;

    private final Map<EntityItem, WritingFormatter> formatters;
    private final Offset offset;

    private final StringBuilder buffer = new StringBuilder();

    private JsonWritingFormatterHandler(Map<EntityItem, WritingFormatter> formatters, Offset offset) {
        this.formatters = formatters;
        this.offset = offset;
        reset();
    }

    @Override
    public StringDecoder getDecoder() {
        return new StringStringDecoder(buffer.toString());
    }

    @Override
    public void reset() {
        buffer.setLength(RESET_BUFFER_LEN);
        offset.reset();
    }

    @Override
    public List<String> getDelimiters(int size, Node node) {
        ArrayList<String> delimiters = new ArrayList<>();
        for (StringDecoder rawDelimiter : formatters.get(node.getEI()).getDelimiters(size)) {
            delimiters.add(rawDelimiter.getString());
        }
        return delimiters;
    }

    @Override
    public void addDelimiter(Iterator<String> delimiterIterator) {
        if (delimiterIterator.hasNext()){
            buffer.append(delimiterIterator.next());
        }
    }

    @Override
    public void addBeginBorder(Node node) {
        buffer.append(formatters.get(node.getEI()).getBeginBorder().getString());
    }

    @Override
    public void addEndBorder(Node node) {
        buffer.append(formatters.get(node.getEI()).getEndBorder().getString());
    }

    @Override
    public void addValue(Node node) {
        buffer.append(formatters.get(node.getEI()).getValue(node).getString());
    }

    @Override
    public void addPropertyName(Node node, String propertyName) {
        buffer.append(formatters.get(node.getEI()).getPropertyName(propertyName).getString());
    }

    public static class Builder {

        private final Map<EntityItem, WritingFormatter> formatters = new HashMap<>();
        private final Offset offset;

        public Builder(Offset offset) {
            this.offset = offset;
        }

        public Builder addFormatter(EntityItem handlerId, WritingFormatter formatter){
            formatters.put(handlerId, formatter);
            return this;
        }

        public WritingFormatterHandler build() throws Exception {
            Optional<Exception> mayBeException = check();
            if (mayBeException.isPresent()){
                throw mayBeException.get();
            }
            return new JsonWritingFormatterHandler(formatters, offset);
        }

        private Optional<Exception> check(){
            String message = checkKeys();
            message += checkHandlers();

            if (message.isEmpty()){
                return Optional.of(new JsonWritingFormatterBuildException(message));
            } else {
                return Optional.empty();
            }
        }

        private String checkKeys(){
            return new NodeEI().checkInstancesStrict(formatters.keySet())
                    ? "There is/are invalid keys; "
                    : "";
        }

        private String checkHandlers(){
            StringBuilder message = new StringBuilder();
            for (Map.Entry<EntityItem, WritingFormatter> entry : formatters.entrySet()) {
                if (entry.getValue() == null){
                    message.append("Handler with key ")
                            .append(entry.getKey())
                            .append("is null; ");
                }
            }

            return message.toString();
        }
    }
}
