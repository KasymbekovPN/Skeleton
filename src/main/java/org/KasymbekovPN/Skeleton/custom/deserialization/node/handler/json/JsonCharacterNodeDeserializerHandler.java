package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

//< !!! del
//public class JsonCharacterNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {
//
//    private static final Character CHARACTER_BEGIN_TRIGGER = '\'';
//    private static final Character CHARACTER_END_TRIGGER = '\'';
//
//    public JsonCharacterNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
//                                                NodeDeserializerHandler parentHandler,
//                                                Node parentNode) {
//        super(dataWrapper, parentHandler, parentNode);
//    }
//
//    @Override
//    public NodeDeserializerHandler run() {
//        Character value = getValue();
//        if (value != null){
//            CharacterNode characterNode = new CharacterNode(parentNode, value);
//            parentHandler.setChildNode(characterNode);
//        }
//
//        return parentHandler;
//    }
//
//    private Character getValue(){
//        Character ch = null;
//        while (dataWrapper.hasNext()){
//            Character next = dataWrapper.next();
//            if (next.equals(CHARACTER_BEGIN_TRIGGER)){
//                if (dataWrapper.hasNext()){
//                    ch = dataWrapper.next();
//                    break;
//                }
//            }
//        }
//
//        while (dataWrapper.hasNext()){
//            Character next = dataWrapper.next();
//            if (next.equals(CHARACTER_END_TRIGGER)){
//                break;
//            }
//        }
//
//        return ch;
//    }
//}
