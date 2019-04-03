package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelliert ein 11x15 groﬂes Spielbrett des Spiels und initialisiert es mit
 * null an jedem Feld. Jedes Feld kann von einer Figur besetzt werden.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class Board {
    /**
     * Anzahl der Zeilen eines Spielbretts
     */
    public static final int NUMBER_OF_ROWS = 11;
    /**
     * Anzahl der Spalten eines Spielbretts
     */
    public static final int NUMBER_OF_COLUMNS = 15;

    private Token[][] board = new Token[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

    /**
     * Initialisiert das Feld mit null an jeder Stelle.
     */
    public Board() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * Entfernt eine Figur von einem Feld und schreibt dort stattdessen null.
     * 
     * @param coordinate Koordinate der Figur
     */
    public void deleteToken(Coordinate coordinate) {
        board[coordinate.getRow()][coordinate.getColumn()] = null;
    }

    /**
     * resets the board. Writes null on every field of the board.
     * 
     * Setzt das Brett zurueck. Schreibt an jede Stelle null.
     */
    public void reset() {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * Gibt die Anzahl der Felder zurueck, welche von einem gegebenen Feld als
     * Startfeld aus erreicht werden koennen, ohne ueber andere Figuren zu gehen.
     * 
     * @param token Ausgangsfigur
     * @return Anzahl der Felder, welche erreicht werden koennen, ohne ueber andere
     *         Figuren zu gehen.
     */
    public int getPartResult(VestaOrCeres token) {
        List<Coordinate> result = new ArrayList<Coordinate>();
        List<Coordinate> waiting = new ArrayList<Coordinate>();
        Coordinate coord = token.getCoordinate();
        waiting.addAll(getEmptyNeighbor(coord));

        while (waiting.size() > 0) {
            if (!result.contains(waiting.get(0))) {
                result.add(waiting.get(0));
                for (int i = 0; i < getEmptyNeighbor(waiting.get(0)).size(); i++) {
                    if (!waiting.contains(getEmptyNeighbor(waiting.get(0)).get(i))
                            && !result.contains(getEmptyNeighbor(waiting.get(0)).get(i))) {
                        waiting.add(getEmptyNeighbor(waiting.get(0)).get(i));
                    }
                }
            }
            waiting.remove(0);
        }
        return result.size();
    }

    /**
     * Gibt eine Liste von Koordinaten von benachbarten Figuren einer gegebenen
     * Figur zurueck. Nachbar bedeutet hier eine Figur, welche genau ein Feld
     * (horizontal oder vertikal) von der Ausgangsfigur entfernt ist.
     * 
     * @param coord Koordinate, von welcher die Nachbarn gefunden werden sollen.
     * @return eine Liste der Koordinaten der Nachbarn.
     */
    public List<Coordinate> getEmptyNeighbor(Coordinate coord) {
        List<Coordinate> emptyNeighbors = new ArrayList<Coordinate>();
        int row = coord.getRow();
        int column = coord.getColumn();
        if (row - 1 >= 0 && board[row - 1][column] == null) {
            emptyNeighbors.add(new Coordinate(row - 1, column));
        }
        if (row + 1 < NUMBER_OF_ROWS && board[row + 1][column] == null) {
            emptyNeighbors.add(new Coordinate(row + 1, column));
        }
        if (column - 1 >= 0 && board[row][column - 1] == null) {
            emptyNeighbors.add(new Coordinate(row, column - 1));
        }
        if (column + 1 < NUMBER_OF_COLUMNS && board[row][column + 1] == null) {
            emptyNeighbors.add(new Coordinate(row, column + 1));
        }
        return emptyNeighbors;
    }

    /**
     * Setzt eine Figur auf ein Feld in einer bestimmten Zeile und Reihe. Wirft
     * Exception wenn das Feld schon besetzt ist.
     * 
     * @param newToken zu setztende Figur
     * @param coord    Koordinate an die die Figur gesetzt werden soll.
     * @throws IllegalArgumentException falls Feld schon besetzt ist.
     */
    public void setToken(Token newToken, Coordinate coord) throws IllegalArgumentException {
        int row = coord.getRow();
        int column = coord.getColumn();
        if (isAlreadyOccupied(row, column)) {
            throw new IllegalArgumentException("field is already occupied by a token!");
        }
        board[row][column] = newToken;
    }

    /**
     * Setzt eine DAWN Figur von einer Koordinate zu einer anderen in einer geraden,
     * horizontalen oder vertikalen Linie. Wenn Teile der Figur ausserhalb des
     * Feldes sind wird diese so abgeschnitten, dass die Teile die auf dem Feld sind
     * korrekt gesetzt werden kˆnnen.
     * 
     * @param coordOne erste Koordinate
     * @param coordTwo zweite Koordinate
     * @throws IllegalArgumentException wenn ein Feld in der Linie schon besetzt ist
     *                                  oder nicht Reihe eins und Reihe zwei oder
     *                                  nicht Spalte eins und Spalte zwei gleich
     *                                  sind
     */
    public void setDAWN(Coordinate coordOne, Coordinate coordTwo) throws IllegalArgumentException {
        int rowOne = coordOne.getRow();
        int columnOne = coordOne.getColumn();
        int rowTwo = coordTwo.getRow();
        int columnTwo = coordTwo.getColumn();

        if (rowOne < 0) {
            rowOne = 0;
        }
        if (rowTwo < 0) {
            rowTwo = 0;
        }
        if (columnOne < 0) {
            columnOne = 0;
        }
        if (columnTwo < 0) {
            columnTwo = 0;
        }
        if (rowOne > NUMBER_OF_ROWS - 1) {
            rowOne = NUMBER_OF_ROWS - 1;
        }
        if (rowTwo > NUMBER_OF_ROWS - 1) {
            rowTwo = NUMBER_OF_ROWS - 1;
        }
        if (columnOne > NUMBER_OF_COLUMNS - 1) {
            columnOne = NUMBER_OF_COLUMNS - 1;
        }
        if (columnTwo > NUMBER_OF_COLUMNS - 1) {
            columnTwo = NUMBER_OF_COLUMNS - 1;
        }
        setToken(new Token(TokenType.DAWN), new Coordinate(rowOne, columnOne),
                new Coordinate(rowTwo, columnTwo));
    }

    /**
     * Setzt eine Figur von einer Koordinate zu einer anderen in einer geraden,
     * horizontalen oder vertikalen Linie.
     *
     * @param newToken zu setzende Figur
     * @param coordOne erste Koordinate
     * @param coordTwo zweite Koordinate
     * @throws IllegalArgumentException wenn ein Feld in der Linie schon besetzt ist
     *                                  oder nicht Reihe eins und Reihe zwei oder
     *                                  nicht Spalte eins und Spalte zwei gleich
     *                                  sind.
     */
    public void setToken(Token newToken, Coordinate coordOne, Coordinate coordTwo)
            throws IllegalArgumentException {
        int rowOne = coordOne.getRow();
        int columnOne = coordOne.getColumn();
        int rowTwo = coordTwo.getRow();
        int columnTwo = coordTwo.getColumn();

        if (rowOne == rowTwo) {
            if (columnOne > columnTwo) {
                int tmp = columnOne;
                columnOne = columnTwo;
                columnTwo = tmp;
            }

            if (isAlreadyOccupied(rowOne, columnOne, rowTwo, columnTwo)) {
                throw new IllegalArgumentException("field is already occupied by a token!");
            }
            for (int i = columnOne; i <= columnTwo; i++) {
                board[rowOne][i] = newToken;
            }
        } else if (columnOne == columnTwo) {
            if (rowOne > rowTwo) {
                int tmp = rowOne;
                rowOne = rowTwo;
                rowTwo = tmp;
            }

            if (isAlreadyOccupied(rowOne, columnOne, rowTwo, columnTwo)) {
                throw new IllegalArgumentException("field is already occupied by a token!");
            }
            for (int i = rowOne; i <= rowTwo; i++) {
                board[i][columnOne] = newToken;
            }
        } else {
            throw new IllegalArgumentException("either rows or columns must be equal!");
        }

    }

    /**
     * Gibt true zurueck wenn auf einer Linie zwischen zwei Koordinaten bereits ein
     * Feld besetzt ist, sonst false.
     * 
     * @param rowOne    Zeile der ersten Koordinate
     * @param columnOne Spalte der ersten Koordinate
     * @param rowTwo    Zeile der zweiten Koordinate
     * @param columnTwo Spalte der zweiten Koordinate
     * @return true wenn ein Feld bereits besetzt ist, sonst false
     */
    public boolean isAlreadyOccupied(int rowOne, int columnOne, int rowTwo, int columnTwo) {
        if (rowOne == rowTwo) {
            for (int i = columnOne; i <= columnTwo; i++) {
                if (isAlreadyOccupied(rowOne, i)) {
                    return true;
                }
            }
        } else {
            for (int i = rowOne; i <= rowTwo; i++) {
                if (isAlreadyOccupied(i, columnOne)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gibt true zurueck wenn das Feld mit der angegebenen Zeile und Spalte besetzt
     * ist, sonst false.
     * 
     * @param row    Zeile des zu pruefenden Feldes
     * @param column Spalte des zu pruefenden Feldes
     * @return true wenn das Feld besetzt ist, sonst false
     */
    public boolean isAlreadyOccupied(int row, int column) {
        if (board[row][column] == null) {
            return false;
        }
        return true;
    }

    /**
     * Gibt true zurueck wenn das Feld der angegebenen Koordinate bereits besetzt
     * ist, sont false.
     * 
     * @param coord Koordinate
     * @return true wenn das Feld besetzt ist, sonst false
     */
    public boolean isAlreadyOccupied(Coordinate coord) {
        return isAlreadyOccupied(coord.getRow(), coord.getColumn());
    }

    /**
     * Gibt die Figur an einer bestimmten Koordinate zurueck, falls das Feld leer
     * ist null.
     * 
     * @param coord Koordinate
     * @return Figur einer bestimmten Koordinate, null falls das Feld leer ist.
     */
    public Token getToken(Coordinate coord) {
        return board[coord.getRow()][coord.getColumn()];
    }

    /**
     * Gibt das Brett als Token Matrix zurueck.
     * 
     * @return Brett
     */
    public Token[][] getBoard() {
        return board;
    }
}
