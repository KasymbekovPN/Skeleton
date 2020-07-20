package org.KasymbekovPN.Skeleton.custom.format.writing.formatter;

import org.KasymbekovPN.Skeleton.custom.format.deserialization.StringStringDecoder;
import org.KasymbekovPN.Skeleton.exception.exception.format.writing.formatter.JsonWritingFormatterBuildException;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.entity.NodeEI;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.format.writing.formatter.WritingFormatter;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonWritingFormatter implements WritingFormatter {

    private static final int RESET_BUFFER_LEN = 0;
    private static final String OFFSET_STEP = "    ";

    private final Map<EntityItem, WritingFormatterHandler> handlers;

    private Offset offset = new Offset(OFFSET_STEP);
    private StringBuilder buffer = new StringBuilder();

    private JsonWritingFormatter(Map<EntityItem, WritingFormatterHandler> handlers) {
        this.handlers = handlers;

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
                .append(handlers.get(node.getEI()).getBeginBorder());
        offset.inc();
    }

    @Override
    public void addEndBorder(Node node) {
        buffer.append(offset.get())
                .append(handlers.get(node.getEI()).getEndBorder());
        offset.dec();
    }

    @Override
    public void addValue(Node node) {
        //< ??? in const
        buffer.append(" : ")
                .append(handlers.get(node.getEI()).getValue(node));
    }

    @Override
    public void addPropertyName(Node node, String propertyName) {
        buffer.append(offset.get())
                .append(handlers.get(node.getEI()).getPropertyName(propertyName));
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

        private final Map<EntityItem, WritingFormatterHandler> handlers = new HashMap<>();

        public Builder addHandler(EntityItem handlerId, WritingFormatterHandler handler){
            handlers.put(handlerId, handler);
            return this;
        }

        public WritingFormatter build() throws Exception {
            Optional<Exception> mayBeException = check();
            if (mayBeException.isPresent()){
                throw mayBeException.get();
            }
            return new JsonWritingFormatter(handlers);
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
            return new NodeEI().checkInstancesStrict(handlers.keySet())
                    ? "There is/are invalid keys; "
                    : "";
        }

        private String checkHandlers(){
            StringBuilder message = new StringBuilder();
            for (Map.Entry<EntityItem, WritingFormatterHandler> entry : handlers.entrySet()) {
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
