package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

//import org.KasymbekovPN.Skeleton.lib.node.Node;
//import org.KasymbekovPN.Skeleton.lib.node.StringNode;
//import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
//import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;
//
////< !!! del
//public class JsonStringNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {
//
//    private static final Character SHIELD = '\\';
//    private static final Character STRING_END_TRIGGER = '"';
//
//    public JsonStringNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
//                                             NodeDeserializerHandler parentHandler,
//                                             Node parentNode) {
//        super(dataWrapper, parentHandler, parentNode);
//    }
//
//    @Override
//    public NodeDeserializerHandler run() {
//        String value = getValue();
//        StringNode stringNode = new StringNode(parentNode, value);
//        parentHandler.setChildNode(stringNode);
//        return parentHandler;
//    }
//
//    private String getValue(){
//        boolean shielded = false;
//        StringBuilder rawValue = new StringBuilder();
//        while(dataWrapper.hasNext()){
//            Character next = dataWrapper.next();
//
//            if (shielded){
//                shielded = false;
//                rawValue.append(next);
//            } else {
//                if (next.equals(STRING_END_TRIGGER)){
//                    break;
//                } else {
//                    if (next.equals(SHIELD)){
//                        shielded = true;
//                    }
//                    rawValue.append(next);
//                }
//            }
//        }
//
//        return rawValue.toString();
//    }
//}