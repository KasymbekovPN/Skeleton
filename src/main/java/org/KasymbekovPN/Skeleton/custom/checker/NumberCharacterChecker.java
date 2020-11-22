package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

// TODO: 22.11.2020 test
public class NumberCharacterChecker implements SimpleChecker<Character> {

    @Override
    public boolean check(Character checkableValue) {
        return Character.isDigit(checkableValue);
    }
}
