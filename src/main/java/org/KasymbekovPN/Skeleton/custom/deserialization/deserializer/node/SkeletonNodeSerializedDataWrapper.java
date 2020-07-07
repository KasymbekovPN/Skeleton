package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.StringDecoder;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SkeletonNodeSerializedDataWrapper implements NodeSerializedDataWrapper {

    private CharacterIterator iterator;

    public SkeletonNodeSerializedDataWrapper(StringDecoder decoder) {
        this.iterator = new CharacterIterator(decoder.getString());
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Character next() {
        return iterator.next();
    }

    @Override
    public void reset() {
        iterator.reset();
    }

    private static class CharacterIterator implements Iterator<Character>{

        private final String str;

        private int index = 0;

        public CharacterIterator(String str) {
            this.str = str;
        }

        @Override
        public boolean hasNext() {
            return index < str.length();
        }

        @Override
        public Character next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return str.charAt(index++);
        }

        public void reset(){
            index = 0;
        }
    }
}
