package edu.kit.informatik;

import java.util.List;

/**
 * Fasst alle Parameter einer Eingabe zusammen. Dazu gehoeren die gewurfelte
 * Zahl und die Koordinaten.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class Parameter {
    private int rolled;
    private List<Coordinate> coordinates;

    /**
     * Setzt die Koordinaten und die gewuerfelte Zahl.
     * 
     * @param coordinates zu setzende Koordinaten.
     * @param rolled      zu setzende gewuerfelte Zahl.
     */
    public Parameter(List<Coordinate> coordinates, int rolled) {
        this.coordinates = coordinates;
        this.rolled = rolled;
    }

    /**
     * Gibt die Koordinatenliste zurueck.
     * 
     * @return Liste der Koordinaten.
     */
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * Gibt die gewuerfelte Zahl zurueck.
     * 
     * @return gewuerfelte zahl.
     */
    public int getRolled() {
        return rolled;
    }
}
