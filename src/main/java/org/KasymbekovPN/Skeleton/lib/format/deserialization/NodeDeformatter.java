package org.KasymbekovPN.Skeleton.lib.format.deserialization;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;

import java.util.*;

public interface NodeDeformatter {
    void setData(NodeSerializedDataWrapper serializedDataWrapper, Node parent);
    Optional<Node> getNode();
    default List<NodeSerializedDataWrapper> getForArray() {return new ArrayList<>();}
    default Map<String, NodeSerializedDataWrapper> getForObject(){return new HashMap<>();}

    //<
//    static Iterator<Character> stringIterator(final String string){
//
//        return new Iterator<Character>(){
//
//            private int index = 0;
//
//            @Override
//            public void remove() {
//                throw new UnsupportedOperationException();
//            }
//
//            @Override
//            public boolean hasNext() {
//                return index < string.length();
//            }
//
//            @Override
//            public Character next() {
//                if (!hasNext())
//                    throw new NoSuchElementException();
//                return string.charAt(index++);
//            }
//        };
//    }
}
