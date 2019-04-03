package edu.kit.informatik;

/**
 * Modelliert eine Koordinate bestehend aus Zeile und Spalte.
 * 
 * @author Daniel
 * @version 2.0
 * 
 */
public class Coordinate {
    /**
     * Zeile der Koordinate.
     */
    protected int row;
    /**
     * Spalte der Koordinate.
     */
    protected int column;

    /**
     * Wirft Exception, falls Zeile < 0 oder groesser als Anzahl der Zeilen des
     * Spielbretts oder Spalten < 0 oder groesser als Anzahl der Spalten des Bretts.
     * Sonst setzte row und column.
     * 
     * @param row    Zeile
     * @param column Spalte
     * @throws IllegalArgumentException falls Zeile < 0 oder > als Anzahl der Zeilen
     *                                  des Spielbretts oder Spalten < 0 oder > als
     *                                  Anzahl der Spalten des Bretts
     */
    public Coordinate(int row, int column) throws IllegalArgumentException {
        if (row >= Board.NUMBER_OF_ROWS || column >= Board.NUMBER_OF_COLUMNS || row < 0
                || column < 0) {
            throw new IllegalArgumentException("row or column out of bounds!");
        }
        this.row = row;
        this.column = column;
    }

    /**
     * Setze Zeile und Spalte auf 0.
     */
    public Coordinate() {
        row = 0;
        column = 0;
    }

    /**
     * Gibt die Reihe der Koordinate zurueck.
     * 
     * @return Reihe
     */
    public int getRow() {
        return row;
    }

    /**
     * Gibt die Spalte der Koordinate zurueck.
     * 
     * @return Spalte
     */
    public int getColumn() {
        return column;
    }

    /**
     * Prueft ob eine zweite Koordinate eine benachbarte Koordinate zu diesem Objekt
     * ist. Gibt true zurueck falls ja, sonst false.
     * 
     * @param coordTwo zu pruefende zweite Koordinate.
     * @return true, falls benachbart, sonst false.
     */
    public boolean neighbor(Coordinate coordTwo) {
        int rowTwo = coordTwo.getRow();
        int columnTwo = coordTwo.getColumn();

        if (row + 1 == rowTwo || row - 1 == rowTwo || column + 1 == columnTwo
                || column - 1 == columnTwo) {
            return true;
        }
        return false;
    }

    /**
     * Misst die Entfernung einer zweiten Koordinate zu diesem Objekt. Dabei muessen
     * die Zeilen oder Spalten gleich sein.
     * 
     * @param coordTwo zweite Koordinate
     * @return Entfernung der zwei Koordinaten
     */
    public int getDistance(Coordinate coordTwo) {
        int rowTwo = coordTwo.getRow();
        int columnTwo = coordTwo.getColumn();
        int rowOne = row;
        int columnOne = column;

        if (rowOne == rowTwo) {
            if (columnOne > columnTwo) {
                int tmp = columnOne;
                columnOne = columnTwo;
                columnTwo = tmp;
            }
            return (columnTwo - columnOne + 1);
        } else {
            if (rowOne > rowTwo) {
                int tmp = rowOne;
                rowOne = rowTwo;
                rowTwo = tmp;
            }
            return (Math.abs(rowTwo - rowOne) + 1);
        }
    }

    /**
     * Ueberschreibt equals. Gibt true zurueck, wenn Reihen und Spalten zweier
     * Koordinaten gleich sind.
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Coordinate)) {
            return false;
        }
        Coordinate coordTwo = (Coordinate) object;
        if (coordTwo.getRow() == this.row && coordTwo.getColumn() == this.column) {
            return true;
        }
        return false;
    }

    /**
     * Gibt die Koordinate als String zurueck.
     */
    @Override
    public String toString() {
        return row + ";" + column;
    }
}
