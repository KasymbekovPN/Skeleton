package org.KasymbekovPN.Skeleton.custom.checker;

import java.util.function.Function;

public class NumberCharacterChecker implements Function<Character, Boolean> {
    @Override
    public Boolean apply(Character character) {
        return Character.isDigit(character);
    }
}
