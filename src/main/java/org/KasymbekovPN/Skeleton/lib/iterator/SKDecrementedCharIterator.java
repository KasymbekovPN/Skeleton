package org.KasymbekovPN.Skeleton.lib.iterator;

import java.util.NoSuchElementException;

// TODO: 22.11.2020 test
public class SKDecrementedCharIterator implements DecrementedCharIterator {

    private final String line;

    private int cursor = 0;

    public SKDecrementedCharIterator(String line) {
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
