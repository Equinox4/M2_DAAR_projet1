package app;

public enum Operand {
    CONCAT('.'),
    DOT('.'),
    ETOILE('*'),
    ALTERN('|'),
    PROTECTION('\\'),
    PARENTHESEOUVRANT('('),
    PARENTHESEFERMANT(')');

    public final char character;

    private Operand(char character) {
        this.character = character;
    }
}
