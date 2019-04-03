package edu.kit.informatik;

/**
 * Erweitert die Klasse Coordinate und ersetzt den Konstruktor fuer eine Reihe
 * und Spalte, sodass ein Dawn Stein gesetzt werden kann und die Exception
 * korrekt geworfen wird. Dies ist noetig, da die Koordinaten einer Dawn Figur
 * auch teilweise ausserhalb de Bretts liegen koennen.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class CoordinateDawn extends Coordinate {

    /**
     * Konstruktor fuer Koordinaten einer Dawn Figur.
     * 
     * @param row    Reihe der Koordinate
     * @param column Spalte der Koordinate
     * @throws IllegalArgumentException falls Koordinaten mehr als 6 Felder vom
     *                                  Spielbrettrand entfernt sind.
     */
    public CoordinateDawn(int row, int column) throws IllegalArgumentException {
        if (row > (Board.NUMBER_OF_ROWS + 5) || column > (Board.NUMBER_OF_COLUMNS + 5) || row < -6
                || column < -6) {
            throw new IllegalArgumentException("row or column out of bounds!");
        }
        this.row = row;
        this.column = column;
    }
}
