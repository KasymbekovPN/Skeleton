package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr;

import java.util.NoSuchElementException;

public class SKDes2NodeCharItr implements Des2NodeCharItr {

    private final String line;

    private int cursor = 0;

    public SKDes2NodeCharItr(String line) {
        this.line = line;
    }

    @Override
    public void dec() {
        if (cursor > 0){
            cursor--;
        }
    }

    @Override
    public boolean hasNext() {
        return cursor < line.length();
    }

    @Override
    public Character next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        }
        return line.charAt(cursor++);
    }
}
