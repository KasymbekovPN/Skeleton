package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.itr;

import java.util.NoSuchElementException;

// TODO: 16.11.2020 del
public class SKDes2NodeCharItrOld implements Des2NodeCharItrOld {

    private final String line;

    private int cursor = 0;

    public SKDes2NodeCharItrOld(String line) {
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
