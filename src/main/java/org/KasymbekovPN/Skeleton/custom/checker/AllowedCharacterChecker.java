package org.KasymbekovPN.Skeleton.custom.checker;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AllowedCharacterChecker implements SimpleChecker<Character> {

    private final Set<Character> allowedCharacters;

    public AllowedCharacterChecker(Set<Character> allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
    }

    public AllowedCharacterChecker(Character... characters) {
        this.allowedCharacters = new HashSet<>(Arrays.asList(characters));
    }

    @Override
    public boolean check(Character checkableValue) {
        return allowedCharacters.contains(checkableValue);
    }
}
