package org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.finder;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.node.context.Des2NodeMode;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

import java.util.EnumMap;
import java.util.Map;

public class JsonFinder implements Finder {

    private final EnumMap<Des2NodeMode, SimpleChecker<Character>> checkers;
    private final SimpleChecker<Character> propertyNameBeginChecker;
    private final SimpleChecker<Character> propertyNameEndChecker;
    private final SimpleChecker<Character> nameValueSeparatorChecker;
    private final EnumMap<Des2NodeMode, SimpleChecker<Character>> valueBeginCheckers;
    private final EnumMap<Des2NodeMode, SimpleChecker<Character>> valueEndCheckers;

    public JsonFinder(EnumMap<Des2NodeMode, SimpleChecker<Character>> checkers,
                      SimpleChecker<Character> propertyNameBeginChecker,
                      SimpleChecker<Character> propertyNameEndChecker,
                      SimpleChecker<Character> nameValueSeparatorChecker,
                      EnumMap<Des2NodeMode, SimpleChecker<Character>> valueBeginCheckers,
                      EnumMap<Des2NodeMode, SimpleChecker<Character>> valueEndCheckers) {
        this.checkers = checkers;
        this.propertyNameBeginChecker = propertyNameBeginChecker;
        this.propertyNameEndChecker = propertyNameEndChecker;
        this.nameValueSeparatorChecker = nameValueSeparatorChecker;
        this.valueBeginCheckers = valueBeginCheckers;
        this.valueEndCheckers = valueEndCheckers;
    }

    @Override
    public Des2NodeMode findEntityBegin(Character character) {
        for (Map.Entry<Des2NodeMode, SimpleChecker<Character>> entry : checkers.entrySet()) {
            if (entry.getValue().check(character)){
                return entry.getKey();
            }
        }

        return Des2NodeMode.UNKNOWN;
    }

    @Override
    public boolean findPropertyNameBegin(Character character) {
        return propertyNameBeginChecker.check(character);
    }

    @Override
    public boolean findPropertyNameEnd(Character character) {
        return propertyNameEndChecker.check(character);
    }

    @Override
    public boolean findNameValueSeparator(Character character) {
        return nameValueSeparatorChecker.check(character);
    }

    @Override
    public boolean findValueBegin(Character character, Des2NodeMode mode) {
        if (valueBeginCheckers.containsKey(mode)){
            return valueBeginCheckers.get(mode).check(character);
        }
        return false;
    }

    @Override
    public boolean findValueEnd(Character character, Des2NodeMode mode) {
        if (valueEndCheckers.containsKey(mode)){
            return valueEndCheckers.get(mode).check(character);
        }
        return false;
    }
}