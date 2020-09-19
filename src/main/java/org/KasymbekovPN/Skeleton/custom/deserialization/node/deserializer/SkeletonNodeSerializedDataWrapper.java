package org.KasymbekovPN.Skeleton.custom.deserialization.node.deserializer;

//< !!! del
//public class SkeletonNodeSerializedDataWrapper implements NodeSerializedDataWrapper {
//
//    private static final Logger log = LoggerFactory.getLogger(SkeletonNodeSerializedDataWrapper.class);
//
//    private CharacterIterator iterator;
//
//    public SkeletonNodeSerializedDataWrapper(StringDecoder decoder) {
//        this.iterator = new CharacterIterator(decoder.getString());
//    }
//
//    @Override
//    public boolean hasNext() {
//        return iterator.hasNext();
//    }
//
//    @Override
//    public Character next() {
//        return iterator.next();
//    }
//
//    @Override
//    public void reset() {
//        iterator.reset();
//    }
//
//    @Override
//    public void decIterator() {
//        iterator.dec();
//    }
//
//    private static class CharacterIterator implements Iterator<Character>{
//
//        private final String str;
//
//        private int index = 0;
//
//        public CharacterIterator(String str) {
//            this.str = str;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return index < str.length();
//        }
//
//        @Override
//        public Character next() {
//            if (!hasNext())
//                throw new NoSuchElementException();
//            return str.charAt(index++);
//        }
//
//        public void reset(){
//            index = 0;
//        }
//
//        public void dec(){
//            if (index > 0){
//                index--;
//            }
//        }
//    }
//}
