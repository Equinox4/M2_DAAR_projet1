package app;

public enum Operand {
    CONCAT(0xC04CA7, '.'),
    DOT(0xD07, '.'),
    ETOILE(0xE7011E, '*'),
    ALTERN(0xA17E54, '|'),
    PROTECTION(0xBADDAD, '\\'),
    PARENTHESEOUVRANT(0x16641664, '('),
    PARENTHESEFERMANT(0x51515151, ')');

    public final int val;
    public final char character;

    private Operand(int val, char character) {
        this.character = character;
        this.val = val;
    }

    public int getValue() {
        return this.val;
    }

    public char getChar() {
        return this.character;
    }

    @Override
    public String toString() {
        return Character.toString(this.character);
    }
}
