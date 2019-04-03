package edu.kit.informatik;

/**
 * Modelliert die verschiedenen Spielsteine die es gibt, mit deren Abkuerzungen.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public enum TokenType {
    /**
     * Steht fuer eine normale Spielfigur von Mission Control mit der Abkuerzung +.
     */
    WEAKER_DAWN("+"),
    /**
     * Steht fuer eine Dawn Figur von Mission Control mit der Abkuerzung +.
     */
    DAWN("+"),
    /**
     * Steht fuer vesta von Nature mit der Abkuerzung V.
     */
    VESTA("V"),
    /**
     * Steht fuer ceres von Nature mit der Abkuerzung C.
     */
    CERES("C");

    private String shortcut;

    /**
     * Setzt shortcut auf die Abkuerzung des jeweiligen Enums.
     * 
     * @param shortcut Abkuerzung des jeweiligen Enums.
     */
    TokenType(String shortcut) {
        this.shortcut = shortcut;
    }

    /**
     * Gibt die Abkuerzng eines Types zurueck.
     * 
     * @return Abkuerzung
     */
    public String getShortcut() {
        return shortcut;
    }
}
