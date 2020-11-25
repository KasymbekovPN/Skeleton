package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

public class NumberCharacterChecker implements SimpleChecker<Character> {

    @Override
    public boolean check(Character checkableValue) {
        return Character.isDigit(checkableValue);
    }
}
