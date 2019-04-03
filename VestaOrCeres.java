package edu.kit.informatik;

/**
 * Modelliert eine Figur von Nature, also Ceres oder Vesta. Die Klasse
 * uebernimmt dabei die Eigenschaften des normalen Tokens.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class VestaOrCeres extends Token {
    private Coordinate coord;

    /**
     * Setzt den Tokentype und die Koordinate der Figur.
     * 
     * @param tokenType TokenType der Figur.
     * @param coord     Koordinate der Figur.
     */
    public VestaOrCeres(TokenType tokenType, Coordinate coord) {
        this.tokenType = tokenType;
        this.coord = coord;
    }

    /**
     * Setzt die Koordinate der Figur.
     * 
     * @param coord zu setzende Koordinate.
     */
    public void setCoordinate(Coordinate coord) {
        this.coord = coord;
    }

    /**
     * Gibt die Koordinate der Figur zurueck.
     * 
     * @return Koordinate
     */
    public Coordinate getCoordinate() {
        return coord;
    }
}
