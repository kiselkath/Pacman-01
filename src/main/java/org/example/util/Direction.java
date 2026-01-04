package org.example.util;

/**
 * Domain model: Direction enum
 * Represents movement direction in the game world.
 * Domain layer - no Swing dependencies.
 */
public enum Direction {
    UP('U'),
    DOWN('D'),
    LEFT('L'),
    RIGHT('R');

    private final char charValue;

    Direction(char charValue) {
        this.charValue = charValue;
    }

    public char getCharValue() {
        return charValue;
    }

    /**
     * Convert character to Direction enum
     */
    public static Direction fromChar(char c) {
        if (c == 'U') {
            return UP;
        } else if (c == 'D') {
            return DOWN;
        } else if (c == 'L') {
            return LEFT;
        } else if (c == 'R') {
            return RIGHT;
        } else {
            throw new IllegalArgumentException("Unknown direction: " + c);
        }
    }
}
