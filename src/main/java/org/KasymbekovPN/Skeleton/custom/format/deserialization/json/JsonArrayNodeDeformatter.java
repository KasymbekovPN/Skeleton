package org.KasymbekovPN.Skeleton.custom.format.deserialization.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.NodeDeformatter;

import java.util.*;

public class JsonArrayNodeDeformatter implements NodeDeformatter {

//    private static final String DELIMITER = ",";
//    private static final String SQUARE = "square";
//    private static final String BRACE = "brace";
//    private static final Character SQUARE_BEGIN = '[';
//    private static final Character SQUARE_END = ']';
//    private static final Character BRACE_BEGIN = '{';
//    private static final Character BRACE_END = '}';
//    private static final Character STOP_COUNT = '"';

    private Node node;
    private List<NodeSerializedDataWrapper> forArray;
    //<
//    private Map<String, Integer> bracketCounters;

    public JsonArrayNodeDeformatter() {
        resetMembers();
    }

    @Override
    public void setData(NodeSerializedDataWrapper serializedDataWrapper, Node parent) {
        resetMembers();
    }

    @Override
    public Optional<Node> getNode() {
        return Optional.empty();
    }

    @Override
    public List<NodeSerializedDataWrapper> getForArray() {
        return null;
    }

    private void resetMembers(){
        node = null;
        forArray = new ArrayList<>();
        //<
//        bracketCounters = new HashMap<>(){{
//           put(SQUARE, 0);
//           put(BRACE, 0);
//        }};
    }

    private List<NodeSerializedDataWrapper> splitDataWrapper(NodeSerializedDataWrapper dataWrapper){
//        DataSplitter dataSplitter = new DataSplitter();
//        String data = dataWrapper.getData();
//        for (int i = 0; i < data.length(); i++) {
//            dataSplitter.add(data.charAt(i));
//        }

        //<
        return null;
    }

    private static class DataSplitter{

        private int state = 0;

        private int squareCounter = -1;
        private int braceCounter = 0;

        private StringBuilder buffer = new StringBuilder();
        private List<StringBuilder> buffers = new ArrayList<>();

        public int getState() {
            return state;
        }

        //< get data list

        void add(Character ch){
            switch (state) {
                case 0:
                    add0(ch);
                    break;
                case 1:
                    add1(ch);
                    break;
                case 2:
                    add2(ch);
                    break;
                case 3:
                    add3(ch);
                    break;
            }
        }

        private void add0(Character ch){
            if (ch.equals('[')) {
                squareCounter = 0;
                state = 1;
            }
        }

        private void add1(Character ch){
            switch (ch){
                case '[':
                    squareCounter++;
                    break;
                case ']':
                    squareCounter--;
                    if (squareCounter == -1) {
                        state = 4;
                        return;
                    }
                    break;
                case '{':
                    braceCounter++;
                    break;
                case '}':
                    braceCounter--;
                    break;
                case '"':
                    state = 2;
                    break;
                case '\\':
                    state = 3;
                    break;
                case ',':
                    if (braceCounter == 0 && squareCounter == 0){
                        buffers.add(buffer);
                        buffer.setLength(0);
                        return;
                    }
                    break;
            }

            buffer.append(ch);
        }

        private void add2(Character ch){
            if (ch.equals('"')){
                state = 1;
            }
            buffer.append(ch);
        }

        private void add3(Character ch){
            state = 2;
            buffer.append(ch);
        }
    }
}
