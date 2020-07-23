package org.KasymbekovPN.Skeleton.custom.format.writing.json.handler;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.exception.exception.format.writing.formatter.JsonWritingFormatterBuildException;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.entity.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonWritingFormatterHandler implements WritingFormatterHandler {

    private static final int RESET_BUFFER_LEN = 0;
    private static final String OFFSET_STEP = "    ";
    private static final String NAME_VALUE_SEPARATOR = ":";

    private final Map<EntityItem, WritingFormatter> formatters;

    private Offset offset = new Offset(OFFSET_STEP);
    private StringBuilder buffer = new StringBuilder();

    private JsonWritingFormatterHandler(Map<EntityItem, WritingFormatter> formatters) {
        this.formatters = formatters;
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
    public void addBeginBorder(Node node) {
        buffer.append(offset.get())
                .append(formatters.get(node.getEI()).getBeginBorder().getString());
        offset.inc();
    }

    @Override
    public void addEndBorder(Node node) {
        buffer.append(offset.get())
                .append(formatters.get(node.getEI()).getEndBorder().getString());
        offset.dec();
    }

    @Override
    public void addValue(Node node) {
        buffer.append(NAME_VALUE_SEPARATOR)
                .append(formatters.get(node.getEI()).getValue(node).getString());
    }

    @Override
    public void addPropertyName(Node node, String propertyName) {
        buffer.append(offset.get())
                .append(formatters.get(node.getEI()).getPropertyName(propertyName).getString());
    }

    private static class Offset {

        private final String offsetStep;

        private String offset;
        private int counter;

        public Offset(String offsetStep) {
            this.offsetStep = offsetStep;
            reset();
        }

        private void inc(){
            offset = String.valueOf(offsetStep).repeat(Math.max(0, ++counter));
        }

        private void dec(){
            if (counter > 0){
                counter--;
            }
            offset = String.valueOf(offsetStep).repeat(counter);
        }

        void reset(){
            offset = "";
            counter = 0;
        }

        String get(){
            return offset;
        }
    }

    public static class Builder {

        private final Map<EntityItem, WritingFormatter> formatters = new HashMap<>();

        public Builder addFormatter(EntityItem handlerId, WritingFormatter formatter){
            formatters.put(handlerId, formatter);
            return this;
        }

        public WritingFormatterHandler build() throws Exception {
            Optional<Exception> mayBeException = check();
            if (mayBeException.isPresent()){
                throw mayBeException.get();
            }
            return new JsonWritingFormatterHandler(formatters);
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
