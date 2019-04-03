package edu.kit.informatik;

/**
 * Modelliert eine Figur von entweder Mission Control oder Nature.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class Token {
    /**
     * Typ der Spielfigur als TokenType.
     */
    protected TokenType tokenType;

    /**
     * Konstruktor, der der TokenType auf null setzt.
     */
    public Token() {
        tokenType = null;
    }

    /**
     * Konstruktor, welcher den TokenType auf einen uebergebenen Wert setzt.
     * 
     * @param tokenType zu setzender TokenType.
     */
    public Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Gibt den TokenType zurueck.
     * 
     * @return TokenType
     */
    public TokenType getTokenType() {
        return tokenType;
    }

    /**
     * Gibt true zurueck wenn der TokenType null ist, sonst false.
     * 
     * @return true, wenn TokenType null, sonst false.
     */
    public boolean isTokenTypeNull() {
        if (tokenType == null) {
            return true;
        }
        return false;
    }
}
